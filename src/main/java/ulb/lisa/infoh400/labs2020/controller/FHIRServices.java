/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.util.ArrayList;
import java.util.List;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;
import ulb.lisa.infoh400.labs2020.model.Person;

/**
 *
 * @author 8Utilisateur
 */
public class FHIRServices {
    
    private final FhirContext ctx;
    
    public FHIRServices(){
        ctx = FhirContext.forR4();
    }
    
    private static ulb.lisa.infoh400.labs2020.model.Patient getPatient(Patient p){
        Person idperson = new Person();
        idperson.setFirstname(p.getNameFirstRep().getGivenAsSingleString());
        idperson.setFamilyname(p.getNameFirstRep().getFamily());
        idperson.setDateofbirth(p.getBirthDate());
        
        ulb.lisa.infoh400.labs2020.model.Patient pat = new ulb.lisa.infoh400.labs2020.model.Patient();
        pat.setIdperson(idperson);
        pat.setStatus("active");
        
        return pat;
    }
    
    private static Patient getPatient(ulb.lisa.infoh400.labs2020.model.Patient p){
        Patient pat = new Patient();
        pat.getNameFirstRep().setFamily(p.getIdperson().getFamilyname());
        List<StringType> givenNames = new ArrayList();
        givenNames.add(new StringType(p.getIdperson().getFirstname()));
        pat.getNameFirstRep().setGiven(givenNames);
        pat.setBirthDate(p.getIdperson().getDateofbirth());
        
        return pat;
    }
    
    public List<ulb.lisa.infoh400.labs2020.model.Patient> searchByFamilyName(String familyName, String serverBase){
        List<ulb.lisa.infoh400.labs2020.model.Patient> patients = new ArrayList();
        
        IGenericClient client = ctx.newRestfulGenericClient(serverBase);
        
        Bundle results = client.search().forResource(Patient.class)
                                .where(Patient.FAMILY.matches().value(familyName))
                                .returnBundle(Bundle.class)
                                .execute();
        
        for( Bundle.BundleEntryComponent entry : results.getEntry()){
            patients.add(getPatient((Patient) entry.getResource()));
        }
        
        return patients;
    }
    
}
