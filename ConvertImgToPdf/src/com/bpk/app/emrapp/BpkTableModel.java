package com.bpk.app.emrapp;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import java.util.Locale;
import java.text.*;
import java.sql.*;

/**
 * Title:        GUI Learning
 * Description:  Class use to make a graphical display ResultSet and manage data
 * from ResultSet
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Surachai Torwong
 * @version 1.0
 */
public class BpkTableModel extends AbstractTableModel
{

    public String columnNames[] = new String[0];
    public boolean[] editableColumns;
    public Vector dataRows = new Vector();
    public Vector idRows = new Vector();

    public String[] getColumnNames()
    {
        return this.columnNames;
    }

    /**
     * Make the data in the resultset available through the TableModel interface
     * @param ResultSet rs
     * @param rs
     * @exception SQLException sqlex
     */
    public void setResultSet(ResultSet rs)
    {
        try
        {
            // �纪�Դ�ͧ Class
            String tmpClass;

            ResultSetMetaData metadata = rs.getMetaData();

            int columns = metadata.getColumnCount();
            columnNames = new String[columns];
            editableColumns = new boolean[columns];

            for(int i = 0; i < columns; i++)
            {
                columnNames[i] = metadata.getColumnLabel(i + 1);
                editableColumns[i] = false;
            }
            dataRows = new Vector();
            Object[] rowData;
            while(rs.next())
            {
                rowData = new Object[columns];

                for(int i = 0; i < columns; i++)
                {
                    if(rs.getObject(i + 1) != null)
                    {
                        tmpClass = rs.getObject(i + 1).getClass().toString();

                        //System.out.println(tmpClass);
                        if(tmpClass.equals("class java.lang.String"))
                        {
                            //System.out.println("String");
                            rowData[i] = rs.getString(i + 1);
                        }
                        else if(tmpClass.equals("class java.lang.Integer"))
                        {
                            //System.out.println("Integer");
                            rowData[i] = new Integer(rs.getInt(i + 1));
                        }
                        else if(tmpClass.equals("class java.lang.Double"))
                        {
                            //System.out.println("Double");
                            NumberFormat nf = NumberFormat.getInstance();
                            nf.setMinimumFractionDigits(1);
                            nf.setMaximumFractionDigits(1);
                            nf.setGroupingUsed(false);
                            rowData[i] = new String(nf.format(rs.getDouble(i + 1)));
                        }
                        else if(tmpClass.equals("class java.lang.Boolean"))
                        {
                            //System.out.println("Boolean");
                            rowData[i] = new Boolean(rs.getBoolean(i + 1));
                        }
                        else if(tmpClass.equals("class java.sql.Timestamp"))
                        {
                            //System.out.println("Timestamp");
                            // �ѹ����ͧ��Ǩ�ͺ����� NULL ���ͻ���� ����� NULL ���ͧ����ѹ���� Object �� NULL ����
                            java.sql.Timestamp ts = rs.getTimestamp(i + 1);
                            if(ts != null)
                            {
                                java.util.Date d = (java.util.Date) ts;
                                java.util.Calendar cal = java.util.Calendar.getInstance(Locale.US);
                                cal.setTime(d);
                                cal.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR) + 543);
                                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault());
                                rowData[i] = df.format(cal.getTime());
                            }
                            else
                            {
                                rowData[i] = new String("");
                            }
                        }
                        else
                        {
                            //System.out.println("Object");
                            rowData[i] = rs.getObject(i + 1);
                        }
                    }
                    else
                    {
                        rowData[i] = "";
                    }
                }
                dataRows.addElement(rowData);
            }
            fireTableChanged(null);
            tmpClass = null;
        }
        catch(SQLException sqlex)
        {
            sqlex.printStackTrace();
        }
    }

    /**
     * ��Ҫԡ����á(Column �á) ������ǨС����� PK
     */
    public void setTableModel(ResultSet rs)
    {
        try
        {
            // �纪�Դ�ͧ Class
            String tmpClass;

            ResultSetMetaData metadata = rs.getMetaData();

            // ���Ѻ Row ID
            int columns = metadata.getColumnCount() - 1;
            columnNames = new String[columns];
            editableColumns = new boolean[columns];

            for(int i = 0; i < columns; i++)
            {
                /*
                 * Meta data ������Ѻ�ҡ 1 �ѧ��� +1
                 * �����Ѻ Row ID �ѧ��� +1
                 * ��ҡѺ��ͧ +2
                 */
                columnNames[i] = metadata.getColumnLabel(i + 2);
                editableColumns[i] = false;
            }

            dataRows = new Vector();
            Object[] rowData;
            idRows = new Vector();
            String rowID;

            while(rs.next())
            {
                rowData = new Object[columns];
                rowID = new String();
                // Column �á set ������ ID
                rowID = rs.getString(1);
                // ���������繢ͧ Data
                for(int i = 0; i < columns; i++)
                {
                    if(rs.getObject(i + 2) != null)
                    {
                        /*
                         * Data ������Ѻ�ҡ 1 �ѧ��� +1
                         * �����Ѻ Row ID �ѧ��� +1
                         * ��ҡѺ��ͧ +2
                         */
                        tmpClass = rs.getObject(i + 2).getClass().toString();

                        //System.out.println(rs.getObject(i+1).getClass());
                        if(tmpClass.equals("class java.lang.String"))
                        {
                            //System.out.println("String");
                            rowData[i] = rs.getString(i + 2);
                        }
                        else if(tmpClass.equals("class java.lang.Integer"))
                        {
                            //System.out.println("Integer");
                            rowData[i] = new Integer(rs.getInt(i + 2));
                        }
                        else if(tmpClass.equals("class java.lang.Double"))
                        {
                            //System.out.println("Double");
                            NumberFormat nf = NumberFormat.getInstance();
                            nf.setMinimumFractionDigits(1);
                            nf.setMaximumFractionDigits(1);
                            nf.setGroupingUsed(false);
                            rowData[i] = new String(nf.format(rs.getDouble(i + 2)));
                        }
                        else if(tmpClass.equals("class java.lang.Boolean"))
                        {
                            //System.out.println("Boolean");
                            rowData[i] = new Boolean(rs.getBoolean(i + 2));
                        }
                        else if(tmpClass.equals("class java.sql.Timestamp"))
                        {
                            //System.out.println("Timestamp");
                            java.sql.Timestamp ts = rs.getTimestamp(i + 2);
                            if(ts != null)
                            {
                                java.util.Date d = (java.util.Date) ts;
                                java.util.Calendar cal = java.util.Calendar.getInstance(Locale.US);
                                cal.setTime(d);
                                cal.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR) + 543);
                                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault());
                                rowData[i] = df.format(cal.getTime());
                            }
                            else
                            {
                                rowData[i] = new String("");
                            }
                        }
                        else
                        {
                            //System.out.println("Object");
                            rowData[i] = rs.getObject(i + 2);
                        }
                    }
                    else
                    {
                        rowData[i] = "";
                    }
                }
                idRows.addElement(rowID);
                dataRows.addElement(rowData);
            }
            fireTableChanged(null);
            tmpClass = null;
        }
        catch(SQLException sqlex)
        {
            sqlex.printStackTrace();
        }
    }

    /** �红����������ͧ����͹ */
    public void popData(int column)
    {
        try
        {
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /** ��Ң��������ͧ���ŧ Table �ա���� */
    public void pushData(int column)
    {
        try
        {
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public String getRowID(int rowSelected)
    {
        try
        {
            if(rowSelected > -1 && rowSelected <= idRows.size() - 1)
            {
                return (String) idRows.elementAt(rowSelected);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public void createHeader(String[] header)
    {
        int len = header.length;

        columnNames = new String[len];
        for(int i = 0; i < len; i++)
        {
            columnNames[i] = header[i];
        }
        fireTableChanged(null);
    }

    public void editHeader(String[] header)
    {
        int len = columnNames.length;

        for(int i = 0; i < len; i++)
        {
            columnNames[i] = header[i];
        }
        fireTableChanged(null);
    }

    public void reset()
    {
        if(idRows != null)
        {
            idRows.removeAllElements();
        }
        if(dataRows != null)
        {
            dataRows.removeAllElements();
        }
        fireTableChanged(null);
    }

    /**
     * ��Ǩ�ͺ Index �ͧ������
     */
    public int indexOfID(String id)
    {
        return idRows.indexOf(id);
    }

    /**
     * Add ������ �������� row ID
     */
    public void addRowData(Object[] rowData)
    {
        dataRows.addElement(rowData);
        fireTableChanged(null);
    }

    /**
     * Add ������ ����� row ID
     */
    public void addRowData(String rowID, Object[] rowData)
    {
        idRows.addElement(rowID);
        addRowData(rowData);
        fireTableChanged(null);
    }

    public Object getRowDataAt(int rowSelected)
    {
        try
        {
            if(rowSelected > -1 && rowSelected <= dataRows.size() - 1)
            {
                return dataRows.elementAt(rowSelected);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Remove ������ ���ç�Ѻ Row ID
     */
    public void removeRowID(String rowID)
    {
        int idx = idRows.indexOf(rowID);

        idRows.removeElementAt(idx);
        removeDataAt(idx);
        fireTableChanged(null);
    }

    public void removeDataAt(int idx)
    {
        dataRows.removeElementAt(idx);
        fireTableChanged(null);
    }

    /**
     * Remove �����ŷ�����
     */
    public void removeAllRow()
    {
        idRows.removeAllElements();
        dataRows.removeAllElements();
        fireTableChanged(null);
    }

    public void editDataAt(String val, int row, int col)
    {
        try
        {
            Object[] obj = ((Object[]) (dataRows.elementAt(row)));
            obj[col] = (Object) val;

            dataRows.removeElementAt(row);
            dataRows.add(row, obj);
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
        }
    }

    /**
     * Return number of columns
     * @return int number of columns
     */
    public int getColumnCount()
    {
        return columnNames.length;
    }

    /**
     * Return number of rows
     * @return int number of rows
     */
    public int getRowCount()
    {
        if(dataRows == null)
        {
            return 0;
        }
        else
        {
            return dataRows.size();
        }
    }

    /** Set ������Ѻ Model */
    public void setValueAt(Object obj, int row, int column)
    {
        ((Object[]) (dataRows.elementAt(row)))[column] = obj;
    }

    /**
     * Return the value at position row, column
     * @param int row, int column
     * @param row
     * @param column
     * @return Object - cast before use
     */
    public Object getValueAt(int row, int column)
    {
        try
        {
            return ((Object[]) (dataRows.elementAt(row)))[column];
        }
        catch(Exception ex)
        {
        }
        return "";
    }

    /** Get column class */
    public Class getColumnClass(int column)
    {
        //henbe create//////////
        Object c = ((Object[]) (dataRows.elementAt(0)))[column];

        if(c == null)
        {
            return null;
        }
        //////////////////////
        return c.getClass();
    }

    /**
     * Return the name for the column
     * @param int column
     * @param column
     * @return String
     */
    public String getColumnName(int column)
    {
        return columnNames[column] == null ? "No Name" : columnNames[column];
    }

    /** Column �˹��� Edit ���ҧ */
    public void setCellEditable(int column, boolean editable)
    {
        try
        {
            editableColumns[column] = editable;
        }
        catch(Exception ex)
        {
        }
    }

    /** Cell �˹��� Edit ���ҧ - ���� Column 㹡�þԨ�ó� */
    public boolean isCellEditable(int r, int c)
    {
        try
        {
            return editableColumns[c];
        }
        catch(Exception ex)
        {
        }
        return false;
    }

    /**
     * Convert ������� Model ����� String
     * @author ��ê�� ���ǧ��
     */
    public String toString()
    {
        try
        {
            int numRows = getRowCount();
            int numCols = getColumnCount();
            // �� StringBuffer
            StringBuffer text = new StringBuffer();

            // ��� Column
            for(int j = 0; j < numCols; j++)
            {
                if(j != numCols - 1)
                {
                    text.append(getColumnName(j));
                    text.append("\t");
                }
                else
                {
                    text.append(getColumnName(j));
                }
            }
            text.append("\n");

            // Data
            for(int i = 0; i < numRows; i++)
            {
                for(int j = 0; j < numCols; j++)
                {
                    //System.out.println(getValueAt(i, j));
                    if(j != numCols - 1)
                    {
                        text.append(getValueAt(i, j));
                        text.append("\t");
                    }
                    else
                    {
                        text.append(getValueAt(i, j));
                    }
                }

                if(i != numRows - 1)
                {
                    text.append("\n");
                }
            }

            return text.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }

    public void setEditableMode()
    {
        editableColumns = new boolean[columnNames.length];

        for(int i = 0; i < columnNames.length; i++)
        {
            editableColumns[i] = false;
        }
    }
}
