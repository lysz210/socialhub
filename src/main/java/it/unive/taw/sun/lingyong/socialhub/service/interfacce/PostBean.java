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
public interface PostBean {
    String getTitolo();
    void setTitolo(String titolo);
    
    String getContenuto();
    void setContenuto(String contenuto);
    
    URI getThumbnail();
    void setThumbnail(URI thumb_src);
    
    URI getLink();
    void setLink(URI link);
    
    String getServizio();
    void setServizio(String servizio);
    
}
