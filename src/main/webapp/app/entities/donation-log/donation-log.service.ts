import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Donation_log } from './donation-log.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class Donation_logService {

    private resourceUrl = 'api/donation-logs';
    private totalUrl = 'api/donation-total';
    private totalMonthUrl = 'api/donation-month';
    private pendingUrl = 'api/pending-payments';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(donation_log: Donation_log): Observable<Donation_log> {
        let copy: Donation_log = Object.assign({}, donation_log);
        copy.processed_date = this.dateUtils.toDate(donation_log.processed_date);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(donation_log: Donation_log): Observable<Donation_log> {
        let copy: Donation_log = Object.assign({}, donation_log);

        copy.processed_date = this.dateUtils.toDate(donation_log.processed_date);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Donation_log> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.processed_date = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.processed_date);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

        findTotalUser(): Observable<number> {
        return this.http.get(`${this.totalUrl}`).map((res: Response) => {
            let jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findMonthlyProgress(): Observable<number> {
      return this.http.get(`${this.totalMonthUrl}`).map((res: Response) => {
          let jsonResponse = res.json();
          return jsonResponse;
      });
    }

    findPendingPayments(): Observable<Donation_log[]> {
      return this.http.get(`${this.pendingUrl}`).map((res: Response) => {
          let jsonResponse = res.json();
          jsonResponse.processed_date = this.dateUtils
              .convertDateTimeFromServer(jsonResponse.processed_date);
          return jsonResponse;
      });
    }


    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].processed_date = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].processed_date);
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
