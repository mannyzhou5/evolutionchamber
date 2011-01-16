package com.fray.evo.ui.swingx2;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXTextField;

import com.fray.evo.EcState;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.Upgrade;
import com.fray.evo.util.ZergLibrary;

/**
 * Represents a waypoint.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class WaypointPanel extends JPanel {
	/**
	 * Displays the list of targets
	 */
	private final TargetTable targetTable;

	/**
	 * Adds a new target.
	 */
	private final JXButton add;

	/**
	 * Removes all selected targets.
	 */
	private final JXButton delete;

	/**
	 * Holds the name of this waypoint.
	 */
	private final JXTextField name;

	/**
	 * Holds the deadline time of this waypoint.
	 */
	private final TimeTextField deadline;

	/**
	 * Removes this waypoint.
	 */
	private final JXButton close;

	public WaypointPanel(String name, int deadline) {
		setLayout(new MigLayout("ins 5"));

		//TODO find colors that match the current OS
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		setBackground(new Color(192, 192, 192));

		this.name = new JXTextField("Name");
		this.name.setText(name);
		add(this.name, "width 100%");

		this.deadline = new TimeTextField();
		this.deadline.setToolTipText("Deadline");
		this.deadline.setText(Utils.formatAsTime(deadline));
		add(this.deadline, "width 75!");

		close = new JXButton(new ImageIcon(WaypointPanel.class.getResource("images/close.png")));
		close.setToolTipText("Remove this waypoint.");
		add(close, "wrap");

		targetTable = new TargetTable();
		JScrollPane sp = new JScrollPane(targetTable);
		add(sp, "span 3, wrap, growx, growy, height 100%");

		add = new JXButton("+");
		add.setToolTipText("Add a target to the waypoint.");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				targetTable.addRow(ZergLibrary.Drone, 1);
			}
		});
		add(add, "split 2, span 3");

		delete = new JXButton("-");
		delete.setToolTipText("Remove the selected targets from the waypoint.");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//remove all selected rows
				int selectedRows[] = targetTable.getSelectedRows();
				Arrays.sort(selectedRows);
				DefaultTableModel model = (DefaultTableModel) targetTable.getModel();
				for (int i = 0; i < selectedRows.length; i++) {
					int selectedRow = selectedRows[i] - i;
					model.removeRow(selectedRow);
				}
				targetTable.clearSelection();
			}
		});
		add(delete);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (Component c : getComponents()) {
			if (!(c instanceof WhatsThisLabel)) {
				c.setEnabled(enabled);
			}
		}
		targetTable.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	/**
	 * Adds an action to perform when this waypoint is removed.
	 * 
	 * @param action
	 */
	public void addOnRemoveListener(ActionListener action) {
		close.addActionListener(action);
	}

	public String getWaypointName() {
		return name.getText();
	}

	public void setDeadline(int seconds) {
		deadline.setText(Utils.formatAsTime(seconds));
	}

	public void addTarget(Unit unit, int quantity) {
		targetTable.addRow(unit, quantity);
	}

	public void addTarget(Building building, int quantity) {
		targetTable.addRow(building, quantity);
	}

	public void addTarget(Upgrade upgrade) {
		targetTable.addRow(upgrade);
	}

	public int getDeadline() {
		return Utils.seconds(deadline.getText());
	}

	/**
	 * Creates an {@link EcState} object based on the information in this panel.
	 * 
	 * @return
	 */
	public EcState buildEcState() {
		EcState waypoint = EcState.defaultDestination();
		waypoint.targetSeconds = getDeadline();

		for (TargetTable.Row row : targetTable.getRows()) {
			Buildable buildable = row.buildable;
			int quantity = row.quantity != null ? row.quantity : 0;
			if (buildable instanceof Unit) {
				Unit unit = (Unit) buildable;
				waypoint.SetUnits(unit, quantity);
			} else if (buildable instanceof Building) {
				Building building = (Building) buildable;
				waypoint.SetBuilding(building, quantity);
			} else if (buildable instanceof Upgrade) {
				Upgrade upgrade = (Upgrade) buildable;
				waypoint.AddUpgrade(upgrade);
			}
		}

		return waypoint;
	}
}
