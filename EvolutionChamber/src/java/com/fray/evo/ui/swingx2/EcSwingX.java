package com.fray.evo.ui.swingx2;

import static com.fray.evo.ui.swingx2.EcSwingXMain.messages;
import static com.fray.evo.ui.swingx2.EcSwingXMain.userSettings;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import org.jgap.InvalidConfigurationException;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcReportable;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.action.EcAction;
import com.fray.evo.ui.swingx.LocaleComboBox;
import com.fray.evo.util.ZergLibrary;

/**
 * The main window.
 * @author mike.angstadt
 *
 */
@SuppressWarnings("serial")
public class EcSwingX extends JXFrame {
	private static final Logger logger = Logger.getLogger(EcSwingX.class.getName());

	/**
	 * Displays all waypoints;
	 */
	private final WaypointsPanel waypointsPanel;

	/**
	 * Starts/stops a simulation.
	 */
	private final JXButton startStop;

	/**
	 * Allows the user to change the language.
	 */
	private final LocaleComboBox locale;

	/**
	 * Allows the user to change the race.
	 */
	private final RaceComboBox race;

	/**
	 * Runs the simulations.
	 */
	private final EvolutionChamber ec;

	/**
	 * Used for displaying the statistics as a simulation is running.
	 */
	private final Timer statsTimer;

	/**
	 * Animates when a simulation is running.
	 */
	private final JXBusyLabel runningBusyLabel;

	/**
	 * Displays the About dialog.
	 */
	private final JXButton about;

	/**
	 * Displays the statistics as a simulation is running.
	 */
	private final StatsPanel statsPanel;

	/**
	 * Displays all simulations that were run in the past. Allows the user to
	 * load/remove simulations.
	 */
	private final HistoryPanel historyPanel;

	/**
	 * Displays various simulation settings.
	 */
	private final SettingsPanel settingsPanel;

	private final NumberTextField processors;

	private final JXTextArea detailedText, simpleText, yabotText;

	private final JXTextField buildOrderName;

	/**
	 * The time the last simulation started.
	 */
	private long timeStarted;

	public EcSwingX() {
		setTitle(messages.getString("title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new JXPanel(new MigLayout()));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				saveWindowSettings();
			}
		});

		//create the EvolutionChamber object
		File seedsDir = new File(EcSwingXMain.userConfigDir, EvolutionChamber.VERSION);
		seedsDir.mkdirs();
		ec = new EvolutionChamber(new File(seedsDir, "seeds.evo"), new File(seedsDir, "seeds2.evo"));
		ec.setReportInterface(new EcReportable() {
			@Override
			public void bestScore(EcState finalState, int intValue, String detailedText, String simpleText, String yabotText) {
				EcSwingX.this.detailedText.setText(detailedText);
				EcSwingX.this.simpleText.setText(simpleText);
				EcSwingX.this.yabotText.setText(yabotText);
				//TODO populate the Build Order table
				//				outputTable.clear();
				//				
				//				int i = 0;
				//				GameLog log = new GameLog();
				//				log.setEnabled(false);
				//				ArrayList<EcAction> actions = s.getActions();
				//				for (int c = 0; c < actions.size(); ++c) {
				//					EcAction a = actions.get(c);
				//					i++;
				//					if (a.isInvalid(s)) {
				//						s.invalidActions++;
				//						continue;
				//					}
				//					EcAction.CanExecuteResult canExecute;
				//					while (!(canExecute = a.canExecute(s, log)).can) {
				//						if (s.seconds > s.targetSeconds || finalState.waypointMissed(s)) {
				//							//							if (s.settings.overDrone && s.getDrones() < s.getOverDrones(s))
				//							//								log.printFailure( GameLog.FailReason.OverDrone, mergedDestination, s );
				//							//							else
				//							//								log.printFailure( GameLog.FailReason.Waypoint, mergedDestination, s );
				//							//return s;
				//							outputTable.clear();
				//							outputTable.validate();
				//							outputTable.repaint();
				//							return;
				//						}
				//						int waypointIndex = finalState.getCurrWaypointIndex(s);
				//						if (waypointIndex != -1 && finalState.getWaypointActions(waypointIndex) > 0)
				//							//log.printWaypoint( waypointIndex, s );
				//							if (canExecute.somethingChanged) {
				//								if (finalState.getMergedWaypoints().isSatisfied(s)) {
				//									//log.printSatisfied(i - s.invalidActions, s, mergedDestination);
				//									//return s;
				//									outputTable.validate();
				//									outputTable.repaint();
				//									return;
				//								}
				//							}
				//					}
				//
				//					if (!(a instanceof EcActionWait)) {
				//						//log.printAction(s, a);
				//						outputTable.addRow(s.seconds, (int) s.supplyUsed, (int) s.supply(), (int) s.minerals, (int) s.gas, a.toString());
				//					}
				//
				//					a.execute(s, log);
				//				}
				//				//log.printFailure(GameLog.FailReason.OutOfActions, s, null);
				//				outputTable.clear();
				//				outputTable.validate();
				//				outputTable.repaint();
			}

			@Override
			public void threadScore(int threadIndex, String output) {
			}
		});

