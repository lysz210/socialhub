import {Component, Output, EventEmitter, OnInit} from "angular2/core";

import History from "./history";
import HistoryEntry from "./history-entry";

import {HistoryEntryComponent} from "./history-entry.component";
import {HistoryService} from "./history.service";

@Component({
    selector: "history",
    templateUrl: "templates/history.component.html",
    styleUrls: [
        "styles/history.css"
    ],
    directives: [
        HistoryEntryComponent
    ]
})
export class HistoryComponent implements OnInit {

    expanded: boolean = false;
    private _history: History = new History();

    @Output() onSelectHistoryRecord: EventEmitter<HistoryEntry> = new EventEmitter();

    constructor( private _historyService: HistoryService) {
        console.log("initialize History component");
    }

    ngOnInit(): void {
        this._historyService.historyEntries.subscribe(
            entry => {
                this._history.addRecord(entry);
                this._history.sort();
            }
        );
    }

    retrieveHistory() {
        console.log("history component init");
        this._historyService.retrieveHistory();
    }

    setQuery(id: number) {
        console.log("settingQuery with history: %s", id);
        let _record: HistoryEntry = this._history.records.find(
            element => element.idHistory == id
        );
        this.onSelectHistoryRecord.emit(_record);
    }

    get entryList(): HistoryEntry[] {
        return this._history ? this._history.records : [];
    }

    get isEmpty(): boolean {
        return !(this._history ? this._history.records.length > 0 : false);
    }

    toggleHistory(): void {
        this.expanded = !this.expanded;
    }
}
