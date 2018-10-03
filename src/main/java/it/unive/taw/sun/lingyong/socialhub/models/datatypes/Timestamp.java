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
public class Timestamp extends AbsDataType {

    public Timestamp() {
        super(DBTableInterface.TIMESTAMP);
    }

    public Timestamp(boolean isPK) {
        this();
        if (isPK) {
            super.setAsPrimaryKey();
        }
    }

    public Timestamp(DBTableInterface refs) {
        this(false, refs);
    }

    public Timestamp(boolean isPK, DBTableInterface refs) {
        this(isPK);
        if (refs != null) {
            super.setReferences(refs);
        }
    }
}
