/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmConvertDocScan.java
 *
 * Created on Jan 25, 2016, 6:18:22 PM
 */
package com.bpk.app.emrapp;

import com.bpk.core.emrcore.dao.DocScanDAOFactory;
import com.bpk.core.emrcore.dao.DocScanDAO;
import com.bpk.persistence.emrdto.BpkDocumentScanVO;
import com.bpk.persistence.emrdto.FolderVO;
import com.bpk.persistence.emrdto.PatientVO;
import com.bpk.persistence.emrdto.VisitVO;
import com.bpk.utility.EventNames;
import com.bpk.utility.MyString;
import com.bpk.utility.Utility;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import javax.swing.Timer;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author SURACHAI.TO
 */
public class FrmConvertDocScan extends javax.swing.JFrame
{

    private PatientVO patientVO = null;

    Thread aThread = null;
    Timer aTimer = null;

    /** Creates new form FrmConvertDocScan */
    public FrmConvertDocScan()
    {
        try
        {
            javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new Theme());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            java.awt.Font aFont = new java.awt.Font("Tahoma", 0, 14);
            UIManager.put("OptionPane.font", aFont);
        }
        catch (Exception ex)
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

        aPnlTop = new javax.swing.JPanel();
        aLblHn = new javax.swing.JLabel();
        aTxtHn = new javax.swing.JTextField();
        aTxtPatientName = new javax.swing.JTextField();
        aLblTotalVisitId = new javax.swing.JLabel();
        aTxtTotalVisitId = new javax.swing.JTextField();
        aBtnConvert = new javax.swing.JButton();
        aBtnCancel = new javax.swing.JButton();
        aBtnFind = new javax.swing.JButton();
        aBtnClear = new javax.swing.JButton();
        aTxtTotalDocument = new javax.swing.JTextField();
        aLblTotalDocument = new javax.swing.JLabel();
        aPnlBody = new javax.swing.JPanel();
        aLblProgress = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Convert DocScan");
        setResizable(false);

        aPnlTop.setLayout(new java.awt.GridBagLayout());

        aLblHn.setFont(new java.awt.Font("Tahoma", 0, 12));
        aLblHn.setText("HN:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        aPnlTop.add(aLblHn, gridBagConstraints);

        aTxtHn.setFont(new java.awt.Font("Tahoma", 0, 12));
        aTxtHn.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aTxtHn.setMinimumSize(new java.awt.Dimension(120, 24));
        aTxtHn.setPreferredSize(new java.awt.Dimension(120, 24));
        aTxtHn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aTxtHnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 0);
        aPnlTop.add(aTxtHn, gridBagConstraints);

        aTxtPatientName.setEditable(false);
        aTxtPatientName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aTxtPatientName.setMinimumSize(new java.awt.Dimension(240, 24));
        aTxtPatientName.setPreferredSize(new java.awt.Dimension(240, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 11);
        aPnlTop.add(aTxtPatientName, gridBagConstraints);

        aLblTotalVisitId.setFont(new java.awt.Font("Tahoma", 0, 12));
        aLblTotalVisitId.setText("Found:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        aPnlTop.add(aLblTotalVisitId, gridBagConstraints);

        aTxtTotalVisitId.setEditable(false);
        aTxtTotalVisitId.setFont(new java.awt.Font("Tahoma", 0, 12));
        aTxtTotalVisitId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aTxtTotalVisitId.setMinimumSize(new java.awt.Dimension(60, 24));
        aTxtTotalVisitId.setPreferredSize(new java.awt.Dimension(60, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 0);
        aPnlTop.add(aTxtTotalVisitId, gridBagConstraints);

        aLblTotalVisitUnit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aLblTotalVisitUnit.setText("visits");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 4);
        aPnlTop.add(aLblTotalVisitUnit, gridBagConstraints);

        aBtnConvert.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        aBtnConvert.setLabel("Convert");
        aBtnConvert.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnConvert.setMaximumSize(new java.awt.Dimension(80, 24));
        aBtnConvert.setMinimumSize(new java.awt.Dimension(80, 24));
        aBtnConvert.setPreferredSize(new java.awt.Dimension(80, 24));
        aBtnConvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnConvertActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlTop.add(aBtnConvert, gridBagConstraints);

        aBtnCancel.setFont(new java.awt.Font("Tahoma", 1, 12));
        aBtnCancel.setEnabled(false);
        aBtnCancel.setLabel("Cancel");
        aBtnCancel.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnCancel.setMaximumSize(new java.awt.Dimension(80, 24));
        aBtnCancel.setMinimumSize(new java.awt.Dimension(80, 24));
        aBtnCancel.setPreferredSize(new java.awt.Dimension(80, 24));
        aBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 11);
        aPnlTop.add(aBtnCancel, gridBagConstraints);

        aBtnFind.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aBtnFind.setText("Find");
        aBtnFind.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnFind.setMaximumSize(new java.awt.Dimension(36, 24));
        aBtnFind.setMinimumSize(new java.awt.Dimension(36, 24));
        aBtnFind.setPreferredSize(new java.awt.Dimension(36, 24));
        aBtnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnFindActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 0);
        aPnlTop.add(aBtnFind, gridBagConstraints);

