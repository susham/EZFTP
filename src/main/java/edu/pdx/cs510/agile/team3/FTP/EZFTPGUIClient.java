/*
 * Created by JFormDesigner on Tue Aug 08 23:04:42 PDT 2017
 */

package edu.pdx.cs510.agile.team3.FTP;

import org.apache.commons.net.ftp.FTPClient;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.*;
import javax.swing.tree.*;

/**
 * @author Susham Yerabolu
 */
public class EZFTPGUIClient extends JFrame{
    FTPCore ftpCore=null;
    boolean isRemoteSiteTreeInit=false; //this boolean is used to initialize JMenuPoPUp
    FTPServerInfo serverInfo=null;
    public EZFTPGUIClient() {
        initComponents();

        //initializing the JTree with directories and files
        initializeLocalSiteTree();
        initRemoteSiteTree();

    }



    private void initRemoteSiteTree(){
        DefaultTreeModel model = new DefaultTreeModel(new DefaultMutableTreeNode());
        tree2.setModel(model);
        tree2.setRootVisible(false);

    }



    private void initializeLocalSiteTree() {

        try {
            if(createLocalNodes() != null) {
                DefaultTreeModel localMachineModel = new DefaultTreeModel(createLocalNodes(), true);
                tree1.setModel(localMachineModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TreeNode createLocalNodes() {
        DefaultMutableTreeNode root=null;
        try {
            root = new DefaultMutableTreeNode("/");
            LocalFileUtil localFileUtil = new LocalFileUtil();
            java.util.List<LocalFile> localRootList = localFileUtil.getRootList();
            for (LocalFile localroot : localRootList) {
                if (localroot.isDirectory()) {
                    DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(localroot.getFileName(), true);
                    //addChilds(subDirectory,localroot.getFilePath());
                    subDirectory.setAllowsChildren(true);
                    root.add(subDirectory);

                } else {
                    root.add(new DefaultMutableTreeNode(localroot.getFileName(), false));
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return root;

    }
   private TreeNode createRemoteNodes(FTPCore ftpCore)throws  IOException{
        DefaultMutableTreeNode root=null;
        if(ftpCore.isConnected()) {
            root= new DefaultMutableTreeNode("/");
            java.util.List<RemoteFile> remoteFileList=ftpCore.getDirectoryContentsAtPath("/");
            for (RemoteFile file:remoteFileList) {
                if(file.directoryFlag){
                    DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(file.getFileName(),true);
                    subDirectory.setAllowsChildren(true);
                    root.add(subDirectory);
                }
                else
                {
                    root.add(new DefaultMutableTreeNode(file.getFileName(),false));
                }
            }
            isRemoteSiteTreeInit=true;

        }

        return root;
    }

    private void localsiteTreeWillExpand(TreeExpansionEvent e)
        throws ExpandVetoException {
        TreePath path = e.getPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        String completePath = "";
        int pathCount = path.getPathCount();
        for (int i = 1; i < pathCount; i++) {

                completePath += File.separator + path.getPath()[i].toString();
        }

        addLocalSiteChild(node,completePath);
    }

    private void addLocalSiteChild(DefaultMutableTreeNode node, String completePath) {
        LocalFileUtil localFileUtil= new LocalFileUtil();
       try {

           java.util.List<LocalFile> localFileList = localFileUtil.getFileListByPath(completePath);
           for (LocalFile file : localFileList) {
               if (file.isDirectory()) {
                   DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(file.getFileName(), true);
                   node.add(subDirectory);
               } else {
                   node.add(new DefaultMutableTreeNode(file.getFileName(), false));
               }
           }

       }
       catch(IOException e){
           e.printStackTrace();
       }

    }

    private void quickConnectActionPerformed(ActionEvent e) {
        FTPCore ftpCore= new FTPCore();

        try {
            String username=txt_UserName.getText().trim();
            String password=new String(txt_Password.getPassword()).trim();
            String serveraddress=txt_ServerAddress.getText().trim();
            int portno=Integer.parseInt(txt_Port.getText().trim());
            String connection_name=txt_ConnectionName.getText().trim();

            FTPServerInfo ftpconnectioninfo= new FTPServerInfo(connection_name,serveraddress,username,password,portno);
            FTPConnection connection_details=ftpCore.connect(ftpconnectioninfo);
            serverInfo=ftpconnectioninfo;
            if(ftpCore.isConnected()) {

                JOptionPane.showMessageDialog(this,
                        "Connected to server successfully.",
                        "Connection success",
                        JOptionPane.PLAIN_MESSAGE);
                showRemoteFileList(ftpCore);

            }
        } catch (ConnectionFailedException e1) {
            JOptionPane.showMessageDialog(this,
                    "Could not connect to the server, please try again",
                    "Connection Failed",
                    JOptionPane.ERROR_MESSAGE);

        }
        catch(NumberFormatException ex){

            JOptionPane.showMessageDialog(this,
                    "Please provide a valid port number",
                    "Connection Failed",
                    JOptionPane.ERROR_MESSAGE);

        }

    }

    //Displays the server files on JTree
    private void showRemoteFileList(FTPCore ftpCore) {
        DefaultTreeModel remoteMachineModel= null;
        try {
            this.ftpCore=ftpCore;
            remoteMachineModel = new DefaultTreeModel(createRemoteNodes(ftpCore),true);
            tree2.setModel(remoteMachineModel);
            tree2.setRootVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void remotesiteTreeWillExpand(TreeExpansionEvent e)
        throws ExpandVetoException
    {
        TreePath remoteFilePath= e.getPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) remoteFilePath.getLastPathComponent();
        String completePath="";
        int pathCount=remoteFilePath.getPathCount();
        for(int i=1;i< pathCount; i++) {

                completePath += File.separator + remoteFilePath.getPath()[i].toString();
        }
        addRemoteChild(node,completePath,ftpCore);
    }

    private void addRemoteChild(DefaultMutableTreeNode node, String completePath, FTPCore ftpCore) {
        try {
            java.util.List<RemoteFile> remoteChildList= ftpCore.getDirectoryContentsAtPath(completePath);
            for (RemoteFile childFile:remoteChildList) {
                if(childFile.directoryFlag)
                {
                    DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(childFile.getFileName(),true);
                    node.add(subDirectory);
                }
                else
                {
                    node.add(new DefaultMutableTreeNode(childFile.getFileName(),false));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void localSiteMouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){

                JPopupMenu menu = new JPopupMenu ();
                JMenuItem uploadItem=new JMenuItem ( "Upload" );
                uploadItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      //get the path selected in JTree and create a dialog box to confirm to upload the directory or files to the server in the root node
                      String path="";
                        Object[] paths = tree1.getSelectionPath().getPath();
                        String completePath= getSelectedNodePath(paths);
                        //call method to put the files or directories in the server
                          File uploadfile= new File(completePath);
                        if(uploadfile.isDirectory())
                        {// upload all the files and sub directories under the root directory of the server
                          String remotePath = JOptionPane.showInputDialog("Enter remote path to upload to");
                          try {
                            ftpCore.uploadDirectory(remotePath, completePath, "");
                          } catch (IOException e1) {
                            e1.printStackTrace();
                          }
                        }
                        else {
                          String remotePath = JOptionPane.showInputDialog("Enter remote path to upload to");
                          //upload a single file to the root directory o
                          ftpCore.uploadFile(remotePath, completePath);
                        }
                    }
                });

                menu.add(uploadItem);
                menu.add ( new JMenuItem ( "Rename" ));
                menu.add ( new JMenuItem ( "Delete" ));
           tree1.setComponentPopupMenu(menu);

        }
    }

    private String getSelectedNodePath(Object[] paths) {
        String completePath="";
        int pathCount=paths.length;
        for(int i=1;i< pathCount; i++) {

                completePath += File.separator + paths[i].toString();
        }
        return completePath;

    }


    private void remoteSiteMouseClicked(MouseEvent e) {
        // TODO add your code here
      FTPClient ftpClient = new FTPClient();
        if (SwingUtilities.isRightMouseButton(e)) {
            if (isRemoteSiteTreeInit) {
                JPopupMenu remotesitemenu = new JPopupMenu();
                JMenuItem createDirectoryItem = new JMenuItem("Create Directory");
                createDirectoryItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            //can make this code better.
                            Object[] paths = tree2.getSelectionPath().getPath();
                            String remotesitePath = getSelectedNodePath(paths);
                            ftpClient.connect(serverInfo.host, serverInfo.port);
                            ftpClient.login(serverInfo.username, serverInfo.password);
                            ftpClient.changeWorkingDirectory(remotesitePath);
                            int returnCode = ftpClient.getReplyCode();
                            if (returnCode != 550) { //If it is a valid directory
                                String directoryName = JOptionPane.showInputDialog("Enter new directory name");
                                if(directoryName !=null) {
                                    boolean isCreated=ftpCore.createNewDirectory(serverInfo,remotesitePath+File.separator+ directoryName);


                                }
                            }

                    }
                    catch(Exception ex)
                    {

                    }
                }
                });

                JMenuItem createFileItem = new JMenuItem("Create File");
                createFileItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] paths = tree2.getSelectionPath().getPath();
                        String remotesitePath = getSelectedNodePath(paths);
                        String fileName = JOptionPane.showInputDialog("Enter new file name");

                        File newFile = new File(System.getProperty("user.dir") + File.separator + fileName);
                      try {
                        newFile.createNewFile();
                      } catch (IOException e1) {
                        e1.printStackTrace();
                      }
                      String[] list = new String[2];
                      list[0] = newFile.getPath();
                      list[1] = remotesitePath;
                      ftpCore.uploadFiles(serverInfo, list);
                      //delete new file from local
                      newFile.delete();
                    }
                });
                JMenuItem deleteItem = new JMenuItem("Delete");
                deleteItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] paths = tree2.getSelectionPath().getPath();
                        String remotesitePath = getSelectedNodePath(paths);
                        //check if a directory or file
                        try {
                          ftpClient.connect(serverInfo.host, serverInfo.port);
                          ftpClient.login(serverInfo.username, serverInfo.password);
                          ftpClient.changeWorkingDirectory(remotesitePath);
                          int returnCode = ftpClient.getReplyCode();
                          if (returnCode != 550) { //If it is a valid directory
                            ftpCore.deleteDirectory(serverInfo, remotesitePath); //delete the folder
                          } else { //Check if valid file
                            InputStream inputStream = ftpClient.retrieveFileStream(remotesitePath);
                            returnCode = ftpClient.getReplyCode();
                            if (returnCode != 550) { // return code 550: file/directory is unavailable
                              ftpCore.deleteFile(serverInfo, remotesitePath); //delete the file
                            }
                          }
                        } catch (Exception ex) {
                          System.out.println("Exception occurred for deleting remote file from GUI\n\n" + ex);
                        }
                    }
                });
                JMenuItem downloadItem = new JMenuItem("Download");
                downloadItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] paths = tree2.getSelectionPath().getPath();
                        String remotesitePath = getSelectedNodePath(paths);
                        //check if a directory or file
                        try {
                          ftpClient.connect(serverInfo.host, serverInfo.port);
                          ftpClient.login(serverInfo.username, serverInfo.password);
                          String savePath = JOptionPane.showInputDialog("Enter the path to save to:");
                          ftpClient.changeWorkingDirectory(remotesitePath);
                          int returnCode = ftpClient.getReplyCode();
                          if (returnCode != 550) { //If it is a valid directory
                            ftpCore.downloadDirectory(remotesitePath, "", savePath); //save the directory
                          } else { //Check if valid file
                            InputStream inputStream = ftpClient.retrieveFileStream(remotesitePath);
                            returnCode = ftpClient.getReplyCode();
                            if (returnCode != 550) { // return code 550: file/directory is unavailable
                              ftpCore.downloadFile(remotesitePath, savePath); //save the file
                            }
                          }
                        } catch (Exception ex) {
                          System.out.println("Exception occurred for deleting remote file from GUI\n\n" + ex);
                        }
                    }
                });
                JMenuItem refreshRemote = new JMenuItem("Refresh");
                refreshRemote.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    showRemoteFileList(ftpCore);
                  }
                });
                remotesitemenu.add(createDirectoryItem);
                remotesitemenu.add(createFileItem);
                remotesitemenu.add(deleteItem);
                remotesitemenu.add(downloadItem);
                remotesitemenu.add(refreshRemote);
                tree2.setComponentPopupMenu(remotesitemenu);
            }
        }
    }

    private void remotesiteMouseClicked(MouseEvent e) {
        // TODO add your code here
    }




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Susham Yerabolu
        panel1 = new JPanel();
        label2 = new JLabel();
        txt_UserName = new JTextField();
        label3 = new JLabel();
        txt_Password = new JPasswordField();
        label4 = new JLabel();
        txt_ServerAddress = new JTextField();
        label5 = new JLabel();
        txt_Port = new JTextField();
        btn_QuickConnect = new JButton();
        label1 = new JLabel();
        txt_ConnectionName = new JTextField();
        button2 = new JButton();
        panel2 = new JPanel();
        scrollPane1 = new JScrollPane();
        tree1 = new JTree();
        scrollPane2 = new JScrollPane();
        tree2 = new JTree();

        //======== this ========
        Container contentPane = getContentPane();

        //======== panel1 ========
        {

            // JFormDesigner evaluation mark
            panel1.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


            //---- label2 ----
            label2.setText("UserName:");

            //---- label3 ----
            label3.setText("Password:");

            //---- label4 ----
            label4.setText("Server Address:");

            //---- label5 ----
            label5.setText("Port:");

            //---- btn_QuickConnect ----
            btn_QuickConnect.setText("Quick Connect");
            btn_QuickConnect.addActionListener(e -> quickConnectActionPerformed(e));

            //---- label1 ----
            label1.setText("Name:");

            //---- button2 ----
            button2.setText("Save Connection");

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(label2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_UserName, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label3)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Password, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label4)
                        .addGap(12, 12, 12)
                        .addComponent(txt_ServerAddress, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label5)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Port, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label1)
                        .addGap(2, 2, 2)
                        .addComponent(txt_ConnectionName, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_QuickConnect)
                        .addGap(18, 18, 18)
                        .addComponent(button2)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label2)
                            .addComponent(txt_UserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ServerAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label5)
                            .addComponent(txt_Port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_QuickConnect)
                            .addComponent(label1)
                            .addComponent(txt_ConnectionName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label3)
                            .addComponent(txt_Password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label4)
                            .addComponent(button2))
                        .addContainerGap(10, Short.MAX_VALUE))
            );
        }

        //======== panel2 ========
        {
            panel2.setLayout(new GridLayout());

            //======== scrollPane1 ========
            {

                //---- tree1 ----
                tree1.setDropMode(DropMode.INSERT);
                tree1.addTreeWillExpandListener(new TreeWillExpandListener() {
                    @Override
                    public void treeWillCollapse(TreeExpansionEvent e)
                        throws ExpandVetoException
                    {}
                    @Override
                    public void treeWillExpand(TreeExpansionEvent e)
                        throws ExpandVetoException
                    {
                        localsiteTreeWillExpand(e);
                    }
                });
                tree1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        localSiteMouseClicked(e);
                    }
                });
                scrollPane1.setViewportView(tree1);
            }
            panel2.add(scrollPane1);

            //======== scrollPane2 ========
            {

                //---- tree2 ----
                tree2.addTreeWillExpandListener(new TreeWillExpandListener() {
                    @Override
                    public void treeWillCollapse(TreeExpansionEvent e)
                        throws ExpandVetoException
                    {}
                    @Override
                    public void treeWillExpand(TreeExpansionEvent e)
                        throws ExpandVetoException
                    {
                        remotesiteTreeWillExpand(e);
                    }
                });
                tree2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        remoteSiteMouseClicked(e);
                        remotesiteMouseClicked(e);
                    }
                });
                scrollPane2.setViewportView(tree2);
            }
            panel2.add(scrollPane2);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(panel2, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Susham Yerabolu
    private JPanel panel1;
    private JLabel label2;
    private JTextField txt_UserName;
    private JLabel label3;
    private JPasswordField txt_Password;
    private JLabel label4;
    private JTextField txt_ServerAddress;
    private JLabel label5;
    private JTextField txt_Port;
    private JButton btn_QuickConnect;
    private JLabel label1;
    private JTextField txt_ConnectionName;
    private JButton button2;
    private JPanel panel2;
    private JScrollPane scrollPane1;
    private JTree tree1;
    private JScrollPane scrollPane2;
    private JTree tree2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
