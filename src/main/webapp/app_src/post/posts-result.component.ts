import {Component, OnInit} from "angular2/core";

import {Observable} from "rxjs/Observable";

import {SearchService} from "./search.service";
import Query from "./query";
import QueryBuilder from "./query-builder";
import Post from "./post";
import {PostListComponent} from "./post-list.component";
import {TokenListComponent} from "../token/token-list.component";
import {SocialnetworkComponent} from "../socialnetwork/socialnetwork.component";
import SocialNetwork from "../socialnetwork/socialnetwork";
import HistoryEntry from "../history/history-entry";
import {HistoryComponent} from "../history/history.component";
import {HistoryService} from "../history/history.service";

import * as Actions from "../configs/actions";

@Component({
    selector: "posts-result",
    templateUrl: "templates/posts-result.component.html",
    styleUrls: [
        "styles/inline-list.css"
    ],
    directives: [
        PostListComponent,
        TokenListComponent,
        SocialnetworkComponent,
        HistoryComponent
    ],
})
export class PostsResultComponent implements OnInit {
    static TOKEN_SEPARATORS = /[ ,;:]/;
    posts: Post[];

    private _actualQ: QueryBuilder;

    private _defaultQ: Query;

    private _services: SocialNetwork[];

    constructor(
        private _searchService: SearchService,
        private _historyService: HistoryService
    ){
        this.posts = [];
        this._services = [];
    }

    ngOnInit(){
        let qBuilder = new QueryBuilder();
        qBuilder.addTags("news");
        this._actualQ = qBuilder;
        this._defaultQ = qBuilder.build();
        this.performSearch(this._defaultQ);
        this._searchService.services.subscribe(
            services => {
                for(let sn of services){
                    this._services.push(sn);
                }
            }
        );
    }

    performSearch(query: Query): void{
        let res: Observable<Post[]> = this._searchService.search(query);
        console.log("search response");
        console.log(res);
        res.subscribe(
            posts => {
                for(let p of posts){
                    this.posts.push(p);
                }
                this._historyService.retrieveHistory();
            },
            error => console.log(error)
        );
    }

    newSearch(): void {
        this.posts = [];
        this.performSearch(this._actualQ.build());
    }

    more(): void {
        let q: Query = this._actualQ ? this._actualQ.build() : this._defaultQ;
        this.performSearch(q);
    }

    get tags(): string[] {
        return this._actualQ ? this._actualQ.tags : [];
    }
    get services(): string[] {
        return this._actualQ ? this._actualQ.services : [];
    }

    onTagify(event:KeyboardEvent) {
        console.log("keyboar event handler");
        let keyCode: number = event.keyCode ? event.keyCode : event.which;
        let _elt: HTMLInputElement = <HTMLInputElement>event.target;
        let _in: string = _elt.value;
        let key: string = _in ? _in.substring( _in.length - 1) : "";
        console.log(key);
        if(PostsResultComponent.TOKEN_SEPARATORS.test(key)){
            this._actualQ.addTags(_in.substring(0, _in.length - 1));
            _elt.value = "";
        } else if(keyCode == 13){
            this._actualQ.addTags(_in.substring(0, _in.length));
            _elt.value = "";
        }
    }

    deleteToken(token: string){
        console.log("PostsResultComponent.deleteToken('%s')", token);
        this._actualQ.removeTags(token);
    }

    changeSocialnetworkState(nome: string): void {
        console.log("PostsResultComponent.changeSocialnetworkState('%s')", nome);
        let service: SocialNetwork = this._services.find(
            element => element.nome == nome
        );
        if(service){
            let nuovoStato: boolean = !service.attivo;
            service.attivo = nuovoStato;
            if(nuovoStato){
                this._actualQ.addServices(nome);
            } else {
                this._actualQ.removeServices(nome);
            }
        }
    }

    historyEntrySelectedHandler(entry: HistoryEntry){
        let qb: QueryBuilder = new QueryBuilder();
        qb.addTags(...(entry.parole));
        qb.addServices(...(entry.servizi));
        this._actualQ = qb;
        let servizi: string[] = entry.servizi;
        this._services.forEach(
            service => {
                service.attivo = servizi.indexOf(service.nome) >= 0;
            }
        );
    }

    log(msg){
        console.log(msg);
    }
    get socialnetworks(): SocialNetwork[] {
        return this._services;
    }
}
