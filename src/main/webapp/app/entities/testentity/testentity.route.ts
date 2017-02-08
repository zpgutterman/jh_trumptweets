import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TestentityComponent } from './testentity.component';
import { TestentityDetailComponent } from './testentity-detail.component';
import { TestentityPopupComponent } from './testentity-dialog.component';
import { TestentityDeletePopupComponent } from './testentity-delete-dialog.component';

import { Principal } from '../../shared';


export const testentityRoute: Routes = [
  {
    path: 'testentity',
    component: TestentityComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.testentity.home.title'
    }
  }, {
    path: 'testentity/:id',
    component: TestentityDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.testentity.home.title'
    }
  }
];

export const testentityPopupRoute: Routes = [
  {
    path: 'testentity-new',
    component: TestentityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.testentity.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'testentity/:id/edit',
    component: TestentityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.testentity.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'testentity/:id/delete',
    component: TestentityDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.testentity.home.title'
    },
    outlet: 'popup'
  }
];
