import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrumptweetSharedModule } from '../shared';

import {
    Register,
    Activate,
    Password,
    DonationSettings,
    PasswordResetInit,
    PasswordResetFinish,
    SessionsService,
    SessionsComponent,
    PasswordStrengthBarComponent,
    RegisterComponent,
    ActivateComponent,
    PasswordComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    SocialRegisterComponent,
    DonationSettingsComponent,
    accountState
} from './';

@NgModule({
    imports: [
        TrumptweetSharedModule,
        RouterModule.forRoot(accountState, { useHash: true })
    ],
    declarations: [
        SocialRegisterComponent,
        ActivateComponent,
        RegisterComponent,
        DonationSettingsComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SessionsComponent,
        SettingsComponent
    ],
    providers: [
        SessionsService,
        Register,
        Activate,
        Password,
        DonationSettings,
        PasswordResetInit,
        PasswordResetFinish
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrumptweetAccountModule {}
