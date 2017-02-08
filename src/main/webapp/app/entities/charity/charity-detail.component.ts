import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Charity } from './charity.model';
import { CharityService } from './charity.service';

@Component({
    selector: 'jhi-charity-detail',
    templateUrl: './charity-detail.component.html'
})
export class CharityDetailComponent implements OnInit, OnDestroy {

    charity: Charity;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private charityService: CharityService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['charity']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.charityService.find(id).subscribe(charity => {
            this.charity = charity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
