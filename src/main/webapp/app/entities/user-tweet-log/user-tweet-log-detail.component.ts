import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { User_tweet_log } from './user-tweet-log.model';
import { User_tweet_logService } from './user-tweet-log.service';

@Component({
    selector: 'jhi-user-tweet-log-detail',
    templateUrl: './user-tweet-log-detail.component.html'
})
export class User_tweet_logDetailComponent implements OnInit, OnDestroy {

    user_tweet_log: User_tweet_log;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private user_tweet_logService: User_tweet_logService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['user_tweet_log']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.user_tweet_logService.find(id).subscribe(user_tweet_log => {
            this.user_tweet_log = user_tweet_log;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
