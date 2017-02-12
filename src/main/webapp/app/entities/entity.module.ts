import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TrumptweetCategoryModule } from './category/category.module';
import { TrumptweetCharityModule } from './charity/charity.module';
import { TrumptweetTweetlogModule } from './tweetlog/tweetlog.module';
import { TrumptweetUser_preferencesModule } from './user-preferences/user-preferences.module';
import { TrumptweetUser_balancesModule } from './user-balances/user-balances.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TrumptweetCategoryModule,
        TrumptweetCharityModule,
        TrumptweetTweetlogModule,
        TrumptweetUser_preferencesModule,
        TrumptweetUser_balancesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetEntityModule {}
