import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Tweetlog } from './tweetlog.model';
import { TweetlogService } from './tweetlog.service';

@Component({
    selector: 'jhi-tweetlog-detail',
    templateUrl: './tweetlog-detail.component.html'
})
export class TweetlogDetailComponent implements OnInit, OnDestroy {

    tweetlog: Tweetlog;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private tweetlogService: TweetlogService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['tweetlog']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.tweetlogService.find(id).subscribe(tweetlog => {
            this.tweetlog = tweetlog;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
