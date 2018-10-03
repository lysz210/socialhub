export default class Query {
    constructor(
        private _tags: string[],
        private _services?: string[]
    ) { }

    get services(): string[] {
        return this._services;
    }

    get tags(): string[] {
        return this._tags;
    }

    toJSON(): any {
        let jsonRepr = {
            tags: this._tags,
            services: this._services
        };
        return jsonRepr;
    }
}
