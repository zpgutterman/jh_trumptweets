import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { User_preferences } from './user-preferences.model';
import { User_preferencesPopupService } from './user-preferences-popup.service';
import { User_preferencesService } from './user-preferences.service';

@Component({
    selector: 'jhi-user-preferences-delete-dialog',
    templateUrl: './user-preferences-delete-dialog.component.html'
})
export class User_preferencesDeleteDialogComponent {

    user_preferences: User_preferences;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private user_preferencesService: User_preferencesService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['user_preferences']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.user_preferencesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'user_preferencesListModification',
                content: 'Deleted an user_preferences'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-preferences-delete-popup',
    template: ''
})
export class User_preferencesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private user_preferencesPopupService: User_preferencesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.user_preferencesPopupService
                .open(User_preferencesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
