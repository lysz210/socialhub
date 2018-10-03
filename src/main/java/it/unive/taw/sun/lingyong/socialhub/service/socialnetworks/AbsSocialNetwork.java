/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.socialnetworks;

import it.unive.taw.sun.lingyong.socialhub.service.interfacce.RisultatiBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.SocialNetwork;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.Tags;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author ciao
 */
public abstract class AbsSocialNetwork implements Iterator<RisultatiBean>,  SocialNetwork {
        
    private Tags _tags;
    private int _windowSize = 5;
    private String _name;
    
    @Override
    public Tags getTags(){
        return this._tags;
    }
    
    @Override
    public void setTags(Tags tags){
        this._tags = tags;
    }
    
    @Override
    public String getSearchString() {
        return String.join(" ", this._tags);
    }

    @Override
    public void setSearchString(String q) {
        String[] tags = q.split(" ");
        this._tags.addAll(Arrays.asList(tags));
    }
    
    @Override
    public int getWindowSize() {
        return this._windowSize;
    }

    @Override
    public void setWindowSize(int size) {
        this._windowSize = size;
    }
    
    @Override
    public void search(Tags tags){
        this.setTags(tags);
    }
    
    @Override
    public Iterator<RisultatiBean> iterator() {
        return this;
    }
    
    @Override
    public void setName(String name){
        this._name = name;
    }
    
    @Override
    public String getName(){
        return this._name;
    }

}
