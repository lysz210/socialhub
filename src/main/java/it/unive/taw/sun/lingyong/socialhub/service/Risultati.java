/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service;

import it.unive.taw.sun.lingyong.socialhub.service.interfacce.PostBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.RisultatiBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ciao
 */
public class Risultati implements RisultatiBean {
    private List<PostBean> _posts = new ArrayList<>();
    private int _window = 5;
    @Override
    public int length() {
        return this._posts.size();
    }

    @Override
    public int getWindow() {
        return this._window;
    }

    @Override
    public void setWindow(int window) {
        this._window = window;
    }

    @Override
    public List<PostBean> getPosts() {
        return this._posts;
    }

    @Override
    public void setPosts(List<PostBean> posts) {
        this._posts = (posts != null) ?
                        ((posts.size() <= this._window) ? posts :  posts.subList(0, _window)) :
                        new ArrayList<PostBean>();
    }

    @Override
    public RisultatiBean addPost(PostBean post) throws IndexOutOfBoundsException {
        if(this._posts.size() > this._window){
            throw new IndexOutOfBoundsException("Questa lista di posts ha gia' raggiunto il limite massimo");
        }
        this._posts.add(post);
        return this;
    }

    @Override
    public Iterator<PostBean> iterator() {
        return this._posts.iterator();
    }
    
}
