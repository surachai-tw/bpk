package com.bpk.pgToSqlServer.ui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author surachai.tw
 */
public class ListSqlFileCellRenderer extends DefaultListCellRenderer
{
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus)
    {

        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        String displayText = value!=null ? value.toString() : "";

        label.setText(displayText);

        return label;
    }
}
