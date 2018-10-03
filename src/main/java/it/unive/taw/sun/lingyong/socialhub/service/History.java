/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service;

import it.unive.taw.sun.lingyong.socialhub.service.interfacce.HistoryBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.HistoryEntryBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ciao
 */
public class History implements HistoryBean {
    private final List<HistoryEntryBean> _records = new ArrayList<>();

    @Override
    public List<HistoryEntryBean> getRecords() {
        return this._records;
    }

    @Override
    public void setRecords(Iterable<HistoryEntryBean> records) {
        for(HistoryEntryBean h: records){
            this._records.add(h);
        }
    }

    @Override
    public HistoryBean addRecord(HistoryEntryBean record) {
        this._records.add(record);
        return this;
    }

    @Override
    public HistoryBean deleteRecord(HistoryEntryBean record) {
        this._records.remove(record);
        return this;
    }

    @Override
    public Iterator<HistoryEntryBean> iterator() {
        return this._records.iterator();
    }
}
