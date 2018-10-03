/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models;

import it.unive.taw.sun.lingyong.socialhub.models.constraints.DerbyConstraintInterface;
import it.unive.taw.sun.lingyong.socialhub.models.constraints.PrimaryKey;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.DerbyDataTypeInterface;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.Varchar;
import java.util.StringJoiner;

/**
 *
 * @author ciao
 */
public enum Servizi implements DBTableInterface {
    nome (new Varchar(128));
    
    public static final String TABLE_NAME = "Servizi";
    
    public static final DerbyConstraintInterface[] CONSTRAINTS = {
        new PrimaryKey(nome)
    };
    
    private DerbyDataTypeInterface _dataType = null;
    
    Servizi(DerbyDataTypeInterface type) {
        this._dataType = type;
    }

    @Override
    public String getColType() {
        return this._dataType.getTypeName();
    }

    @Override
    public int getColLenght() {
        return this._dataType.getLength();
    }
    
    @Override
    public DerbyDataTypeInterface getDataType(){
        return this._dataType;
    }

    public static final String getCreateTable() {
        String[] d = {
            String.format("%s %s", nome, nome.getDataType().getDeclaration())
        };
        StringJoiner sj = new StringJoiner(", ", ", ", "").setEmptyValue("");
        for(DerbyConstraintInterface elt: CONSTRAINTS){
            sj.add(elt.getDeclaration());
        }
        String q = String.format("CREATE TABLE %s (%s%s)", TABLE_NAME, String.join(", ", d), sj.toString());
        return q;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public DBTableInterface getReferences() {
        return this._dataType.getReferences();
    }
}
