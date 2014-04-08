import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


/**
 * This implements a DFSMaze Runner Algorithm modified from the class
 * RandomMazeRunner to suit this algorithm in its use of stack
 *
 * @author Sunil Gautam (Student No. 12312253)
 * @Version 01/04/2014 18:40
 */
public class DFSMazeRunner<MC extends MazeCell> extends MazeRunner<MC> {

	private Stack<MC> stack; //stackited nodes for DFSalgorithm
	private LinkedList<MC> sol; //Recorded nodes for solution path
	//used the deque data structure for easier printing of solution path

	public DFSMazeRunner() //Constructor to initilise: stack & sol
	{
		stack=new Stack<MC>();
		sol = new LinkedList<MC>();
	}

	/**
	 * Tries to find the solution to the given maze by the DFSAlgorithm
	 * Steps in DFSAlgorithm:
	 * 1. Start at initial cell. 
	 * 2. Put all stackited neighbours into a stack.
	 * 3. Pull from the stack
	 * 4. This is now the node to check the neighbours of and repeat steps 2 & 3 until the donut is found
	 *
	 * @param Maze<MC> maze		The maze to solve.
	 * @param PrintWriter writer	The print writer on which to output the maze solution. (For Testing also)
	 */

	public void solveMaze(Maze<MC> maze, PrintWriter writer) 
	{

		int cellsExpanded = 0;
		MC curCell = maze.getStart();
		stack.push(curCell);
		curCell.setState(MazeCell.CellState.visited);
		
		 while(!stack.empty())
	        {
			 cellsExpanded++;
			    curCell = stack.pop();
			    sol.add(curCell);
	            //System.out.println(curCell + " has been visited.");

	            // Get this vertex's list of previousConnections (starting at the end of the list).
	            Iterator<MC> list = maze.getNeighbors(curCell);
	           
	            // Loop through the list backwards.  This is done so destinations will come out in numerical order.
	            while(list.hasNext())
	            {
	            	MC listItr = list.next();
	                MC destination = listItr;

	                // If the node has not already been visited, add it to the stack for processing.
	                if(destination.getState()!=MazeCell.CellState.visited)
	                {
	                	destination.setState(MazeCell.CellState.visited);
	                    stack.push(destination);
	                }
	                if(curCell==maze.getDonut()){
	                	//System.out.println("found=== "+curCell + "==" + maze.getDonut());
	                	print(curCell,maze,writer,cellsExpanded);//////
	                	break;
	                }
	            }

	        }
		
	}

	private void print(MC curCell, Maze<MC> maze, PrintWriter writer,int cellsExpanded) {
		//Output string for solution path
		int pathLength = 0; //Path length for output
		String solutionString = "";
		
		writer.print("DFS SolutionPath: "); //Puts this initial string into writer for output

		while (sol.getLast() != maze.getStart()) //Iterates through the solution deque, end is start of maze
		{
			MC currentCell = sol.getLast();
			sol.removeLast();
			MC possibleNeighbour = sol.getLast();///////////

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
