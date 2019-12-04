import * as React from 'react';
import { Switch } from 'react-router-dom';

import JobScheduler from './modules/projects/job-scheduler/job-scheduler';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

const Routes = () => (
  <div className="view-routes">
    <Switch>
      <ErrorBoundaryRoute path="/" component={JobScheduler} />
    </Switch>
  </div>
);

export default Routes;
