/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models;

import it.unive.taw.sun.lingyong.socialhub.models.constraints.DerbyConstraintInterface;
import it.unive.taw.sun.lingyong.socialhub.models.constraints.ForeignKey;
import it.unive.taw.sun.lingyong.socialhub.models.constraints.PrimaryKey;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.BigInt;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.DerbyDataTypeInterface;
import it.unive.taw.sun.lingyong.socialhub.models.datatypes.Varchar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author ciao
 */
public enum HistoryServices implements DBTableInterface {
    history ( new BigInt(History.id_history)),
    service ( new Varchar(Servizi.nome));
        
    public static final String TABLE_NAME = "HistoryServices";
    public static final DerbyConstraintInterface[] CONSTRAINTS = {
        new PrimaryKey(history, service),
        new ForeignKey(history, History.id_history),
        new ForeignKey(service, Servizi.nome)
    };
    
    private String _type;
    private int _length;
        
    private DerbyDataTypeInterface _dataType = null;
    
    HistoryServices(DerbyDataTypeInterface type) {
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
    public DBTableInterface getReferences(){
        return this._dataType.getReferences();
    }
    
    @Override
    public DerbyDataTypeInterface getDataType(){
        return this._dataType;
    }
    
    public static Map<DBTableInterface, Integer> colMap(){
        Map<DBTableInterface, Integer> cMap =  new HashMap<>();
        cMap.put(history, 1);
        cMap.put(service, 2);
        return cMap;
    }

    public static final String getCreateTable() {
        String[] d = {
            String.format("%s %s", history, history._dataType.getDeclaration()),
            String.format("%s %s", service, service._dataType.getDeclaration())
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
}