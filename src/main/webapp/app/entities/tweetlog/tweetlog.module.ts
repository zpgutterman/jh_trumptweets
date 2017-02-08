import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';
import { TrumptweetAdminModule } from '../../admin/admin.module';

import {
    TweetlogService,
    TweetlogPopupService,
    TweetlogComponent,
    TweetlogDetailComponent,
    TweetlogDialogComponent,
    TweetlogPopupComponent,
    TweetlogDeletePopupComponent,
    TweetlogDeleteDialogComponent,
    tweetlogRoute,
    tweetlogPopupRoute,
} from './';

let ENTITY_STATES = [
    ...tweetlogRoute,
    ...tweetlogPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        TrumptweetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TweetlogComponent,
        TweetlogDetailComponent,
        TweetlogDialogComponent,
        TweetlogDeleteDialogComponent,
        TweetlogPopupComponent,
        TweetlogDeletePopupComponent,
    ],
    entryComponents: [
        TweetlogComponent,
        TweetlogDialogComponent,
        TweetlogPopupComponent,
        TweetlogDeleteDialogComponent,
        TweetlogDeletePopupComponent,
    ],
    providers: [
        TweetlogService,
        TweetlogPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetTweetlogModule {}
