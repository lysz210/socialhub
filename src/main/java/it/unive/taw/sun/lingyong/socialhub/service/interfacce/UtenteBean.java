/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.interfacce;

import java.net.URI;

/**
 *
 * @author ciao
 */
public interface UtenteBean {
    
    String getIdUtente();
    void setIdUtente(String id);
    
    String getNome();
    void setNome(String nome);
    
    URI getFoto();
    void setFoto(URI src);
}
