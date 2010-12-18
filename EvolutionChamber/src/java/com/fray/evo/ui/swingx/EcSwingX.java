package com.fray.evo.ui.swingx;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import static com.fray.evo.ui.swingx.EcSwingXMain.userSettings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXStatusBar;
import org.jgap.InvalidConfigurationException;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcReportable;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.ZergUpgradeLibrary;

//TODO: Refactor this monster. - Lomilar
public class EcSwingX extends JXPanel implements EcReportable
{
	private static final Logger logger = Logger.getLogger(EcSwingX.class.getName());
	private JTextArea			outputText;
	private JLabel				status1;
	private JLabel				status2;
	private JLabel				status3;
	protected long				timeStarted;
	protected long				lastUpdate;
	private String				simpleBuildOrder;
	private String				detailedBuildOrder;
	private String				yabotBuildOrder;
	private boolean				isDetailedBuildOrder;
	private boolean				isYabotBuildOrder;
	private boolean				isSimpleBuildOrder;
	private int					gridy			= 0;
	private JXStatusBar			statusbar;
	private List<JComponent>	inputControls	= new ArrayList<JComponent>();

	private final EvolutionChamber ec;
	List<EcState> destination = new ArrayList<EcState>();
	
	private JPanel historyPanel;
	private List<JPanel> waypointPanels = new ArrayList<JPanel>();
	private JPanel newWaypointPanel;
	private JPanel statsPanel;
	private JPanel settingsPanel;
	
	private boolean running = false;
	
	private JButton				goButton;
	private JButton				stopButton;
	private LocaleComboBox		localeComboBox;
	private JButton				clipboardButton;
	private JButton				switchSimpleButton;
	private JButton				switchDetailedButton;
	private JButton				switchYabotButton;
	private JTextArea			statsText;
	private JTabbedPane			tabPane;
	private Component			lastSelectedTab;
	private JList				historyList;
	
	private JFrame				frame;

	/**
	 * Constructor.
	 * @param frame the window that holds this panel.
	 */
	public EcSwingX(JFrame frame)
	{
		ec = new EvolutionChamber(new File(new File(EcSwingXMain.userConfigDir,EvolutionChamber.VERSION), "seeds.evo"), new File(new File(EcSwingXMain.userConfigDir,EvolutionChamber.VERSION), "seeds2.evo"));
		ec.setReportInterface(this);
		
		this.frame = frame;
		initializeWaypoints();

		setLayout(new BorderLayout());

		JSplitPane outside = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		{ // Left
			JPanel leftbottom = new JPanel(new GridBagLayout());
			JScrollPane stuffPanel = new JScrollPane(leftbottom);
			{
				{
					addControlParts(leftbottom);
					tabPane = new JTabbedPane(JTabbedPane.LEFT);
					{
						//history tab
						historyPanel = new JPanel(new BorderLayout());
						addStart(historyPanel);

						//waypoint tabs
						for (int i = 0; i < destination.size()-1; i++)
						{
							JPanel lb = new JPanel(new GridBagLayout());
							waypointPanels.add(lb);
							addInputContainer(destination.get(i), lb);
						}
						
						//new waypoint tab
						newWaypointPanel = new JPanel(); //just make it an empty panel
						tabPane.addChangeListener(new ChangeListener(){
							@Override
							public void stateChanged(ChangeEvent event) {
								if (running && tabPane.getSelectedComponent() == newWaypointPanel){
									tabPane.setSelectedComponent(lastSelectedTab);
								} else {
									lastSelectedTab = tabPane.getSelectedComponent();
								}
							}
						});
						tabPane.addMouseListener(new MouseListener(){
							public void mouseClicked(MouseEvent event) {
								if (!running && tabPane.getSelectedComponent() == newWaypointPanel){
									//create a new waypoint
									try{
										//create waypoint object
										EcState newWaypoint = (EcState) ec.getInternalDestination().clone();
										if (destination.size() > 1){
											//add 3 minutes to the last waypoint's time
											newWaypoint.targetSeconds = destination.get(destination.size()-2).targetSeconds + (60*3);
										} else {
											newWaypoint.targetSeconds = 60*3;
										}
										destination.add(destination.size()-1, newWaypoint); //final dest stays on end
										
										//create panel
										JPanel newWaypointPanel = new JPanel(new GridBagLayout());
										waypointPanels.add(waypointPanels.size()-1, newWaypointPanel); //final dest panel stays on end
										addInputContainer(newWaypoint, newWaypointPanel);
										
										//add the new waypoint to the tabs
										refreshTabs();
										
										//select new waypoint
										tabPane.setSelectedComponent(newWaypointPanel);
									} catch (CloneNotSupportedException e){
									}
								}
							}
							
							public void mouseEntered(MouseEvent arg0) {
							}
							public void mouseExited(MouseEvent arg0) {
							}
							public void mousePressed(MouseEvent arg0) {
							}
							public void mouseReleased(MouseEvent arg0) {
							}
						});
						
						//final waypoint tab
						JPanel finalDestinationPanel = new JPanel(new GridBagLayout());
						waypointPanels.add(finalDestinationPanel);
						addInputContainer(destination.get(destination.size()-1), finalDestinationPanel);

						//stats tab
						statsPanel = new JPanel(new BorderLayout());
						addStats(statsPanel);

						//settings tab
						settingsPanel = new JPanel(new GridBagLayout());
						addSettings(settingsPanel);
						
						//add tabs to JTabbedPane
						refreshTabs();

						//select final waypoint tab
						tabPane.setSelectedComponent(waypointPanels.get(waypointPanels.size()-1));
					}
					GridBagConstraints gridBagConstraints = new GridBagConstraints();
					gridBagConstraints.anchor = GridBagConstraints.WEST;
					gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
					gridBagConstraints.weightx = .25;
					gridBagConstraints.gridy = gridy;
					gridBagConstraints.gridwidth = 4;
					gridBagConstraints.insets = new Insets(1, 1, 1, 1);
					leftbottom.add(tabPane, gridBagConstraints);
					addStatusBar(leftbottom);
				}
			}
			outside.setLeftComponent(stuffPanel);
		}
		{ // Right
			JPanel right = new JPanel(new GridBagLayout());
			addOutputContainer(right);
			addOutputButtons(right);
			outside.setRightComponent(new JScrollPane(right));
		}

		add(outside);
		outside.setDividerLocation(490);
	}
	
