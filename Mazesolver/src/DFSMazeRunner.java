import java.io.PrintWriter;
import java.util.Iterator;
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
	private Stack<MC> sol; //Recorded nodes for solution path
			//used the deque data structure for easier printing of solution path
	
	public DFSMazeRunner() //Constructor to initilise: stack & sol
	{
		stack = new Stack<MC>();
		sol = new Stack<MC>();
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
		//curCell.setState(MazeCell.CellState.visited);		
		//System.out.println(curCell);

		while (!stack.isEmpty()) {
			cellsExpanded++;
			
			curCell = stack.peek(); //Set the current cell to top of stack
			//System.out.println(curCell);
			sol.push(curCell); //Add the cell to be removed to sol stack to print solution path.
			
			if(stack.peek()!=null) //If cells on the top of the stack is not empty
				stack.pop(); //Remove cell from stack 
			else //If there is no solution path write and return
			{
				writer.print("No solution path found");
				return;
			}
			
			if (maze.getDonut() == curCell) //If the current cell is the donut 
			{
				//System.out.println("maze solved"+maze.getDonut());
				break;

			} 
			else //The current cell is not the solution, continue search
			{ 
				Iterator<MC> i = maze.getNeighbors(curCell);
				MC next = null;
				
				while (i.hasNext()) 
				{
					next = i.next();

					if (next.getState() == MazeCell.CellState.notVisited) 
					{
						
						//Neighbour found, set state to visit in progress and add to stack
						next.setState(MazeCell.CellState.visited);
						stack.push(next);
						//System.out.println(next);
											
					}
					
				}
				// Set current cell's state to visited, prepared to be popped.
				curCell.setState(MazeCell.CellState.visited);
				//stack.pop();
			}
			
		}
		
		//Output string for solution path
		int pathLength = 0; //Path length for output
		String solutionString = "";
		writer.print("DFSSolutionPath: "); //Puts this initial string into writer for output
		
		while (maze.getStart()!=sol.lastElement()) //Iterates through the solution deque, end is start of maze
		{
			MC currentCell = sol.lastElement();
			//MC last=sol.lastElement();
			sol.remove(currentCell);
			MC possibleNeighbour = sol.lastElement();

			if (isAdjacentNeighbour(currentCell, possibleNeighbour, maze)) 
			{
				
				if (currentCell.isDonut()) //If cell is donut do not print space behind it
					solutionString =  currentCell + solutionString;
				else //o/w add cell and space as deque
					solutionString = currentCell + " " + solutionString;
				
				pathLength++;
			} 
			else 
			{
				sol.remove(sol.lastElement());
				//sol.pop();
				Stack<MC> last=new Stack<MC>();
				last.push(currentCell);
				MC lastElement=last.lastElement();
				sol.push(lastElement);
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
