/*
 * FrmBpkAllocate.java
 *
 * Created on 29/08/2015, 9:51:08 PM
 */

package com.bpk.bop;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author SURACHAI.TO
 */
public class FrmBpkAllocate extends javax.swing.JFrame {

    NumberFormat nf4 = NumberFormat.getInstance();
    NumberFormat nf2 = NumberFormat.getInstance();
    Calendar aCal = Calendar.getInstance(new Locale("en", "US"));

    /** Creates new form FrmBpkAllocate */
    public FrmBpkAllocate()
    {
        initComponents();

        nf2.setMaximumFractionDigits(0);
        nf2.setMinimumFractionDigits(0);
        nf2.setMaximumIntegerDigits(2);
        nf2.setMinimumIntegerDigits(2);
        nf2.setGroupingUsed(false);

        nf4.setMaximumFractionDigits(0);
        nf4.setMinimumFractionDigits(0);
        nf4.setMaximumIntegerDigits(4);
        nf4.setMinimumIntegerDigits(4);
        nf4.setGroupingUsed(false);

        aCal.set(Calendar.DAY_OF_MONTH, aCal.get(Calendar.DAY_OF_MONTH)-1);
        aCal.setTime(aCal.getTime());

        this.aTxtFromDate.setText(nf4.format(aCal.get(Calendar.YEAR))+"-"+nf2.format(aCal.get(Calendar.MONTH)+1)+"-"+nf2.format(aCal.get(Calendar.DAY_OF_MONTH)-1));
        this.aTxtToDate.setText(nf4.format(aCal.get(Calendar.YEAR))+"-"+nf2.format(aCal.get(Calendar.MONTH)+1)+"-"+nf2.format(aCal.get(Calendar.DAY_OF_MONTH)-1));
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

        aPnlTop = new javax.swing.JPanel();
        aLblFromDate = new javax.swing.JLabel();
        aTxtFromDate = new javax.swing.JTextField();
        aLblToDate = new javax.swing.JLabel();
        aTxtToDate = new javax.swing.JTextField();
        aBtnStart = new javax.swing.JButton();
        aPnlCenter = new javax.swing.JPanel();
        aPgrStatus = new javax.swing.JProgressBar();
        aBtnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BPK Allocate ");

        aPnlTop.setLayout(new java.awt.GridBagLayout());

        aLblFromDate.setText("From Date:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        aPnlTop.add(aLblFromDate, gridBagConstraints);

        aTxtFromDate.setMaximumSize(new java.awt.Dimension(84, 24));
        aTxtFromDate.setMinimumSize(new java.awt.Dimension(84, 24));
        aTxtFromDate.setPreferredSize(new java.awt.Dimension(84, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 0);
        aPnlTop.add(aTxtFromDate, gridBagConstraints);

        aLblToDate.setText("To Date:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 5, 0, 0);
        aPnlTop.add(aLblToDate, gridBagConstraints);

        aTxtToDate.setMaximumSize(new java.awt.Dimension(84, 24));
        aTxtToDate.setMinimumSize(new java.awt.Dimension(84, 24));
        aTxtToDate.setPreferredSize(new java.awt.Dimension(84, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 0);
        aPnlTop.add(aTxtToDate, gridBagConstraints);

        aBtnStart.setText("Start");
        aBtnStart.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnStart.setMaximumSize(new java.awt.Dimension(72, 24));
        aBtnStart.setMinimumSize(new java.awt.Dimension(72, 24));
        aBtnStart.setPreferredSize(new java.awt.Dimension(72, 24));
        aBtnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnStartActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 5, 0, 11);
        aPnlTop.add(aBtnStart, gridBagConstraints);

        getContentPane().add(aPnlTop, java.awt.BorderLayout.PAGE_START);

        aPnlCenter.setLayout(new java.awt.GridBagLayout());

        aPgrStatus.setMaximumSize(new java.awt.Dimension(32767, 24));
        aPgrStatus.setMinimumSize(new java.awt.Dimension(10, 24));
        aPgrStatus.setPreferredSize(new java.awt.Dimension(150, 24));
        aPgrStatus.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        aPnlCenter.add(aPgrStatus, gridBagConstraints);

        aBtnCancel.setText("Cancel");
        aBtnCancel.setEnabled(false);
        aBtnCancel.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnCancel.setMaximumSize(new java.awt.Dimension(72, 24));
        aBtnCancel.setMinimumSize(new java.awt.Dimension(72, 24));
        aBtnCancel.setPreferredSize(new java.awt.Dimension(72, 24));
        aBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 11, 11, 11);
        aPnlCenter.add(aBtnCancel, gridBagConstraints);

        getContentPane().add(aPnlCenter, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-480)/2, (screenSize.height-180)/2, 480, 180);
    }// </editor-fold>//GEN-END:initComponents

    private void aBtnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnStartActionPerformed
    {//GEN-HEADEREND:event_aBtnStartActionPerformed
        this.aBtnStart.setEnabled(false);
        this.aTxtFromDate.setEditable(false);
        this.aTxtToDate.setEditable(false);
        this.aBtnCancel.setEnabled(true);
    }//GEN-LAST:event_aBtnStartActionPerformed

    private void aBtnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnCancelActionPerformed
    {//GEN-HEADEREND:event_aBtnCancelActionPerformed
        this.aBtnStart.setEnabled(true);
        this.aTxtFromDate.setEditable(true);
        this.aTxtToDate.setEditable(true);
        this.aBtnCancel.setEnabled(false);
    }//GEN-LAST:event_aBtnCancelActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmBpkAllocate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aBtnCancel;
    private javax.swing.JButton aBtnStart;
    private javax.swing.JLabel aLblFromDate;
    private javax.swing.JLabel aLblToDate;
    private javax.swing.JProgressBar aPgrStatus;
    private javax.swing.JPanel aPnlCenter;
    private javax.swing.JPanel aPnlTop;
    private javax.swing.JTextField aTxtFromDate;
    private javax.swing.JTextField aTxtToDate;
    // End of variables declaration//GEN-END:variables

}
