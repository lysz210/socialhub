import {Component, Input, Output, EventEmitter} from "angular2/core";

import {TokenComponent} from "./token.component";

@Component({
    selector: "token-list",
    templateUrl: "templates/token-list.component.html",
    styleUrls: [
        "styles/inline-list.css"
    ],
    directives: [
        TokenComponent
    ]
})
export class TokenListComponent {

    @Input() tokens: string[];

    @Output() onDeleteToken: EventEmitter<string> = new EventEmitter();

    deleteToken(token: string){
        console.log("TokenListComponent.deleteToken('%s')", token);
        this.onDeleteToken.emit(token);
    }
}
