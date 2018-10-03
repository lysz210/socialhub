/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models;

import it.unive.taw.sun.lingyong.socialhub.models.constraints.ForeignKey;
import it.unive.taw.sun.lingyong.socialhub.models.constraints.PrimaryKey;
import it.unive.taw.sun.lingyong.socialhub.models.constraints.DerbyConstraintInterface;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.BigIntAutoincrement;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.DerbyDataTypeInterface;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.Timestamp;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.Varchar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;


/**
 *
 * @author ciao
 */
public enum History implements DBTableInterface {
    
    id_history ( new BigIntAutoincrement()),
    utente ( new Varchar()),
    parole ( new Varchar(512)),
    data ( new Timestamp());
    
    public static final String TABLE_NAME = "History";
    
    public static final DerbyConstraintInterface[] CONSTRAINTS = {
        new PrimaryKey(id_history),
        new ForeignKey(utente, Utenti.id_utente)
    };
    
    
    private DerbyDataTypeInterface _dataType = null;
    
    History(DerbyDataTypeInterface type) {
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
    
    public static DBTableInterface[] getInsertValues(){
        DBTableInterface[] values = {
                                        utente,
                                        parole,
                                        data
                                    };
           
        return values;
    }
    
    public static Map<DBTableInterface, Integer> colMap(){
        Map<DBTableInterface, Integer> cMap =  new HashMap<>();
        cMap.put(id_history, 1);
        cMap.put(utente, 2);
        cMap.put(parole, 3);
        cMap.put(data, 4);
        return cMap;
    }
    
    public static String getCreateTable() {
        String[] d = {
            String.format("%s %s", id_history, id_history._dataType.getDeclaration()),
            String.format("%s %s", utente, utente._dataType.getDeclaration()),
            String.format("%s %s", parole, parole._dataType.getDeclaration()),
            String.format("%s %s", data, data._dataType.getDeclaration())
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
