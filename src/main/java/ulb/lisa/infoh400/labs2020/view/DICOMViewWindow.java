/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.view;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import ulb.lisa.infoh400.labs2020.controller.DicomInstanceController;

/**
 *
 * @author 8Utilisateur
 */
public class DICOMViewWindow extends javax.swing.JFrame {
    
    File selectedFile;
    DicomInstanceController dcmCtrl;
    
    /**
     * Creates new form OpenDICOMDIRWindow
     */
    public DICOMViewWindow(File f) {
        initComponents();
        
        selectedFile = f;
        dcmCtrl = new DicomInstanceController(selectedFile);
        
        dcmAttributesTextArea.setText(dcmCtrl.getAllAttributesAsString());
        Image img = dcmCtrl.getImageData();
        if( img != null ){
            imageLabel.setIcon(new ImageIcon(img));
            imageLabel.setText("");
        }
        else{
            imageLabel.setIcon(null);
            imageLabel.setText("<no image loaded>");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        dcmAttributesTextArea = new javax.swing.JTextArea();
        imageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                    .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea dcmAttributesTextArea;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
