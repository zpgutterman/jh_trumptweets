import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Tweet_log } from './tweet-log.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class Tweet_logService {

    private resourceUrl = 'api/tweet-logs';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(tweet_log: Tweet_log): Observable<Tweet_log> {
        let copy: Tweet_log = Object.assign({}, tweet_log);
        copy.tweet_date = this.dateUtils.toDate(tweet_log.tweet_date);
        copy.categorize_time = this.dateUtils.toDate(tweet_log.categorize_time);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(tweet_log: Tweet_log): Observable<Tweet_log> {
        let copy: Tweet_log = Object.assign({}, tweet_log);

        copy.tweet_date = this.dateUtils.toDate(tweet_log.tweet_date);

        copy.categorize_time = this.dateUtils.toDate(tweet_log.categorize_time);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Tweet_log> {
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
