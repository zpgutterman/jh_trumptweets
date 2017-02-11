import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { User_preferencesComponent } from './user-preferences.component';
import { User_preferencesDetailComponent } from './user-preferences-detail.component';
import { User_preferencesPopupComponent } from './user-preferences-dialog.component';
import { User_preferencesDeletePopupComponent } from './user-preferences-delete-dialog.component';

import { Principal } from '../../shared';


export const user_preferencesRoute: Routes = [
  {
    path: 'user-preferences',
    component: User_preferencesComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_preferences.home.title'
    }
  }, {
    path: 'user-preferences/:id',
    component: User_preferencesDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_preferences.home.title'
    }
  }
];

export const user_preferencesPopupRoute: Routes = [
  {
    path: 'user-preferences-new',
    component: User_preferencesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_preferences.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'user-preferences/:id/edit',
    component: User_preferencesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_preferences.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'user-preferences/:id/delete',
    component: User_preferencesDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_preferences.home.title'
    },
    outlet: 'popup'
  }
];
