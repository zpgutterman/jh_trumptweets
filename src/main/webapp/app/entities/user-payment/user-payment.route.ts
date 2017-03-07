import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { User_paymentComponent } from './user-payment.component';
import { User_paymentDetailComponent } from './user-payment-detail.component';
import { User_paymentPopupComponent } from './user-payment-dialog.component';
import { User_paymentDeletePopupComponent } from './user-payment-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class User_paymentResolvePagingParams implements Resolve<any> {

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

export const user_paymentRoute: Routes = [
  {
    path: 'user-payment',
    component: User_paymentComponent,
    resolve: {
      'pagingParams': User_paymentResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_payment.home.title'
    }
  }, {
    path: 'user-payment/:id',
    component: User_paymentDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_payment.home.title'
    }
  }
];

export const user_paymentPopupRoute: Routes = [
  {
    path: 'user-payment-new',
    component: User_paymentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_payment.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'user-payment/:id/edit',
    component: User_paymentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_payment.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'user-payment/:id/delete',
    component: User_paymentDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'trumptweetApp.user_payment.home.title'
    },
    outlet: 'popup'
  }
];
