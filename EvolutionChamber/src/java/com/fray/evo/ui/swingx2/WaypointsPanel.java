package com.fray.evo.ui.swingx2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXButton;

import com.fray.evo.EcState;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.Upgrade;
import com.fray.evo.util.ZergLibrary;

/**
 * Displays all the waypoints.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class WaypointsPanel extends JPanel {
	private final JPanel addPanel;
	private final JXButton add;
	private final ActionListener onAddOrRemoveWaypoint;
	private final List<WaypointPanel> waypointPanels = new ArrayList<WaypointPanel>();

	/**
	 * Constructor.
	 * 
	 * @param onAddOrRemoveWaypoint the action to perform when a waypoint is
	 * added or removed
	 */
	public WaypointsPanel(final ActionListener onAddOrRemoveWaypoint) {
		this.onAddOrRemoveWaypoint = onAddOrRemoveWaypoint;
		setLayout(new MigLayout("ins 0"));

		add = new JXButton("Add");
		add.setToolTipText("Create a new waypoint.");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int time = waypointPanels.isEmpty() ? 3 * 60 : waypointPanels.get(waypointPanels.size() - 1).getDeadline() + 3 * 60;
				addWaypoint("WP " + waypointPanels.size(), time).addTarget(ZergLibrary.Drone, 1);
				if (onAddOrRemoveWaypoint != null) {
					onAddOrRemoveWaypoint.actionPerformed(null);
				}
			}
		});

		//centers the "add" button vertically
		addPanel = new JPanel(new MigLayout("ins 0"));
		addPanel.add(new JLabel(), "height 50%, wrap");
		addPanel.add(add, "wrap");
		addPanel.add(new JLabel(), "height 50%");
		add(addPanel, "height 230!, top");
	}

	public WaypointsPanel() {
		this(null);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (WaypointPanel panel : waypointPanels) {
			panel.setEnabled(enabled);
		}
		add.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	public void addWaypoint(EcState state) {
		WaypointPanel panel = addWaypoint("WP " + waypointPanels.size(), state.targetSeconds);

		for (Map.Entry<Unit, Integer> target : state.getUnits().entrySet()) {
			if (target.getValue() > 0) {
				panel.addTarget(target.getKey(), target.getValue());
			}
		}

		for (Map.Entry<Building, Integer> target : state.getBuildings().entrySet()) {
			if (target.getValue() > 0) {
				panel.addTarget(target.getKey(), target.getValue());
			}
		}

		for (Upgrade target : state.getUpgrades()) {
			panel.addTarget(target);
		}
	}

	public WaypointPanel addWaypoint(String name, int deadline) {
		final WaypointPanel panel = new WaypointPanel(name, deadline);
		panel.addOnRemoveListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(panel);
				waypointPanels.remove(panel);
				validate();
				repaint();
				if (onAddOrRemoveWaypoint != null){
					onAddOrRemoveWaypoint.actionPerformed(null);
				}
			}
		});

		remove(addPanel);
		add(panel, "width 300!, height 230!, top");
		add(addPanel, "height 230!, top");

		waypointPanels.add(panel);
		validate();
		repaint();

		return panel;
	}

	public void clearWaypoints() {
		for (WaypointPanel panel : waypointPanels) {
			remove(panel);
		}
		waypointPanels.clear();
	}

	/**
	 * Builds the EcState object that is passed into the EvolutionChamber object
	 * as the destination. Includes all the waypoints.
	 * 
	 * @return the destination
	 */
	public EcState buildDestination() {
		//sort the waypoints by deadline
		List<WaypointPanel> sorted = new ArrayList<WaypointPanel>(waypointPanels);
		Collections.sort(sorted, new Comparator<WaypointPanel>() {
			@Override
			public int compare(WaypointPanel arg0, WaypointPanel arg1) {
				return arg0.getDeadline() - arg1.getDeadline();
			}
		});

		//build the EcState object
		EcState finalDestination = sorted.get(sorted.size() - 1).buildEcState();
		for (int i = 0; i < sorted.size() - 1; i++) {
			WaypointPanel panel = sorted.get(i);
			finalDestination.waypoints.add(panel.buildEcState());
		}
		return finalDestination;
	}
}
