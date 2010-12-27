package com.fray.evo.ui.swingx2;

import java.awt.Color;
import java.awt.Component;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;

@SuppressWarnings("serial")
public class OutputTable extends JXTable {
	public OutputTable(){
		setEditable(false);
		
		DefaultTableModel model = (DefaultTableModel) getModel();
		model.addColumn("Time");
		model.addColumn("Supply");
		model.addColumn("Minerals");
		model.addColumn("Gas");
		model.addColumn("Action");
	}
	
	public void clear(){
		DefaultTableModel model = (DefaultTableModel) getModel();
		model.setRowCount(0);
	}
	
	public void addRow(int seconds, int supply, int maxSupply, int minerals, int gas, String action){
		DefaultTableModel model = (DefaultTableModel) getModel();
		model.addRow(new Object[]{Utils.formatAsTime(seconds), supply + "/" + maxSupply, minerals, gas, action});
	}
	
	//http://www.roseindia.net/java/example/java/swing/SadingRows.shtml
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		Component comp = super.prepareRenderer(renderer, row, col);
		if (!isCellSelected(row, col)){
			//TODO find colors that match the current OS
			if (row % 2 == 1) {
				comp.setBackground(Color.lightGray);
			} else {
				comp.setBackground(Color.white);
			}
		}
		return comp;
	}
}
