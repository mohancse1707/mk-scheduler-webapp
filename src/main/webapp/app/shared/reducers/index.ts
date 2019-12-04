
import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';
import locale, { LocaleState } from './locale';

import jobScheduler, { JobSchedulerDetailsState } from 'app/modules/projects/job-scheduler/job-scheduler.reducer';

export interface IRootState {
  readonly locale: LocaleState;
  readonly jobScheduler: JobSchedulerDetailsState;
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  locale,
  jobScheduler,
  loadingBar
});

export default rootReducer;
