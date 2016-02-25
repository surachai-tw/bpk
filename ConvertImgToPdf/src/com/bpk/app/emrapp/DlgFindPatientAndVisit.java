/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgFindPatientAndVisit.java
 *
 * Created on Feb 10, 2016, 1:17:06 PM
 */
package com.bpk.app.emrapp;

import com.bpk.core.emrcore.dao.DocScanDAO;
import com.bpk.core.emrcore.dao.DocScanDAOFactory;
import com.bpk.persistence.emrdto.BpkDocumentScanVO;
import com.bpk.persistence.emrdto.PatientVO;
import com.bpk.persistence.emrdto.VisitVO;
import com.bpk.utility.Utility;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author SURACHAI.TO
 */
public class DlgFindPatientAndVisit extends javax.swing.JDialog
{

    BpkDocumentScanVO aBpkDocumentScanVO = null;

    /** Creates new form DlgFindPatientAndVisit */
    public DlgFindPatientAndVisit(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        aTblMdlPatient = new com.bpk.app.emrapp.BpkTableModel();
        aTblMdlVisit = new com.bpk.app.emrapp.BpkTableModel();
        aPnlTop = new javax.swing.JPanel();
        aLblHnByManual = new javax.swing.JLabel();
        aTxtHnByManual = new javax.swing.JTextField();
        aLblPatientByManual = new javax.swing.JLabel();
        aTxtPatientNameByManual = new javax.swing.JTextField();
        aBtnFindPatient = new javax.swing.JButton();
        aTxtLimit = new javax.swing.JTextField();
        aPnlCenter = new javax.swing.JPanel();
        aSplitPane = new javax.swing.JSplitPane();
        aPnlPatientList = new javax.swing.JPanel();
        aScrlPatient = new javax.swing.JScrollPane();
        aTblPatient = new javax.swing.JTable();
        aPnlVisitList = new javax.swing.JPanel();
        aScrlVisit = new javax.swing.JScrollPane();
        aTblVisit = new javax.swing.JTable();
        aPnlButton = new javax.swing.JPanel();
        aBtnSelectVn = new javax.swing.JButton();
        aBtnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find Patient");

        aPnlTop.setLayout(new java.awt.GridBagLayout());

        aLblHnByManual.setFont(new java.awt.Font("Tahoma", 0, 12));
        aLblHnByManual.setText("HN:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlTop.add(aLblHnByManual, gridBagConstraints);

        aTxtHnByManual.setFont(new java.awt.Font("Tahoma", 0, 12));
        aTxtHnByManual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aTxtHnByManual.setMinimumSize(new java.awt.Dimension(120, 24));
        aTxtHnByManual.setPreferredSize(new java.awt.Dimension(120, 24));
        aTxtHnByManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aTxtHnByManualActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 11, 0);
        aPnlTop.add(aTxtHnByManual, gridBagConstraints);

        aLblPatientByManual.setFont(new java.awt.Font("Tahoma", 0, 12));
        aLblPatientByManual.setText("Patient:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlTop.add(aLblPatientByManual, gridBagConstraints);

        aTxtPatientNameByManual.setFont(new java.awt.Font("Tahoma", 0, 12));
        aTxtPatientNameByManual.setMinimumSize(new java.awt.Dimension(240, 24));
        aTxtPatientNameByManual.setPreferredSize(new java.awt.Dimension(240, 24));
        aTxtPatientNameByManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aTxtPatientNameByManualActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 11, 11);
        aPnlTop.add(aTxtPatientNameByManual, gridBagConstraints);

        aBtnFindPatient.setText("Find");
        aBtnFindPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnFindPatientActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 11, 0);
        aPnlTop.add(aBtnFindPatient, gridBagConstraints);

