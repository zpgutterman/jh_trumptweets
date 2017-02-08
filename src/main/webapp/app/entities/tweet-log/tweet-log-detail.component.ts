import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Tweet_log } from './tweet-log.model';
import { Tweet_logService } from './tweet-log.service';

@Component({
    selector: 'jhi-tweet-log-detail',
    templateUrl: './tweet-log-detail.component.html'
})
export class Tweet_logDetailComponent implements OnInit, OnDestroy {

    tweet_log: Tweet_log;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private tweet_logService: Tweet_logService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['tweet_log']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.tweet_logService.find(id).subscribe(tweet_log => {
            this.tweet_log = tweet_log;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
