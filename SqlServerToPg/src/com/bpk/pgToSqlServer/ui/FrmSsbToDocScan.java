/*
 * FrmSsbToDocScan.java
 *
 * Created on Feb 29, 2016, 3:25:13 PM
 */
package com.bpk.pgToSqlServer.ui;

import com.bpk.pgToSqlServer.utility.Utility;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author surachai.tw
 */
public class FrmSsbToDocScan extends javax.swing.JFrame
{

    private final SyncPatientAll aSyncPatientAll = new SyncPatientAll();
    private Thread aThreadPatientAll = null;
    private Timer aTimerPatientAll = null;
    private final SyncVisitAll aSyncVisitAll = new SyncVisitAll();
    private Thread aThreadVisitAll = null;
    private Timer aTimerVisitAll = null;
    private final SyncDiagnosisByDate aSyncDiagnosisByDate = new SyncDiagnosisByDate();
    private Thread aThreadDiagnosisByDate = null;
    private Timer aTimerDiagnosisByDate = null;

    private final SyncAllByHn aSyncAllByHn = new SyncAllByHn();
    private Thread aThreadAllByHn = null;
    private Timer aTimerAllByHn = null;

    /** Creates new form FrmSsbToDocScan */
    public FrmSsbToDocScan()
    {
        try
        {
            javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new Theme());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            java.awt.Font aFont = new java.awt.Font("Tahoma", 0, 14);
            UIManager.put("OptionPane.font", aFont);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
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

        aPnlCenter = new javax.swing.JPanel();
        aPnlSyncPatient = new javax.swing.JPanel();
        aBtnSyncPatientAll = new javax.swing.JButton();
        aPnlSyncVisitAll = new javax.swing.JPanel();
        aBtnSyncVisitAll = new javax.swing.JButton();
        aPnlSyncAllByHn = new javax.swing.JPanel();
        aBtnSyncAllByHn = new javax.swing.JButton();
        aPnlSyncDxByDate = new javax.swing.JPanel();
        aBtnSyncDxByDate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sync Data SSB - DocScan");

        aPnlCenter.setLayout(new java.awt.GridBagLayout());

        aPnlSyncPatient.setLayout(new java.awt.GridBagLayout());

        aBtnSyncPatientAll.setText("Sync Patient All");
        aBtnSyncPatientAll.setMaximumSize(new java.awt.Dimension(120, 24));
        aBtnSyncPatientAll.setMinimumSize(new java.awt.Dimension(120, 24));
        aBtnSyncPatientAll.setPreferredSize(new java.awt.Dimension(120, 24));
        aBtnSyncPatientAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnSyncPatientAllActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlSyncPatient.add(aBtnSyncPatientAll, gridBagConstraints);

        aPrgStatusPatient.setMinimumSize(new java.awt.Dimension(24, 24));
        aPrgStatusPatient.setPreferredSize(new java.awt.Dimension(24, 24));
        aPrgStatusPatient.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 11);
        aPnlSyncPatient.add(aPrgStatusPatient, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        aPnlCenter.add(aPnlSyncPatient, gridBagConstraints);

        aPnlSyncVisitAll.setLayout(new java.awt.GridBagLayout());

        aBtnSyncVisitAll.setText("Sync Visit All");
        aBtnSyncVisitAll.setMaximumSize(new java.awt.Dimension(120, 24));
        aBtnSyncVisitAll.setMinimumSize(new java.awt.Dimension(120, 24));
        aBtnSyncVisitAll.setPreferredSize(new java.awt.Dimension(120, 24));
        aBtnSyncVisitAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnSyncVisitAllActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlSyncVisitAll.add(aBtnSyncVisitAll, gridBagConstraints);

        aPrgStatusVisitAll.setMinimumSize(new java.awt.Dimension(24, 24));
        aPrgStatusVisitAll.setPreferredSize(new java.awt.Dimension(24, 24));
        aPrgStatusVisitAll.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 11);
        aPnlSyncVisitAll.add(aPrgStatusVisitAll, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        aPnlCenter.add(aPnlSyncVisitAll, gridBagConstraints);

        aPnlSyncAllByHn.setLayout(new java.awt.GridBagLayout());

        aBtnSyncAllByHn.setText("Sync All By HN");
        aBtnSyncAllByHn.setMaximumSize(new java.awt.Dimension(120, 24));
        aBtnSyncAllByHn.setMinimumSize(new java.awt.Dimension(120, 24));
        aBtnSyncAllByHn.setPreferredSize(new java.awt.Dimension(120, 24));
        aBtnSyncAllByHn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnSyncAllByHnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlSyncAllByHn.add(aBtnSyncAllByHn, gridBagConstraints);

        aPrgStatusAllByHn.setMinimumSize(new java.awt.Dimension(24, 24));
        aPrgStatusAllByHn.setPreferredSize(new java.awt.Dimension(24, 24));
        aPrgStatusAllByHn.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 11);
        aPnlSyncAllByHn.add(aPrgStatusAllByHn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        aPnlCenter.add(aPnlSyncAllByHn, gridBagConstraints);

        aPnlSyncDxByDate.setLayout(new java.awt.GridBagLayout());

        aBtnSyncDxByDate.setText("Sync Dx By Date");
        aBtnSyncDxByDate.setMaximumSize(new java.awt.Dimension(120, 24));
        aBtnSyncDxByDate.setMinimumSize(new java.awt.Dimension(120, 24));
        aBtnSyncDxByDate.setPreferredSize(new java.awt.Dimension(120, 24));
        aBtnSyncDxByDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnSyncDxByDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlSyncDxByDate.add(aBtnSyncDxByDate, gridBagConstraints);

        aPrgStatusDxByDate.setMinimumSize(new java.awt.Dimension(24, 24));
        aPrgStatusDxByDate.setPreferredSize(new java.awt.Dimension(24, 24));
        aPrgStatusDxByDate.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 11);
        aPnlSyncDxByDate.add(aPrgStatusDxByDate, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        aPnlCenter.add(aPnlSyncDxByDate, gridBagConstraints);

        getContentPane().add(aPnlCenter, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-800)/2, (screenSize.height-240)/2, 800, 240);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Overridden so we can exit when window is closed
     * @param ev WindowEvent
     */
    protected void processWindowEvent(java.awt.event.WindowEvent ev)
    {
        super.processWindowEvent(ev);
        if (ev.getID() == java.awt.event.WindowEvent.WINDOW_CLOSING)
        {
            if (JOptionPane.showConfirmDialog(this, "Exit program ?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                Utility.writeLog(System.getProperty("user.dir")+System.getProperty("file.separator")+"SsbToDocScan.log");
                System.exit(1);
            } else
            {
                this.setVisible(true);
            }
        }
    }

    private void aBtnSyncPatientAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnSyncPatientAllActionPerformed
    {//GEN-HEADEREND:event_aBtnSyncPatientAllActionPerformed
        aTimerPatientAll = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                aPrgStatusPatient.setValue(aSyncPatientAll.getStatus());
                if (aSyncPatientAll.getStatus() == 100)
                {
                    Toolkit.getDefaultToolkit().beep();
                    aTimerPatientAll.stop();
                }
            }
        });
        aThreadPatientAll = new Thread(aSyncPatientAll);
        aThreadPatientAll.start();
        aTimerPatientAll.start();
    }//GEN-LAST:event_aBtnSyncPatientAllActionPerformed

    private void aBtnSyncVisitAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnSyncVisitAllActionPerformed
    {//GEN-HEADEREND:event_aBtnSyncVisitAllActionPerformed
        aTimerVisitAll = new Timer(1000, new ActionListener()
        {

            public void actionPerformed(ActionEvent evt)
            {
                aPrgStatusVisitAll.setValue(aSyncVisitAll.getStatus());
                if (aSyncVisitAll.getStatus() == 100)
                {
                    Toolkit.getDefaultToolkit().beep();
                    aTimerVisitAll.stop();
                }
            }
        });
        aThreadVisitAll = new Thread(aSyncVisitAll);
        aThreadVisitAll.start();
        aTimerVisitAll.start();
    }//GEN-LAST:event_aBtnSyncVisitAllActionPerformed

    private void aBtnSyncDxByDateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnSyncDxByDateActionPerformed
    {//GEN-HEADEREND:event_aBtnSyncDxByDateActionPerformed
        String txtDay = JOptionPane.showInputDialog("�ӹǹ�ѹ��͹��ѧ: ").trim();
        int numDay = 0;
        Utility.printCoreDebug(this, "�ӹǹ�ѹ��͹��ѧ: "+txtDay);
        try
        {
            numDay = Integer.parseInt(txtDay);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, "��سҡ�͡�ӹǹ�ѹ��ٻẺ����Ţ", "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        aSyncDiagnosisByDate.setBackDate(numDay);

        aTimerDiagnosisByDate = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                aPrgStatusDxByDate.setValue(aSyncDiagnosisByDate.getStatus());
                if (aSyncDiagnosisByDate.getStatus() == 100)
                {
                    Toolkit.getDefaultToolkit().beep();
                    aTimerDiagnosisByDate.stop();
                }
            }
        });
        aThreadDiagnosisByDate = new Thread(aSyncDiagnosisByDate);
        aThreadDiagnosisByDate.start();
        aTimerDiagnosisByDate.start();
    }//GEN-LAST:event_aBtnSyncDxByDateActionPerformed

    private void aBtnSyncAllByHnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnSyncAllByHnActionPerformed
    {//GEN-HEADEREND:event_aBtnSyncAllByHnActionPerformed
        String txtHn = JOptionPane.showInputDialog("HN: ").trim();
        Utility.printCoreDebug(this, "Input HN: "+txtHn);
        aSyncAllByHn.setHn(txtHn);

        aTimerAllByHn = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                aPrgStatusAllByHn.setValue(aSyncAllByHn.getStatus());
                if (aSyncAllByHn.getStatus() == 100)
                {
                    Toolkit.getDefaultToolkit().beep();
                    aTimerAllByHn.stop();
                }
            }
        });
        aThreadAllByHn = new Thread(aSyncAllByHn);
        aThreadAllByHn.start();
        aTimerAllByHn.start();
    }//GEN-LAST:event_aBtnSyncAllByHnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                new FrmSsbToDocScan().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aBtnSyncAllByHn;
    private javax.swing.JButton aBtnSyncDxByDate;
    private javax.swing.JButton aBtnSyncPatientAll;
    private javax.swing.JButton aBtnSyncVisitAll;
    private javax.swing.JPanel aPnlCenter;
    private javax.swing.JPanel aPnlSyncAllByHn;
    private javax.swing.JPanel aPnlSyncDxByDate;
    private javax.swing.JPanel aPnlSyncPatient;
    private javax.swing.JPanel aPnlSyncVisitAll;
    private final javax.swing.JProgressBar aPrgStatusAllByHn = new javax.swing.JProgressBar();
    private final javax.swing.JProgressBar aPrgStatusDxByDate = new javax.swing.JProgressBar();
    private final javax.swing.JProgressBar aPrgStatusPatient = new javax.swing.JProgressBar();
    private final javax.swing.JProgressBar aPrgStatusVisitAll = new javax.swing.JProgressBar();
    // End of variables declaration//GEN-END:variables
}
