/*
 * Created by JFormDesigner on Fri Jul 21 22:03:03 PDT 2017
 */

package edu.pdx.cs510.agile.team3.FTP;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;

/**
 * @author Susham Yerabolu
 */
public class FileListViewer extends JFrame implements TreeWillExpandListener {
    public FileListViewer() {
        initComponents();

    }

    private TreeNode createNodes() {
        DefaultMutableTreeNode root;
        DefaultMutableTreeNode grandparent;
        DefaultMutableTreeNode parent;
        LocalFileUtil localFileUtil= new LocalFileUtil();
        //java.util.List<LocalFile> localRootList= localFileUtil.getRootList();
        root = new DefaultMutableTreeNode("root");

       /* for (LocalFile localroot:localRootList) {
            DefaultMutableTreeNode directorynode= new DefaultMutableTreeNode(new File(localroot.filePath),true);
            List<LocalFile> directoryFiles=localFileUtil.getFileListByPath(localroot.filePath);
            for (LocalFile file: directoryFiles) {

                DefaultMutableTreeNode files_Directory= new DefaultMutableTreeNode(new File(file.filePath),true);
                directorynode.add(files_Directory);
                
            }
            root.add(directorynode);

        }

        grandparent = new DefaultMutableTreeNode("Potrero Hill");
        root.add(grandparent);

        parent = new DefaultMutableTreeNode("Restaurants");
        grandparent.add(parent);*/


        return root;
    }


    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Susham Yerabolu
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();

        tree1 = new JTree();
        tree1.setDragEnabled(true);
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        tree2 = new JTree();
        tree2.setDragEnabled(true);
        button1 = new JButton();
        button2 = new JButton();

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
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                        .addContainerGap())
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
                    .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                        .addContainerGap())
            );
            panel2Layout.setVerticalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                        .addContainerGap())
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
                            .addGap(16, 16, 16)
                            .addComponent(button1))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(button2)))
                    .addGap(18, 18, 18)
                    .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(122, 122, 122)
                    .addComponent(button1)
                    .addGap(42, 42, 42)
                    .addComponent(button2)
                    .addContainerGap(223, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Susham Yerabolu
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTree tree1;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JTree tree2;
    private JButton button1;
    private JButton button2;

    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {


    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
