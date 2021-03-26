/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.controller;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.v23.message.ACK;
import ca.uhn.hl7v2.model.v23.message.ADT_A01;
import ca.uhn.hl7v2.model.v23.segment.EVN;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.PID;
import ca.uhn.hl7v2.model.v23.segment.PV1;
import ca.uhn.hl7v2.parser.Parser;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ulb.lisa.infoh400.labs2020.model.Patient;

/**
 *
 * @author 8Utilisateur
 */
public class HL7Services {
    
    public static boolean sendADT_A01(Patient pat, String host, int port){
        try {
            ADT_A01 adt = new ADT_A01();
            adt.initQuickstart("ADT", "A01", "D");
            
            MSH msh = adt.getMSH();
            msh.getSendingApplication().getNamespaceID().setValue("INFOH400");
            
            EVN evn = adt.getEVN();
            evn.getEventTypeCode().setValue("A01");
            evn.getRecordedDateTime().getTimeOfAnEvent().setValue(new Date());
            evn.getEventReasonCode().setValue("01");
            
            PID pid = adt.getPID();
            pid.getPatientIDInternalID(0).getID().setValue(String.valueOf(pat.getIdpatient()));
            pid.getPatientIDInternalID(0).getIdentifierTypeCode().setValue("PI");
            pid.getPatientName(0).getFamilyName().setValue(pat.getIdperson().getFamilyname());
            pid.getPatientName(0).getGivenName().setValue(pat.getIdperson().getFirstname());
            pid.getPatientName(0).getNameTypeCode().setValue("L");
            pid.getDateOfBirth().getTimeOfAnEvent().setValue(pat.getIdperson().getDateofbirth());
            
            PV1 pv1 = adt.getPV1();
            pv1.getPatientClass().setValue("O");
            pv1.getAdmissionType().setValue("R");
                        
            HapiContext context = new DefaultHapiContext();
            Parser parser = context.getPipeParser();
            String encodedMessage = parser.encode(adt);
            
            System.out.println(encodedMessage);
            
            Connection conn = context.newClient(host, port, false);
            
            Initiator init = conn.getInitiator();
            ACK response = (ACK) init.sendAndReceive(adt);
            
            String responseString = parser.encode(response);
            System.out.println("Response:");
            System.out.println(responseString);
            
            return ("AA".equals(response.getMSA().getAcknowledgementCode().getValue()));
        } catch (HL7Exception | IOException | LLPException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        }      
        
        return false;
    }
    
}
