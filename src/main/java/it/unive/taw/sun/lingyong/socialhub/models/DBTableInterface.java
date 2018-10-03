/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models;

import it.unive.taw.sun.lingyong.socialhub.models.datatypes.DerbyDataTypeInterface;

/**
 *
 * @author ciao
 */
public interface DBTableInterface {
    static String TABLE_NAME = "Test";
    
    static final String INT = "INT";
    static final String VARCHAR = "VARCHAR";
    static final String BIGINT = "BIGINT";
    static final String TIMESTAMP = "TIMESTAMP";
    
    String name();
    String getColType();
    int getColLenght();
    String getTableName();
    DBTableInterface getReferences();
    DerbyDataTypeInterface getDataType();
}
