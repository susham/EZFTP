/*
 * Created by JFormDesigner on Fri Jul 21 22:03:03 PDT 2017
 */

package edu.pdx.cs510.agile.team3.FTP;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.*;

/**
 * @author Susham Yerabolu
 */
public class FileListViewer extends JFrame {
    public FileListViewer(FTPCore ftpCore) {
        initComponents();
        tree1.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        try {
            DefaultTreeModel localMachineModel= new DefaultTreeModel(createLocalNodes(),true);
            DefaultTreeModel remoteMachineModel= new DefaultTreeModel(createRemoteNodes(ftpCore),true);
            tree1.setModel(localMachineModel);
            tree2.setModel(remoteMachineModel);

        } catch (IOException e) {
            e.printStackTrace();
        }



        TreeWillExpandListener treeWillExpandListener = new TreeWillExpandListener() {
          public void treeWillCollapse(TreeExpansionEvent treeExpansionEvent)
                  throws ExpandVetoException {

          }

          public void treeWillExpand(TreeExpansionEvent treeExpansionEvent) throws ExpandVetoException {
              TreePath path = treeExpansionEvent.getPath();
              DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
              String completePath="";
              int pathCount=path.getPathCount();
              for(int i=1;i< pathCount; i++) {
                  if (i == 1)
                      completePath += path.getPath()[i].toString();
                  else
                      completePath += "/" + path.getPath()[i].toString();
              }
              addChilds(node,completePath);
              System.out.println("WillExpand: " + completePath);

          }
      };
      tree1.addTreeWillExpandListener(treeWillExpandListener);

      tree1.addTreeSelectionListener(new TreeSelectionListener() {
          public void valueChanged(TreeSelectionEvent e) {
              DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                      tree1.getLastSelectedPathComponent();

              if (node == null) return;

              String data = node.getUserObject().toString();
              System.out.println("Selected Node:" + data);


          }
      });

    }




    private TreeNode createLocalNodes() throws IOException {
        root= new DefaultMutableTreeNode("/");
        LocalFileUtil localFileUtil= new LocalFileUtil();
        java.util.List<LocalFile> localRootList= localFileUtil.getRootList();
        for (LocalFile localroot:localRootList) {
            if (localroot.isDirectory()) {
                DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(localroot.getFilePath(),true);
                //addChilds(subDirectory,localroot.getFilePath());
                subDirectory.setAllowsChildren(true);
                root.add(subDirectory);

            } else {
                root.add(new DefaultMutableTreeNode(localroot.getFilePath(),false));

            }

        }


        return root;
    }





    private void addChilds(DefaultMutableTreeNode rootNode, String path) {
        LocalFileUtil localFileUtil= new LocalFileUtil();
        try {

            List<LocalFile> localFileList= localFileUtil.getFileListByPath(path);
            for(LocalFile file:localFileList) {
                if(file.isDirectory()) {
                    DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(file.getFileName(),true);
                    subDirectory.setAllowsChildren(true);
                   // addChilds(subDirectory, file.getFilePath());
                    rootNode.add(subDirectory);
                } else {
                    rootNode.add(new DefaultMutableTreeNode(file.getFileName(),false));
                }
            }


        }catch(Exception e)
        {

        }

    }

    private String getParentPath(DefaultMutableTreeNode rootNode,String path) {

        if(rootNode.getParent() != null)
        {
            path=rootNode.getParent()+path;
            getParentPath((DefaultMutableTreeNode)rootNode.getParent(), path);
        }

            return path;
    }


    private TreeNode createRemoteNodes(FTPCore ftpCore)throws  IOException{
        DefaultMutableTreeNode root=null;
        if(ftpCore.isConnected()) {
           root= new DefaultMutableTreeNode("/");
            List<RemoteFile> remoteFileList=ftpCore.getDirectoryContentsAtPath("/");
            for (RemoteFile file:remoteFileList) {
                if(file.directoryFlag){
                    DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(file.getFilePath(),true);
                    //addChilds(subDirectory,localroot.getFilePath());
                    subDirectory.setAllowsChildren(true);
                    root.add(subDirectory);

                }
                else
                {
                    root.add(new DefaultMutableTreeNode(file.getFilePath(),false));

                }
                //System.out.println(file.directoryFlag);
                //defaultTreeModel= new DefaultTreeModel(createRemoteNodes(),true);
            }

        }

        return root;


    }


    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Susham Yerabolu
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        try {

            tree1 = new JTree();
            panel2 = new JPanel();
            scrollPane2 = new JScrollPane();
            tree2= new JTree();
            button1 = new JButton();
            button2 = new JButton();
            progressBar1 = new JProgressBar();

        }
        catch(Exception e)
        {

        }

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


            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(tree1);
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                        .addContainerGap())
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(39, Short.MAX_VALUE))
            );
        }

        //======== panel2 ========
        {

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(tree2);
            }

            GroupLayout panel2Layout = new GroupLayout(panel2);
            panel2.setLayout(panel2Layout);
            panel2Layout.setHorizontalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                        .addContainerGap())
            );
            panel2Layout.setVerticalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 466, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }

        //---- button1 ----
        button1.setText(">>");

        //---- button2 ----
        button2.setText("<<");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(16, 16, 16)
                                    .addComponent(button1))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(button2)))
                            .addGap(18, 18, 18)
                            .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(166, Short.MAX_VALUE))))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(panel2, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(116, 116, 116)
                                    .addComponent(button1)
                                    .addGap(42, 42, 42)
                                    .addComponent(button2)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                            .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Susham Yerabolu
    private DefaultTreeModel defaultTreeModel;
    private  DefaultMutableTreeNode root;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTree tree1;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JTree tree2;
    private JButton button1;
    private JButton button2;
    private JProgressBar progressBar1;


    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
