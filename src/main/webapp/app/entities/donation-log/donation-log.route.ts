import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { Donation_logComponent } from './donation-log.component';
import { Donation_logDetailComponent } from './donation-log-detail.component';
import { Donation_logPopupComponent } from './donation-log-dialog.component';
import { Donation_logDeletePopupComponent } from './donation-log-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class Donation_logResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const donation_logRoute: Routes = [
  {
    path: 'donation-log',
    component: Donation_logComponent,
    resolve: {
      'pagingParams': Donation_logResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.donation_log.home.title'
    }
  }, {
    path: 'donation-log/:id',
    component: Donation_logDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.donation_log.home.title'
    }
  }
];

export const donation_logPopupRoute: Routes = [
  {
    path: 'donation-log-new',
    component: Donation_logPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.donation_log.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'donation-log/:id/edit',
    component: Donation_logPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.donation_log.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'donation-log/:id/delete',
    component: Donation_logDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.donation_log.home.title'
    },
    outlet: 'popup'
  }
];
