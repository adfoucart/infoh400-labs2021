/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.view;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import ulb.lisa.infoh400.labs2020.controller.DicomDirectoryController;

/**
 *
 * @author 8Utilisateur
 */
public class OpenDICOMDIRWindow extends javax.swing.JFrame {
    
    File selectedFile;
    DicomDirectoryController dcmCtrl;
    
    /**
     * Creates new form OpenDICOMDIRWindow
     */
    public OpenDICOMDIRWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectDICOMDIRButton = new javax.swing.JButton();
        saveToDbButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dicomdirTree = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        dcmAttributesTextArea = new javax.swing.JTextArea();
        imageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        selectDICOMDIRButton.setText("Select DICOMDIR");
        selectDICOMDIRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDICOMDIRButtonActionPerformed(evt);
            }
        });

        saveToDbButton.setText("Save to Database");
        saveToDbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToDbButtonActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        dicomdirTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        dicomdirTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                dicomdirTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(dicomdirTree);

        dcmAttributesTextArea.setColumns(20);
        dcmAttributesTextArea.setRows(5);
        dcmAttributesTextArea.setEnabled(false);
        jScrollPane2.setViewportView(dcmAttributesTextArea);

        imageLabel.setBackground(new java.awt.Color(0, 0, 0));
        imageLabel.setForeground(new java.awt.Color(255, 255, 255));
        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setText("<no image loaded>");
        imageLabel.setToolTipText("");
        imageLabel.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectDICOMDIRButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveToDbButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                    .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectDICOMDIRButton)
                    .addComponent(saveToDbButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectDICOMDIRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectDICOMDIRButtonActionPerformed
        selectedFile = SelectFilePopup.getFile();
        if( selectedFile != null ){
            dcmCtrl = new DicomDirectoryController(selectedFile);
            dicomdirTree.setModel(dcmCtrl.getDicomDirectory());
        }
    }//GEN-LAST:event_selectDICOMDIRButtonActionPerformed

    private void dicomdirTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_dicomdirTreeValueChanged
        // Get the AttributeList from the selected object in the jTree
        dcmCtrl.loadRecord(dicomdirTree.getLastSelectedPathComponent());
        
        dcmAttributesTextArea.setText(dcmCtrl.getRecordAttributesAsString());
        Image img = dcmCtrl.getImageData();
        if( img != null ){
            imageLabel.setIcon(new ImageIcon(img));
            imageLabel.setText("");
        }
        else{
            imageLabel.setIcon(null);
            imageLabel.setText("<no image loaded>");
        }
    }//GEN-LAST:event_dicomdirTreeValueChanged
    
    private void saveToDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToDbButtonActionPerformed
        dcmCtrl.saveImageToDatabase();
    }//GEN-LAST:event_saveToDbButtonActionPerformed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea dcmAttributesTextArea;
    private javax.swing.JTree dicomdirTree;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton saveToDbButton;
    private javax.swing.JButton selectDICOMDIRButton;
    // End of variables declaration//GEN-END:variables
}
