/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models.constraints;

import it.unive.taw.sun.lingyong.socialhub.models.DBTableInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @author ciao
 */
public class Unique implements DerbyConstraintInterface {
    List<DBTableInterface> cols = new ArrayList<>();
    
    public Unique(DBTableInterface... cols){
        if(cols != null && cols.length > 0){
            this.cols.addAll(Arrays.asList(cols));
        }
    }
    
    @Override
    public String getDeclaration() {
        StringJoiner sj = new StringJoiner(", ", "PRIMARY KEY (", ")");
        sj.setEmptyValue("");
        for(DBTableInterface elt: this.cols){
            sj.add(elt.name());
        }
        return sj.toString();
    }
}
