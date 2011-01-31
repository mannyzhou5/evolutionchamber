package com.fray.evo.ui.cli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.swing.Timer;

import com.fray.evo.EcReportable;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.ui.swingx.EcSwingXMain;

/**
 * Runs Evolution Chamber from the command line.
 * 
 * @author mike.angstadt
 * 
 */
public class EcCommandLine {
	/**
	 * The detailed build order text that was last generated.
	 */
	private static String lastDetailed;

	/**
	 * The YABOT build order text that was last generated.
	 */
	private static String lastYabot;

	/**
	 * The number of threads to use.
	 */
	private static int threads;

	/**
	 * Simulation will terminate when all threads reach this age. -1 to run
	 * forever.
	 */
	private static int maxAge;

	/**
	 * Simulation will terminate when it has run for this many minutes. -1 to run
	 * forever.
	 */
	private static int timeLimit;

	/**
	 * True to only output the last build order when the simulation ends.
	 */
	private static boolean onlyOutputFinal;

	/**
	 * True to also print the YABOT string.
	 */
	private static boolean printYabot;

	/**
	 * The waypoint goals.
	 */
	private static InputFile inputFile;

	/**
	 * The time the simulation began.
	 */
	private static long startTime;

	/**
	 * True to stop the simulation.
	 */
	private static boolean stop = false;

	public static void main(String args[]) throws Exception {
		getArguments(args);

		final EvolutionChamber ec = new EvolutionChamber();
		ec.setDestination(inputFile.getDestination());
		ec.setThreads(threads);
		ec.setReportInterface(new EcReportable() {
			@Override
			public void threadScore(int threadIndex, String output) {
			}

			@Override
			public synchronized void bestScore(EcState finalState, int intValue, String detailedText, String simpleText, String yabotText) {
				//only output complete builds
				boolean isSatisfied = detailedText.contains(EcSwingXMain.messages.getString("Satisfied"));

				if (isSatisfied) {
					lastDetailed = detailedText;
					lastYabot = yabotText;
					if (!onlyOutputFinal) {
						System.out.println(detailedText);
						if (printYabot) {
							System.out.println(yabotText);
						}
					}
				}
			}
		});

		Timer ageTimer = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (maxAge > 0) {
					int oldThreads = 0;
					for (int i = 0; i < ec.getThreads(); i++) {
						int age = ec.getEvolutionsSinceDiscovery(i);
						if (age > maxAge) {
							oldThreads++;
						}
					}
					if (oldThreads == ec.getThreads()) {
						stop = true;
					}
				}

				if (timeLimit > 0) {
					double runningMinutes = (System.currentTimeMillis() - startTime) / 1000.0 / 60.0;
					if (runningMinutes > timeLimit) {
						stop = true;
					}
				}
			}
		});

		startTime = System.currentTimeMillis();
		ec.go();
		ageTimer.start();

		while (!stop) {
			Thread.sleep(200);
		}

		ageTimer.stop();
		ec.stopAllThreads();
		if (lastDetailed == null) {
			System.out.println("Unable to find a valid build.");
		} else {
			if (onlyOutputFinal) {
				System.out.println(lastDetailed);
				if (printYabot) {
					System.out.println(lastYabot);
				}
			}
		}
	}

	private static void getArguments(String args[]) throws Exception {
		//print help message
		if (args.length == 0 || findArg(args, "--help") >= 0) {
			final String br = "\n\t\t";
			System.out.println("Evolution Chamber v" + EvolutionChamber.VERSION + " Command-Line Interface");
			System.out.println();
			System.out.println("Usage:\njava -jar evolutionchamber-cli-version-" + EvolutionChamber.VERSION + ".jar [arguments] file");
			System.out.println();
			System.out.println("Example:\njava -jar evolutionchamber-cli-version-" + EvolutionChamber.VERSION + ".jar -a 200 -f input.txt");
			System.out.println();
			System.out.println("Generate sample input file:\njava -jar evolutionchamber-cli-version-" + EvolutionChamber.VERSION + ".jar -s > sample.txt");
			System.out.println();
			System.out.println("Arguments:");
			System.out.println("\t-t\tThe number of threads to spawn." + br + "(default: number of available processors)");
			System.out.println("\t-a\tThe max age that the fittest build of each thread can be before" + br + "the simulation is terminated (a good value is 200)." + br + "(default: keep running forever)");
			System.out.println("\t-i\tMinutes that the simulation should run for." + br + "(default: keep running forever)");
			System.out.println("\t-f\tOnly output the final build order" + br + "(default: output all build orders).");
			System.out.println("\t-y\tOutput YABOT string too.");
			System.out.println("\t-l\tSet the language (supported: en, ko, de, es, fr)" + br + "(defaults to English).");
			System.out.println("\t-s\tPrints a sample input file.");
			System.out.println("\t--help\tPrints this help message.");
			System.exit(0);
		}

		//print sample file
		if (findArg(args, "-s") >= 0) {
			BufferedReader in = new BufferedReader(new InputStreamReader(EcCommandLine.class.getResourceAsStream("sample.txt")));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			System.exit(0);
		}

		int index;

		index = findArg(args, "-t");
		threads = index >= 0 ? Integer.parseInt(args[index + 1]) : Runtime.getRuntime().availableProcessors();

		index = findArg(args, "-a");
		maxAge = index >= 0 ? Integer.parseInt(args[index + 1]) : -1;

		index = findArg(args, "-i");
		timeLimit = index >= 0 ? Integer.parseInt(args[index + 1]) : -1;

		index = findArg(args, "-f");
		onlyOutputFinal = index >= 0 && maxAge >= 0;

		index = findArg(args, "-y");
		printYabot = index >= 0;

		index = findArg(args, "-l");
		if (index >= 0) {
			EcSwingXMain.messages.changeLocale(new Locale(args[index + 1]));
		}

		File waypoints = new File(args[args.length - 1]);
		inputFile = new InputFile(waypoints);
	}

	/**
	 * Gets the position of an argument in the arguments list.
	 * 
	 * @param args the argument list
	 * @param arg the argument to search for
	 * @return the index of the argument or -1 if not found
	 */
	private static int findArg(String args[], String arg) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(arg)) {
				return i;
			}
		}
		return -1;
	}
}
