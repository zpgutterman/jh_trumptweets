import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';

import {
    Tweet_logService,
    Tweet_logPopupService,
    Tweet_logComponent,
    Tweet_logDetailComponent,
    Tweet_logDialogComponent,
    Tweet_logPopupComponent,
    Tweet_logDeletePopupComponent,
    Tweet_logDeleteDialogComponent,
    tweet_logRoute,
    tweet_logPopupRoute,
} from './';

let ENTITY_STATES = [
    ...tweet_logRoute,
    ...tweet_logPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Tweet_logComponent,
        Tweet_logDetailComponent,
        Tweet_logDialogComponent,
        Tweet_logDeleteDialogComponent,
        Tweet_logPopupComponent,
        Tweet_logDeletePopupComponent,
    ],
    entryComponents: [
        Tweet_logComponent,
        Tweet_logDialogComponent,
        Tweet_logPopupComponent,
        Tweet_logDeleteDialogComponent,
        Tweet_logDeletePopupComponent,
    ],
    providers: [
        Tweet_logService,
        Tweet_logPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetTweet_logModule {}
