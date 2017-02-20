import { Component, OnInit, AfterViewInit, Renderer, ElementRef, OnDestroy } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';

import { DonationSettings } from './donation-settings.service';
import { LoginModalService, User, UserService, Principal, AccountService } from '../../shared';

@Component({
    selector: 'jhi-donate-settings',
    templateUrl: './donation-settings.component.html'
})
export class DonationSettingsComponent implements OnInit {

  confirmPassword: string;
  doNotMatch: string;
  error: string;
  errorEmailExists: string;
  errorUserExists: string;
  user: any;
  success: boolean;
  private subscription: any;
  modalRef: NgbModalRef;

    constructor(
        private loginModalService: LoginModalService,
        private donationSettingsService: DonationSettings,
        private elementRef: ElementRef,
        private route: ActivatedRoute,
        private renderer: Renderer,
        private account: AccountService,
        private userService: UserService,
        private principal: Principal,
      ) {}


      ngOnInit () {
          this.principal.identity().then((account) => {
              this.user = account;
              console.log("account login is " + this.user.monthlyLimit);
          });

      }


    save() {
        this.donationSettingsService.save(this.user).subscribe(() => {
        this.success = true;
        }, (response) => this.processError(response));
    }


    private processError(response) {
        this.success = null;
        this.error = "ERROR";
    }

}