		//create the stats timer
		statsTimer = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//display the statistics as a simulation is running

				long timeRunning = System.currentTimeMillis() - timeStarted;
				statsPanel.setTimeRunning(timeRunning);

				double gamesPlayed = ec.getGamesPlayed();
				double timeRunningSeconds = timeRunning / 1000.0;
				double gamesPlayedPerSecond = gamesPlayed / timeRunningSeconds;

				statsPanel.setGamesPlayed(gamesPlayed);
				statsPanel.setGamesPlayedPerSecond((int) gamesPlayedPerSecond);
				statsPanel.setMaxBuildOrderLength(ec.getChromosomeLength());
				statsPanel.setStagnationLimit(ec.getStagnationLimit());
				statsPanel.setMutationRate(ec.getBaseMutationRate() / ec.getChromosomeLength());
				int threadIndex = 0;
				for (Double score : ec.getBestScores()) {
					statsPanel.setProcessor(threadIndex, ec.getEvolutionsSinceDiscovery(threadIndex), score);
					threadIndex++;
				}
			}
		});

		//create the language dropdown
		locale = new LocaleComboBox(new Locale[] { new Locale("en"), new Locale("ko"), new Locale("de") }, messages.getLocale());
		locale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Locale selected = locale.getSelectedLocale();
				Locale current = messages.getLocale();
				if (selected.getLanguage().equals(current.getLanguage()) && (current.getCountry() == null || selected.getCountry().equals(current.getCountry()))) {
					//do nothing if the current language was selected
					return;
				}

				//change the language
				messages.changeLocale(selected);
				userSettings.setLocale(selected);

				//re-create the window
				final EcSwingX newFrame = new EcSwingX();
				newFrame.setPreferredSize(getPreferredSize());
				newFrame.setIconImage(getIconImage());
				newFrame.pack();
				newFrame.setLocationRelativeTo(null);

				//remove the old window
				dispose();

				//for when a Mac user selects "Quit" from the application menu
				EcSwingXMain.mainWindow = newFrame;

				//display the new window
				newFrame.setVisible(true);
			}
		});
		getContentPane().add(locale, "split 2");

		//create the race dropdown
		race = new RaceComboBox();
		getContentPane().add(race);

		//create the about button
		about = new JXButton(messages.getString("about"));
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				AboutDialog about = AboutDialog.getInstance(EcSwingX.this);
				about.setVisible(true);
			}
		});
		getContentPane().add(about, "align right, wrap");

		//create processors textbox
		getContentPane().add(new JXLabel(messages.getString("processors")), "split 6");
		processors = new NumberTextField();
		processors.setText(Integer.toString(ec.getThreads()));
		getContentPane().add(processors, "width 50!");
		WhatsThisLabel whatsThis = new WhatsThisLabel(messages.getString("processors.help"));
		getContentPane().add(whatsThis, "gapright 15");

		getContentPane().add(new JXLabel(messages.getString("buildOrderName")));
		buildOrderName = new JXTextField();
		getContentPane().add(buildOrderName, "width 100!");
		whatsThis = new WhatsThisLabel(messages.getString("buildOrderName.help"));
		getContentPane().add(whatsThis, "wrap");

		JTabbedPane tabbedPane = new JTabbedPane();
		{
			//create the waypoints panel
			waypointsPanel = new WaypointsPanel(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					//this will run whenever a waypoint is added or removed
					//this must be done or else the scroll bars won't appear
					validate();
				}
			});
			WaypointPanel w = waypointsPanel.addWaypoint(messages.getString("waypointName", 0), 2 * 60 * 60);
			w.addTarget(ZergLibrary.Roach, 6);
			JScrollPane scrollPane = new JScrollPane(waypointsPanel);
			tabbedPane.addTab("Waypoints", scrollPane);

			//create the settings panel
			settingsPanel = new SettingsPanel();
			scrollPane = new JScrollPane(settingsPanel);
			tabbedPane.addTab("Settings", scrollPane);
		}
		getContentPane().add(tabbedPane, "height 300!, width 100%, span 2, wrap");

		//create the busy label
		runningBusyLabel = new JXBusyLabel();
		getContentPane().add(runningBusyLabel, "align right, split 2, span 2");

		//create the start/stop button
		startStop = new JXButton(messages.getString("start"));
		startStop.setToolTipText(messages.getString("start.help"));
		startStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (ec.isRunning()) {
					//stop the simulation
					ec.stopAllThreads();

					startStop.setText(messages.getString("start"));
					startStop.setToolTipText(messages.getString("start.help"));
					statsTimer.stop();
					runningBusyLabel.setBusy(false);
					timeStarted = 0;

					//enable everything
					locale.setEnabled(true);
					race.setEnabled(true);
					waypointsPanel.setEnabled(true);
					about.setEnabled(true);
					settingsPanel.setEnabled(true);
					historyPanel.setEnabled(true);
					processors.setEnabled(true);
					buildOrderName.setEnabled(true);
				} else {
					//start the simulation
					try {
						//get the waypoint information
						EcState finalDestination = waypointsPanel.buildDestination();

						//apply the settings
						settingsPanel.applySettings(finalDestination);

						//set the name of the build order
						String name = buildOrderName.getText().trim();
						finalDestination.setName(name.isEmpty() ? null : name);

						//start
						ec.setDestination(finalDestination);
						int p = processors.getValue();
						ec.setThreads(p > 0 ? p : 1); //can't have 0 threads
						ec.go();

						timeStarted = System.currentTimeMillis();
						statsPanel.setProcessorCount(ec.getThreads());
						runningBusyLabel.setBusy(true);
						statsTimer.start();
						startStop.setText(messages.getString("stop"));
						startStop.setToolTipText(messages.getString("stop.help"));

						//disable everything
						locale.setEnabled(false);
						race.setEnabled(false);
						waypointsPanel.setEnabled(false);
						about.setEnabled(false);
						settingsPanel.setEnabled(false);
						historyPanel.setEnabled(false);
						processors.setEnabled(false);
						buildOrderName.setEnabled(false);
					} catch (InvalidConfigurationException e) {
						logger.log(Level.SEVERE, "Problem configuring JGAP.", e);
					}
				}
			}
		});
		getContentPane().add(startStop, "wrap");

		JTabbedPane left = new JTabbedPane();
		{
			detailedText = new JXTextArea();
			detailedText.setEditable(false);
			JScrollPane sp = new JScrollPane(detailedText);
			left.addTab(messages.getString("detailed"), null, sp, messages.getString("detailed.help"));

			simpleText = new JXTextArea();
			simpleText.setEditable(false);
			sp = new JScrollPane(simpleText);
			left.addTab(messages.getString("simple"), null, sp, messages.getString("simple.help"));

			yabotText = new JXTextArea();
			yabotText.setEditable(false);
			yabotText.setLineWrap(true);
			sp = new JScrollPane(yabotText);
			left.addTab(messages.getString("yabot"), null, sp, messages.getString("yabot.help"));
		}
		getContentPane().add(left, "top, width 50%, height 100%");

		JTabbedPane right = new JTabbedPane();
		{
			//create the stats panel
			statsPanel = new StatsPanel();
			JScrollPane sp = new JScrollPane(statsPanel);
			right.addTab(messages.getString("stats"), null, sp, messages.getString("stats.help"));

			//create the history panel
			historyPanel = new HistoryPanel(this, ec.getHistory(), new HistoryPanel.Callback() {
				@Override
				public void loadBuildOrder(EcBuildOrder buildOrder) {
					displayBuild(buildOrder);
				}

				@Override
				public void loadBuildOrderAndWaypoints(EcBuildOrder buildOrder) {
					waypointsPanel.clearWaypoints();
					for (EcState state : buildOrder.waypoints) {
						waypointsPanel.addWaypoint(state);
					}
					waypointsPanel.addWaypoint(buildOrder);
					settingsPanel.setSettings(buildOrder);
					buildOrderName.setText(buildOrder.getName());
				}

				@Override
				public void deleteBuildOrder(EcBuildOrder buildOrder) {
					ec.getHistory().remove(buildOrder);
					ec.saveSeeds();
				}

				@Override
				public void clearHistory() {
					ec.getHistory().clear();
					ec.saveSeeds();
				}
			});
			sp = new JScrollPane(historyPanel);
			right.addTab(messages.getString("history"), null, sp, messages.getString("history.help"));
		}
		getContentPane().add(right, "top, width 50%, height 100%");
	}

	/**
	 * Persists the current window settings.
	 */
	public void saveWindowSettings() {
		// save the window settings on exit
		int currentExtendedState = getExtendedState();

		// get the preferred size of the non-maximized view
		if (currentExtendedState != JFrame.NORMAL)
			setExtendedState(JFrame.NORMAL);
		Dimension currentSize = getSize();

		userSettings.setWindowExtensionState(currentExtendedState);
		userSettings.setWindowSize(currentSize);
	}

	private void displayBuild(EcBuildOrder destination) {
		if (destination == null)
			return;
		EcBuildOrder source = new EcBuildOrder();
		EcBuildOrder source2 = new EcBuildOrder();
		EcBuildOrder source3 = new EcBuildOrder();
		EcEvolver evolver;
		try {
			evolver = new EcEvolver(source, destination.clone(), ec.getActions());
			ByteArrayOutputStream baos;
			evolver.setLoggingStream(new PrintStream(baos = new ByteArrayOutputStream()));
			evolver.enableLogging(true);
			for (EcAction a : destination.actions) {
				source.addAction(a.getClass().newInstance());
				source2.addAction(a.getClass().newInstance());
				source3.addAction(a.getClass().newInstance());
			}
			source.targetSeconds = destination.targetSeconds;
			source2.targetSeconds = destination.targetSeconds;
			source3.targetSeconds = destination.targetSeconds;
			source.settings = destination.settings;
			source2.settings = destination.settings;
			source3.settings = destination.settings;
			source.scoutDrone = destination.scoutDrone;
			source2.scoutDrone = destination.scoutDrone;
			source3.scoutDrone = destination.scoutDrone;
			evolver.doEvaluate(source);
			String detailedText = new String(baos.toByteArray());
			String simpleText = evolver.doSimpleEvaluate(source2);
			String yabotText = evolver.doYABOTEvaluate(source3);

			//populate the text boxes
			this.detailedText.setText(detailedText);
			this.simpleText.setText(simpleText);
			this.yabotText.setText(yabotText);
		} catch (CloneNotSupportedException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
	}
}
