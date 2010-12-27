package com.fray.evo.ui.swingx2;

import static com.fray.evo.ui.swingx2.EcSwingXMain.messages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXTable;

import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.Upgrade;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.ZergUpgradeLibrary;

/**
 * Holds the units/buildings/upgrades for a waypoint.
 * 
 */
//TODO fix the quirkiness of the dropdowns in the second column
//TODO add validations to the quantity column
@SuppressWarnings("serial")
public class TargetTable extends JXTable {
	private JXComboBox unitComboBox;
	private JXComboBox upgradeComboBox;
	private JXComboBox buildingComboBox;

	public TargetTable() {
		setRowHeight(20);
		DefaultTableModel model = (DefaultTableModel) getModel();
		model.addColumn("Type");
		model.addColumn("Name");
		model.addColumn("Quantity");
		getColumnModel().getColumn(0).setPreferredWidth(20);
		getColumnModel().getColumn(2).setPreferredWidth(10);

		//		ListSelectionModel lsModel = getSelectionModel();
		//		lsModel.addListSelectionListener(new ListSelectionListener(){
		//			@Override
		//			public void valueChanged(ListSelectionEvent event) {
		//				Type type = (Type)getValueAt(event.getFirstIndex(), 0);
		//				JXComboBox comboBox = null;
		//				switch (type){
		//				case UNIT:
		//					comboBox = unitComboBox;
		//					break;
		//				case UPGRADE:
		//					comboBox = upgradeComboBox;
		//					break;
		//				case BUILDING:
		//					comboBox = buildingComboBox;
		//					break;
		//				}
		//				getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(comboBox));
		//			}
		//		});

		JXComboBox typeBox = new JXComboBox(Type.values());
		getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(typeBox));

		List<BuildableItem> items = new ArrayList<BuildableItem>();
		for (Unit unit : ZergUnitLibrary.getInstance().getList()) {
			if (unit == ZergUnitLibrary.Larva) {
				continue;
			}
			items.add(new BuildableItem(unit));
		}
		unitComboBox = new JXComboBox(items.toArray());
		unitComboBox.setRenderer(new ComboBoxRenderer());

		items.clear();
		for (Upgrade upgrade : ZergUpgradeLibrary.getInstance().getList()) {
			items.add(new BuildableItem(upgrade));
		}
		upgradeComboBox = new JXComboBox(items.toArray());
		upgradeComboBox.setRenderer(new ComboBoxRenderer());

		items.clear();
		for (Building building : ZergBuildingLibrary.getInstance().getList()) {
			items.add(new BuildableItem(building));
		}
		buildingComboBox = new JXComboBox(items.toArray());
		buildingComboBox.setRenderer(new ComboBoxRenderer());

		getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(unitComboBox));
		getColumnModel().getColumn(1).setCellRenderer(new ComboBoxRenderer());

		model.addTableModelListener(new TableModelListener() {
			// TODO: BUG - The value list is not updated when the type changes on a different row than the next click on the name column.
			@Override
			public void tableChanged(TableModelEvent e) {
				// The Type column was changed. We need to update
				// the list of targets in the Name column
				if (e.getColumn() == 0) {
					TableModel model = (TableModel) e.getSource();

					Type newType = (Type) model.getValueAt(e.getFirstRow(), e.getColumn());

					JXComboBox comboBox;
					if (newType == Type.UNIT) {
						comboBox = unitComboBox;
					} else if (newType == Type.UPGRADE) {
						comboBox = upgradeComboBox;
						model.setValueAt("", e.getFirstRow(), 2); //clear the "quantity" column, since this is an upgrade
					} else if (newType == Type.BUILDING) {
						comboBox = buildingComboBox;
					} else {
						comboBox = unitComboBox;
					}
					model.setValueAt(comboBox.getItemAt(0), e.getFirstRow(), 1);
					getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
				}
			}
		});
	}

	public void addRow(Buildable unit, int quantity) {
		addRow(unit, Integer.toString(quantity));
	}

	public void addRow(Buildable unit) {
		addRow(unit, "");
	}

	private void addRow(Buildable unit, String quantity) {
		clearSelection();
		DefaultTableModel model = (DefaultTableModel) getModel();
		Type type;
		if (unit instanceof Upgrade) {
			type = Type.UPGRADE;
		} else if (unit instanceof Building) {
			type = Type.BUILDING;
		} else {
			type = Type.UNIT;
		}
		model.addRow(new Object[] { type, new BuildableItem(unit), quantity });
	}

	public List<Row> getRows() {
		List<Row> rows = new ArrayList<Row>();

		DefaultTableModel model = (DefaultTableModel) getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			BuildableItem item = (BuildableItem) model.getValueAt(i, 1);
			String quantity = (String) model.getValueAt(i, 2);

			Integer quantityInt;
			if (quantity.isEmpty()) {
				quantityInt = null;
			} else {
				quantityInt = Integer.valueOf(quantity);
			}
			Row row = new Row(item.buildable, quantityInt);
			rows.add(row);
		}

		return rows;
	}

	//http://www.roseindia.net/java/example/java/swing/SadingRows.shtml
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		Component comp = super.prepareRenderer(renderer, row, col);
		if (!isCellSelected(row, col)) {
			//TODO find colors that match the current OS
			if (row % 2 == 1) {
				comp.setBackground(Color.lightGray);
			} else {
				comp.setBackground(Color.white);
			}
		}
		return comp;
	}

	private static enum Type {
		UNIT("Unit"), UPGRADE("Upgrade"), BUILDING("Building");

		public final String display;

		private Type(String display) {
			this.display = display;
		}

		@Override
		public String toString() {
			return display;
		}
	}

	private static class ComboBoxRenderer extends JLabel implements ListCellRenderer, TableCellRenderer {
		public ComboBoxRenderer() {
			setOpaque(true);
			setVerticalAlignment(CENTER);
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			BuildableItem item = (BuildableItem) value;

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			setIcon(item.imageIcon);
			setText(item.display);
			setFont(list.getFont());

			return this;
		}

		@Override
		public Component getTableCellRendererComponent(JTable list, Object value, boolean isSelected, boolean arg3, int arg4, int arg5) {
			BuildableItem item = (BuildableItem) value;

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			setIcon(item.imageIcon);
			setText(item.display);
			setFont(list.getFont());

			return this;
		}
	}

	private static class BuildableItem {
		private static final int MAX_SIZE = 16;
		public final Buildable buildable;
		public final String display;
		public final ImageIcon imageIcon;

		public BuildableItem(Buildable buildable) {
			this.buildable = buildable;
			this.display = messages.getString(buildable.getName());

			URL url = TargetTable.class.getResource("images/" + buildable.getName() + ".gif");
			ImageIcon imageIcon;
			if (url == null) {
				imageIcon = new ImageIcon("");
			} else {
				imageIcon = new ImageIcon(url);
			}

			Image image = imageIcon.getImage();

			//get scaled image dimensions
			int scaledWidth, scaledHeight;
			if (image.getWidth(null) > image.getHeight(null)) {
				scaledWidth = MAX_SIZE;
				scaledHeight = image.getHeight(null) * MAX_SIZE / image.getWidth(null);
			} else {
				scaledHeight = MAX_SIZE;
				scaledWidth = image.getWidth(null) * MAX_SIZE / image.getHeight(null);
			}

			//scale the image
			//see http://today.java.net/article/2007/03/30/perils-imagegetscaledinstance
			BufferedImage bufScaled = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D) bufScaled.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
			this.imageIcon = new ImageIcon(bufScaled);
		}

		@Override
		public String toString() {
			return display;
		}
	}

	/**
	 * Represents a row in this table.
	 * @author mike.angstadt
	 *
	 */
	public class Row {
		public final Buildable buildable;
		public final Integer quantity;

		public Row(Buildable buildable, Integer quantity) {
			this.buildable = buildable;
			this.quantity = quantity;
		}
	}
}
