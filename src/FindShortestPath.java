/**
 * @author Lukas Bozinov
 *
 */
public class FindShortestPath {

	private static DLPriorityQueue<Hexagon> hexQueue = new DLPriorityQueue<Hexagon>(); // Initializes a DLPriorityQueue.
																						// Static so that it can be
																						// statically referenced
																						// throughout the code.
	private static Dungeon dungeon; // Declares a static Dungeon named dungeon. This is used later on for storing
									// the file name of the .txt file I'm loading.
	private static int pathLength = 0; // Initializes a static int variable to keep track of the length of the final
										// path (shortest path).
	private static boolean exitFound = false; // Initializes a boolean variable that checks whether or not an exit was
												// found in the chambers or not.

	/**
	 * This is the main method. The only argument it takes is the command-line
	 * argument (as explained in the PDF file given to us).
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// enclose everything in a try/catch statement for any possible errors
		try {
			// check to see whether or not the file is given or not
			if (args.length < 1)
				throw new Exception("No input file specified"); // if not, throw an exception that will be caught lower
																// down in the code

			String dungeonFileName = args[0]; // assign the filename to a string variable
			dungeon = new Dungeon(dungeonFileName); // assign the string as the only parameter in the creation of the
													// static dungeon object

			Hexagon starter = dungeon.getStart(); // get the starting hexagon of the dungeon

			hexQueue.add(starter, 0); // add the starting hexagon to the DLPriorityQueue (with priority zero)
			starter.markEnqueued(); // mark the starting hexagon as being in the queue

			// while the DLPriorityQueue has items in it, and the exit has yet to be found,
			while (hexQueue.isEmpty() == false && exitFound == false) {
				Hexagon currChamber = hexQueue.removeMin(); // remove the front hexagon and store it in currChamber
				currChamber.markDequeued(); // dequeue the currChamber after it's removed from the queue

				// if the chamber is marked as the exit chamber
				if (currChamber.isExit()) {
					exitFound = true; // mark the exit being found as true
					pathLength = currChamber.getDistanceToStart() + 1; // calculate shortest path length
					break; // break out of the while loop and skip straight to the print statements
				}

				// if the current chamber is a dragon chamber, or is adjacent to a dragon
				// chamber
				if (currChamber.isDragon() == true || adjacentDragon(currChamber) == true) {
					continue; // skip back to the top of the while loop
				} else { // if not
					considerNeighbour(currChamber); // consider all possible neighbour nodes that are not null and not a
													// wall
				}
			}

		} catch (Exception e) { // catch any exception that might be thrown
			System.out.println("Exception caught!"); // declare an exception was caught
			e.printStackTrace(); // print out what happened
		}

		if (exitFound == true) // if the exit was found
			System.out.println("Path of length " + pathLength + " found"); // print the final string with path length
																			// included
		else // otherwise
			System.out.println("No path found"); // declare that there was no path that was traversable (at least
													// without being burnt to a crisp!)

	}

	/**
	 * This private helper method determines whether or not the hexagons adjacent to
	 * a current hexagon (denoted "chamber") are in the way of a dragon or not. If
	 * they are, return true. If not, return false.
	 * 
	 * @param chamber
	 * @return boolean true/false
	 */
	private static boolean adjacentDragon(Hexagon chamber) {
		for (int i = 0; i < 6; i++) { // Since a hexagon can only have up to 6 adjacent hexagons, the index of the for
										// loop is 6
			if (chamber.getNeighbour(i) != null && chamber.getNeighbour(i).isDragon()) // if the neighbouring hexagon
																						// isn't null and the neighbour
																						// isn't a dragon either
				return true; // true
		}
		return false; // false if all else fails
	}

	/**
	 * This private helper method just cleans up the first portion of code a bit. It
	 * takes in a parameter, chamber, (can be called the "current" chamber), and
	 * considers every neighbouring hexagon before making the best decision on where
	 * next to pave a path to the exit.
	 * 
	 * @param chamber
	 */
	private static void considerNeighbour(Hexagon chamber) {
		boolean distModified = false; //checks if the distance of the neighbour's distance to start was modified

		for (int i = 0; i < 6; i++) {// Since a hexagon can only have up to 6 adjacent hexagons, the index of the for loop is 6

			//if the current chamber's neighbour isn't null and isn't a wall
			if (chamber.getNeighbour(i) != null && chamber.getNeighbour(i).isWall() == false
					&& chamber.getNeighbour(i).isMarkedDequeued() == false) {
				
				Hexagon neighbour = chamber.getNeighbour(i); //assign the neighbour of the chamber to a Hexagon object named "neighbour"
				int distCurrToStart = 1 + chamber.getDistanceToStart(); //assign the distance from the current hexagon to the start to distCurrToStart (referred to as D in the PDF file assigned to me)

				//if the neighbouring hexagon's distance to the beginning of the chambers is greater than the distance from the current hexagon to the start
				if (neighbour.getDistanceToStart() > distCurrToStart) {
					neighbour.setDistanceToStart(distCurrToStart); //modify the neighbouring hexagon's distance to the beginning of the chambers
					distModified = true; //set the modified boolean to true
					neighbour.setPredecessor(chamber); //set the neighbour's predecessor to be able to trace back the path when the program finishes

				}

				//f the neighbouring hexagon is marked as enqueued, and the neighbouring hexagon's distance to the beginning of the chambers was modified
				if (neighbour.isMarkedEnqueued() == true && distModified == true) {
					
					//update the priority of the neighbour with a new priority combining two distances
					hexQueue.updatePriority(neighbour,
							neighbour.getDistanceToStart() + neighbour.getDistanceToExit(dungeon));

				} else if (neighbour.isMarkedEnqueued() == false) { //otherwise, if the neighbour is not marked as enqueued

					hexQueue.add(neighbour, neighbour.getDistanceToStart() + neighbour.getDistanceToExit(dungeon)); //add the neighbour to the queue
					neighbour.markEnqueued(); //mark the neighbour as being enqueued
				}

			}
		}
	}

}
