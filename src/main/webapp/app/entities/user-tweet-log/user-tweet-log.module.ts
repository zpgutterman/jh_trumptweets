import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';
import { TrumptweetAdminModule } from '../../admin/admin.module';

import {
    User_tweet_logService,
    User_tweet_logPopupService,
    User_tweet_logComponent,
    User_tweet_logDetailComponent,
    User_tweet_logDialogComponent,
    User_tweet_logPopupComponent,
    User_tweet_logDeletePopupComponent,
    User_tweet_logDeleteDialogComponent,
    user_tweet_logRoute,
    user_tweet_logPopupRoute,
} from './';

let ENTITY_STATES = [
    ...user_tweet_logRoute,
    ...user_tweet_logPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        TrumptweetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        User_tweet_logComponent,
        User_tweet_logDetailComponent,
        User_tweet_logDialogComponent,
        User_tweet_logDeleteDialogComponent,
        User_tweet_logPopupComponent,
        User_tweet_logDeletePopupComponent,
    ],
    entryComponents: [
        User_tweet_logComponent,
        User_tweet_logDialogComponent,
        User_tweet_logPopupComponent,
        User_tweet_logDeleteDialogComponent,
        User_tweet_logDeletePopupComponent,
    ],
    providers: [
        User_tweet_logService,
        User_tweet_logPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetUser_tweet_logModule {}
