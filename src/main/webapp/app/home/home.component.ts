import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';
import { User_balances, User_balancesService } from '../entities/user-balances';
import { Donation_log, Donation_logService } from '../entities/donation-log';
import { ITEMS_PER_PAGE, Account, LoginModalService, Principal } from '../shared';
import { PaginationConfig } from '../blocks/config/uib-pagination.config';
import { Response } from '@angular/http';
import { Tweetlog } from '../entities/tweetlog/tweetlog.model';
import { TweetlogService } from '../entities/tweetlog/tweetlog.service';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    donationLog: Donation_log[];
    monthlyProgress: number;
    pending_payments: Donation_log[];
    totalDonation: number;
    user_balances: User_balances[];
    modalRef: NgbModalRef;
    tweetlogs: Tweetlog[];
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    today: number = Date.now();
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    routeData: any;
    previousPage: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private principal: Principal,
        private donationLogService: Donation_logService,
        private loginModalService: LoginModalService,
        private ubService: User_balancesService,
        private eventManager: EventManager,
        private tweetlogService: TweetlogService,
        private alertService: AlertService,
        private paginationUtil: PaginationUtil,
        private paginationConfig: PaginationConfig,
        private parseLinks: ParseLinks,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {
      {
          this.tweetlogs = [];
          this.itemsPerPage = 5;
          this.jhiLanguageService.setLocations(['home']);

          this.routeData = this.activatedRoute.data.subscribe(data => {
              this.page = data['pagingParams'].page;
              this.previousPage = data['pagingParams'].page;
              this.reverse = data['pagingParams'].ascending;
              this.predicate = data['pagingParams'].predicate;
          });
      }


    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            if (this.isAuthenticated()) {
              this.loadAll();
              this.registerChangeInTweetlogs();
              this.loadTotalDonation();
              this.loadPendingPayments();
              this.loadMonthlyProgress();
            this.loadBalances();
          }
        });
        this.registerAuthenticationSuccess();
    }

    loadBalances() {
       // Get all comments
        this.ubService.findByUser()
                          .subscribe(
                              user_balances => this.user_balances = user_balances, //Bind to view
                               err => {
                                   // Log errors if any
                                   console.log(err);
                               });
   }
   loadTotalDonation() {
      this.donationLogService.findTotalUser()
      .subscribe(
          totalDonation => this.totalDonation = totalDonation, //Bind to view
           err => {
               // Log errors if any
               console.log(err);
           });
   }

   loadMonthlyProgress() {
      this.donationLogService.findMonthlyProgress()
      .subscribe(
          monthlyProgress => this.monthlyProgress = monthlyProgress, //Bind to view
           err => {
               // Log errors if any
               console.log(err);
           });
   }

   loadPendingPayments() {
     this.donationLogService.findPendingPayments().subscribe(
         pending_payments => this.pending_payments = pending_payments, //Bind to view
          err => {
              // Log errors if any
              console.log(err);
          });
   }


    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    ngOnDestroy() {
        this.routeData.unsubscribe;
    }

    trackId (index: number, item: Tweetlog) {
        return item.id;
    }



    registerChangeInTweetlogs() {
        this.eventSubscriber = this.eventManager.subscribe('tweetlogListModification', (response) => this.reset());
    }

    sort () {
        let result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    loadAll () {
        this.tweetlogService.query({
            page: this.page -1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    reset () {
        this.page = 0;
        this.tweetlogs = [];
        this.loadAll();
    }

    loadPage (page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    private onSuccess(data, headers) {
        // hide anonymous user from user management: it's a required user for Spring Security

        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.tweetlogs = data;
    }

    transition () {
        this.router.navigate(['/'], { queryParams:
                {
                    page: this.page,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
        });
        this.loadAll();
    }
    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
