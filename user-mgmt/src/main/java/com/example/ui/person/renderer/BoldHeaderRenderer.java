package com.example.ui.person.renderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class BoldHeaderRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 1L;
	
	private final TableCellRenderer originalRenderer;
	
	public BoldHeaderRenderer(TableCellRenderer originalRenderer) {
		super();
		this.originalRenderer = originalRenderer;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		JLabel renderer = (JLabel) originalRenderer.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column);
		renderer.setFont(renderer.getFont().deriveFont(Font.BOLD, 14.0f));
		if (column < 3) {
			renderer.setHorizontalAlignment(JLabel.LEADING);
		} else {
			renderer.setHorizontalAlignment(JLabel.CENTER);
		}
		return renderer;
	}
}