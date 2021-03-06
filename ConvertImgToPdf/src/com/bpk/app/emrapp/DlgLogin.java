package com.bpk.app.emrapp;

import com.bpk.core.emrcore.dao.DocScanDAO;
import com.bpk.core.emrcore.dao.DocScanDAOFactory;
import com.bpk.persistence.emrdto.BaseServicePointVO;
import com.bpk.utility.Utility;
import com.bpk.utility.fix.FixServicePointType;
import java.util.List;

/**
 *
 * @author SURACHAI.TO
 */
public class DlgLogin extends javax.swing.JDialog
{

    private boolean isCancel = false;

    /** Creates new form DlgLogin */
    public DlgLogin(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();

        aTxtUsername.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                aTxtPassword.requestFocus();
            }
        });

        DocScanDAO aDocScanDAO = DocScanDAOFactory.newDocScanDAO();
        try
        {
            List listBaseServicePointVO = aDocScanDAO.listBaseServicePointByType(FixServicePointType.IPD);

            this.aCmbBaseServicePoint.removeAllItems();
            BaseServicePointVO blankVO = new BaseServicePointVO();
            blankVO.setDescription("-- Service Point --");
            this.aCmbBaseServicePoint.addItem(blankVO);
            for (int i = 0, sizei = listBaseServicePointVO.size(); i < sizei; i++)
            {
                this.aCmbBaseServicePoint.addItem(listBaseServicePointVO.get(i));
            }

            listBaseServicePointVO = aDocScanDAO.listBaseServicePointByType(FixServicePointType.OPD);

            for (int i = 0, sizei = listBaseServicePointVO.size(); i < sizei; i++)
            {
                this.aCmbBaseServicePoint.addItem(listBaseServicePointVO.get(i));
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            aDocScanDAO = null;
        }
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
        aLblName = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        aPnlBody = new javax.swing.JPanel();
        aLblUsername = new javax.swing.JLabel();
        aTxtUsername = new javax.swing.JTextField();
        aLblPassword = new javax.swing.JLabel();
        aLblBaseServicePoint = new javax.swing.JLabel();
        aCmbBaseServicePoint = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        aPnlButton = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        aBtnLogin = new javax.swing.JButton();
        aBtnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login to Document Scan");

        aPnlTop.setLayout(new java.awt.GridBagLayout());

        aLblName.setFont(new java.awt.Font("Tahoma", 0, 24));
        aLblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        aLblName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bpk/app/emrapp/images/LogoTop.jpg"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        aPnlTop.add(aLblName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        aPnlTop.add(jSeparator2, gridBagConstraints);

        getContentPane().add(aPnlTop, java.awt.BorderLayout.NORTH);

        aPnlBody.setLayout(new java.awt.GridBagLayout());

        aLblUsername.setFont(new java.awt.Font("Tahoma", 0, 12));
        aLblUsername.setText("Username:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        aPnlBody.add(aLblUsername, gridBagConstraints);

        aTxtUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aTxtUsername.setMinimumSize(new java.awt.Dimension(180, 24));
        aTxtUsername.setPreferredSize(new java.awt.Dimension(180, 24));
        aTxtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aTxtUsernameActionPerformed(evt);
            }
        });
        aTxtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                aTxtUsernameFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 11);
        aPnlBody.add(aTxtUsername, gridBagConstraints);

        aLblPassword.setFont(new java.awt.Font("Tahoma", 0, 12));
        aLblPassword.setText("Password:");
        aLblPassword.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        aPnlBody.add(aLblPassword, gridBagConstraints);

        aTxtPassword.setFont(new java.awt.Font("Tahoma", 0, 14));
        aTxtPassword.setMinimumSize(new java.awt.Dimension(180, 24));
        aTxtPassword.setPreferredSize(new java.awt.Dimension(180, 24));
        aTxtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aTxtPasswordActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 11);
        aPnlBody.add(aTxtPassword, gridBagConstraints);

        aLblBaseServicePoint.setFont(new java.awt.Font("Tahoma", 0, 12));
        aLblBaseServicePoint.setText("Service Point:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 11);
        aPnlBody.add(aLblBaseServicePoint, gridBagConstraints);
        aLblBaseServicePoint.getAccessibleContext().setAccessibleName("Service Point:");
        aLblBaseServicePoint.getAccessibleContext().setAccessibleDescription("");

        aCmbBaseServicePoint.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        aCmbBaseServicePoint.setMinimumSize(new java.awt.Dimension(180, 24));
        aCmbBaseServicePoint.setPreferredSize(new java.awt.Dimension(180, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 11);
        aPnlBody.add(aCmbBaseServicePoint, gridBagConstraints);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bpk/app/emrapp/images/LogoTiny.jpg"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        aPnlBody.add(jLabel1, gridBagConstraints);

        getContentPane().add(aPnlBody, java.awt.BorderLayout.CENTER);

        aPnlButton.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        aPnlButton.add(jSeparator1, gridBagConstraints);

        aBtnLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        aBtnLogin.setText("Login");
        aBtnLogin.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnLogin.setMaximumSize(new java.awt.Dimension(80, 28));
        aBtnLogin.setMinimumSize(new java.awt.Dimension(80, 28));
        aBtnLogin.setPreferredSize(new java.awt.Dimension(80, 28));
        aBtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnLoginActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        aPnlButton.add(aBtnLogin, gridBagConstraints);

        aBtnCancel.setFont(new java.awt.Font("Tahoma", 1, 12));
        aBtnCancel.setText("Cancel");
        aBtnCancel.setMargin(new java.awt.Insets(0, 0, 0, 0));
        aBtnCancel.setMaximumSize(new java.awt.Dimension(80, 28));
        aBtnCancel.setMinimumSize(new java.awt.Dimension(80, 28));
        aBtnCancel.setPreferredSize(new java.awt.Dimension(80, 28));
        aBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 5, 11, 11);
        aPnlButton.add(aBtnCancel, gridBagConstraints);

        getContentPane().add(aPnlButton, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-480)/2, (screenSize.height-360)/2, 480, 360);
    }// </editor-fold>//GEN-END:initComponents

    private void aBtnLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnLoginActionPerformed
    {//GEN-HEADEREND:event_aBtnLoginActionPerformed
        this.dispose();
}//GEN-LAST:event_aBtnLoginActionPerformed

    private void aBtnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aBtnCancelActionPerformed
    {//GEN-HEADEREND:event_aBtnCancelActionPerformed
        isCancel = true;
        this.dispose();
}//GEN-LAST:event_aBtnCancelActionPerformed

    private void aTxtPasswordActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aTxtPasswordActionPerformed
    {//GEN-HEADEREND:event_aTxtPasswordActionPerformed
        this.aBtnLoginActionPerformed(evt);
    }//GEN-LAST:event_aTxtPasswordActionPerformed

    private void aTxtUsernameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aTxtUsernameActionPerformed
    {//GEN-HEADEREND:event_aTxtUsernameActionPerformed
        char[] pass = aTxtPassword.getPassword();

        Object obj = this.aCmbBaseServicePoint.getSelectedItem();
        if (obj != null && obj instanceof BaseServicePointVO)
        {
            if (aTxtUsername.getText().trim().length() > 0 && pass.length > 0)
            {
                this.dispose();
            }
        } else
        {
            if (aTxtUsername.getText().trim().length() > 0 && pass.length > 0)
            {
                selectDefaultServicePoint();
            } else if (aTxtUsername.getText().trim().length() > 0 && pass.length == 0)
            {
                selectDefaultServicePoint();
                aTxtPassword.requestFocus();
            }
        }
    }//GEN-LAST:event_aTxtUsernameActionPerformed

    private void aTxtUsernameFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_aTxtUsernameFocusLost
    {//GEN-HEADEREND:event_aTxtUsernameFocusLost
        Object obj = this.aCmbBaseServicePoint.getSelectedItem();
        if (obj==null)
        {
            selectDefaultServicePoint();
        } 
        
        if(obj instanceof BaseServicePointVO)
        {
            BaseServicePointVO aBaseServicePointVO = (BaseServicePointVO)obj;
            if(Utility.isNull(aBaseServicePointVO.getObjectID()))        
            {
                selectDefaultServicePoint();
            }
        }
    }//GEN-LAST:event_aTxtUsernameFocusLost

    private void selectDefaultServicePoint()
    {
        DocScanDAO aDocScanDAO = DocScanDAOFactory.newDocScanDAO();
        try
        {
            String defaultBaseServicePointId = aDocScanDAO.getDefaultServicePointByEmployeeId(this.aTxtUsername.getText().trim());

            this.aCmbBaseServicePoint.setSelectedItem(new BaseServicePointVO(defaultBaseServicePointId));
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            aDocScanDAO = null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                DlgLogin dialog = new DlgLogin(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton aBtnLogin;
    private javax.swing.JComboBox aCmbBaseServicePoint;
    private javax.swing.JLabel aLblBaseServicePoint;
    private javax.swing.JLabel aLblName;
    private javax.swing.JLabel aLblPassword;
    private javax.swing.JLabel aLblUsername;
    private javax.swing.JPanel aPnlBody;
    private javax.swing.JPanel aPnlButton;
    private javax.swing.JPanel aPnlTop;
    private final javax.swing.JPasswordField aTxtPassword = new javax.swing.JPasswordField();
    private javax.swing.JTextField aTxtUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables

    /**
     * Overridden so we can exit when window is closed
     * @param ev WindowEvent
     */
    protected void processWindowEvent(java.awt.event.WindowEvent ev)
    {
        super.processWindowEvent(ev);
        if (ev.getID() == java.awt.event.WindowEvent.WINDOW_CLOSING)
        {
            this.aBtnCancelActionPerformed(null);
        }
    }

    public String[] getLoginData()
    {
        if (!isCancel)
        {
            String[] loginData = new String[3];
            loginData[0] = this.aTxtUsername.getText();
            loginData[1] = new String(this.aTxtPassword.getPassword());

            Object obj = this.aCmbBaseServicePoint.getSelectedItem();
            if (obj != null && obj instanceof BaseServicePointVO)
            {
                BaseServicePointVO aBaseServicePointVO = (BaseServicePointVO) obj;
                loginData[2] = aBaseServicePointVO.getObjectID();
            } else
            {
                loginData[2] = "";
            }

            // Utility.printCoreDebug(this, "SpId = " + loginData[2]);

            return loginData;
        }
        return null;
    }
}
