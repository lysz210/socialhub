import {Component, Input, Output, EventEmitter} from "angular2/core";

@Component({
    selector: "token",
    templateUrl: "templates/token.component.html"
})
export class TokenComponent {

    @Input("mToken") token: string;

    @Output() delete: EventEmitter<string> = new EventEmitter();

    deleteToken(){
        console.log("TokenComponent.deleteToken('%s')", this.token);
        this.delete.emit(this.token);
    }
}
