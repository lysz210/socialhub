
export class Utente {
    constructor(
        private _idUtente: string,
        private _nome: string,
        private _foto: URL
    ){}

    get idUtente(): string {
        return this._idUtente;
    }

    get nome(): string {
        return this._nome;
    }

    get foto(): URL {
        return this._foto;
    }
}