	private void refreshTabs(){
		tabPane.removeAll();
		tabPane.addTab(messages.getString("tabs.history"), historyPanel);
		for (int i = 0; i < waypointPanels.size()-1; i++)
		{
			JPanel p = waypointPanels.get(i);
			tabPane.addTab(messages.getString("tabs.waypoint", i), p);
		}
		tabPane.addTab(messages.getString("tabs.waypoint", "+"), newWaypointPanel);
		tabPane.addTab(messages.getString("tabs.final"), waypointPanels.get(waypointPanels.size()-1));
		tabPane.addTab(messages.getString("tabs.stats"), statsPanel);
		tabPane.addTab(messages.getString("tabs.settings"), settingsPanel);
	}

	private void addStart(JPanel start)
	{
		historyList = new JList();
		historyList.setMaximumSize(new Dimension(80, 100));
		JScrollPane scrollPane = new JScrollPane(historyList);
		scrollPane.setPreferredSize(start.getSize());
		start.add(scrollPane);
		historyList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				displayBuild((EcBuildOrder) historyList.getSelectedValue());
			}
		});
		final PopupMenu deleteMenu = new PopupMenu(messages.getString("history.options"));
		MenuItem menuItem = new MenuItem(messages.getString("history.delete"));
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ec.getHistory().remove(historyList.getSelectedValue());
				refreshHistory();
				ec.saveSeeds();
			}
		});
		deleteMenu.add(menuItem);
		menuItem = new MenuItem(messages.getString("history.load"));
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				expandWaypoints((EcState) historyList.getSelectedValue());
				refreshTabs();
				readDestinations();
			}
		});
		deleteMenu.insert(menuItem, 0);
		historyList.add(deleteMenu);
		historyList.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				// if right mouse button clicked (or me.isPopupTrigger())
				if (SwingUtilities.isRightMouseButton(me) && !historyList.isSelectionEmpty()
						&& historyList.locationToIndex(me.getPoint()) == historyList.getSelectedIndex())
				{
					deleteMenu.show(historyList, me.getX(), me.getY());
				}
			}
		});
		refreshHistory();
	}

	private void displayBuild(EcBuildOrder destination)
	{
		if (destination == null)
			return;
		EcBuildOrder source = new EcBuildOrder();
		EcBuildOrder source2 = new EcBuildOrder();
		EcBuildOrder source3 = new EcBuildOrder();
		EcEvolver evolver;
		try
		{
			evolver = new EcEvolver(source, destination.clone(), ec.getActions());
			ByteArrayOutputStream baos;
			evolver.setLoggingStream(new PrintStream(baos = new ByteArrayOutputStream()));
			evolver.enableLogging(true);
			for (EcAction a : destination.actions)
			{
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
			EcBuildOrder result = evolver.doEvaluate(source);
			String detailedText = new String(baos.toByteArray());
			String simpleText = evolver.doSimpleEvaluate(source2);
			String yabotText = evolver.doYABOTEvaluate(source3);
			receiveBuildOrders(detailedText, simpleText, yabotText);
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		catch (InstantiationException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		catch (IllegalAccessException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private void refreshHistory()
	{
		ArrayList<EcBuildOrder> results = new ArrayList<EcBuildOrder>();
		for (EcBuildOrder destination : ec.getHistory())
		{
			EcBuildOrder source = new EcBuildOrder();
			EcEvolver evolver = new EcEvolver(source, destination, ec.getActions());
			evolver.enableLogging(true);
			for (EcAction a : destination.actions)
				source.addAction(a);
			source.targetSeconds = destination.targetSeconds;
			source.scoutDrone = destination.scoutDrone;
			EcBuildOrder result = evolver.doEvaluate(source);
			if (result.seconds > 60)
				results.add(destination);
		}
		historyList.setListData(results.toArray());
	}

	private void addSettings(JPanel settings)
	{
		{
			// somebody enlighten me please how this could be done easier... but
			// it works :)
			final String[] radioButtonCaptions = {messages.getString("settings.workerParity.none"), messages.getString("settings.workerParity.untilSaturation"), messages.getString("settings.workerParity.allowOverdroning")};
			final int defaultSelected;
			if (destination.get(destination.size()-1).settings.overDrone)
			{
				defaultSelected = 1;
			}
			else if (destination.get(destination.size()-1).settings.workerParity)
			{
				defaultSelected = 2;
			}
			else
			{
				defaultSelected = 0;
			}
			addRadioButtonBox(settings, messages.getString("settings.workerParity"), radioButtonCaptions, defaultSelected,
					new CustomActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (getSelected(e).equals(radioButtonCaptions[1]))
							{
								destination.get(destination.size()-1).settings.workerParity = true;
								destination.get(destination.size()-1).settings.overDrone = false;
							}
							else if (getSelected(e).equals(radioButtonCaptions[2]))
							{
								destination.get(destination.size()-1).settings.workerParity = false;
								destination.get(destination.size()-1).settings.overDrone = true;
							}
							else
							{
								destination.get(destination.size()-1).settings.workerParity = false;
								destination.get(destination.size()-1).settings.overDrone = false;
							}
						}

						@Override
						void reverse(Object o)
						{
							//TODO: Code this up
						}
					});
			gridy++;
		}
		addCheck(settings, messages.getString("settings.useExtractorTrick"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.get(destination.size()-1).settings.useExtractorTrick = getTrue(e);
			}

			@Override
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination.get(destination.size()-1).settings.useExtractorTrick);
			}
		}).setSelected(destination.get(destination.size()-1).settings.useExtractorTrick);
		gridy++;
		addCheck(settings, messages.getString("settings.pullWorkersFromGas"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.get(destination.size()-1).settings.pullWorkersFromGas = getTrue(e);
			}
			@Override
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination.get(destination.size()-1).settings.pullWorkersFromGas);
			}
		}).setSelected(destination.get(destination.size()-1).settings.useExtractorTrick);
		gridy++;
		addCheck(settings, messages.getString("settings.pullThreeWorkersTogether"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.get(destination.size()-1).settings.pullThreeWorkersOnly = getTrue(e);
			}
			@Override
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination.get(destination.size()-1).settings.pullThreeWorkersOnly);
			}
		}).setSelected(destination.get(destination.size()-1).settings.pullThreeWorkersOnly);
		gridy++;
		addCheck(settings, messages.getString("settings.avoidMiningGas"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.get(destination.size()-1).settings.avoidMiningGas = getTrue(e);
			}
			@Override
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination.get(destination.size()-1).settings.avoidMiningGas);
			}
		}).setSelected(destination.get(destination.size()-1).settings.avoidMiningGas);
		gridy++;
		addInput(settings, messages.getString("settings.maxExtractorTrickSupply"), new CustomActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				destination.get(destination.size()-1).settings.maximumExtractorTrickSupply = getDigit(e);
			}
			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination.get(destination.size()-1).settings.maximumExtractorTrickSupply));
			}
		}).setText("200");
		gridy++;
		addInput(settings, messages.getString("settings.minPoolSupply"), new CustomActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				destination.get(destination.size()-1).settings.minimumPoolSupply = getDigit(e);
			}
			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination.get(destination.size()-1).settings.minimumPoolSupply));
			}
		}).setText("2");
		gridy++;
		addInput(settings, messages.getString("settings.minExtractorSupply"), new CustomActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				destination.get(destination.size()-1).settings.minimumExtractorSupply = getDigit(e);
			}
			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination.get(destination.size()-1).settings.minimumExtractorSupply));
			}
		}).setText("2");
		gridy++;
		addInput(settings, messages.getString("settings.minHatcherySupply"), new CustomActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				destination.get(destination.size()-1).settings.minimumHatcherySupply = getDigit(e);

			}
			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination.get(destination.size()-1).settings.minimumHatcherySupply));
			}
		}).setText("2");
	}

	private void initializeWaypoints()
	{
		try
		{
			for (int i = 1; i < 5; i++){ //add 4 waypoints
				EcState state = (EcState) ec.getInternalDestination().clone();
				state.targetSeconds = (i*3) * 60;
				destination.add(state);
			}
			destination.add((EcState) ec.getInternalDestination().clone()); //final destination
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private void addStats(JPanel stats)
	{
		stats.add(statsText = new JTextArea());
		statsText.setEditable(false);
		statsText.setAlignmentX(0);
		statsText.setAlignmentY(0);
		statsText.setTabSize(4);
	}

	private void addStatusBar(JPanel leftbottom)
	{
		statusbar = new JXStatusBar();
		status1 = new JLabel(messages.getString("status.ready"));
		statusbar.add(status1);
		status2 = new JLabel(messages.getString("status.notRunning"));
		statusbar.add(status2);
		status3 = new JLabel("");
		statusbar.add(status3);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .5;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.gridy = gridy + 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		leftbottom.add(statusbar, gridBagConstraints);
		Timer t = new Timer(200, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (timeStarted == 0)
					status1.setText(messages.getString("status.ready"));
				else
				{
					long ms = new Date().getTime() - timeStarted;
					long seconds = ms / 1000;
					long minutes = seconds / 60;
					long hours = minutes / 60;
					status1.setText(messages.getString("status.running", hours % 60, minutes % 60, seconds % 60));
				}
				if (lastUpdate != 0)
				{
					long ms = new Date().getTime() - lastUpdate;
					long seconds = ms / 1000;
					long minutes = seconds / 60;
					long hours = minutes / 60;
					status2.setText(messages.getString("status.lastUpdate", hours % 60, minutes % 60, seconds % 60));
					{
						double evaluations = ec.getGamesPlayed();
						double evalseconds = (System.currentTimeMillis() - timeStarted);
						evalseconds = evalseconds / 1000.0;
						double permsPerSecond = evaluations;
						permsPerSecond /= evalseconds;
						StringBuilder stats = new StringBuilder();
						int threadIndex = 0;
						stats.append(messages.getString("stats.gamesPlayed", evaluations / 1000));
						stats.append("\n" + messages.getString("stats.maxBuildOrderLength", ec.getChromosomeLength()));
						stats.append("\n" + messages.getString("stats.stagnationLimit", ec.getStagnationLimit()));
						stats.append("\n" + messages.getString("stats.gamesPlayedPerSec", (int) permsPerSecond));
						stats.append("\n" + messages.getString("stats.mutationRate", ec.getBaseMutationRate() / ec.getChromosomeLength()));
						for (Double d : ec.getBestScores())
							stats.append("\n" + messages.getString("stats.processor", threadIndex, ec.getEvolutionsSinceDiscovery(threadIndex++), d));
						statsText.setText(stats.toString());
					}
				}
				statusbar.repaint();
			}
		});
		t.start();
	}

	private void addOutputContainer(JPanel component)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		component.add(new JScrollPane(outputText = new JTextArea()), gridBagConstraints);
		outputText.setAlignmentX(0);
		outputText.setAlignmentY(0);
		outputText.setTabSize(4);
		outputText.setEditable(false);
		outputText.setLineWrap(true);
