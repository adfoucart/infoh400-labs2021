/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.controller;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomDictionary;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.display.SourceImage;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.StorageSOPClassSCU;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ulb.lisa.infoh400.labs2020.view.OpenDICOMDIRWindow;

/**
 *
 * @author 8Utilisateur
 */
public class DicomInstanceController {
    private File dcmInstanceFile;
    private AttributeList al = null;
    DicomDictionary dic = new DicomDictionary();

    public DicomInstanceController(File dcmInstanceFile) {
        this.dcmInstanceFile = dcmInstanceFile;
    }
    
    public AttributeList getAttributeList(){
        if( al == null ){
            al = new AttributeList();
            try {
                al.read(dcmInstanceFile);
            } catch (IOException | DicomException ex) {
                Logger.getLogger(DicomInstanceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return al;
    }
    
    public String getAttributeAsString(AttributeTag at){
        if( al == null ) getAttributeList();
        
        return al.get(at).getDelimitedStringValuesOrEmptyString();
    }
    
    public File getDcmInstanceFile(){
        return dcmInstanceFile;
    }
    
    public Image getImageData(){
        try {
            SourceImage sImg = new SourceImage(dcmInstanceFile.getAbsolutePath());
            Image img = sImg.getBufferedImage();
            return img;
        } catch (IOException | DicomException ex) {
            Logger.getLogger(OpenDICOMDIRWindow.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return null;
    }

    public String getAllAttributesAsString() {
        if( al == null ) getAttributeList();
        
        String outString = "";
        for( AttributeTag tag: al.keySet() ){
            outString += "[" + dic.getFullNameFromTag(tag) + "] " + getAttributeAsString(tag) + "\n";
        }
        
        return outString;        
    }
    
    public void storeDICOMImage(String aetitle, String host, int port, String sopClassUID, String sopInstanceUID){
        try {
            new StorageSOPClassSCU(host,port,aetitle,"INFOH400",dcmInstanceFile.getAbsolutePath(),sopClassUID,sopInstanceUID,0,0);
        } catch (DicomNetworkException | DicomException | IOException ex) {
            Logger.getLogger(OpenDICOMDIRWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
