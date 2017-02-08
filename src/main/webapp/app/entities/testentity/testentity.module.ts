import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../../shared';

import {
    TestentityService,
    TestentityPopupService,
    TestentityComponent,
    TestentityDetailComponent,
    TestentityDialogComponent,
    TestentityPopupComponent,
    TestentityDeletePopupComponent,
    TestentityDeleteDialogComponent,
    testentityRoute,
    testentityPopupRoute,
} from './';

let ENTITY_STATES = [
    ...testentityRoute,
    ...testentityPopupRoute,
];

@NgModule({
    imports: [
        TrumptweetSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TestentityComponent,
        TestentityDetailComponent,
        TestentityDialogComponent,
        TestentityDeleteDialogComponent,
        TestentityPopupComponent,
        TestentityDeletePopupComponent,
    ],
    entryComponents: [
        TestentityComponent,
        TestentityDialogComponent,
        TestentityPopupComponent,
        TestentityDeleteDialogComponent,
        TestentityDeletePopupComponent,
    ],
    providers: [
        TestentityService,
        TestentityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetTestentityModule {}
