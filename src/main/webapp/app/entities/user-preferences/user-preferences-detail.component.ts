import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { User_preferences } from './user-preferences.model';
import { User_preferencesService } from './user-preferences.service';

@Component({
    selector: 'jhi-user-preferences-detail',
    templateUrl: './user-preferences-detail.component.html'
})
export class User_preferencesDetailComponent implements OnInit, OnDestroy {

    user_preferences: User_preferences;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private user_preferencesService: User_preferencesService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['user_preferences']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.user_preferencesService.find(id).subscribe(user_preferences => {
            this.user_preferences = user_preferences;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
