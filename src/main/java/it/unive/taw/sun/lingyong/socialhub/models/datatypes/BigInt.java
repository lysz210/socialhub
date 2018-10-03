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
public class BigInt extends AbsDataType {

    public BigInt() {
        super(BIGINT, Long.SIZE);
    }

    public BigInt(boolean isPK) {
        this();
        if (isPK) {
            setAsPrimaryKey();
        }
    }

    public BigInt(DBTableInterface refs) {
        this(false, refs);
    }

    public BigInt(boolean isPK, DBTableInterface refs) {
        this(isPK);
        if (refs != null) {
            setReferences(refs);
        }
    }
}
