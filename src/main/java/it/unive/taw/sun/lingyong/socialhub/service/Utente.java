/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service;

import it.unive.taw.sun.lingyong.socialhub.service.interfacce.UtenteBean;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ciao
 */
public class Utente implements UtenteBean{
    private String _idUtente;
    private String _nome;
    private URI _foto;
    
    public Utente() {}
    
    public Utente(String id, String nome, URI foto){
        this._idUtente = id;
        this._nome = nome;
        this._foto = foto;
    }

    @Override
    public String getIdUtente() {
        return this._idUtente;
    }

    @Override
    public void setIdUtente(String id) {
        this._idUtente = id;
    }

    @Override
    public String getNome() {
        return this._nome;
    }

    @Override
    public void setNome(String nome) {
        this._nome = nome;
    }

    @Override
    public URI getFoto() {
        return this._foto;
    }

    @Override
    public void setFoto(URI src) {
        this._foto = src;
    }
    
    @Override
    public String toString(){
        String json;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(this);
        } catch (IOException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
            json = "null";
        }
        return json;
    }
}
