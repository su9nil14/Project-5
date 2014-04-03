import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Stack;



/**
 * This implementation uses a nonrecursive version of depth-first search
 * using stack.
 *
 * @author Sunil Gautam (Student No. 12312253) + credit to Cian cronin for printing the solution path method
 * @Version 01/04/2014 18:40
 */
public class DFSMazeRunner<MC extends MazeCell> extends MazeRunner<MC> {
	
	private class SolutionPathInfo {
		
		public MC nextInSolution;
	}
	
	
	private Stack<MC> stack,sol;
	
	public DFSMazeRunner()
	{
		stack = new Stack<MC>();
		sol = new Stack<MC>();

	}

	/**
	 * Tries to find the solution to the given maze by the DFS Algorithm

	 * @param Maze<MC> maze		The maze to solve.
	 * @param PrintWriter writer	The print writer on which to output the maze solution. (For Testing also)
	 */
	@SuppressWarnings("unchecked")
	public void solveMaze(Maze<MC> maze, PrintWriter writer) 
	{
		int cellsExpanded = 0;
		MC curCell = maze.getStart();		
		stack.push(curCell);
		curCell.setState(MazeCell.CellState.visited);
		SolutionPathInfo info = getSolutionPathInfo(curCell);
		
		while(!stack.isEmpty()){
			cellsExpanded++;
			sol.push(stack.peek());	//for the test
			
			MC n = stack.pop();
			//System.out.print(n.toString() +  " ");
			Iterator<MC> i = maze.getNeighbors(n);
			MC next = null;
					
			while(i.hasNext()){
				next = i.next();
				if((next.getState()) == MazeCell.CellState.notVisited){
					
					next.setState(MazeCell.CellState.visited);
					stack.push(next);
				}
			}
		}
	
		//Output string for solution path
				int pathLength = 0; //Path length for output
				String solutionString = "";
				
				writer.print("DFS Solution Path: "); //Puts this initial string into writer for output

				while (sol.lastElement() != maze.getStart()) //Iterates through the solution deque, end is start of maze
				{
					MC currentCell = sol.lastElement();
					sol.remove(sol.lastElement());
					MC possibleNeighbour = sol.lastElement();

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
						sol.remove(sol.lastElement());						
						
					}
					curCell = info.nextInSolution;
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
	
	/**
	 * A helper function that returns a pointer to the SolutionPathInfo
	 * associated with a given cell.  This funciton takes care of
	 * creating and associating an instance of SolutionPathInfo if
	 * one does not already exist.
	 *
	 * @param curCell	The cell from which we retrieve the
	 * 	SolutionPathInfo closure.
	 */
	
	@SuppressWarnings("unchecked")
	private SolutionPathInfo getSolutionPathInfo(MazeCell curCell)
	{
		if(null == curCell.getExtraInfo()) {
			curCell.setExtraInfo( new SolutionPathInfo() );
		}

		return (SolutionPathInfo)curCell.getExtraInfo();
	}
	

}
