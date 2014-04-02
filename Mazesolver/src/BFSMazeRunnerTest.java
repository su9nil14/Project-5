import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This JUnit test class invokes the BFSMazeRunner for a number of mazes
 * in the initial files for project
 */
@RunWith(Parameterized.class)
public class BFSMazeRunnerTest {

  private String mazeFile;
  private String expectedOutput;

  public BFSMazeRunnerTest(String inputFile, String expectedOutput) {
	  this.mazeFile = inputFile;
	  this.expectedOutput = expectedOutput;
  }

  @Parameters //Expected results from BFS testing against from BFSMazeRunner
  public static Iterable<String[]> inputFilesAndExpectedResults() {
    return Arrays.asList(new String[][] {
      {"maze1.txt","BFS Solution Path: (0,0) (1,0) (2,0) (2,1) (2,2)\n4\n12"},
      {"maze2.txt","BFS Solution Path: (0,0) (1,0) (2,0) (3,0) (3,1) (3,2) (4,2) (4,3) (4,4) (4,5) (4,6)\n10\n29"},
      {"maze3.txt","BFS Solution Path: (0,0) (0,1) (0,2) (0,3) (1,3) (1,4) (1,5) (1,6) (0,6) (0,7) (0,8) (1,8) (1,7)\n12\n44"},
      {"maze4.txt","BFS Solution Path: (0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (0,6) (0,7) (0,8) (0,9) (0,10) (0,11) (0,12) (0,13) "
      		+ "(1,13) (2,13) (3,13) (4,13) (4,12) (5,12) (6,12) (6,11) (6,10) (7,10) (7,9) (7,8) (7,7) (7,6) (7,5) (7,4) (7,3) (7,2) "
      		+ "(6,2) (5,2) (5,1) (4,1) (3,1) (3,0)\n37\n225"},
      {"nocycles1.txt","BFS Solution Path: (7,0) (8,0) (8,1) (8,2) (7,2) (6,2) (5,2) (5,3) (4,3) (3,3) (2,3) (1,3) (1,4) (2,4) (3,4) "
      		+ "(3,5) (3,6) (3,7) (3,8) (4,8) (4,9) (3,9) (2,9) (1,9) (1,8) (0,8) (0,9)\n26\n100"},
      {"nocycles2.txt","BFS Solution Path: (14,19) (14,18) (13,18) (13,17) (13,16) (12,16) (12,15) (12,14) (12,13) (13,13) (14,13) "
      		+ "(14,12) (13,12) (12,12) (11,12) (10,12) (9,12) (9,11) (9,10) (8,10) (8,9) (8,8) (8,7) (8,6) (8,5) (7,5) (7,6) (6,6) "
      		+ "(6,7) (5,7) (5,8) (5,9) (5,10) (5,11) (4,11) (3,11) (3,10) (2,10) (2,9) (1,9) (0,9) (0,8) (1,8) (2,8) (2,7) (3,7) (3,6) "
      		+ "(3,5) (3,4) (2,4) (1,4) (1,3) (0,3) (0,2) (0,1) (1,1) (1,0) (0,0)\n57\n400"},
      {"nopath1.txt","No solution path found"}, //Changed the lines for unvis queue in BFSMazeRunner class 
      {"nopath2.txt","No solution path found"}, //to interact for these specific circumstances
      {"obstacles1.txt","BFS Solution Path: (0,0) (1,0) (2,0) (3,0) (3,1) (3,2) (3,3)\n6\n15"},
      {"obstacles2.txt","BFS Solution Path: (2,2) (3,2) (3,3) (3,4) (3,5) (4,5) (4,6) (4,7) (4,8) (4,9) (4,10) (4,11) (5,11) (6,11) "
      		+ "(7,11) (8,11) (9,11) (10,11) (10,12) (10,13) (10,14) (10,15) (11,15) (12,15) (13,15)\n24\n258"},
      {"bigmaze1.txt","BFS Solution Path: (0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (0,6) (0,7) (0,8) (0,9) (0,10) (0,11) (0,12) (0,13) "
      		+ "(1,13) (2,13) (3,13) (4,13) (4,12) (5,12) (6,12) (6,11) (6,10) (5,10) (5,9) (6,9) (6,8) (5,8) (5,7) (5,6) (6,6) (7,6) "
      		+ "(7,5) (7,4) (7,3) (7,2) (6,2) (5,2) (5,1) (4,1) (3,1) (3,0)\n41\n241"},
      {"bigmaze2.txt","BFS Solution Path: (0,0) (0,1) (0,2) (0,3) (0,4) (1,4) (2,4) (2,5) (2,6) (2,7) (3,7) (3,8) (4,8) (5,8) (6,8) "
      		+ "(7,8) (7,9) (7,10) (7,11) (8,11) (8,12) (8,13) (8,14) (7,14) (7,15) (7,16) (7,17) (8,17) (8,18) (8,19) (8,20) (9,20) "
      		+ "(9,21) (9,22) (9,23) (9,24) (9,25) (9,26) (8,26) (8,27) (8,28) (9,28) (9,29)\n42\n299"},
      {"bigmaze3.txt","BFS Solution Path: (0,9) (0,8) (0,7) (0,6) (1,6) (2,6) (3,6) (4,6) (4,5) (5,5) (6,5) (7,5) (8,5) (8,4) (7,4) "
      		+ "(7,3) (8,3) (9,3)\n17\n92"},
      {"bigmaze4.txt","BFS Solution Path: (2,0) (3,0) (3,1) (3,2) (4,2) (4,3) (4,4) (4,5) (5,5) (6,5) (6,6) (7,6) (7,7) (8,7) (8,8) "
      		+ "(9,8) (9,9) (8,9) (8,10) (9,10)\n19\n109"},
      {"bigmaze5.txt","BFS Solution Path: (5,17) (4,17) (3,17) (3,16) (3,15) (3,14) (3,13) (3,12) (3,11) (3,10) (3,9) (3,8) (2,8) "
      		+ "(2,7) (2,6) (1,6) (1,5) (1,4) (1,3) (1,2) (1,1) (0,1)\n21\n108"},
      {"bigmaze6.txt","BFS Solution Path: (0,0) (1,0) (1,1) (1,2) (1,3) (1,4) (1,5) (2,5) (3,5) (3,4) (4,4) (4,5) (4,6) (5,6) (6,6) "
      		+ "(6,5)\n15\n52"},
      {"bigmaze7.txt","BFS Solution Path: (0,5) (0,4) (1,4) (1,3) (1,2) (2,2) (2,1) (3,1) (4,1) (5,1) (6,1) (7,1) (8,1) (9,1) (10,1) "
      		+ "(10,2) (10,3) (10,4) (10,5) (11,5) (11,6) (11,7) (12,7) (12,6) (13,6) (13,7) (14,7) (14,6) (15,6) (16,6) (17,6) (18,6) "
      		+ "(18,7) (19,7)\n33\n121"},
      {"bigmaze9.txt","BFS Solution Path: (0,0) (0,1) (0,2) (1,2) (1,3) (1,4) (0,4) (0,5) (0,6) (1,6) (2,6) (2,5) (3,5) (3,6) (3,7) "
      		+ "(3,8) (3,9) (4,9) (5,9) (5,10) (6,10) (6,9) (7,9) (7,8) (7,7) (8,7) (9,7) (9,8) (10,8) (11,8) (11,9) (11,10) (12,10) "
      		+ "(12,11) (13,11) (13,10) (14,10) (14,11) (14,12) (14,13) (15,13) (15,14) (15,15) (16,15) (17,15) (18,15) (18,16) (19,16) "
      		+ "(19,17) (19,18) (20,18) (21,18) (21,19) (22,19) (23,19) (24,19) (24,20) (25,20) (25,21) (25,22) (26,22) (27,22) (28,22) "
      		+ "(28,23) (29,23) (30,23) (31,23) (31,24) (32,24) (32,25) (32,26) (32,27) (31,27) (30,27) (29,27) (29,28) (29,29) (30,29) "
      		+ "(30,30) (31,30) (32,30) (33,30) (34,30) (34,31) (35,31) (36,31) (37,31) (37,32) (37,33) (37,34) (38,34) (38,35) (38,36) "
      		+ "(38,37) (39,37) (39,38) (39,39)\n96\n1589"}
    });
  }


