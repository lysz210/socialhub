/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.interfacce;

import java.util.List;

/**
 *
 * @author ciao
 */
public interface HistoryBean extends Iterable<HistoryEntryBean> {
    
    List<HistoryEntryBean> getRecords();
    void setRecords(Iterable<HistoryEntryBean> records);
    
    HistoryBean addRecord(HistoryEntryBean record);
    
    HistoryBean deleteRecord(HistoryEntryBean record);
}