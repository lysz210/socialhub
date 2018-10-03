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
public class Varchar extends AbsDataType {

    public static final int DEFAULT_LENGTH = 128;

    public Varchar() {
        this(DEFAULT_LENGTH);
    }

    public Varchar(int len) {
        super(DBTableInterface.VARCHAR, len);
        if (len <= 0) {
            super.setLength(DEFAULT_LENGTH);
        }
    }

    public Varchar(int len, boolean isPK) {
        this(len);
        if (isPK) {
            super.setAsPrimaryKey();
        }
    }

    public Varchar(DBTableInterface refs) {
        this(false, refs);
        System.out.println(refs);
    }

    public Varchar(boolean isPK, DBTableInterface refs) {
        this(refs != null ? refs.getColLenght() : DEFAULT_LENGTH, isPK);
        if (refs != null) {
            super.setReferences(refs);
        }
    }

    public String getDeclaration() {
        String sql = String.format("%s(%d)", type, length);
        if (isPrimaryKey) {
            sql += " PRIMARY KEY";
        }
        if (isForeignKey()) {
            sql += " REFERENCES " + references.getTableName();
        }
        return sql;
    }

}
