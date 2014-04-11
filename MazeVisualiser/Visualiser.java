import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class Visualiser<MC extends MazeCell> extends JFrame implements MazeChangeListener {


	/**
	 * each cells representing an individual maze cell
	 */
	private JLabel[][] cells;
	/**
	 * constructor
	 * @param title the title of the frame
	 * @param maze the maze this frame renders
	 */
	public Visualiser(SquareCellMaze maze){
		super("Maze Runner Visualiser");
		this.setContentPane(new JPanel(new GridLayout(maze.getHeight(), maze.getWidth())));
		
		// create the array of labels to represent the maze cells
		this.cells = new JLabel[maze.getHeight()][maze.getWidth()];

		
		// populate labels with cells
		for (int i = 0; i < maze.getHeight(); i++){
			for (int j = 0; j < maze.getWidth(); j++){
				
				MazeCell cell = maze.getCell(i, j);
				
				// create the label
				if (cell.isStart())
					cells[i][j] = new JLabel("  X  ");//start
				else if (cell.isDonut())
					cells[i][j] = new JLabel("  D  ");//donut(finish)
				else
					cells[i][j] = new JLabel("     ");
				
				// center any internal components
				cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				cells[i][j].setVerticalAlignment(SwingConstants.CENTER);
				
				// set the background color
				cells[i][j].setOpaque(true);
				cells[i][j].setBackground(Color.YELLOW);//BACKGROUND
				if (cell.isStart()) {
					cells[i][j].setBackground(Color.BLUE);//VISITED
				} else if (cell.isDonut()) {
					cells[i][j].setBackground(Color.RED);//FINISH
				} else {
					cells[i][j].setBackground(Color.GREEN);//UNVISITED
				}
				
				// create the border / walls
				int top = ((SquareCell) cell).isWall(Direction.north)? 1 : 0;
				int left = ((SquareCell) cell).isWall(Direction.west)? 1 : 0;
				int bottom = ((SquareCell) cell).isWall(Direction.south)? 1 : 0;
				int right = ((SquareCell) cell).isWall(Direction.east)? 1 : 0;
				
				cells[i][j].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.WHITE));
				
				// add the label to the frame
				this.getContentPane().add(cells[i][j]);
			}
		}
	}
	
	/**
	 * update the state of the given cell
	 * @param cell the cell to update
	 */
	@Override
	public void cellStateChangeEvent(MazeCell cell) {
		
		JLabel label = cells[((SquareCell) cell).getRow()][((SquareCell) cell).getCol()];

		// update the background color
		if (cell.getState() == MazeCell.CellState.notVisited){
			label.setBackground(Color.GREEN);
		} else if (cell.getState() == MazeCell.CellState.visitInProgress){
			label.setBackground(Color.GRAY);
		} else if (cell.getState() == MazeCell.CellState.visited){
			label.setBackground(Color.BLUE);
			
		}  else if (cell.getState() == MazeCell.CellState.solutionPath){
			label.setBackground(Color.BLACK);
		}
		
	}

	@Override
	public void stateChangeEvent() {
		
	}
}
