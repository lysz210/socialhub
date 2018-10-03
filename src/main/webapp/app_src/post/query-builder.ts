import Query from "./query";

export default class QueryBuilder {
    private _tags: string[] = [];
    private _services: string[] = [];

    get tags(): string[] {
        return this._tags;
    }
    get services(): string[] {
        return this._services;
    }

    addTags(...tags: string[]): QueryBuilder {
        this._tags.push(...tags);
        return this;
    }

    removeTags(...tags: string[]): QueryBuilder {
        this._removeFromArray(this._tags, ...tags);
        return this;
    }

    addServices(...services: string[]): QueryBuilder {
        this._services.push(...services);
        return this;
    }

    removeServices(...services: string[]){
        this._removeFromArray(this._services, ...services);
        return this;
    }

    build(): Query{
        return new Query(this._tags, this._services);
    }

    private _removeFromArray(src: string[], ...elements: string[]){
        for(let tag of elements){
            let _index = src.indexOf(tag);
            if(_index >= 0){
                src.splice(_index, 1);
            }
        }
    }
}
