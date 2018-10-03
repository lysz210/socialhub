/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service;

import it.unive.taw.sun.lingyong.socialhub.service.interfacce.PostBean;
import java.net.URI;

/**
 *
 * @author ciao
 */
public class Post implements PostBean{
    
    private String _titolo;
    private String _contenuto;
    private URI _thumb;
    private URI _link;
    private String _servizio;
    
    public Post(){ }
    
    public Post(String titolo, String contenuto, URI thumb, URI link, String servizio){
        this._titolo = titolo;
        this._contenuto = contenuto;
        this._thumb = thumb;
        this._link = link;
        this._servizio = servizio;
    }

    @Override
    public String getTitolo() {
        return this._titolo;
    }

    @Override
    public void setTitolo(String titolo) {
        this._titolo = titolo;
    }

    @Override
    public String getContenuto() {
        return this._contenuto;
    }

    @Override
    public void setContenuto(String contenuto) {
        this._contenuto = contenuto;
    }

    @Override
    public URI getThumbnail() {
        return this._thumb;
    }

    @Override
    public void setThumbnail(URI thumb_src) {
        this._thumb = thumb_src;
    }

    @Override
    public String getServizio() {
        return this._servizio;
    }

    @Override
    public void setServizio(String servizio) {
        this._servizio = servizio;
    }

    @Override
    public URI getLink() {
        return this._link;
    }

    @Override
    public void setLink(URI link) {
        this._link = link;
    }
    
}
