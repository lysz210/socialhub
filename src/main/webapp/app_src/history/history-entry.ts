export default class HistoryEntry {
    constructor(
        private _idHistory: number,
        private _servizi: string[],
        private _parole: string[],
        private _data: Date
    ) {}

    get idHistory(): number {
        return this._idHistory;
    }

    get servizi(): string[] {
        return this._servizi;
    }

    get parole(): string[] {
        return this._parole;
    }

    get data(): Date {
        return this._data;
    }
}
