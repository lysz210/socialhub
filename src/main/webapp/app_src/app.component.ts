import {Component} from "angular2/core";
import {RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS} from "angular2/router";

import {LoginComponent} from "./utente/login.component";

import {HTTP_PROVIDERS} from "angular2/http";

import {LoginService} from "./utente/login.service";

import {SearchService} from "./post/search.service";

import {PostsResultComponent} from "./post/posts-result.component";


import {HistoryService} from "./history/history.service";

// @RouteConfig(LIFECYCLE_ROOTES)


@Component({
    selector: "my-app",
    templateUrl: "templates/app.component.html",
    directives: [
        ROUTER_DIRECTIVES,
        LoginComponent,
        PostsResultComponent
    ],
    providers: [
        ROUTER_PROVIDERS,
        HTTP_PROVIDERS,
        LoginService,
        SearchService,
        HistoryService
    ]
})
export class AppComponent {
    public index: number = 0;

    constructor(){ }
 }
