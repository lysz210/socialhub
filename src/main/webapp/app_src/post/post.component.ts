import {Component, Input} from "angular2/core";

import Post from "./post";

@Component({
    selector: "post-element",
    templateUrl: "templates/post.component.html"
})
export class PostElementComponent {

    @Input() myPost: Post;

    get link(): string {
        if(this.myPost.link){
            return this.myPost.link.toString();
        } else {
            return "#";
        }
    }

    get titolo(): string {
        return this.myPost.titolo;
    }

    get servizio(): string {
        return this.myPost.servizio;
    }

    get imageSrc(): string {
        return this.myPost.thumb.toString();
    }
    get hasImage(): boolean {
        return (this.myPost.thumb) ? true : false;
    }

    get content(): string {
        return this.myPost.contenuto;
    }
    get hasContent(): boolean {
        return (this.myPost.contenuto) ? true : false;
    }

}
