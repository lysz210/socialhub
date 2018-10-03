/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.interfacce;

import java.util.List;

/**
 *
 * @author ciao
 */
public interface RisultatiBean extends Iterable<PostBean> {
    int length();
    
    int getWindow();
    void setWindow(int window);
    
    List<PostBean> getPosts();
    void setPosts(List<PostBean> posts);
    
    RisultatiBean addPost(PostBean post);
}