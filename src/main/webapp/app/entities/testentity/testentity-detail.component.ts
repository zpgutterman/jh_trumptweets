import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Testentity } from './testentity.model';
import { TestentityService } from './testentity.service';

@Component({
    selector: 'jhi-testentity-detail',
    templateUrl: './testentity-detail.component.html'
})
export class TestentityDetailComponent implements OnInit, OnDestroy {

    testentity: Testentity;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private testentityService: TestentityService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['testentity']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.testentityService.find(id).subscribe(testentity => {
            this.testentity = testentity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
