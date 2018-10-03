/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models.datatypes;

import it.unive.taw.sun.lingyong.socialhub.models.DBTableInterface;

/**
 *
 * @author ciao
 */
public interface DerbyDataTypeInterface {
    
    static final String INT = "INT";
    static final String VARCHAR = "VARCHAR";
    static final String BIGINT = "BIGINT";
    static final String TIMESTAMP = "TIMESTAMP";
    
    String getTypeName();
    int getLength();
    boolean isPrimaryKey();
    boolean isForeignKey();
    DBTableInterface getReferences();
    String getDeclaration();
}
