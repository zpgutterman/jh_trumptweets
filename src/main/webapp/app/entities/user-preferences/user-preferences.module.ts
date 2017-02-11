import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';
import { TrumptweetAdminModule } from '../../admin/admin.module';

import {
    User_preferencesService,
    User_preferencesPopupService,
    User_preferencesComponent,
    User_preferencesDetailComponent,
    User_preferencesDialogComponent,
    User_preferencesPopupComponent,
    User_preferencesDeletePopupComponent,
    User_preferencesDeleteDialogComponent,
    user_preferencesRoute,
    user_preferencesPopupRoute,
} from './';

let ENTITY_STATES = [
    ...user_preferencesRoute,
    ...user_preferencesPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        TrumptweetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        User_preferencesComponent,
        User_preferencesDetailComponent,
        User_preferencesDialogComponent,
        User_preferencesDeleteDialogComponent,
        User_preferencesPopupComponent,
        User_preferencesDeletePopupComponent,
    ],
    entryComponents: [
        User_preferencesComponent,
        User_preferencesDialogComponent,
        User_preferencesPopupComponent,
        User_preferencesDeleteDialogComponent,
        User_preferencesDeletePopupComponent,
    ],
    providers: [
        User_preferencesService,
        User_preferencesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetUser_preferencesModule {}
