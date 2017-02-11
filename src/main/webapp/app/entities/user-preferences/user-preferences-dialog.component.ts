import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { User_preferences } from './user-preferences.model';
import { User_preferencesPopupService } from './user-preferences-popup.service';
import { User_preferencesService } from './user-preferences.service';
import { User, UserService } from '../../shared';
import { Category, CategoryService } from '../category';
@Component({
    selector: 'jhi-user-preferences-dialog',
    templateUrl: './user-preferences-dialog.component.html'
})
export class User_preferencesDialogComponent implements OnInit {

    user_preferences: User_preferences;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    categories: Category[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private user_preferencesService: User_preferencesService,
        private userService: UserService,
        private categoryService: CategoryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['user_preferences']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.user_preferences.id !== undefined) {
            this.user_preferencesService.update(this.user_preferences)
                .subscribe((res: User_preferences) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.user_preferencesService.create(this.user_preferences)
                .subscribe((res: User_preferences) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: User_preferences) {
        this.eventManager.broadcast({ name: 'user_preferencesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
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
}

@Component({
    selector: 'jhi-user-preferences-popup',
    template: ''
})
export class User_preferencesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private user_preferencesPopupService: User_preferencesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.user_preferencesPopupService
                    .open(User_preferencesDialogComponent, params['id']);
            } else {
                this.modalRef = this.user_preferencesPopupService
                    .open(User_preferencesDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
