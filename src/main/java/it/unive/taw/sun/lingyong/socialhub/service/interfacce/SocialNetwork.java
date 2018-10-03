/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.interfacce;

import java.util.Properties;

/**
 *
 * @author ciao
 */
public interface SocialNetwork extends Iterable<RisultatiBean> {
    String getSearchString();
    void setSearchString(String q);
    
    Tags getTags();
    void setTags(Tags tags);
    
    int getWindowSize();
    void setWindowSize(int size);
    
    boolean connect(Properties pts);
    
    void search(Tags tags);
    
    void setName(String name);
    String getName();
    
}
