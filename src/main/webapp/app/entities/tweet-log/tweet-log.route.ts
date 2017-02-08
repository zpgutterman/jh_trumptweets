import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { Tweet_logComponent } from './tweet-log.component';
import { Tweet_logDetailComponent } from './tweet-log-detail.component';
import { Tweet_logPopupComponent } from './tweet-log-dialog.component';
import { Tweet_logDeletePopupComponent } from './tweet-log-delete-dialog.component';

import { Principal } from '../../shared';


export const tweet_logRoute: Routes = [
  {
    path: 'tweet-log',
    component: Tweet_logComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweet_log.home.title'
    }
  }, {
    path: 'tweet-log/:id',
    component: Tweet_logDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweet_log.home.title'
    }
  }
];

export const tweet_logPopupRoute: Routes = [
  {
    path: 'tweet-log-new',
    component: Tweet_logPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweet_log.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'tweet-log/:id/edit',
    component: Tweet_logPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweet_log.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'tweet-log/:id/delete',
    component: Tweet_logDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweet_log.home.title'
    },
    outlet: 'popup'
  }
];