  //~ Test the BFSMazeRunner ..........................................................
  @Test
  public void testRandomMazeRunner()
  {

		MazeRunner<SquareCell> runner = new BFSMazeRunner<SquareCell>();
		SquareCellMaze maze = null;
	  try {
      // Create a maze from the given filename (passed as a parameter to the
      // constructor of the JUnit test class, and stored in mazeFile).
	  	maze = SquareCellMaze.SquareCellMazeFactory.parseMaze(mazeFile);

      StringWriter output = new StringWriter();
	  	PrintWriter writer = new PrintWriter(output, true);

	  	// Solve the maze
	  	runner.solveMaze(maze, writer);

	  	// ensure the writer is closed so it flushes the output.
	  	writer.close();

      // Changed now so that it is successful with the expectedOutput for BFSMazeRunner
      assertEquals("BFSMazeRunner solved maze " + mazeFile, expectedOutput, output.toString());

	  } catch (FileNotFoundException fnfe) {
	  	System.err.println("Could not find file " + mazeFile);
	  } catch (IOException ioe) {
	  	System.err.println("Error reading file " + mazeFile);
	  }
	}

  //~ A main method to run the above tests ..........................................................
  //  from the command line, copy hamcrest-core-1.3.jar and junit-4.11.jar to the current directory, then:
  //  compile: javac -cp junit-4.11.jar:. MazeRunnerTest.java 
  //  run: java -cp hamcrest-core-1.3.jar:junit-4.11.jar:. MazeRunnerTest
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(BFSMazeRunnerTest.class);
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
  }
}
