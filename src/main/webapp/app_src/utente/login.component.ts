import {Component} from "angular2/core";

import {LoginService} from "./login.service";
import {Utente} from "./utente";

import * as Actions from "../configs/actions";

@Component({
    selector: "login",
    templateUrl: "templates/login.component.html",
})
export class LoginComponent {

    private _utente: Utente = null;

    constructor(
        private _loginService: LoginService
    ) {
        console.log("login");
        _loginService.utente.subscribe(
            utente => this._utente = utente,
            error => {
                console.log("LoginComponent(error utente)")
                this._utente = null;
            }
        );
    }

    get isLogged(): boolean {
        return this._utente != null;
    }

    get utente(): Utente {
        return this._utente;
    }

    get actionLogin(): string {
        return Actions.ACTION_LOGIN;
    }

    get actionLogout(): string {
        return Actions.ACTION_LOGOUT;
    }
}
