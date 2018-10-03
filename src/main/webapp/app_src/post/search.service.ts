import {Injectable} from "angular2/core";

import {Http, Headers, RequestOptions, URLSearchParams} from "angular2/http";

import {Observable} from "rxjs/Observable";

import Post from "./post";

import * as Actions from "../configs/actions";

import Query from "./query";

import SocialNetwork from "../socialnetwork/socialnetwork";

@Injectable()
export class SearchService {
    constructor(private _http: Http) {
        // TODO recuperare tutti i servizi attualmente disponibili nel servizio
    }

    search(q: Query): Observable<Post[]> {
        let params: URLSearchParams = new URLSearchParams();
        params.set("q", JSON.stringify(q));
        let headers: Headers = new Headers({
            "Content-type": "application/json"
        });
        let options: RequestOptions = new RequestOptions({
            headers: headers,
            search: params
        });
        console.log("searceService.search('%s')", JSON.stringify(q));
        return this._http
            .get(Actions.ACTION_POSTS, options)
            .map(
                response => {
                    let data = response.json();
                    console.log(data);
                    let res: Post[] = data.posts.map(
                        element => {
                            let _post: Post = new Post();
                            _post.titolo = element.titolo;
                            _post.servizio = element.servizio;
                            _post.link = element.link;
                            if(element.thumbnail){
                                _post.thumb = element.thumbnail
                            }
                            if(element.contenuto){
                                _post.contenuto = element.contenuto;
                            }
                            return _post;
                        });
                    return res;
                }
            ).catch(
                err => {
                    console.log("errore SearchService recupero html")
                    console.log(err);
                    return Observable.throw(err.json());
                }
            );
    }

    get services(): Observable<SocialNetwork[]> {
        return this._http
            .get(Actions.ACTION_SERVICES)
            .map(
                response => {
                    let data = response.json();
                    console.log(data);
                    let res: SocialNetwork[] = data.map(
                        service => {
                            let _social: SocialNetwork = new SocialNetwork(service);
                            return _social;
                        });
                    return res;
                }
            ).catch(
                err => {
                    console.log("errore SearchService recupero servizi")
                    console.log(err);
                    return Observable.throw(err.json());
                }
            );
    }
}
