import {Injectable} from "angular2/core";
import {Http, Headers} from 'angular2/http';

import {Observable} from 'rxjs/Observable';

import {Utente} from "./utente";

import * as Actions from "../configs/actions";

@Injectable()
export class LoginService {

    constructor(private _http: Http) { }

    get utente(): Observable<Utente> {
        console.log(Actions.ACTION_UTENTE);
        return this._http
            .get(Actions.ACTION_UTENTE)
            .map(
                response => {
                    let data = response.json();
                    let utente = new Utente(data.idUtente, data.nome, data.foto);
                    console.log(utente);
                    return utente;
                }
            ).catch(
                err => {
                    console.log("errore recupero utente");
                    console.log(err);
                    return Observable.throw(err.json());
                }
            );
    }
}
