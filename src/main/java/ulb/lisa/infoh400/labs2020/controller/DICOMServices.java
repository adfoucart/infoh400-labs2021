/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.controller;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.ReceivedObjectHandler;
import com.pixelmed.network.StorageSOPClassSCPDispatcher;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 8Utilisateur
 */
public class DICOMServices {
    
    public void startStoreSCP() {
        try {
            new Thread(new StorageSOPClassSCPDispatcher(104, "HISSTORE", new File("D:\\Adrien\\Documents\\NetBeansProjects\\infoh400-labs2021\\src\\main\\resources\\localpacs"), new OurReceivedObjectHandler())).start();
        } catch (IOException ex) {
            Logger.getLogger(DICOMServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private class OurReceivedObjectHandler extends ReceivedObjectHandler {

        @Override
        public void sendReceivedObjectIndication(String dicomFileName, String transferSyntax, String callingAETitle) throws DicomNetworkException, DicomException, IOException {
            if (dicomFileName != null) {
                System.err.println("Received: " + dicomFileName + " from " + callingAETitle + " in " + transferSyntax);
                DicomInstanceController dcmInstanceCtrl = new DicomInstanceController(new File(dicomFileName));
                
                dcmInstanceCtrl.saveInstanceToDatabase();
            }
        }
    }
    
}
