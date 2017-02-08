import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CharityComponent } from './charity.component';
import { CharityDetailComponent } from './charity-detail.component';
import { CharityPopupComponent } from './charity-dialog.component';
import { CharityDeletePopupComponent } from './charity-delete-dialog.component';

import { Principal } from '../../shared';


export const charityRoute: Routes = [
  {
    path: 'charity',
    component: CharityComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.charity.home.title'
    }
  }, {
    path: 'charity/:id',
    component: CharityDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.charity.home.title'
    }
  }
];

export const charityPopupRoute: Routes = [
  {
    path: 'charity-new',
    component: CharityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.charity.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'charity/:id/edit',
    component: CharityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.charity.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'charity/:id/delete',
    component: CharityDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.charity.home.title'
    },
    outlet: 'popup'
  }
];
