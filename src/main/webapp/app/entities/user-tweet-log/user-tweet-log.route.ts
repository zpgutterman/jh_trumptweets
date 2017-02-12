import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { User_tweet_logComponent } from './user-tweet-log.component';
import { User_tweet_logDetailComponent } from './user-tweet-log-detail.component';
import { User_tweet_logPopupComponent } from './user-tweet-log-dialog.component';
import { User_tweet_logDeletePopupComponent } from './user-tweet-log-delete-dialog.component';

import { Principal } from '../../shared';


export const user_tweet_logRoute: Routes = [
  {
    path: 'user-tweet-log',
    component: User_tweet_logComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_tweet_log.home.title'
    }
  }, {
    path: 'user-tweet-log/:id',
    component: User_tweet_logDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_tweet_log.home.title'
    }
  }
];

export const user_tweet_logPopupRoute: Routes = [
  {
    path: 'user-tweet-log-new',
    component: User_tweet_logPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_tweet_log.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'user-tweet-log/:id/edit',
    component: User_tweet_logPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_tweet_log.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'user-tweet-log/:id/delete',
    component: User_tweet_logDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_tweet_log.home.title'
    },
    outlet: 'popup'
  }
];
