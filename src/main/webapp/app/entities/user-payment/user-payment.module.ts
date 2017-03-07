import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';
import { TrumptweetAdminModule } from '../../admin/admin.module';

import {
    User_paymentService,
    User_paymentPopupService,
    User_paymentComponent,
    User_paymentDetailComponent,
    User_paymentDialogComponent,
    User_paymentPopupComponent,
    User_paymentDeletePopupComponent,
    User_paymentDeleteDialogComponent,
    user_paymentRoute,
    user_paymentPopupRoute,
    User_paymentResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...user_paymentRoute,
    ...user_paymentPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        TrumptweetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        User_paymentComponent,
        User_paymentDetailComponent,
        User_paymentDialogComponent,
        User_paymentDeleteDialogComponent,
        User_paymentPopupComponent,
        User_paymentDeletePopupComponent,
    ],
    entryComponents: [
        User_paymentComponent,
        User_paymentDialogComponent,
        User_paymentPopupComponent,
        User_paymentDeleteDialogComponent,
        User_paymentDeletePopupComponent,
    ],
    providers: [
        User_paymentService,
        User_paymentPopupService,
        User_paymentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetUser_paymentModule {}