//		outputText.setPreferredSize(new Dimension(0,0));
		String welcome = messages.getString("welcome");
		simpleBuildOrder = welcome;
		detailedBuildOrder = welcome;
		outputText.setText(welcome);
	}

	private void addInputContainer(final EcState dest, final JPanel components)
	{
		// addInput(component, "", new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// ec.POPULATION_SIZE = getDigit(e);
		// }
		// }).setText("30");
		// addInput(component, "Chromosome Length", new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// ec.CHROMOSOME_LENGTH = getDigit(e);
		// }
		// }).setText("120");
		addInput(components, messages.getString("waypoint.drones"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Drone, getDigit(e));
			}

			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getDrones()));
			}
		});
		addInput(components, messages.getString("waypoint.deadline"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.targetSeconds = getDigit(e);
				((JTextField)e.getSource()).setText( formatAsTime(dest.targetSeconds) );
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(formatAsTime(dest.targetSeconds));
			}
		}).setText(formatAsTime(dest.targetSeconds));
		gridy++;
		addInput(components, messages.getString("waypoint.overlords"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Overlord, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getOverlords()));
			}
		});
		addInput(components, messages.getString("waypoint.overseers"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Overseer, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getOverseers()));
			}
		});
		gridy++;
		if (dest == destination.get(destination.size()-1)) // only put this option on the Final waypoint.
		{
			addInput(components, messages.getString("waypoint.scoutTiming"), new CustomActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					destination.get(destination.size()-1).scoutDrone = getDigit(e);
					((JTextField)e.getSource()).setText( formatAsTime(dest.scoutDrone) );
				}
				void reverse(Object o)
				{
					JTextField c = (JTextField) o;
					c.setText(formatAsTime((destination.get(destination.size()-1).scoutDrone)));
				}
			}).setText( formatAsTime(dest.scoutDrone));
		}
		addCheck(components, messages.getString("waypoint.burrow"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Burrow);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Burrow);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isBurrow());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.queens"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Queen, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getQueens()));
			}
		});
		addCheck(components, messages.getString("waypoint.pneumatizedCarapace"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.PneumatizedCarapace);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.PneumatizedCarapace);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isPneumatizedCarapace());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.zerglings"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Zergling, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getZerglings()));
			}
		});
		addCheck(components, messages.getString("waypoint.ventralSacs"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.VentralSacs);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.VentralSacs);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isVentralSacs());
			}
		});
		gridy++;
		addCheck(components, messages.getString("waypoint.metabolicBoost"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.MetabolicBoost);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.MetabolicBoost);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isMetabolicBoost());
			}
		});
		addCheck(components, messages.getString("waypoint.adrenalGlands"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.AdrenalGlands);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.AdrenalGlands);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isAdrenalGlands());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.banelings"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Baneling, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getBanelings()));
			}
		});
		addCheck(components, messages.getString("waypoint.centrifugalHooks"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.CentrifugalHooks);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.CentrifugalHooks);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isCentrifugalHooks());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.roaches"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Roach, getDigit(e));

			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getRoaches()));
			}
		});
		gridy++;
		addCheck(components, messages.getString("waypoint.glialReconstitution"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.GlialReconstitution);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.GlialReconstitution);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isGlialReconstitution());
			}
		});
		addCheck(components, messages.getString("waypoint.tunnelingClaws"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.TunnelingClaws);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.TunnelingClaws);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isTunnelingClaws());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.hydralisks"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Hydralisk, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getHydralisks()));
			}
		});
		addCheck(components, messages.getString("waypoint.groovedSpines"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.GroovedSpines);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.GroovedSpines);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isGroovedSpines());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.infestors"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Infestor, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getInfestors()));
			}
		});
		gridy++;
		addCheck(components, messages.getString("waypoint.neuralParasite"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.NeuralParasite);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.NeuralParasite);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isNeuralParasite());
			}
		});
		addCheck(components, messages.getString("waypoint.pathogenGlands"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.PathogenGlands);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.PathogenGlands);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isPathogenGlands());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.mutalisks"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Mutalisk, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getMutalisks()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.ultralisks"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Ultralisk, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getUltralisks()));
			}
		});
		addCheck(components, messages.getString("waypoint.chitinousPlating"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.ChitinousPlating);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.ChitinousPlating);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isChitinousPlating());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.corruptors"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Corruptor, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getCorruptors()));
			}
		});
		addInput(components, messages.getString("waypoint.broodlords"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetUnits( ZergUnitLibrary.Broodlord, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getBroodlords()));
			}
		});
		gridy++;
		addCheck(components, messages.getString("waypoint.melee") + " +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Melee1);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Melee1);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isMelee1());
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Melee2);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Melee2);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isMelee2());
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Melee3);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Melee3);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isMelee3());
			}
		});
		gridy++;
		addCheck(components, messages.getString("waypoint.missile") + " +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Missile1);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Missile1);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isMissile1());
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Missile2);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Missile2);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isMissile2());
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Missile3);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Missile3);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isMissile3());
			}
		});
		gridy++;
		addCheck(components, messages.getString("waypoint.carapace") + " +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Armor1);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Armor1);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isArmor1());
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Armor2);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Armor2);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isArmor2());
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.Armor3);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.Armor3);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isArmor3());
			}
		});
		gridy++;
		addCheck(components, messages.getString("waypoint.flyerAttack") + " +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isFlyerAttack1());
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.FlyerAttacks2);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.FlyerAttacks2);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isFlyerAttack2());
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.FlyerAttacks3);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.FlyerAttacks3);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isFlyerAttack3());
			}
		});
		gridy++;
		addCheck(components, messages.getString("waypoint.flyerArmor") + " +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.FlyerArmor1);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.FlyerArmor1);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isFlyerArmor1());
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.FlyerArmor2);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.FlyerArmor2);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isFlyerArmor2());
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getTrue(e)) dest.AddUpgrade(ZergUpgradeLibrary.FlyerArmor3);
				else dest.RemoveUpgrade(ZergUpgradeLibrary.FlyerArmor3);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(dest.isFlyerArmor3());
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.bases"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.requiredBases = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.requiredBases));
			}
		});
		addInput(components, messages.getString("waypoint.lairs"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.Lair, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getLairs()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.hives"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.Hive, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getHives()));
			}
		});
		addInput(components, messages.getString("waypoint.gasExtractors"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.Extractor, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getGasExtractors()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.evolutionChambers"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.EvolutionChamber, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getEvolutionChambers()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.spineCrawlers"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.SpineCrawler, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getSpineCrawlers()));
			}
		});
		addInput(components, messages.getString("waypoint.sporeCrawlers"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.SporeCrawler, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getSporeCrawlers()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.spawningPools"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.SpawningPool, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getSpawningPools()));
			}
		});
		addInput(components, messages.getString("waypoint.banelingNests"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.BanelingNest, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getBanelingNest()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.roachWarrens"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.RoachWarren, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getRoachWarrens()));
			}
		});
		addInput(components, messages.getString("waypoint.hydraliskDens"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.HydraliskDen, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getHydraliskDen()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.infestationPits"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.InfestationPit, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getInfestationPit()));
			}
		});
		addInput(components, messages.getString("waypoint.spires"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.Spire, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getSpire()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.nydusNetworks"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.NydusNetwork, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getNydusNetwork()));
			}
		});
		addInput(components, messages.getString("waypoint.nydusWorms"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.NydusWorm, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getNydusWorm()));
			}
		});
		gridy++;
		addInput(components, messages.getString("waypoint.ultraliskCaverns"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.UltraliskCavern, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getUltraliskCavern()));
			}
		});
		addInput(components, messages.getString("waypoint.greaterSpires"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dest.SetBuilding(ZergBuildingLibrary.GreaterSpire, getDigit(e));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(dest.getGreaterSpire()));
			}
		});
		gridy++;
		
		int width = 4;
		if (dest != destination.get(destination.size()-1)){ //add a "remove" button for all waypoints except the final destination
			inputControls.add(addButton(components, messages.getString("waypoint.remove"), 1, new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					inputControls.removeAll(Arrays.asList(components.getComponents()));
					int selectedIndex = tabPane.getSelectedIndex();
					destination.remove(dest);
					waypointPanels.remove(components);
					refreshTabs();
					if (selectedIndex > 0)
						tabPane.setSelectedIndex(selectedIndex-1); //if WP3 was removed, select WP2
				}
			}));
			width = 3;
		}
		
		inputControls.add(addButton(components, messages.getString("waypoint.reset"), width, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for (int i = 0; i < components.getComponentCount(); i++)
				{
					Component component = components.getComponent(i);
					if (component instanceof JTextField)
					{
						JTextField textField = (JTextField) component;
						if (textField.getText().indexOf(":") == -1) // is
						{
							// not
							// a
							// "Deadline"
							// field
							textField.setText("0");
							textField.getActionListeners()[0].actionPerformed(new ActionEvent(textField, 0, ""));
						}
					}
					else if (component instanceof JCheckBox)
					{
						JCheckBox checkBox = (JCheckBox) component;
						checkBox.setSelected(false);
						checkBox.getActionListeners()[0].actionPerformed(new ActionEvent(checkBox, 0, ""));
					}
				}
			}
		}));
	}

	private void readDestinations()
	{
		for (int i = 0; i < inputControls.size(); i++)
		{
			JComponent component = inputControls.get(i);
			if (component instanceof JTextField)
			{
				ActionListener actionListener = ((JTextField) component).getActionListeners()[0];
				if (actionListener instanceof CustomActionListener)
					((CustomActionListener)actionListener).reverse(component);
			}
			else if (component instanceof JCheckBox)
			{
				ActionListener actionListener = ((JCheckBox) component).getActionListeners()[0];
				if (actionListener instanceof CustomActionListener)
					((CustomActionListener)actionListener).reverse(component);
			}
		}
	}
	
	private void addOutputButtons(JPanel component)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		gridBagConstraints.weightx = 0.25;
		clipboardButton = new JButton(messages.getString("copyToClipboard"));
		component.add(clipboardButton, gridBagConstraints);
		clipboardButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(outputText.getText()), null);
			}
		});

		switchDetailedButton = new JButton(messages.getString("detailedFormat"));
		isDetailedBuildOrder = true;
		gridBagConstraints.weightx = 0.25;
		component.add(switchDetailedButton, gridBagConstraints);
		switchDetailedButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				outputText.setText(detailedBuildOrder);
				outputText.setTabSize(4);
				isDetailedBuildOrder = true;
				isYabotBuildOrder = false;
				isSimpleBuildOrder = false;
			}
		});

		switchSimpleButton = new JButton(messages.getString("simpleFormat"));
		isSimpleBuildOrder = false;
		gridBagConstraints.weightx = 0.25;
		component.add(switchSimpleButton, gridBagConstraints);
		switchSimpleButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				outputText.setText(simpleBuildOrder);
				outputText.setTabSize(14);
				isSimpleBuildOrder = true;
				isYabotBuildOrder = false;
				isDetailedBuildOrder = false;
			}
		});

		switchYabotButton = new JButton(messages.getString("yabotFormat"));
		isYabotBuildOrder = false;
		gridBagConstraints.weightx = 0.25;
		component.add(switchYabotButton, gridBagConstraints);
		switchYabotButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				outputText.setText(yabotBuildOrder);
				outputText.setTabSize(14);
				isYabotBuildOrder = true;
				isSimpleBuildOrder = false;
				isDetailedBuildOrder = false;
			}
		});
	}

	private void addControlParts(JPanel component)
	{
		localeComboBox = new LocaleComboBox(new Locale[]{new Locale("en"), new Locale("ko"), new Locale("de")}, messages.getLocale());
		localeComboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				Locale selected = localeComboBox.getSelectedLocale();
				Locale current = messages.getLocale();
				if (selected.getLanguage().equals(current.getLanguage()) && (current.getCountry() == null || selected.getCountry().equals(current.getCountry()))){
					//do nothing if the current language was selected
					return;
				}
				
				//change the language
				messages.changeLocale(selected);
				userSettings.setLocale(selected);
				
				//re-create the window
				final JFrame newFrame = new JFrame();
				EcSwingXMain.mainWindow = newFrame; //for when a Mac user selects "Quit" from the application menu
				newFrame.setTitle(messages.getString("title", EvolutionChamber.VERSION));
				newFrame.setDefaultCloseOperation(frame.getDefaultCloseOperation());
				newFrame.getContentPane().add(new EcSwingX(newFrame));
				
				newFrame.addWindowListener(new WindowAdapter() {				
					@Override
					public void windowClosing(WindowEvent windowevent) {
						// save the window settings on exit
						int currentExtendedState = newFrame.getExtendedState();
						
						// get the preferred size of the non-maximized view
						if( currentExtendedState != JFrame.NORMAL)
							newFrame.setExtendedState(JFrame.NORMAL);
						Dimension currentSize = frame.getSize();
						
						userSettings.setWindowExtensionState(currentExtendedState);
						userSettings.setWindowSize(currentSize);
					}
				});
				
				newFrame.setPreferredSize(frame.getPreferredSize());
				newFrame.setIconImage(frame.getIconImage());
				newFrame.pack();
				newFrame.setLocationRelativeTo(null);
				
				//remove the old window
				frame.dispose();
				
				//display the new window
				newFrame.setVisible(true);
			}
		});
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		component.add(localeComboBox, gridBagConstraints);
		
		gridy++;
		
		addInput(component, messages.getString("processors"), new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ec.setThreads(getDigit(e));
				((JTextField) e.getSource()).setText(Integer.toString(ec.getThreads()));
			}
			void reverse(Object o)
			{
				((JTextField) o).setText(Integer.toString(ec.getThreads()));
			}
		}).setText(Integer.toString(ec.getThreads()));
		stopButton = addButton(component, messages.getString("stop"), new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ec.stopAllThreads();
				running = false;
				goButton.setEnabled(true);
				stopButton.setEnabled(false);
				historyList.setEnabled(true);
				localeComboBox.setEnabled(true);
				timeStarted = 0;
				for (JComponent j : inputControls)
					j.setEnabled(true);
				lastUpdate = 0;
				refreshHistory();
			}
		});
		stopButton.setEnabled(false);
		final EcReportable ri = this;
		goButton = addButton(component, messages.getString("start"), new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				running = true;

				for (JComponent j : inputControls)
					j.setEnabled(false);
				restartChamber();
				tabPane.setSelectedComponent(statsPanel);
				timeStarted = new Date().getTime();
				goButton.setEnabled(false);
				stopButton.setEnabled(true);
				historyList.setEnabled(false);
				localeComboBox.setEnabled(false);
			}
		});
		gridy++;
	}

	private JButton addButton(JPanel container, String string, ActionListener actionListener)
	{
		final JButton button = new JButton();

		button.setText(string);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(button, gridBagConstraints);
		button.addActionListener(actionListener);
		return button;
	}

	private JLabel addLabel(JPanel container, String string)
	{
		final JLabel label = new JLabel();

		GridBagConstraints gridBagConstraints;
		label.setText(string);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(label, gridBagConstraints);

		return label;
	}

	private JButton addButton(JPanel container, String string, int gridwidth, ActionListener actionListener)
	{
		final JButton button = new JButton();

		button.setText(string);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridwidth;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(button, gridBagConstraints);
		button.addActionListener(actionListener);
		return button;
	}

	protected int getDigit(ActionEvent e)
	{
		JTextField tf = (JTextField) e.getSource();
		String text = tf.getText();
		try
		{
			if (text.contains(":"))
			{
				String[] split = text.split(":");
				if (Integer.parseInt(split[0]) < 0)
					throw new NumberFormatException();
				if (Integer.parseInt(split[1]) < 0)
					throw new NumberFormatException();
				return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
			}

			Integer i = Integer.parseInt(text);
			if (i < 0)
				throw new NumberFormatException();
			return i;
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			tf.setText("0");
			return 0;
		}
		catch (NumberFormatException ex)
		{
			tf.setText("0");
			return 0;
		}
	}

	private void restartChamber()
	{
		if (ec.isRunning())
			ec.stopAllThreads();
		try
		{
			EcState finalDestination = collapseWaypoints();
			ec.setDestination(finalDestination);
			ec.go();
		}
		catch (InvalidConfigurationException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private EcState collapseWaypoints() throws CloneNotSupportedException
	{
		EcState finalDestination = (EcState) destination.get(destination.size()-1).clone();
		for (int i = 0; i < destination.size() - 1; i++)
		{
			if (destination.get(i).getEstimatedActions() > 1)
				finalDestination.waypoints.add((EcState) destination.get(i).clone());
		}
		return finalDestination;
	}

	private void expandWaypoints(EcState s)
	{
		try
		{
			//clear destinations
			destination.clear();
			
			//rebuild destinations
			EcState finalDestination = (EcState) s.clone();
			finalDestination.waypoints.clear();
			for (int i = 0; i < s.waypoints.size(); i++)
			{
				destination.add((EcState) s.waypoints.get(i).clone());
			}
			destination.add(finalDestination); //final destination goes last
			
			//clear panels
			for (JPanel p : waypointPanels){
				inputControls.removeAll(Arrays.asList(p.getComponents()));
			}
			waypointPanels.clear();
			
			//rebuild panels
			for (int i = 0; i < destination.size()-1; i++)
			{
				JPanel lb = new JPanel(new GridBagLayout());
				waypointPanels.add(lb);
				addInputContainer(destination.get(i), lb);
			}
			JPanel finalDestinationPanel = new JPanel(new GridBagLayout());
			waypointPanels.add(finalDestinationPanel); //final waypoint panel goes last
			addInputContainer(destination.get(destination.size()-1), finalDestinationPanel);
			
			//rebuild the tabs
			refreshTabs();
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private JPanel addRadioButtonBox(JPanel container, String title, String[] captions, int defaultSelected,
			final CustomActionListener a)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);

		JRadioButton[] buttons = new JRadioButton[captions.length];
		ButtonGroup group = new ButtonGroup();
		JPanel radioButtonBox = new JPanel();
		radioButtonBox.setBorder(BorderFactory.createTitledBorder(title));

		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JRadioButton(captions[i]);
			buttons[i].addActionListener(a);
			inputControls.add(buttons[i]);
			group.add(buttons[i]);
			if (i == defaultSelected)
				buttons[i].setSelected(true);
			radioButtonBox.add(buttons[i]);
		}
		container.add(radioButtonBox, gridBagConstraints);
		return radioButtonBox;
	}

	protected String getSelected(ActionEvent e)
	{
		JRadioButton radioButton = (JRadioButton) e.getSource();
		return radioButton.getText();
	}

	protected boolean getTrue(ActionEvent e)
	{
		JCheckBox tf = (JCheckBox) e.getSource();
		//this.ec.bestScore = new Double(0); //why is this here??
		return tf.isSelected();
	}

	private JTextField addInput(JPanel container, String name, final CustomActionListener a)
	{
		GridBagConstraints gridBagConstraints;

		JXLabel label = new JXLabel("  " + name);
		label.setAlignmentX(.5f);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(label, gridBagConstraints);

		final JTextField textField = new JTextField();
		textField.setColumns(5);
		textField.setText("0");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(textField, gridBagConstraints);
		textField.addActionListener(a);
		textField.addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				a.actionPerformed(new ActionEvent(e.getSource(), 0, "changed"));
			}

			@Override
			public void focusGained(FocusEvent e)
			{
			}
		});
		inputControls.add(label);
		inputControls.add(textField);
		return textField;
	}

	private JCheckBox addCheck(JPanel container, String name, final CustomActionListener a)
	{
		GridBagConstraints gridBagConstraints;

		final JCheckBox checkBox = new JCheckBox();
		checkBox.setText(name);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .5;
		if (name.length() == 2)
			gridBagConstraints.gridwidth = 1;
		else
			gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(checkBox, gridBagConstraints);
		checkBox.addActionListener(a);
		checkBox.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				a.actionPerformed(new ActionEvent(checkBox, 0, "changed"));
			}
		});
		inputControls.add(checkBox);
		return checkBox;
	}
	
	@Override
	public void bestScore(final EcState finalState, int intValue, final String detailedText, final String simpleText,
			final String yabotText)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				receiveBuildOrders(detailedText, simpleText, yabotText);
				lastUpdate = new Date().getTime();
			}

		});
	}

	private void receiveBuildOrders(final String detailedText, final String simpleText, final String yabotText)
	{
		simpleBuildOrder = simpleText;
		detailedBuildOrder = detailedText;
		yabotBuildOrder = yabotText;
		if (isSimpleBuildOrder)
		{
			outputText.setText(simpleText);
		}
		else if (isYabotBuildOrder)
		{
			outputText.setText(yabotBuildOrder);
		}
		else
		{
			outputText.setText(detailedText);
		}
	}

	private String formatAsTime(int time) {
		int minutes = time / 60;
		int seconds = time % 60;
		
		return Integer.toString(minutes) + ":"
			+ (seconds < 10 ? "0" : "")
			+ Integer.toString(seconds);
	}
	
	@Override
	public void threadScore(int threadIndex, String output)
	{
		// TODO Auto-generated method stub

	}

}
