import {Component, Input, Output, EventEmitter} from "angular2/core";

import SocialNetwork from "./socialnetwork";

@Component({
    selector: "socialnetwork-element",
    templateUrl: "templates/socialnetwork.component.html",
    styleUrls: [
        "styles/socialnetwork.component.css"
    ]
})
export class SocialnetworkComponent {

    @Input() mSocialnetwork: SocialNetwork;

    @Output() onStateChange: EventEmitter<string> = new EventEmitter();

    changeState(){
        console.log("changing state");
        this.onStateChange.emit(this.mSocialnetwork.nome);
    }

    get nome(): string {
        return this.mSocialnetwork.nome;
    }

    get isActive(): boolean {
        return this.mSocialnetwork.attivo;
    }
}
