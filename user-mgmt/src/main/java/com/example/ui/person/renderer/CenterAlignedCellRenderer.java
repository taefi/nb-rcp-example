package com.example.ui.person.renderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CenterAlignedCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		JLabel defaultRenderer = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		return defaultRenderer;
	}
}
