import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * This is the launcher that runs BFSMazeRunner
 * Modified from the MazeRunnerLauncher
 * @ModificationAuthor Cian Cronin (Student No. 12310411)
 */
public class BFSMazeRunnerLauncher {

	/**
	 * Parse the command line and instantiate the maze and maze
	 * runner and then run the maze runner on the maze.
	 */
	public static void main(String[] args) {
		boolean useVisualizer = false;
		boolean useTracer = false;
		long updateInterval = 500;
		MazeRunner<SquareCell> runner = null;

		// Check basic argument validity and print usage information.
		if(args.length < 1) {
			printUsage();
			return;
		}

		int i=0;

		if( args[i].equals("-r") ) {
			i++;
			runner = new BFSMazeRunner<SquareCell>();
		}

		// Simple parsing of the parameters.
		if( args[i].equals("-v") ) {
			i++;
			useVisualizer = true;
		}

		if(i>= args.length) {
			printUsage();
			return;
		}

		if( args[i].equals("-t") ) {
			i++;
			useTracer = true;
		}

		if(i>= args.length) {
			printUsage();
			return;
		}

		//***use this if you make the visualizer (as part of extra credit)
		//this just sets an interval for the visualizer to pause between moving
		//from one maze cell to the next
		if( args[i].equals("-p") ) {

			i++;

			if(i>= args.length) {
				printUsage();
				return;
			}

			try {
				updateInterval = Long.parseLong(args[i]);
			} catch (NumberFormatException noe) {
				System.err.println("Bad Pause Interval. Defaulting to " + updateInterval);
			}

			i++;

			if( useVisualizer == false ) {
				System.err.println("-v not set. Ignoring -p options");
			}
		}

		if(i>= args.length) {
			printUsage();
			return;
		}


		/* Default to BFS maze runner if no runner option
		 * is given
		 */
		if( runner == null ) {
			runner = new BFSMazeRunner<SquareCell>();
		}


		SquareCellMaze maze = null;

		try {

			// Create a maze from the given filename.
			maze = SquareCellMaze.SquareCellMazeFactory.parseMaze(args[i]);

			// Attach the visualizer if -v flag given
			if(useVisualizer) {
				//*** add your code here if doing extra credit visualizer
			}

			// Attach the tracer if -t flag given
			if(useTracer) {
				PrintWriter debugWriter = new PrintWriter(
					new OutputStreamWriter(System.err), true);

				maze.addMazeChangeListener(
					new SquareCellMazeTracer(debugWriter));
			}

			// This is why Java I/O sucks sometimes.
			PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(System.out)), true);

			// Solve the mazer
			runner.solveMaze(maze, writer);

			// ensure the writer is closed so it flushes the output.
			writer.close();

		} catch (FileNotFoundException fnfe) {
			System.err.println("Could not find file " + args[i]);
		} catch (IOException ioe) {
			System.err.println("Error reading file " + args[i]);
		}

	}

	/**
	 * Prints the Usage to standard error.
	 */
	private static void printUsage() {
		System.err.println("Usage: java BFSMazeRunnerLauncher [-r] [-v] [-t] [-p milliseconds] <mazefile>");
		System.err.println("\t-r -- Use the BFSMazeRunner (default if no other runner specified)\n");
		System.err.println("\t-v -- visualizer maze graphically");
		System.err.println("\t-t -- Output tracing information");
		System.err.println("\t-p -- wait between moving to each cell for visualizer. Use with -v.");
		System.err.println("");
		System.err.println("Flags must be given in *order*");
	}
}