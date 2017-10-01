import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { User_preferences } from './user-preferences.model';
@Injectable()
export class User_preferencesService {

    private resourceUrl = 'api/user-preferences';
    private up = 'api/up/';
    private lastFour = 'api/lastFour/';

    constructor(private http: Http) { }

    create(user_preferences: User_preferences): Observable<User_preferences> {
        let copy: User_preferences = Object.assign({}, user_preferences);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(user_preferences: User_preferences): Observable<User_preferences> {
        let copy: User_preferences = Object.assign({}, user_preferences);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<User_preferences> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByUser(): Observable<User_preferences> {
        return this.http.get(`${this.up}`).map((res: Response) => {
          console.log("response" + res.json());
            return res.json();
        });
    }

    getLastFourCC(): Observable<string> {
    return this.http.get(`${this.lastFour}`).map((res: Response) => {
        let jsonResponse = res.json();
        console.log("response " + res.json());
        return jsonResponse;
    });
}


    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }



    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
