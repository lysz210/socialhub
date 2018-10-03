import HistoryEntry from "./history-entry";

export default class History {
    private _records: HistoryEntry[] = [];

    get records(): HistoryEntry[] {
        return this._records;
    }
    set records(recs: HistoryEntry[]){
        this._records = recs;
    }

    addRecord(...record: HistoryEntry[]): void{
        this._records.push(...record);
    }

    sort(){
        this._records.sort(
            (a, b) => {
                let offset = b.idHistory - a.idHistory;
                return offset;
            }
        );
    }
}
