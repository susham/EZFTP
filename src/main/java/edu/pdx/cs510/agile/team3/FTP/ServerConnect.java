// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

/*
 * Created by JFormDesigner on Tue Jul 18 13:52:42 PDT 2017
 */

package edu.pdx.cs510.agile.team3.FTP;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Susham Yerabolu
 */
public class ServerConnect extends JFrame {
    public ServerConnect() {
        initComponents();
    }


    //This Method uses the FTPCore.connect() method to connect to the server specified by the user.
    private void connect_to_Server(ActionEvent e) {

        String username=txt_username.getText().trim();
        String password=new String(txt_serverpassword.getPassword()).trim();
        String serveraddress=txt_serveraddress.getText();
        int portno=Integer.parseInt(txt_portno.getText());
        String connection_name=txt_connectionName.getText();




       FTPServerInfo ftpconnectioninfo= new FTPServerInfo(connection_name,serveraddress,username,password,portno);
       FTPCore ftpCore= new FTPCore();

        try {
        FTPConnection connection_details=ftpCore.connect(ftpconnectioninfo);

            JOptionPane.showMessageDialog(this,
                    "Connected to server successfully.",
                    "Connection success",
                    JOptionPane.PLAIN_MESSAGE);

        } catch (ConnectionFailedException e1) {
            JOptionPane.showMessageDialog(this,
                    "Could not connect to the server, please try again",
                    "Connection Failed",
                    JOptionPane.ERROR_MESSAGE);

        }



        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Susham Yerabolu
        lbl_ServerConnect = new JLabel();
        lbl_serveraddress = new JLabel();
        lbl_serverport = new JLabel();
        lbl_username = new JLabel();
        lbl_password = new JLabel();
        txt_serveraddress = new JTextField();
        txt_portno = new JTextField();
        txt_username = new JTextField();
        btn_connect = new JButton();
        btn_save = new JButton();
        txt_serverpassword = new JPasswordField();
        lbl_connectionname = new JLabel();
        txt_connectionName = new JTextField();

        //======== this ========
        Container contentPane = getContentPane();

        //---- lbl_ServerConnect ----
        lbl_ServerConnect.setText("Server Connect");
        lbl_ServerConnect.setFont(new Font(".SF NS Text", Font.BOLD, 16));

        //---- lbl_serveraddress ----
        lbl_serveraddress.setText("Server Address:");

        //---- lbl_serverport ----
        lbl_serverport.setText("Server Port:");
        lbl_serverport.setHorizontalAlignment(SwingConstants.RIGHT);

        //---- lbl_username ----
        lbl_username.setText("Username:");
        lbl_username.setHorizontalAlignment(SwingConstants.RIGHT);

        //---- lbl_password ----
        lbl_password.setText("Password:");
        lbl_password.setHorizontalAlignment(SwingConstants.RIGHT);

        //---- btn_connect ----
        btn_connect.setText("Connect");
        btn_connect.addActionListener(e -> connect_to_Server(e));

        //---- btn_save ----
        btn_save.setText("Save");

        //---- lbl_connectionname ----
        lbl_connectionname.setText("Connection Name:");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lbl_ServerConnect)
                    .addGap(135, 135, 135))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(104, 104, 104)
                            .addComponent(btn_connect)
                            .addGap(26, 26, 26)
                            .addComponent(btn_save, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(83, 83, 83)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lbl_serveraddress, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_username, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_password, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_connectionname, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_serverport, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_serveraddress, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addComponent(txt_username, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addComponent(txt_serverpassword, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addComponent(txt_connectionName, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addComponent(txt_portno, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))))
                    .addContainerGap(91, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbl_ServerConnect)
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(lbl_serveraddress)
                        .addComponent(txt_serveraddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_serverport)
                        .addComponent(txt_portno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(22, 22, 22)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_username)
                        .addComponent(txt_username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_password)
                        .addComponent(txt_serverpassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(9, 9, 9)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_connectionname)
                        .addComponent(txt_connectionName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_connect)
                        .addComponent(btn_save))
                    .addContainerGap(1, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Susham Yerabolu
    private JLabel lbl_ServerConnect;
    private JLabel lbl_serveraddress;
    private JLabel lbl_serverport;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JTextField txt_serveraddress;
    private JTextField txt_portno;
    private JTextField txt_username;
    private JButton btn_connect;
    private JButton btn_save;
    private JPasswordField txt_serverpassword;
    private JLabel lbl_connectionname;
    private JTextField txt_connectionName;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
