/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.interfacce;

import java.util.HashSet;
import java.util.List;

/**
 *
 * @author ciao
 */
public class Tags extends HashSet<String> {
    
    public static Tags fromStringArray(List<String> tags){
        Tags t = new Tags();
        for(String s: tags){
            t.add(s);
        }
        return t;
    }
    
    public boolean equals(Tags t){
        
        if(t == null){
            return false;
        }
        if(t.size() != this.size()){
            return false;
        }
        return this.containsAll(t);
    }
}
