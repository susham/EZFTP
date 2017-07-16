import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Fri Jul 14 18:21:48 PDT 2017
 */



/**
 * @author Susham Yerabolu
 */
public class ServerConnect extends JPanel {
    public ServerConnect() {
        initComponents();
    }

    private void btn_connectActionPerformed(ActionEvent e) {
        // TODO add your code here

        JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.");

    }

    private void btn_connectActionPerformed() {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Susham Yerabolu
        lbl_Connect = new JLabel();
        txt_serveraddress = new JTextField();
        txt_portnumber = new JTextField();
        lbl_portnumber = new JLabel();
        lbl_username = new JLabel();
        txt_username = new JTextField();
        lbl_password = new JLabel();
        txt_password = new JTextField();
        btn_connect = new JButton();
        btn_saveconnection = new JButton();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


        //---- lbl_Connect ----
        lbl_Connect.setText("Server Address:");

        //---- lbl_portnumber ----
        lbl_portnumber.setText("Port Number:");

        //---- lbl_username ----
        lbl_username.setText("User Name:");

        //---- lbl_password ----
        lbl_password.setText("Password:");

        //---- btn_connect ----
        btn_connect.setText("Connect");
        btn_connect.addActionListener(e -> btn_connectActionPerformed(e));

        //---- btn_saveconnection ----
        btn_saveconnection.setText("Save Connection");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lbl_password, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                        .addComponent(lbl_username, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                        .addComponent(lbl_Connect, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                        .addComponent(lbl_portnumber, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_portnumber, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addComponent(txt_serveraddress, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addComponent(txt_username, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addComponent(txt_password, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                    .addGap(70, 70, 70))
                .addGroup(layout.createSequentialGroup()
                    .addGap(116, 116, 116)
                    .addComponent(btn_connect)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btn_saveconnection)
                    .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(66, 66, 66)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_Connect)
                        .addComponent(txt_serveraddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_portnumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_portnumber))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_username)
                        .addComponent(txt_username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_password)
                        .addComponent(txt_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_connect)
                        .addComponent(btn_saveconnection))
                    .addContainerGap(34, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Susham Yerabolu
    private JLabel lbl_Connect;
    private JTextField txt_serveraddress;
    private JTextField txt_portnumber;
    private JLabel lbl_portnumber;
    private JLabel lbl_username;
    private JTextField txt_username;
    private JLabel lbl_password;
    private JTextField txt_password;
    private JButton btn_connect;
    private JButton btn_saveconnection;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
