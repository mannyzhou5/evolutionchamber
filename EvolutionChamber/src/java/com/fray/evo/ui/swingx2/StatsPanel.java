package com.fray.evo.ui.swingx2;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXLabel;

/**
 * Displays simulation statistics as a simulation is running.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class StatsPanel extends JPanel { //this must extend JPanel instead of JXPanel because JXPanel doesn't work right in a JScrollPanel
	/**
	 * The length of time that a simulation has been running for.
	 */
	private final JXLabel timeRunning;

	/**
	 * The total number of games played.
	 */
	private final JXLabel totalGamesPlayed;

	/**
	 * The maximum build order length.
	 */
	private final JXLabel maxBuildOrderLength;

	/**
	 * The stagnation limit.
	 */
	private final JXLabel stagnationLimit;

	/**
	 * The mutation rate.
	 */
	private final JXLabel mutationRate;

	/**
	 * The games played per second.
	 */
	private final JXLabel gamesPlayedPerSecond;

	/**
	 * The list of controls used to represent each processor in the simulation.
	 */
	private final List<Group> processors = new ArrayList<Group>();

	/**
	 * Formats numbers according to the current Locale. For example, "1463" is
	 * displayed as "1,463" in the US and "1 463" in some European countries.
	 */
	private final NumberFormat nf;

	/**
	 * Formats a part of a time string (hour, minute or second).
	 */
	private final NumberFormat nfTime;

	public StatsPanel() {
		setLayout(new MigLayout("ins 0"));

		nf = NumberFormat.getInstance();
		nfTime = new DecimalFormat("00"); //ensures that a leading 0 is displayed

		HeaderLabel label;
		WhatsThisLabel whatsThis;

		//add "run time" row
		whatsThis = new WhatsThisLabel("The total amount of time that the simulation has been running.");
		add(whatsThis, "split 2");
		label = new HeaderLabel("Run time:");
		timeRunning = new JXLabel();
		add(label);
		add(timeRunning, "wrap");

		//add "games played" row
		whatsThis = new WhatsThisLabel("The total number of games that were played in this simulation.");
		add(whatsThis, "split 2");
		label = new HeaderLabel("Games played:");
		totalGamesPlayed = new JXLabel();
		add(label);
		add(totalGamesPlayed, "wrap");

		//add "games played per second" row
		whatsThis = new WhatsThisLabel("The number of games played per second.");
		add(whatsThis, "split 2");
		label = new HeaderLabel("Games played / sec:");
		gamesPlayedPerSecond = new JXLabel();
		add(label);
		add(gamesPlayedPerSecond, "wrap");

		//add "max build order length" row
		whatsThis = new WhatsThisLabel("The maximum length that the build order is allowed to be.");
		add(whatsThis, "split 2");
		label = new HeaderLabel("Max build order length:");
		maxBuildOrderLength = new JXLabel();
		add(label);
		add(maxBuildOrderLength, "wrap");

		//add "stagnation limit" row
		whatsThis = new WhatsThisLabel("The stagnation limit.");
		add(whatsThis, "split 2");
		label = new HeaderLabel("Stagnation Limit:");
		stagnationLimit = new JXLabel();
		add(label);
		add(stagnationLimit, "wrap");

		//add "mutation rate" row
		whatsThis = new WhatsThisLabel("The mutation rate.");
		add(whatsThis, "split 2");
		label = new HeaderLabel("Mutation Rate:");
		mutationRate = new JXLabel();
		add(label);
		add(mutationRate, "wrap");
	}

	/**
	 * Sets the length of time that a simulation has been running for.
	 * 
	 * @param ms the time in milliseconds
	 */
	public void setTimeRunning(long ms) {
		long seconds = ms / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;

		timeRunning.setText(nfTime.format(hours % 60) + ":" + nfTime.format(minutes % 60) + ":" + nfTime.format(seconds % 60));
	}

	/**
	 * Sets the total number of games played.
	 * 
	 * @param totalGamesPlayed
	 */
	public void setGamesPlayed(double totalGamesPlayed) {
		this.totalGamesPlayed.setText(nf.format(totalGamesPlayed / 1000) + "K");
	}

	/**
	 * Sets the maximum build order length.
	 * 
	 * @param maxBuildOrderLength
	 */
	public void setMaxBuildOrderLength(int maxBuildOrderLength) {
		this.maxBuildOrderLength.setText(nf.format(maxBuildOrderLength));
	}

	/**
	 * Sets the stagnation limit.
	 * 
	 * @param stagnationLimit
	 */
	public void setStagnationLimit(int stagnationLimit) {
		this.stagnationLimit.setText(nf.format(stagnationLimit));
	}

	/**
	 * Sets the mutation rate.
	 * 
	 * @param mutationRate
	 */
	public void setMutationRate(double mutationRate) {
		this.mutationRate.setText(nf.format(mutationRate));
	}

	/**
	 * Sets the games played per second.
	 * 
	 * @param gamesPlayedPerSecond
	 */
	public void setGamesPlayedPerSecond(int gamesPlayedPerSecond) {
		this.gamesPlayedPerSecond.setText(nf.format(gamesPlayedPerSecond));
	}

	/**
	 * Sets the information on a specific processor (thread).
	 * 
	 * @param threadIndex the thread number
	 * @param evolutionsSinceDiscovery
	 * @param score
	 */
	public void setProcessor(int threadIndex, int evolutionsSinceDiscovery, Double score) {
		if (threadIndex < processors.size()) {
			processors.get(threadIndex).value.setText("age (" + nf.format(evolutionsSinceDiscovery) + ") score: (" + nf.format(score) + ")");
		}
	}

	/**
	 * Sets the total number of processors (threads) being used in the
	 * simulation.
	 * 
	 * @param threads
	 */
	public void setProcessorCount(int threads) {
		//remove the existing labels
		for (Group group : processors) {
			remove(group.help);
			remove(group.header);
			remove(group.value);
		}
		validate();
		processors.clear();

		//add new labels for each processor
		for (int i = 0; i < threads; i++) {
			WhatsThisLabel help = new WhatsThisLabel("Information about a specific thread in the simulation.\n * Age: The number of evolution iterations since the thread discovered its best build order.\n * Score: The fitness score of the thread's best build order.");
			HeaderLabel header = new HeaderLabel("Processor " + i);
			JXLabel value = new JXLabel();

			add(help, "split 2");
			add(header);
			add(value, "wrap");
			processors.add(new Group(header, value, help));
		}
		validate();
		repaint();
	}

	/**
	 * A label that is used as a heading.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private static class HeaderLabel extends JXLabel {
		public HeaderLabel(String text) {
			super("<html><b>" + text + "</b></html>");
		}
	}

	/**
	 * A group of controls that represents a single processor (thread) in the
	 * simulation.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private static class Group {
		public final HeaderLabel header;
		public final JXLabel value;
		public final WhatsThisLabel help;

		public Group(HeaderLabel header, JXLabel value, WhatsThisLabel help) {
			this.header = header;
			this.value = value;
			this.help = help;
		}
	}
}
