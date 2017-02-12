import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { User_balancesComponent } from './user-balances.component';
import { User_balancesDetailComponent } from './user-balances-detail.component';
import { User_balancesPopupComponent } from './user-balances-dialog.component';
import { User_balancesDeletePopupComponent } from './user-balances-delete-dialog.component';

import { Principal } from '../../shared';


export const user_balancesRoute: Routes = [
  {
    path: 'user-balances',
    component: User_balancesComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_balances.home.title'
    }
  }, {
    path: 'user-balances/:id',
    component: User_balancesDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_balances.home.title'
    }
  }
];

export const user_balancesPopupRoute: Routes = [
  {
    path: 'user-balances-new',
    component: User_balancesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_balances.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'user-balances/:id/edit',
    component: User_balancesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_balances.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'user-balances/:id/delete',
    component: User_balancesDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_balances.home.title'
    },
    outlet: 'popup'
  }
];
