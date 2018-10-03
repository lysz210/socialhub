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
public class BigIntAutoincrement extends BigInt {

    public BigIntAutoincrement() {
        super();
    }

    public BigIntAutoincrement(boolean isPK) {
        super(isPK);
    }

    public BigIntAutoincrement(DBTableInterface refs) {
        super(false, refs);
    }

    public BigIntAutoincrement(boolean isPK, DBTableInterface refs) {
        super(isPK);
        if (refs != null) {
            setReferences(refs);
        }
    }

    public String getDeclaration() {
        String sql = type;
        if (isPrimaryKey) {
            sql += " PRIMARY KEY";
        }
        sql += " GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";
        if (isForeignKey()) {
            sql += " REFERENCES " + references.getTableName();
        }
        return sql;
    }
}
