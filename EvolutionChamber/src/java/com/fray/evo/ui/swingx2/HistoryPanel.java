package com.fray.evo.ui.swingx2;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXPanel;

import com.fray.evo.EcBuildOrder;

/**
 * Displays all previously run simulations and allows the user to load or delete
 * them.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class HistoryPanel extends JXPanel {
	/**
	 * The list of simulations.
	 */
	private final JXList list;

	/**
	 * Deletes the selected simulation.
	 */
	private final JXButton remove;

	/**
	 * Deletes all simulations.
	 */
	private final JXButton clear;

	/**
	 * Constructor.
	 * @param parent the parent window
	 * @param history the previously run build orders
	 * @param callback a callback that allows the parent window to handle certain events
	 */
	public HistoryPanel(final Component parent, final List<EcBuildOrder> history, final Callback callback) {
		setLayout(new MigLayout("fillx, ins 0"));

		//create the list
		DefaultListModel model = new DefaultListModel();
		for (EcBuildOrder bo : history) {
			model.addElement(new Item(bo));
		}
		list = new JXList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new HistoryListCellRenderer());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (list.isEnabled()) {
					int index = list.locationToIndex(event.getPoint());
					if (index >= 0){
						ListModel dlm = list.getModel();
						Item item = (Item) dlm.getElementAt(index);
						list.ensureIndexIsVisible(index);
	
						if (event.getClickCount() == 1) {
							//single click
							callback.loadBuildOrder(item.buildOrder);
						} else if (event.getClickCount() == 2) {
							//double click
							callback.loadBuildOrderAndWaypoints(item.buildOrder);
					}
					}
				}
			}
		});
		JScrollPane sp = new JScrollPane(list);
		add(sp, "growx, growy, width 100%, height 100%, wrap");

		//create a help label describing this panel
		WhatsThisLabel label = new WhatsThisLabel("Displays all previously run simulations.\n * Click once to see the simulation's generated build order.\n * Double click to load the simulation.");
		add(label, "gapright 15, split 3");

		//create the remove button
		remove = new JXButton("Remove");
		remove.setToolTipText("Delete the selected simulation.");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Item selected = (Item) list.getSelectedValue();
				if (selected != null) {
					callback.deleteBuildOrder(selected.buildOrder);

					DefaultListModel model = (DefaultListModel) list.getModel();
					model.removeElement(selected);
				}
			}
		});
		add(remove);

		//create the clear button
		clear = new JXButton("Clear");
		clear.setToolTipText("Delete all simulations.");
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				int choice = JOptionPane.showConfirmDialog(parent, "Are you sure you want to clear the entire History?", "Clear History?", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					callback.clearHistory();

					DefaultListModel model = (DefaultListModel) list.getModel();
					model.clear();
				}
			}
		});
		add(clear);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (Component c : getComponents()) {
			if (!(c instanceof WhatsThisLabel)) {
				c.setEnabled(enabled);
			}
		}
		list.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	/**
	 * Allows the parent window to respond to actions that take place in the
	 * HistoryPanel.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	public interface Callback {
		/**
		 * Called when a build order is loaded from history.
		 * 
		 * @param buildOrder
		 */
		void loadBuildOrder(EcBuildOrder buildOrder);

		/**
		 * Called when a build order and its waypoints are loaded from history.
		 * 
		 * @param buildOrder
		 */
		void loadBuildOrderAndWaypoints(EcBuildOrder buildOrder);

		/**
		 * Called when a build order is deleted.
		 * 
		 * @param buildOrder the build order to delete.
		 */
		void deleteBuildOrder(EcBuildOrder buildOrder);

		/**
		 * Called when the entire history is cleared.
		 */
		void clearHistory();
	}

	/**
	 * Represents a row in the history list.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private static class Item {
		public final EcBuildOrder buildOrder;

		public Item(EcBuildOrder buildOrder) {
			this.buildOrder = buildOrder;
		}

		@Override
		public String toString() {
			String s = buildOrder.toString();

			String name = buildOrder.getName();
			if (name != null) {
				s = name + " - " + s;
			}

			return s;
		}
	}

	/**
	 * Renders each row in the history list.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private static class HistoryListCellRenderer extends JLabel implements ListCellRenderer {
		public HistoryListCellRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Item item = (Item) value;

			//create text to display
			StringBuilder display = new StringBuilder();
			display.append("<html>");
			if (item.buildOrder.getName() != null) {
				display.append("<b>");
				display.append(Utils.escapeHtml(item.buildOrder.getName()));
				display.append("</b> - ");
			}
			display.append(Utils.escapeHtml(item.buildOrder.toString()));
			display.append("</html>");
			setText(display.toString());

			//set colors depending on whether the row is selected or not
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			return this;
		}
	}
}
