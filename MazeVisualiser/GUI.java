import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// class SlidePuzzleGUI
// This class contains all the parts of the GUI interface
@SuppressWarnings("serial")
class PuzzleFrameGUI<MC extends MazeCell> extends JPanel {
	/**
	 * instance variables
	 */

	private GraphicsPanel puzzleGraphics;
	private SquareCell puzzleBoard;
	private boolean east,west,north,south;
	private int row,column;
	private boolean isStart,isDonut;
	private MazeRunner<SquareCell> runner;
	private Image backgroundImage,colImage;
	private String filename="C:/Users/su9nil14/Dropbox/workspace/Mazesolver/background.gif";
	private String filename2="C:/Users/su9nil14/Dropbox/workspace/Mazesolver/tab.gif";
	public static final int NUM_CELLS_PER_SIDE = 12;
	public static final int CELL_WIDTH = 40;
	public static final int OFFSET_X = 0;
	public static final int OFFSET_Y = 0;

	//constructor
	public PuzzleFrameGUI(int row,int column,MC maze,Maze<MC> maze2) {
		this.row=row;
		this.column=column;
		this.isStart=maze.isStart();
		this.isDonut=maze.isDonut();
		puzzleBoard= new SquareCell(row, column, isStart, isDonut, north, east, south, west);
		//--- Create a button.  Add a listener to it.
		JButton checkButton = new JButton("Solve?");
		checkButton.addActionListener(new Solve());

		// Create control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		controlPanel.add(checkButton);


		//Create graphics panel
		puzzleGraphics = new GraphicsPanel(row,column);

		//Set the layout and add the components
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(puzzleGraphics, BorderLayout.CENTER);
		try {
			backgroundImage = ImageIO.read(new File(filename));
			colImage = ImageIO.read(new File(filename2));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end constructor


	//class GraphicsPanel
	// This is defined inside the outer class so that
	// it can use the outer class instance variables.
	class GraphicsPanel extends JPanel implements MouseListener {

		private int ROWS;
		private int COLS;

		private static final int CELL_SIZE = 50; // Pixels
		private Font myFont;


		//constructor
		public GraphicsPanel(int row2,int column2) {
			ROWS=row;
			COLS=column;
			myFont = new Font("Arial", Font.BOLD, CELL_SIZE/2);
			this.setPreferredSize(
					new Dimension(CELL_SIZE * COLS, CELL_SIZE*ROWS));
			this.setBackground(Color.red);
			this.addMouseListener(this);  // Listen own mouse events.
		}//end constructor


		// method paintComponent
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, 500, 500, null);
			//g.drawImage(colImage, 0, 0, 5, 50, null);
			for (int i=0; i <= NUM_CELLS_PER_SIDE; i++) 
				g.drawLine(OFFSET_X, OFFSET_Y + i*CELL_WIDTH, 
						OFFSET_X + CELL_WIDTH*NUM_CELLS_PER_SIDE,
						OFFSET_Y + i*CELL_WIDTH);

			// vertical lines
			for (int i=0; i <= NUM_CELLS_PER_SIDE; i++) 
				g.drawLine(OFFSET_X + i*CELL_WIDTH, OFFSET_Y, 
						OFFSET_X + i*CELL_WIDTH,
						OFFSET_Y + CELL_WIDTH*NUM_CELLS_PER_SIDE);


		}//end paintComponent


		//listener mousePressed
		public void mousePressed(MouseEvent e) {
			//--- map x,y coordinates into a row and col.
			int col = e.getX()/CELL_SIZE;
			int row = e.getY()/CELL_SIZE;


			this.repaint();  // Show any updates to model.
		}//end mousePressed


		//ignore these events
		public void mouseClicked (MouseEvent e) {

		}
		public void mouseReleased(MouseEvent e) {

		}
		public void mouseEntered (MouseEvent e) {

		}
		public void mouseExited  (MouseEvent e) {

		}
	}//end class GraphicsPanel

	//inner class NewGameAction
	public class NewGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			puzzleBoard.broadcastChange();
			puzzleGraphics.repaint();
		}
	}//end inner class NewGameAction


	public class Solve implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			runner = new BFSMazeRunner<SquareCell>();
			if(puzzleBoard.isDonut()){
				JOptionPane.showMessageDialog(null, "This puzzle is solved");
			}
			else{
				JOptionPane.showMessageDialog(null, "This puzzle is not solveable");
			}       	
		}
	}	

}//end class PuzzleFrameGUI