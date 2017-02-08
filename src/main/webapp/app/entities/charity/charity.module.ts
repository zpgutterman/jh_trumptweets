import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';

import {
    CharityService,
    CharityPopupService,
    CharityComponent,
    CharityDetailComponent,
    CharityDialogComponent,
    CharityPopupComponent,
    CharityDeletePopupComponent,
    CharityDeleteDialogComponent,
    charityRoute,
    charityPopupRoute,
} from './';

let ENTITY_STATES = [
    ...charityRoute,
    ...charityPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CharityComponent,
        CharityDetailComponent,
        CharityDialogComponent,
        CharityDeleteDialogComponent,
        CharityPopupComponent,
        CharityDeletePopupComponent,
    ],
    entryComponents: [
        CharityComponent,
        CharityDialogComponent,
        CharityPopupComponent,
        CharityDeleteDialogComponent,
        CharityDeletePopupComponent,
    ],
    providers: [
        CharityService,
        CharityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetCharityModule {}
