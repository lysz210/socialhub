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
public class PrimaryKey implements DerbyConstraintInterface{
    
    List<DBTableInterface> pks = new ArrayList<>();
    
    public PrimaryKey(DBTableInterface... pks){
        if(pks != null && pks.length > 0){
            this.pks.addAll(Arrays.asList(pks));
        }
    }
    
    @Override
    public String getDeclaration() {
        StringJoiner sj = new StringJoiner(", ", "PRIMARY KEY (", ")");
        sj.setEmptyValue("");
        for(DBTableInterface elt: this.pks){
            sj.add(elt.name());
        }
        return sj.toString();
    }
}
