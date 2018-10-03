/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models.constraints;

import it.unive.taw.sun.lingyong.socialhub.models.DBTableInterface;

/**
 *
 * @author ciao
 */
public class ForeignKey implements DerbyConstraintInterface{
    
    DBTableInterface fk = null;
    DBTableInterface refs = null;
    
    public ForeignKey(DBTableInterface fk, DBTableInterface refs) {
        if(fk != null && refs != null){
            this.fk = fk;
            this.refs = refs;
        }
    }

    @Override
    public String getDeclaration() {
        return (this.fk == null) ? 
                "" : 
                String.format("FOREIGN KEY (%s) REFERENCES %s", fk.name(), refs.getTableName());
    }
    
}
