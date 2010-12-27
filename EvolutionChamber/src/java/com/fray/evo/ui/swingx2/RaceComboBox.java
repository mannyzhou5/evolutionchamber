package com.fray.evo.ui.swingx2;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.jdesktop.swingx.JXComboBox;

import com.fray.evo.util.Race;

/**
 * A dropdown list that displays all the races that EC supports.
 * @author mike.angstadt
 *
 */
@SuppressWarnings("serial")
public class RaceComboBox extends JXComboBox {
	public RaceComboBox() {
		setRenderer(new ComboBoxRenderer());

		Item item = new Item(Race.Zerg, new ImageIcon(RaceComboBox.class.getResource("images/zerg.png")));
		addItem(item);
		//		item = new Item(Race.Protoss, new ImageIcon("images/protoss.png"));
		//		addItem(item);
		//		item = new Item(Race.Terran, new ImageIcon("images/terran.png"));
		//		addItem(item);
	}

	/**
	 * Gets the selected race.
	 * @return
	 */
	public Race getSelectedRace() {
		Item item = (Item) getSelectedItem();
		return item.race;
	}

	/**
	 * Represents an item in this dropdown list.
	 * @author mike.angstadt
	 *
	 */
	private static class Item {
		public final Race race;
		public final String text;
		public final ImageIcon image;

		public Item(Race race, ImageIcon image) {
			this.race = race;
			this.text = race.getName();
			this.image = image;
		}
		
		@Override
		public String toString(){
			return text;
		}
	}

	private static class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		public ComboBoxRenderer() {
			setOpaque(true);
			setVerticalAlignment(CENTER);
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Item item = (Item) value;

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			setIcon(item.image);
			setText(item.text);
			setFont(list.getFont());

			return this;
		}
	}
}
