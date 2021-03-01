/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.controller;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomDictionary;
import com.pixelmed.dicom.DicomDirectory;
import com.pixelmed.dicom.DicomDirectoryRecord;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ulb.lisa.infoh400.labs2020.view.OpenDICOMDIRWindow;

/**
 *
 * @author 8Utilisateur
 */
public class DicomDirectoryController {
    
    DicomDirectory dcmdir = null;
    DicomDirectoryRecord ddr;
    File dcmdirfile;
    AttributeList recordAttributeList;
    DicomDictionary dic = new DicomDictionary();

    public DicomDirectoryController(File dcmdirfile) {
        this.dcmdirfile = dcmdirfile;
        
        try {
            AttributeList list = new AttributeList();
            list.read(new DicomInputStream(dcmdirfile));
            dcmdir = new DicomDirectory(list);
        } catch (IOException | DicomException ex) {
            Logger.getLogger(OpenDICOMDIRWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DicomDirectory getDicomDirectory(){
        return dcmdir;
    }
    
    public void loadRecord(Object ddr){
        this.ddr = (DicomDirectoryRecord) ddr;
        recordAttributeList = this.ddr.getAttributeList();
    }
    
    public String getRecordAttributesAsString(){
        if( recordAttributeList == null ) return "";
        
        String outString = "";
        for( AttributeTag tag: recordAttributeList.keySet() ){
            outString += "[" + dic.getFullNameFromTag(tag) + "] " + recordAttributeList.get(tag).getDelimitedStringValuesOrEmptyString() + "\n";
        }
        
        return outString;
    }
    
    public Image getImageData(){
        if( recordAttributeList == null ) return null;
        if( recordAttributeList.get(TagFromName.DirectoryRecordType).getDelimitedStringValuesOrEmptyString().equals("IMAGE") ){
            String rel_path = recordAttributeList.get(TagFromName.ReferencedFileID).getDelimitedStringValuesOrEmptyString();
            File dcmInstanceFile = new File(dcmdirfile.getParent(), rel_path);

            try {
                SourceImage sImg = new SourceImage(dcmInstanceFile.getAbsolutePath());
                Image img = sImg.getBufferedImage();
                return img;
            } catch (IOException | DicomException ex) {
                Logger.getLogger(OpenDICOMDIRWindow.class.getName()).log(Level.SEVERE, null, ex);
            }                
        }
        return null;
    }

    public boolean saveImageToDatabase() {
        if( recordAttributeList == null ) return false;
        if( recordAttributeList.get(TagFromName.DirectoryRecordType).getDelimitedStringValuesOrEmptyString().equals("IMAGE") ){
            String rel_path = recordAttributeList.get(TagFromName.ReferencedFileID).getDelimitedStringValuesOrEmptyString();
            File dcmInstanceFile = new File(dcmdirfile.getParent(), rel_path);
            
            AttributeList al = new AttributeList();
            try {
                al.read(dcmInstanceFile);
                String instanceUID = al.get(TagFromName.SOPInstanceUID).getDelimitedStringValuesOrEmptyString();
                String studyUID = al.get(TagFromName.StudyInstanceUID).getDelimitedStringValuesOrEmptyString();
                String seriesUID = al.get(TagFromName.SeriesInstanceUID).getDelimitedStringValuesOrEmptyString();
                String patientID = al.get(TagFromName.PatientID).getDelimitedStringValuesOrEmptyString();

                ulb.lisa.infoh400.labs2020.model.Image img = new ulb.lisa.infoh400.labs2020.model.Image();
                img.setInstanceuid(instanceUID);
                img.setStudyuid(studyUID);
                img.setSeriesuid(seriesUID);
                img.setPatientDicomIdentifier(patientID);

                EntityManagerFactory emfac = Persistence.createEntityManagerFactory("infoh400_PU");
                ImageJpaController imgCtrl = new ImageJpaController(emfac);
                imgCtrl.create(img);

                return true;
            } catch (IOException | DicomException ex) {
                Logger.getLogger(DicomDirectoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
