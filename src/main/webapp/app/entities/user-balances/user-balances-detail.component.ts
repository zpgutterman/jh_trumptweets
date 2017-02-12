import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { User_balances } from './user-balances.model';
import { User_balancesService } from './user-balances.service';

@Component({
    selector: 'jhi-user-balances-detail',
    templateUrl: './user-balances-detail.component.html'
})
export class User_balancesDetailComponent implements OnInit, OnDestroy {

    user_balances: User_balances;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private user_balancesService: User_balancesService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['user_balances']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.user_balancesService.find(id).subscribe(user_balances => {
            this.user_balances = user_balances;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
