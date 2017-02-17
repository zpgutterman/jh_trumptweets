import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DonationSettingsComponent } from './donation-settings.component';

export const donateRoute: Route = {
  path: 'donation-settings',
  component: DonationSettingsComponent,
  data: {
    authorities: [],
    pageTitle: 'Donation Settings'
  },
  canActivate: [UserRouteAccessService]
};
