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
 * viene usato l'enumeratore per la rappresentazione della tabella
 * @author ciao
 */
public enum Utenti implements DBTableInterface {
    id_utente ( new Varchar(128));
    
    public static final String TABLE_NAME = "Utenti";
    public static final DerbyConstraintInterface[] CONSTRAINTS = {
        new PrimaryKey(id_utente),
    };
    
    private String _type;
    private int _length;
    
    private DerbyDataTypeInterface _dataType = null;
    
    Utenti(DerbyDataTypeInterface type) {
        this._dataType = type;
    }

    @Override
    public String getColType() {
        return this._type;
    }

    @Override
    public int getColLenght() {
       return this._length;
    }
    
    @Override
    public DerbyDataTypeInterface getDataType(){
        return this._dataType;
    }

    public static final String getCreateTable() {
        String[] d = {
            String.format("%s %s", id_utente, id_utente._dataType.getDeclaration())
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
