/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author 8Utilisateur
 */
public class GlobalProperties {
    
    private static GlobalProperties instance = null;
    private Properties props = null;
    
    private GlobalProperties(){
        props = new Properties();
        String fileName = "app.config";
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
            props.load(is);
        } catch (IOException ex) {
            setDefaultProperties();
        }
    }
    
    private void setDefaultProperties(){
        props.setProperty("app.name", "HIS");
        props.setProperty("app.site", "Unknown");
        
        props.setProperty("dicom.localscp.port", "104");
        props.setProperty("dicom.localscp.aet", "HISSTORE");
        props.setProperty("dicom.localscp.savedir", "D:/Adrien/Documents/NetBeansProjects/infoh400-labs2021/src/main/resources/localpacs");
        
        props.setProperty("dicom.storescu.aet", "INFOH400");
        
        props.setProperty("dicom.remotescp.port", "11112");
        props.setProperty("dicom.remotescp.host", "localhost");
        props.setProperty("dicom.remotescp.aet", "STORESCP");
        
        props.setProperty("dicom.dicomdir.defaultpath", "D:/Adrien/pCloud/ULB/TPs/INFOH400/Data");
        
        props.setProperty("hl7.localserver.port", "54321");
        
        props.setProperty("fhir.defaultHost", "http://localhost:8080/infoh400-labs2021-web/fhir");
    }
            
    public static Properties getProperties(){
        if( instance == null ){
            instance = new GlobalProperties();
        }
        
        return instance.props;
    }
    
}
