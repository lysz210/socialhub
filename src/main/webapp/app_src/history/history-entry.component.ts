import {Component, Input, Output, EventEmitter} from "angular2/core";

import HistoryEntry from "./history-entry";

@Component({
    selector: "history-entry",
    templateUrl: "templates/history-entry.component.html"
})
export class HistoryEntryComponent {

    @Input() record: HistoryEntry;

    @Output() onSelectRecord: EventEmitter<number> = new EventEmitter();

    get id(): number {
        return this.record.idHistory;
    }

    get strParole(): string {
        return this.record.parole.join(", ");
    }

    get strServizi(): string {
        return this.record.servizi.join(", ");
    }

    selectEntry() {
        this.onSelectRecord.emit(this.record.idHistory);
    }
}
