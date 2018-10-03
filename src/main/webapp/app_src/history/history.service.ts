import {Injectable} from "angular2/core";

import {Http} from "angular2/http";

import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

import * as Actions from "../configs/actions";

import History from "./history";
import HistoryEntry from "./history-entry";

@Injectable()
export class HistoryService {
    constructor(private _http: Http){ }

    private _historyIndexes: number[] = [];

    historyEntries: Subject<HistoryEntry> = new Subject<HistoryEntry>();

    retrieveHistory(): void {
        console.log("HistoryService.historyentries()");
        let hResponse: Observable<HistoryEntry> = this._http
            .get(Actions.ACTION_HISTORY)
            .flatMap(
                response => {
                    let data = response.json();
                    console.log("hisotry mappint: ");
                    console.log(data);
                    let res = data.records.map(
                        (element) => {
                            let entry: HistoryEntry = new HistoryEntry(
                                element.idHistory,
                                element.servizi,
                                element.parole,
                                new Date(element.data)
                            )
                            return entry;
                        }
                    );
                    console.log("mapped");
                    console.log(res);
                    return res;
                }
            ).filter(
                (entry: HistoryEntry) => {
                    console.log("filtering history");
                    console.log(entry);
                    let id: number = entry.idHistory;
                    let index: number = this._historyIndexes.indexOf(id);
                    let isNew: boolean = index < 0;
                    if(isNew){
                        this._historyIndexes.push(id);
                    }
                    return isNew;
                }
            ).catch(
                err => {
                    console.log("error retrivieng history");
                    return Observable.throw(err.json());
                }
            );
        hResponse.subscribe(
            (entry: HistoryEntry) => this.historyEntries.next(entry)
        );
    }
}
