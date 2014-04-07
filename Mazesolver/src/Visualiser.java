import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Visualiser<MC extends MazeCell> extends JFrame implements MazeChangeListener {

	private static final Color VISITED_COLOR = new Color(192, 228, 255);
	private JLabel visitedCountLabel;	
	private JFrame j;

	public Visualiser(MazeRunner<SquareCell> runner) {
		j=new JFrame();
		j.setTitle("Maze Visualiser");
		j.setSize(500, 500);
		j.setLocationRelativeTo(null);
		j.setBackground(Color.BLACK);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		visitedCountLabel = new JLabel("Visited: 0");
		j.setVisible(true);
	}

	public void stateChangeEvent() {

		repaint();
	}

	@Override
	public void cellStateChangeEvent(MazeCell cell){
		// TODO Auto-generated method stub

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
