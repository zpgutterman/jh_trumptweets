import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';
import { User_balances, User_balancesService } from '../entities/user-balances';
import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    user_balances: User_balances[];
    modalRef: NgbModalRef;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private ubService: User_balancesService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['home']);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            if (this.isAuthenticated()) {
            this.loadBalances();
          }
        });
        this.registerAuthenticationSuccess();
    }

    loadBalances() {
       // Get all comments
        this.ubService.findByUser()
                          .subscribe(
                              user_balances => this.user_balances = user_balances, //Bind to view
                               err => {
                                   // Log errors if any
                                   console.log(err);
                               });
   }



    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
