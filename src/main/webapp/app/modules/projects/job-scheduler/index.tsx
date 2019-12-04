
import * as React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import { JobScheduler } from 'app/modules/projects/job-scheduler/job-scheduler';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute path={match.url} component={JobScheduler} />
  </div>
);

export default Routes;
