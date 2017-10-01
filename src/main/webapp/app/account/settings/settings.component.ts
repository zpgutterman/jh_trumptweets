import { Component, OnInit } from '@angular/core';
import { EventManager, JhiLanguageService, AlertService } from 'ng-jhipster';
import { User_preferences, User_preferencesService } from '../../entities/user-preferences';
import { Principal, AccountService, JhiLanguageHelper } from '../../shared';
import { Category, CategoryService } from '../../entities/category';
import { Response } from '@angular/http';

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
    error: string;
    lastFour: string;
    success: string;
    settingsAccount: any;
    user_preferences: User_preferences;
    languages: any[];
    categories: Category[];
    isSaving: boolean;


    constructor(
        private account: AccountService,
        private upService: User_preferencesService,
        private principal: Principal,
        private alertService: AlertService,
        private languageService: JhiLanguageService,
        private categoryService: CategoryService,
        private eventManager: EventManager,
        private languageHelper: JhiLanguageHelper
    ) {
        this.languageService.setLocations(['settings']);
    }

    ngOnInit () {
        this.principal.identity().then((account) => {
            this.settingsAccount = this.copyAccount(account);
            this.loadPref();
            this.loadCC();
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    private onSaveSuccess (result: User_preferences) {
        this.eventManager.broadcast({ name: 'user_preferencesListModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    loadCC() {
      this.upService.getLastFourCC()
                        .subscribe(
                            lastFour => this.lastFour = lastFour, //Bind to view
                             err => {
                                 // Log errors if any
                                 console.log(err);
                             });
    }

    loadPref() {
       // Get all comments
        this.upService.findByUser()
                          .subscribe(
                              user_preferences => this.user_preferences = user_preferences, //Bind to view
                               err => {
                                   // Log errors if any
                                   console.log(err);
                               });
   }

    save () {
        this.account.save(this.settingsAccount).subscribe(() => {
            this.error = null;
            this.success = 'OK';
            this.principal.identity(true).then((account) => {
                this.settingsAccount = this.copyAccount(account);
            });

            if (this.user_preferences.id !== undefined) {
                this.upService.update(this.user_preferences)
                    .subscribe((res: User_preferences) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
            } else {
                this.upService.create(this.user_preferences)
                    .subscribe((res: User_preferences) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
            }

            this.languageService.getCurrent().then((current) => {
                if (this.settingsAccount.langKey !== current) {
                    this.languageService.changeLanguage(this.settingsAccount.langKey);
                }
            });
        }, () => {
            this.success = null;
            this.error = 'ERROR';
        });
    }




    trackCategoryById(index: number, item: Category) {
        return item.id;
    }

    copyAccount (account) {
        return {
            activated: account.activated,
            email: account.email,
            firstName: account.firstName,
            langKey: account.langKey,
            lastName: account.lastName,
            login: account.login,
            imageUrl: account.imageUrl,
            tweetLimit: account.tweetLimit,
            monthlyLimit: account.monthlyLimit,
            transferThreshold: account.transferThreshold

        };
    }
}
