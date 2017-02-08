import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TrumptweetCategoryModule } from './category/category.module';
import { TrumptweetCharityModule } from './charity/charity.module';
import { TrumptweetTweet_logModule } from './tweet-log/tweet-log.module';
import { TrumptweetTweetlogModule } from './tweetlog/tweetlog.module';
import { TrumptweetTestentityModule } from './testentity/testentity.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TrumptweetCategoryModule,
        TrumptweetCharityModule,
        TrumptweetTweet_logModule,
        TrumptweetTweetlogModule,
        TrumptweetTestentityModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetEntityModule {}
