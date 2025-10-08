package com.example.ui.person.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
/**
 * A mouse listener for a JTable component.
 * @author www.codejava.neet
 *
 */
public class TableRightClickListener extends MouseAdapter {
     
    private JTable table;
     
    public TableRightClickListener(JTable table) {
        this.table = table;
    }
   
    @Override
    public void mouseClicked(MouseEvent event) {
    	// selects the row at which point the mouse is clicked
        Point point = event.getPoint();
        int currentRow = table.rowAtPoint(point);
        //table.setRowSelectionInterval(currentRow, currentRow);
        
        if (SwingUtilities.isRightMouseButton(event)) {
        	JOptionPane.showMessageDialog(table, 
        			String.format("You managed to right-click on row %d", 
        					currentRow + 1));        	
        }
    }
}
