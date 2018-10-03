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
public abstract class AbsDataType implements DerbyDataTypeInterface {

    String type = "";
    int length = 0;
    boolean isPrimaryKey = false;
    DBTableInterface references = null;

    AbsDataType(String type) {
        this.type = type;
    }

    AbsDataType(String type, int length) {
        this.type = type;
        this.length = length;
    }

    AbsDataType(String type, int length, boolean isPK) {
        this.type = type;
        this.length = length;
        this.isPrimaryKey = isPK;
    }

    AbsDataType(String type, int length, boolean isPK, DBTableInterface refs) {
        this.type = type;
        this.length = length;
        this.isPrimaryKey = isPK;
        this.references = refs;
    }

    @Override
    public String getTypeName() {
        return this.type;
    }

    void setTypeName(String type) {
        this.type = type;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean isPrimaryKey() {
        return this.isPrimaryKey;
    }

    void setAsPrimaryKey() {
        this.isPrimaryKey = true;
    }

    void unsetAsPrimaryKey() {
        this.isPrimaryKey = false;
    }

    @Override
    public boolean isForeignKey() {
        return this.references != null;
    }

    @Override
    public DBTableInterface getReferences() {
        return this.references;
    }

    void setReferences(DBTableInterface refs) {
        this.references = refs;
    }

    @Override
    public String getDeclaration() {
        String sql = type;
        if (isPrimaryKey) {
            sql += " PRIMARY KEY";
        }
        if (isForeignKey()) {
            sql += " REFERENCES " + references.getTableName();
        }
        return sql;
    }

}
