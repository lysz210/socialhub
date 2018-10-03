/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.interfacce;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author ciao
 */
public interface HistoryEntryBean {
    
    long getIdHistory();
    void setIdHistory(long id);
    
    Set<String> getServizi();
    void setServizi(Iterable<String> servizi);
    
    Set<String> getParole();
    void setParole(Iterable<String> parole);
    
    Date getData();
    void setData(Date data);
    
    HistoryEntryBean addServizio(String servizio);
    
    HistoryEntryBean addParola(String parola);
}