        aTxtLimit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aTxtLimit.setText("100");
        aTxtLimit.setMinimumSize(new java.awt.Dimension(60, 24));
        aTxtLimit.setPreferredSize(new java.awt.Dimension(60, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 5, 11, 11);
        aPnlTop.add(aTxtLimit, gridBagConstraints);

        getContentPane().add(aPnlTop, java.awt.BorderLayout.NORTH);

        aPnlCenter.setLayout(new java.awt.BorderLayout());

        aSplitPane.setDividerLocation(400);
        aSplitPane.setDividerSize(2);

        aPnlPatientList.setMinimumSize(new java.awt.Dimension(400, 250));
        aPnlPatientList.setPreferredSize(new java.awt.Dimension(400, 251));
        aPnlPatientList.setLayout(new java.awt.GridBagLayout());

        aTblPatient.setFont(new java.awt.Font("Tahoma", 0, 14));
        aTblPatient.setModel(aTblMdlPatient);
        aTblPatient.setRowHeight(21);
        aTblPatient.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        aTblPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                aTblPatientMouseReleased(evt);
            }
        });
        aScrlPatient.setViewportView(aTblPatient);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 4, 4);
        aPnlPatientList.add(aScrlPatient, gridBagConstraints);

        aSplitPane.setLeftComponent(aPnlPatientList);

        aPnlVisitList.setMinimumSize(new java.awt.Dimension(400, 250));
        aPnlVisitList.setPreferredSize(new java.awt.Dimension(400, 250));
        aPnlVisitList.setLayout(new java.awt.GridBagLayout());

        aScrlVisit.setFont(new java.awt.Font("Tahoma", 0, 14));

        aTblVisit.setFont(new java.awt.Font("Tahoma", 0, 14));
        aTblVisit.setModel(aTblMdlVisit);
        aTblVisit.setRowHeight(21);
        aTblVisit.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        aScrlVisit.setViewportView(aTblVisit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 4, 4);
        aPnlVisitList.add(aScrlVisit, gridBagConstraints);

        aSplitPane.setRightComponent(aPnlVisitList);

        aPnlCenter.add(aSplitPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(aPnlCenter, java.awt.BorderLayout.CENTER);

        aPnlButton.setLayout(new java.awt.GridBagLayout());

        aBtnSelectVn.setFont(new java.awt.Font("Tahoma", 1, 11));
        aBtnSelectVn.setText("Select VN");
        aBtnSelectVn.setToolTipText("");
        aBtnSelectVn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnSelectVn.setMaximumSize(new java.awt.Dimension(84, 32));
        aBtnSelectVn.setMinimumSize(new java.awt.Dimension(84, 32));
        aBtnSelectVn.setPreferredSize(new java.awt.Dimension(84, 32));
        aBtnSelectVn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnSelectVnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlButton.add(aBtnSelectVn, gridBagConstraints);

        aBtnCancel.setFont(new java.awt.Font("Tahoma", 1, 11));
        aBtnCancel.setText("Cancel");
        aBtnCancel.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnCancel.setMaximumSize(new java.awt.Dimension(84, 32));
        aBtnCancel.setMinimumSize(new java.awt.Dimension(84, 32));
        aBtnCancel.setPreferredSize(new java.awt.Dimension(84, 32));
        aBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(12, 5, 11, 11);
        aPnlButton.add(aBtnCancel, gridBagConstraints);

        getContentPane().add(aPnlButton, java.awt.BorderLayout.PAGE_END);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-840)/2, (screenSize.height-320)/2, 840, 320);
    }// </editor-fold>//GEN-END:initComponents

    public static BpkDocumentScanVO showDlgFindPatientAndVisit(JFrame frm, PatientVO pPatientVO)
    {
        BpkDocumentScanVO aBpkDocumentScanVO = null;
        DlgFindPatientAndVisit aDlgFindPatientAndVisit = new DlgFindPatientAndVisit(frm, true);
        aDlgFindPatientAndVisit.setPatientVO(pPatientVO);
        aDlgFindPatientAndVisit.findPatient();
        aDlgFindPatientAndVisit.setVisible(true);
        aBpkDocumentScanVO = aDlgFindPatientAndVisit.getBpkDocumentScanVO();
        aDlgFindPatientAndVisit = null;

        return aBpkDocumentScanVO;
    }

    private void aBtnFindPatientActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnFindPatientActionPerformed
    {//GEN-HEADEREND:event_aBtnFindPatientActionPerformed
        findPatient();
}//GEN-LAST:event_aBtnFindPatientActionPerformed

    public void findPatient()
    {
        this.aTblMdlPatient.removeAllRow();
        DocScanDAO aDocScanDAO = DocScanDAOFactory.newDocScanDAO();
        try
        {
            List listPatientVO = aDocScanDAO.searchPatient(this.aTxtHnByManual.getText().trim(), this.aTxtPatientNameByManual.getText().trim(), "100");

            String[] header = new String[4];
            header[0] = "HN";
            header[1] = "Patient Name";
            header[2] = "Age";
            header[3] = "Gender";
            this.aTblMdlPatient.createHeader(header);

            for (int i = 0, sizei = listPatientVO.size(); i < sizei; i++)
            {
                PatientVO aPatientVO = (PatientVO) listPatientVO.get(i);
                Object[] rowData = new Object[header.length];
                rowData[0] = aPatientVO.getHn();
                rowData[1] = aPatientVO.getPatientName();
                rowData[2] = aPatientVO.getAge();
                rowData[3] = aPatientVO.getFixGenderDescription();

                this.aTblMdlPatient.addRowData(aPatientVO.getObjectID(), rowData);
            }

            this.aTblPatient.getColumnModel().getColumn(0).setPreferredWidth(100);
            this.aTblPatient.getColumnModel().getColumn(1).setPreferredWidth(200);
            this.aTblPatient.getColumnModel().getColumn(2).setPreferredWidth(80);
            this.aTblPatient.getColumnModel().getColumn(3).setPreferredWidth(30);

            this.aTblMdlPatient.fireTableDataChanged();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            aDocScanDAO = null;
        }
    }

    private void aTxtHnByManualActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aTxtHnByManualActionPerformed
    {//GEN-HEADEREND:event_aTxtHnByManualActionPerformed
        aBtnFindPatientActionPerformed(evt);
    }//GEN-LAST:event_aTxtHnByManualActionPerformed

    private void aTxtPatientNameByManualActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aTxtPatientNameByManualActionPerformed
    {//GEN-HEADEREND:event_aTxtPatientNameByManualActionPerformed
        aBtnFindPatientActionPerformed(evt);
    }//GEN-LAST:event_aTxtPatientNameByManualActionPerformed

    private void aTblPatientMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_aTblPatientMouseReleased
    {//GEN-HEADEREND:event_aTblPatientMouseReleased
        if (!evt.isPopupTrigger())
        {
            this.aTblMdlVisit.removeAllRow();

            int row = this.aTblPatient.getSelectedRow();
            if (row != -1)
            {
                String patientId = (String) this.aTblMdlPatient.getRowID(row);

                DocScanDAO aDao = DocScanDAOFactory.newDocScanDAO();
                try
                {
                    List listVisitVO = aDao.listVisitByPatientId(patientId);

                    String[] header = new String[3];
                    header[0] = "VN";
                    header[1] = "Visit Date";
                    header[2] = "PDx";
                    this.aTblMdlVisit.createHeader(header);

                    for (int i = 0, sizei = listVisitVO.size(); i < sizei; i++)
                    {
                        VisitVO aVisitVO = (VisitVO) listVisitVO.get(i);
                        Object[] rowData = new Object[header.length];
                        rowData[0] = aVisitVO.getVn();
                        rowData[1] = Utility.formatDateForDisplay(aVisitVO.getVisitDate()) + " " + aVisitVO.getVisitTime();
                        rowData[2] = aVisitVO.getPDx();

                        this.aTblMdlVisit.addRowData(aVisitVO.getObjectID(), rowData);
                    }

                    this.aTblVisit.getColumnModel().getColumn(0).setPreferredWidth(100);
                    this.aTblVisit.getColumnModel().getColumn(1).setPreferredWidth(200);
                    this.aTblVisit.getColumnModel().getColumn(2).setPreferredWidth(200);

                    this.aTblMdlVisit.fireTableDataChanged();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_aTblPatientMouseReleased

    private void aBtnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnCancelActionPerformed
    {//GEN-HEADEREND:event_aBtnCancelActionPerformed
        this.aBpkDocumentScanVO = null;
        this.dispose();
    }//GEN-LAST:event_aBtnCancelActionPerformed

    private void aBtnSelectVnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnSelectVnActionPerformed
    {//GEN-HEADEREND:event_aBtnSelectVnActionPerformed
        int row = this.aTblVisit.getSelectedRow();
        if (row != -1)
        {
            String visitId = (String) this.aTblMdlVisit.getRowID(row);

            this.aBpkDocumentScanVO = new BpkDocumentScanVO();

            DocScanDAO aDao = DocScanDAOFactory.newDocScanDAO();
            try
            {
                VisitVO aVisitVO = aDao.readVisit(visitId);
                this.aBpkDocumentScanVO.setHn(aVisitVO.getHn());
                this.aBpkDocumentScanVO.setPatientName(aVisitVO.getPatientName());
                this.aBpkDocumentScanVO.setVn(aVisitVO.getVn());
                this.aBpkDocumentScanVO.setPrintDate(aVisitVO.getVisitDate());
                this.aBpkDocumentScanVO.setPrintTime(aVisitVO.getVisitTime());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                aDao = null;
            }
            this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "�ѧ��������͡ VN", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_aBtnSelectVnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                DlgFindPatientAndVisit dialog = new DlgFindPatientAndVisit(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter()
                {

                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aBtnCancel;
    private javax.swing.JButton aBtnFindPatient;
    private javax.swing.JButton aBtnSelectVn;
    private javax.swing.JLabel aLblHnByManual;
    private javax.swing.JLabel aLblPatientByManual;
    private javax.swing.JPanel aPnlButton;
    private javax.swing.JPanel aPnlCenter;
    private javax.swing.JPanel aPnlPatientList;
    private javax.swing.JPanel aPnlTop;
    private javax.swing.JPanel aPnlVisitList;
    private javax.swing.JScrollPane aScrlPatient;
    private javax.swing.JScrollPane aScrlVisit;
    private javax.swing.JSplitPane aSplitPane;
    private com.bpk.app.emrapp.BpkTableModel aTblMdlPatient;
    private com.bpk.app.emrapp.BpkTableModel aTblMdlVisit;
    private javax.swing.JTable aTblPatient;
    private javax.swing.JTable aTblVisit;
    private javax.swing.JTextField aTxtHnByManual;
    private javax.swing.JTextField aTxtLimit;
    private javax.swing.JTextField aTxtPatientNameByManual;
    // End of variables declaration//GEN-END:variables

    public BpkDocumentScanVO getBpkDocumentScanVO()
    {


        return aBpkDocumentScanVO;
    }

    public void setPatientVO(PatientVO pPatientVO)
    {
        if (pPatientVO != null && ((pPatientVO.getHn() != null && !"".equals(pPatientVO.getHn())) || (pPatientVO.getPatientName() != null && !"".equals(pPatientVO.getPatientName()))))
        {
            this.aTxtHnByManual.setText(pPatientVO.getHn());
            this.aTxtPatientNameByManual.setText(pPatientVO.getPatientName());
        }
    }
}