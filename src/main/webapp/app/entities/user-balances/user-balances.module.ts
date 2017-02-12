import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';
import { TrumptweetAdminModule } from '../../admin/admin.module';

import {
    User_balancesService,
    User_balancesPopupService,
    User_balancesComponent,
    User_balancesDetailComponent,
    User_balancesDialogComponent,
    User_balancesPopupComponent,
    User_balancesDeletePopupComponent,
    User_balancesDeleteDialogComponent,
    user_balancesRoute,
    user_balancesPopupRoute,
} from './';

let ENTITY_STATES = [
    ...user_balancesRoute,
    ...user_balancesPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        TrumptweetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        User_balancesComponent,
        User_balancesDetailComponent,
        User_balancesDialogComponent,
        User_balancesDeleteDialogComponent,
        User_balancesPopupComponent,
        User_balancesDeletePopupComponent,
    ],
    entryComponents: [
        User_balancesComponent,
        User_balancesDialogComponent,
        User_balancesPopupComponent,
        User_balancesDeleteDialogComponent,
        User_balancesDeletePopupComponent,
    ],
    providers: [
        User_balancesService,
        User_balancesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetUser_balancesModule {}
