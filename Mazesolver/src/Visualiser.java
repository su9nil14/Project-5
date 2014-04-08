import java.awt.Color;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Visualiser<MC extends MazeCell> extends JFrame implements MazeChangeListener {

	private static final Color VISITED_COLOR = new Color(192, 228, 255);
	private JLabel visitedCountLabel;	
	private JFrame j;
	private MC mc;
	private int HEIGHT=500,WIDTH=500;

	public Visualiser(Maze<MC> maze,MC mc) {
		this.mc=mc;
		j = new JFrame("Maze solver");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setContentPane(new PuzzleFrameGUI<MC>(3,3,mc, maze));
		j.setSize(HEIGHT, WIDTH);
		j.show();  // make window visible
		j.setResizable(false);
	}

	public void stateChangeEvent() {

		repaint();
	}

	@Override
	public void cellStateChangeEvent(MazeCell cell){
		cell.notify();
		stateChangeEvent();
		
	}

	/**
	 * Returns the primary graphical component used to display this maze.
	 */
	public JFrame getGraphicalComponent() {
		return j;
	}

	/**
	 * Sets all squares in the maze as not visited.
	 */
	public void clearVisited(Maze<MC> maze,MC mc) {
		maze.resetCellState();
		updateMoves(mc);
	}

	// helper to set the graphical text label of how many squares are visited
	private void updateMoves(MC mc) {

		visitedCountLabel.setText("Visited: ");
		setVisited(mc);
	}
	public void setVisited(MC mc) {
		mc.setState(MazeCell.CellState.visited);
		updateMoves(mc);
	}


}