        aBtnClear.setFont(new java.awt.Font("Tahoma", 0, 12));
        aBtnClear.setText("Clear");
        aBtnClear.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnClear.setMaximumSize(new java.awt.Dimension(36, 24));
        aBtnClear.setMinimumSize(new java.awt.Dimension(36, 24));
        aBtnClear.setPreferredSize(new java.awt.Dimension(36, 24));
        aBtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnClearActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 0);
        aPnlTop.add(aBtnClear, gridBagConstraints);

        aTxtTotalDocument.setEditable(false);
        aTxtTotalDocument.setFont(new java.awt.Font("Tahoma", 0, 12));
        aTxtTotalDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aTxtTotalDocument.setMinimumSize(new java.awt.Dimension(60, 24));
        aTxtTotalDocument.setPreferredSize(new java.awt.Dimension(60, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 0);
        aPnlTop.add(aTxtTotalDocument, gridBagConstraints);

        aLblTotalDocument.setFont(new java.awt.Font("Tahoma", 0, 12));
        aLblTotalDocument.setText("docs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 11);
        aPnlTop.add(aLblTotalDocument, gridBagConstraints);

        getContentPane().add(aPnlTop, java.awt.BorderLayout.PAGE_START);

        aPnlBody.setLayout(new java.awt.GridBagLayout());

        aStatusProgress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aStatusProgress.setFocusable(false);
        aStatusProgress.setMinimumSize(new java.awt.Dimension(200, 24));
        aStatusProgress.setPreferredSize(new java.awt.Dimension(200, 24));
        aStatusProgress.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 12, 11, 11);
        aPnlBody.add(aStatusProgress, gridBagConstraints);

        aLblProgress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aLblProgress.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        aPnlBody.add(aLblProgress, gridBagConstraints);

        getContentPane().add(aPnlBody, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-488)/2, (screenSize.height-267)/2, 488, 267);
    }// </editor-fold>//GEN-END:initComponents

    public void setConvertInProgress(boolean isInProgress)
    {
        this.aTxtHn.setEnabled(!isInProgress);
        this.aBtnFind.setEnabled(!isInProgress);
        this.aBtnClear.setEnabled(!isInProgress);
        this.aBtnConvert.setEnabled(!isInProgress);
        this.aBtnCancel.setEnabled(isInProgress);
    }

    private void aBtnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnCancelActionPerformed
    {//GEN-HEADEREND:event_aBtnCancelActionPerformed
        setConvertInProgress(false);
    }//GEN-LAST:event_aBtnCancelActionPerformed

    private void aBtnConvertActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnConvertActionPerformed
    {//GEN-HEADEREND:event_aBtnConvertActionPerformed
        setConvertInProgress(true);

        if (this.getPatientVO() != null)
        {
            /*
            List listVisitVO = null;
            DocScanDAO aDao = DocScanDAOFactory.newDocScanDAO();
            try
            {
                listVisitVO = aDao.listVisitByPatientId(this.getPatientVO().getObjectID());
                if (Utility.isNotNull(listVisitVO))
                {
                    for (int i = 0, sizei = listVisitVO.size(); i < sizei; i++)
                    {
                        VisitVO tmpVisitVO = (VisitVO) listVisitVO.get(i);

                        List listFolderVO = tmpVisitVO.getListFolderVO();
                        if (Utility.isNotNull(listFolderVO))
                        {
                            for (int j = 0, sizej = listFolderVO.size(); j < sizej; j++)
                            {
                                FolderVO tmpFolderVO = (FolderVO) listFolderVO.get(j);

                                List listBpkDocumentScanVO = tmpFolderVO.getListBpkDocumentScanVO();

                                if (Utility.isNotNull(listBpkDocumentScanVO))
                                {
                                    for (int k = 0, sizek = listBpkDocumentScanVO.size(); k < sizek; k++)
                                    {
                                        BpkDocumentScanVO tmpBpkDocumentScanVO = (BpkDocumentScanVO) listBpkDocumentScanVO.get(k);

                                        String url = DocScanDAOFactory.getDocScanUrl() + DocScanDAOFactory.getHnImageFolder(this.getPatientVO().getOriginalHn()) + "/" + tmpFolderVO.getObjectID() + "/" + tmpBpkDocumentScanVO.getImageFileName();
                                        Utility.printCoreDebug(this, url);
                                        String path = DocScanDAOFactory.getDocScanOutputPath() + DocScanDAOFactory.getHnImageFolder(this.getPatientVO().getOriginalHn(), System.getProperty("file.separator")) + System.getProperty("file.separator") + tmpFolderVO.getObjectID() + System.getProperty("file.separator");
                                        Utility.printCoreDebug(this, path);

                                        String jrxmlFilename = generateJrxmlPage(tmpBpkDocumentScanVO.getImageFileName(), path, false, 0, path);
                                        convertPage(jrxmlFilename, path, path);

                                        deleteJrxmlFile(path+jrxmlFilename);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
             *
             */

             final ConvertDocScan aConvertDocScan = new ConvertDocScan();
             aConvertDocScan.setPatientVO(patientVO);
             aTimer = new Timer(1000, new ActionListener(){
                public void actionPerformed(ActionEvent evt)
                {
                    aLblProgress.setText(aConvertDocScan.getStatusText());
                    aStatusProgress.setValue(aConvertDocScan.getStatus());
                    if(aConvertDocScan.getStatus()==100)
                    {
                        Toolkit.getDefaultToolkit().beep();
                        aTimer.stop();
                        setConvertInProgress(false);
                    }
                }
             });
             aThread = new Thread(aConvertDocScan);
             aThread.start();
             aTimer.start();
        }
        setConvertInProgress(false);
    }//GEN-LAST:event_aBtnConvertActionPerformed

    private void aBtnFindActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnFindActionPerformed
    {//GEN-HEADEREND:event_aBtnFindActionPerformed
        aTxtHnActionPerformed(evt);
    }//GEN-LAST:event_aBtnFindActionPerformed

    private void aBtnClearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnClearActionPerformed
    {//GEN-HEADEREND:event_aBtnClearActionPerformed
        this.setPatientVO(null);

        this.aTxtHn.setText("");
        this.aTxtPatientName.setText("");
        this.aTxtTotalVisitId.setText("");
        this.aTxtTotalDocument.setText("");

        this.aLblProgress.setText(" ");
        this.aStatusProgress.setValue(0);
    }//GEN-LAST:event_aBtnClearActionPerformed

    private void aTxtHnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aTxtHnActionPerformed
    {//GEN-HEADEREND:event_aTxtHnActionPerformed
        DocScanDAO aDocScanDAO = DocScanDAOFactory.newDocScanDAO();
        HashMap result = aDocScanDAO.readPatient(this.aTxtHn.getText().trim());
        Object objVisit = result.get("COUNT_VISIT");
        if (objVisit != null)
        {
            Integer cntVisit = (Integer) objVisit;
            this.aTxtTotalVisitId.setText(cntVisit.toString());
        }

        Object objDocScan = result.get("COUNT_DOC_SCAN");
        if (objDocScan != null)
        {
            Integer cntDocScan = (Integer) objDocScan;
            this.aTxtTotalDocument.setText(cntDocScan.toString());
        }

        Object tmpPatientVO = result.get(EventNames.RESULT_DATA);
        if (tmpPatientVO != null)
        {
            this.setPatientVO((PatientVO) tmpPatientVO);

            this.aTxtHn.setText(this.getPatientVO().getHn());
            this.aTxtPatientName.setText(this.getPatientVO().getPatientName());
        }

        this.aLblProgress.setText(" ");
        this.aStatusProgress.setValue(0);
    }//GEN-LAST:event_aTxtHnActionPerformed
    public final static String TEMP_FILENAME = "TempFilename";
    public final static String HEADER1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"";
    public final static String HEADER2 = "\" pageWidth=\"595\" pageHeight=\"842\" whenNoDataType=\"AllSectionsNoDetail\" columnWidth=\"595\" leftMargin=\"0\" rightMargin=\"0\" topMargin=\"0\" bottomMargin=\"0\" isSummaryNewPage=\"true\"><property name=\"ireport.zoom\" value=\"1.0\"/><property name=\"ireport.x\" value=\"0\"/><property name=\"ireport.y\" value=\"0\"/><parameter name=\"SUBREPORT_DIR\" class=\"java.lang.String\" isForPrompting=\"false\"><defaultValueExpression><![CDATA[\"";
    // public final static String PARAM_SUB_REPORT_DIR = "D:\\\\BPKHIS\\\\trunk\\\\Sources\\\\CodeJSP\\\\jboss\\\\server\\\\default\\\\deploy\\\\bpkjasper.war\\\\";
    public final static String REPORT_ELEMENT1 = "\"]]></defaultValueExpression></parameter><background><band height=\"842\" splitType=\"Stretch\"><image hAlign=\"Center\" vAlign=\"Middle\"><reportElement x=\"0\" y=\"0\" width=\"595\" height=\"842\"/><imageExpression class=\"java.lang.String\"><![CDATA[\"";
    // public final static String IMAGE_FULLPATH = "D:\\\\BPKHIS\\\\trunk\\\\Sources\\\\CodeJSP\\\\jboss\\\\server\\\\default\\\\deploy\\\\bpkimage.war\\\\imed_doc_new\\\\46\\\\0000003XX\\\\946000000391\\\\";
    public final static String REPORT_ELEMENT2 = "\"]]></imageExpression></image></band></background><title><band splitType=\"Stretch\"/></title><pageHeader><band splitType=\"Stretch\"/></pageHeader><columnHeader><band splitType=\"Stretch\"/></columnHeader><detail><band splitType=\"Stretch\"/></detail><columnFooter><band splitType=\"Stretch\"/></columnFooter><pageFooter><band splitType=\"Stretch\"/></pageFooter><summary>";
    public final static String HAS_NEXT_PAGE1 = "<band height=\"842\" splitType=\"Stretch\"><subreport><reportElement x=\"0\" y=\"0\" width=\"595\" height=\"842\"/><connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression><subreportExpression class=\"java.lang.String\"><![CDATA[$P{SUBREPORT_DIR} + \"";
    public final static String HAS_NEXT_PAGE2 = "\"]]></subreportExpression></subreport></band>";
    public final static String NO_NEXT_PAGE = "<band splitType=\"Stretch\"/>";
    public final static String FOOTER = "</summary></jasperReport>";

    /***
     * ����Ѻ���ҧ JRXML File 
     * @param reportName
     * @param inputImageAbsFilename
     * @param isHasNextPage
     * @param indexPage
     * @param outputJrxmlPath
     * @return     
    public static String generateJrxmlPage(String reportName, String inputImageAbsFilename, boolean isHasNextPage, int indexPage, String outputJrxmlPath)
    {
        String imgReportName = reportName;
        reportName = reportName.toUpperCase();
        reportName = reportName.replaceAll(".JPG", "");
        reportName = reportName.replaceAll(".JPEG", "");
        reportName = reportName.replaceAll(".GIF", "");
        reportName = reportName.replaceAll(".PNG", "");
        reportName = reportName.replaceAll(".BMP", "");

        StringBuilder jrxmlBuilder = new StringBuilder(HEADER1);
        jrxmlBuilder.append(reportName);
        jrxmlBuilder.append(HEADER2);
        jrxmlBuilder.append(MyString.addSlash(outputJrxmlPath));
        jrxmlBuilder.append(REPORT_ELEMENT1);
        jrxmlBuilder.append(MyString.addSlash(inputImageAbsFilename)).append(imgReportName);
        jrxmlBuilder.append(REPORT_ELEMENT2);
        if (isHasNextPage)
        {
            jrxmlBuilder.append(HAS_NEXT_PAGE1);
            jrxmlBuilder.append((reportName + "_" + (indexPage + 1) + ".jasper"));
            jrxmlBuilder.append(HAS_NEXT_PAGE2);
        }
        else
        {
            jrxmlBuilder.append(NO_NEXT_PAGE);
        }
        jrxmlBuilder.append(FOOTER);

        String filename = reportName + (isHasNextPage ? ("_" + indexPage) : "") + ".jrxml";
        String jrxmlAbsFilename = outputJrxmlPath + filename;
        writeFile(jrxmlAbsFilename, jrxmlBuilder.toString());

        return filename;
    }
     */

    /** Write text data to file 
    public static boolean writeFile(String filename, String data)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(filename);
            Writer out = new OutputStreamWriter(fos);
            out.write(data);
            out.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    */

    /** 
    public static void deleteJrxmlFile(String jrxmlAbsFilename)
    {
        try
        {
            File file = new File(jrxmlAbsFilename);
            file.delete();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    */

    /**
     * ����Ѻ Convert JRXML File --> Jasper File --> PDF File
     * @param jrxmlFilename
     * @param inputJrxmlPath
     * @param outputPdfPath
     * @return     
    public static boolean convertPage(String jrxmlFilename, String inputJrxmlPath, String outputPdfPath)
    {
        boolean result = true;
        HashMap hm = new HashMap();
        Connection conn = null;
        String reportName = jrxmlFilename.replaceAll(".jrxml", "");
        String jasperFilename = reportName + ".jasper";

        try
        {
            System.out.println("Start convert ....");
            conn = DocScanDAOFactory.getConnection();

            JasperCompileManager.compileReportToFile(inputJrxmlPath + jrxmlFilename, inputJrxmlPath + jasperFilename);

            // Generate jasper print
            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(inputJrxmlPath + jasperFilename, hm, conn);

            // Export pdf file
            JasperExportManager.exportReportToPdfFile(jprint, outputPdfPath + reportName + ".pdf");
            System.out.println("Done exporting reports to pdf");

            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // ����Ѻ������ file �͡��ѧ convert
        File jasperFile = new File(inputJrxmlPath + jasperFilename);
        jasperFile.delete();
        
        return result;
    }
     */

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {

        System.setProperty("bpk.debug", "true");

        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                new FrmConvertDocScan().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aBtnCancel;
    private javax.swing.JButton aBtnClear;
    private javax.swing.JButton aBtnConvert;
    private javax.swing.JButton aBtnFind;
    private javax.swing.JLabel aLblHn;
    private javax.swing.JLabel aLblProgress;
    private javax.swing.JLabel aLblTotalDocument;
    private javax.swing.JLabel aLblTotalVisitId;
    private final javax.swing.JLabel aLblTotalVisitUnit = new javax.swing.JLabel();
    private javax.swing.JPanel aPnlBody;
    private javax.swing.JPanel aPnlTop;
    private final javax.swing.JProgressBar aStatusProgress = new javax.swing.JProgressBar();
    private javax.swing.JTextField aTxtHn;
    private javax.swing.JTextField aTxtPatientName;
    private javax.swing.JTextField aTxtTotalDocument;
    private javax.swing.JTextField aTxtTotalVisitId;
    // End of variables declaration//GEN-END:variables

    public PatientVO getPatientVO()
    {
        return patientVO;
    }

    public void setPatientVO(PatientVO newPatientVO)
    {
        this.patientVO = newPatientVO;
    }
}
