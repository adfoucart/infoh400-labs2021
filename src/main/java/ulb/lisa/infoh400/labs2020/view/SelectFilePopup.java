/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.view;

import java.io.File;
import java.util.Properties;
import javax.swing.JFileChooser;
import ulb.lisa.infoh400.labs2020.controller.GlobalProperties;

/**
 *
 * @author 8Utilisateur
 */
public class SelectFilePopup {
    
    static Properties props = GlobalProperties.getProperties();
    static String defaultPath = props.getProperty("dicom.dicomdir.defaultpath");
    
    public static File getFile(String defaultPath){
        JFileChooser jfc = new JFileChooser(defaultPath);
        
        int returnValue = jfc.showOpenDialog(null);
        
        if( returnValue == JFileChooser.APPROVE_OPTION ){
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            
            return selectedFile;
        }
        
        return null;
    }
    public static File getFile(){
        return getFile(defaultPath);
    }
    
}
