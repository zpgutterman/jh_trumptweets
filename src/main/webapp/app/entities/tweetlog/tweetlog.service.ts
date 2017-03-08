import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Tweetlog } from './tweetlog.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class TweetlogService {

    private resourceUrl = 'api/tweetlogs';
    private resourceCatUrl = 'api/tweetlogscategorized';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(tweetlog: Tweetlog): Observable<Tweetlog> {
        let copy: Tweetlog = Object.assign({}, tweetlog);
        copy.tweet_date = this.dateUtils.toDate(tweetlog.tweet_date);
        copy.categorize_time = this.dateUtils.toDate(tweetlog.categorize_time);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(tweetlog: Tweetlog): Observable<Tweetlog> {
        let copy: Tweetlog = Object.assign({}, tweetlog);

        copy.tweet_date = this.dateUtils.toDate(tweetlog.tweet_date);

        copy.categorize_time = this.dateUtils.toDate(tweetlog.categorize_time);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Tweetlog> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.tweet_date = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.tweet_date);
            jsonResponse.categorize_time = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.categorize_time);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    queryCategorized(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceCatUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].tweet_date = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].tweet_date);
            jsonResponse[i].categorize_time = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].categorize_time);
        }
        res._body = jsonResponse;
        return res;
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
