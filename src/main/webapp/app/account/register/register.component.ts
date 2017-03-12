import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { Register } from './register.service';
import { LoginModalService } from '../../shared';

declare var Panda:any;

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit, AfterViewInit {

    confirmPassword: string;
    public isCollapsedTL = true;
    public isCollapsedML = true;
    public isCollapsedTT = true;
    public MLDisabled = true;
    public TTDisabled = true;
    public badTT = true;
    private cardToken:any;
    doNotMatch: string;
    tweetLow: string;
    tweetHigh: string;
    monthlyLow: string;
    monthlyHigh: string;
    monthlyLowTweet: string;
    transferLow: string;
    transferHigh: string;
    transferLowTweet: string;
    invalidDonation: string;
    error: string;
    token: string;
    errorEmailExists: string;
    errorInvalidCard: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer
    ) {
        this.languageService.setLocations(['register']);
    }

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
        this.setUpCard();
    }

    setUpCard() {
    Panda.init('pk_test_Xr7iBvkRL4U9t0ETYfnIxQ', 'form');
    Panda.on('success', cardToken => this.handleCardToken(cardToken));
    Panda.on('error', errors => this.handleCardTokenError(errors));
  }

  handleCardToken(token) {
this.token = token;
this.register();
  }

  handleCardTokenError(errors) {
    console.log("panda fails " + errors);
    this.errorInvalidCard = 'ERROR';
  }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    tweetLimitBlur() {
      this.tweetLow = null;
      this.tweetHigh = null;
      if (parseFloat(this.registerAccount.tweetLimit) < .10 )
      this.tweetLow = 'ERROR';
      if (parseFloat(this.registerAccount.tweetLimit) > 999.99 )
      this.tweetHigh = 'ERROR';
      if (this.tweetLow == null && this.tweetHigh == null)
      this.MLDisabled = false;
      else {
        this.MLDisabled = true;
      }

    }

    monthlyLimitBlur() {
      this.monthlyLow = null;
      this.monthlyHigh = null;
      this.monthlyLowTweet = null;
      if (parseFloat(this.registerAccount.monthlyLimit) < 2.50 )
      this.monthlyLow = 'ERROR';
      if (parseFloat(this.registerAccount.monthlyLimit) > 999.99 )
      this.monthlyHigh = 'ERROR';
      if (parseFloat(this.registerAccount.monthlyLimit) < parseFloat(this.registerAccount.tweetLimit))
      this.monthlyLowTweet = 'ERROR';
      if (this.monthlyLow == null && this.monthlyHigh == null && this.monthlyLowTweet == null)
      this.TTDisabled = false;
      else {
        this.TTDisabled = true;
      }
    }

    transferThresholdBlur() {
        this.transferLow = null;
        this.transferHigh = null;
        this.transferLowTweet = null;
        if (parseFloat(this.registerAccount.transferThreshold) < 2.00 )
        this.transferLow = 'ERROR';
        if (parseFloat(this.registerAccount.transferThreshold) > 999.99 )
        this.transferHigh = 'ERROR';
        if (parseFloat(this.registerAccount.transferThreshold) < parseFloat(this.registerAccount.tweetLimit))
        this.transferLowTweet = 'ERROR';
        if (this.transferLow == null && this.transferHigh == null && this.transferLowTweet == null) {
        this.badTT = false;
        }
        else {
          this.badTT = true;
        }

    }


    register() {

        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else if (parseInt(this.registerAccount.tweetLimit,10) > parseInt(this.registerAccount.monthlyLimit,10)) {
            this.invalidDonation = 'ERROR';
        }
        else {

            this.doNotMatch = null;
            this.error = null;
            this.invalidDonation = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then(key => {
                this.registerAccount.langKey = key;
                console.log("setting token as " + this.token)
                this.registerAccount.token = this.token;
                this.registerService.save(this.registerAccount).subscribe(() => {
                    this.success = true;
                }, (response) => this.processError(response));
            });
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response._body === 'login already in use') {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response._body === 'e-mail address already in use') {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
