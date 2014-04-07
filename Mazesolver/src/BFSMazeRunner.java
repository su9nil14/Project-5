import java.util.LinkedList;
import java.util.Queue;
import java.util.Deque;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * This implements a BFS Maze Runner Algorithm modified from the class
 * RandomMazeRunner to suit this algorithm in its use of queue
 *
 * @author Cian Cronin (Student No. 12310411)
 * @Version 01/04/2014 18:40
 */
public class BFSMazeRunner<MC extends MazeCell> extends MazeRunner<MC> {
	
	private Queue<MC> unvis; //Unvisited nodes for BFS algorithm
	private Deque<MC> sol; //Recorded nodes for solution path
			//used the deque data structure for easier printing of solution path
	
	public BFSMazeRunner() //Constructor to initilise: unvis & sol
	{
		unvis = new LinkedList<MC>();
		sol = new LinkedList<MC>();
	}

	/**
	 * Tries to find the solution to the given maze by the BFS Algorithm
	 * Steps in BFS Algorithm:
	 * 1. Start at initial cell. 
	 * 2. Put all unvisited neighbours into a queue.
	 * 3. Pull from the queue
	 * 4. This is now the node to check the neighbours of and repeat steps 2 & 3 until the donut is found
	 *
	 * @param Maze<MC> maze		The maze to solve.
	 * @param PrintWriter writer	The print writer on which to output the maze solution. (For Testing also)
	 */
	public void solveMaze(Maze<MC> maze, PrintWriter writer) 
	{
		
		int cellsExpanded = 0;
		unvis.add(maze.getStart());
		
		maze.getStart().setState(MazeCell.CellState.visitInProgress);
		
		MC current = maze.getStart();

		while (!current.isDonut()) {
			cellsExpanded++;
			
			current = (MC) unvis.peek(); //Set the current cell to head of queue
			
			sol.add(unvis.peek()); //Add the cell to be removed to sol queue to print solution path.
			
			if(unvis.peek()!=null) //If the unvisited cells is not empty
				unvis.remove(); //Remove cell from unvis queue
			else //If there is no solution path write and return
			{
				writer.print("No solution path found");
				return;
			}
			
			if (maze.getDonut() == current) //If the current cell is the donut 
			{
				//Do Nothing as solution is found
			} 
			else //The current cell is not the solution, continue search
			{ 
				Iterator<MC> i = maze.getNeighbors(current);
				MC next = null;
				
				while (i.hasNext()) 
				{
					next = i.next();
					if (next.getState() == MazeCell.CellState.notVisited) 
					{
						//Neighbour found, set state to visit in progress and add to unvis
						next.setState(MazeCell.CellState.visitInProgress);
						unvis.add(next);
					}
				}
				// Set current cell's state to visited, prepared to be popped.
				current.setState(MazeCell.CellState.visited);
			}
		}
		
		//Output string for solution path
		int pathLength = 0; //Path length for output
		String solutionString = "";
		
		writer.print("BFS Solution Path: "); //Puts this initial string into writer for output

		while (sol.getLast() != maze.getStart()) //Iterates through the solution deque, end is start of maze
		{
			MC currentCell = sol.getLast();
			sol.removeLast();
			MC possibleNeighbour = sol.getLast();

			if (isAdjacentNeighbour(currentCell, possibleNeighbour, maze)) 
			{
				
				if (currentCell.isDonut()) //If cell is donut do not print space behind it
					solutionString =  currentCell + solutionString;
				else //o/w add cell and space as deque
					solutionString = currentCell + " " + solutionString;
				
				pathLength++;
			} 
			else //o/w not a neighbour, remove previous from deque, re-add to last in queue
			{
				sol.removeLast();				
				sol.addLast(currentCell);
				

			}
		}
		
		solutionString = maze.getStart() + " " + solutionString; //Add start of maze to writer

		writer.print(solutionString + "\n"); //The solution path string printed for tests
		writer.print(pathLength + "\n"); //Length of the path printed for tests
		writer.print(cellsExpanded); //Cells expanded printed for tests
	}

	
	/**
	 * Method for isAdjacentNeighbour
	 * 
	 * @param MC CurrentCell - the current cell which you will find the neighbour of maze
	 * @param Maze<MC> maze - the maze in which the cells are contained.
	 * @param MC nextCell - the cell to check if it is a neighbour of the currentCell
	 * @return true if adjacentCell is neighbour of currentCell, false o/w
	 */
	private boolean isAdjacentNeighbour(MC CurrentCell, MC adjacentCell, Maze<MC> maze) {
		Iterator<MC> iterator = maze.getNeighbors(CurrentCell);
		while (iterator.hasNext())
		{
			MC neighbour = iterator.next();
			
			//If the adjacent cell is a neighbour of CurrentCell, return true
			if (adjacentCell == neighbour) return true;
			
		}
		
		return false; //Return false if all adjacent cells checked and no neighbours found
	}

}
