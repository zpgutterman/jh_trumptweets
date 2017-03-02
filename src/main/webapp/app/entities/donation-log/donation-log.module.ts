import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';
import { TrumptweetAdminModule } from '../../admin/admin.module';

import {
    Donation_logService,
    Donation_logPopupService,
    Donation_logComponent,
    Donation_logDetailComponent,
    Donation_logDialogComponent,
    Donation_logPopupComponent,
    Donation_logDeletePopupComponent,
    Donation_logDeleteDialogComponent,
    donation_logRoute,
    donation_logPopupRoute,
    Donation_logResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...donation_logRoute,
    ...donation_logPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        TrumptweetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Donation_logComponent,
        Donation_logDetailComponent,
        Donation_logDialogComponent,
        Donation_logDeleteDialogComponent,
        Donation_logPopupComponent,
        Donation_logDeletePopupComponent,
    ],
    entryComponents: [
        Donation_logComponent,
        Donation_logDialogComponent,
        Donation_logPopupComponent,
        Donation_logDeleteDialogComponent,
        Donation_logDeletePopupComponent,
    ],
    providers: [
        Donation_logService,
        Donation_logPopupService,
        Donation_logResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetDonation_logModule {}
