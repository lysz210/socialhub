import {Component, Input} from "angular2/core";

import Post from "./post";
import {PostElementComponent} from "./post.component";

@Component({
    selector: "post-list",
    templateUrl: "templates/post-list.component.html",
    directives: [
        PostElementComponent
    ],
})
export class PostListComponent {

    @Input() posts: Post[];

    get length(): number {
        return this.posts.length;
    }
}
