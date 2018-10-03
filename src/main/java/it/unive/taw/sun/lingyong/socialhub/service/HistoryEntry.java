/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service;

import it.unive.taw.sun.lingyong.socialhub.service.interfacce.HistoryEntryBean;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ciao
 */
public class HistoryEntry implements HistoryEntryBean {
    
    private long _idHistory;
    private final Set<String> _servizi = new HashSet<>();
    private final Set<String> _parole = new HashSet<>();
    private Date _data = new Date();
    

    @Override
    public long getIdHistory() {
        return this._idHistory;
    }

    @Override
    public void setIdHistory(long id) {
        this._idHistory = id;
    }

    @Override
    public Set<String> getServizi() {
        return this._servizi;
    }

    @Override
    public void setServizi(Iterable<String> servizi) {
        for(String servizio: servizi){
            this._servizi.add(servizio);
        }
    }

    @Override
    public Set<String> getParole() {
        return this._parole;
    }

    @Override
    public void setParole(Iterable<String> parole) {
        for(String parola: parole){
            this._parole.add(parola);
        }
    }
    
    @Override
    public void setData(Date data){
        this._data = data;
    }
    
    @Override
    public Date getData(){
        return this._data;
    }

    @Override
    public HistoryEntryBean addServizio(String servizio) {
        this._servizi.add(servizio);
        return this;
    }

    @Override
    public HistoryEntryBean addParola(String parola) {
        this._parole.add(parola);
        return this;
    }
    
}
