import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TweetlogComponent } from './tweetlog.component';
import { TweetlogDetailComponent } from './tweetlog-detail.component';
import { TweetlogPopupComponent } from './tweetlog-dialog.component';
import { TweetlogDeletePopupComponent } from './tweetlog-delete-dialog.component';

import { Principal } from '../../shared';


export const tweetlogRoute: Routes = [
  {
    path: 'tweetlog',
    component: TweetlogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweetlog.home.title'
    }
  }, {
    path: 'tweetlog/:id',
    component: TweetlogDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweetlog.home.title'
    }
  }
];

export const tweetlogPopupRoute: Routes = [
  {
    path: 'tweetlog-new',
    component: TweetlogPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweetlog.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'tweetlog/:id/edit',
    component: TweetlogPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweetlog.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'tweetlog/:id/delete',
    component: TweetlogDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.tweetlog.home.title'
    },
    outlet: 'popup'
  }
];
