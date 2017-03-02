import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Donation_log } from './donation-log.model';
import { Donation_logService } from './donation-log.service';

@Component({
    selector: 'jhi-donation-log-detail',
    templateUrl: './donation-log-detail.component.html'
})
export class Donation_logDetailComponent implements OnInit, OnDestroy {

    donation_log: Donation_log;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private donation_logService: Donation_logService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['donation_log']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.donation_logService.find(id).subscribe(donation_log => {
            this.donation_log = donation_log;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
