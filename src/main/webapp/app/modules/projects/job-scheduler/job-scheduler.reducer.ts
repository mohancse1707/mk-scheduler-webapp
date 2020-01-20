import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction, IPayloadResult } from 'react-jhipster';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IJobSchedulerDetails } from 'app/modules/projects/job-scheduler/job-scheduler.model';
import { defaultJobSchedulerDetailsResponse } from 'app/modules/projects/job-scheduler/job-scheduler-response.model';

const baseUrl = 'app/scheduler/rest';

export const ACTION_TYPES = {
  FETCH_ALL_JOB_SCHEDULERS: 'jobScheduler/FETCH_ALL_JOB_SCHEDULERS',
  CREATE_OR_UPDATE_JOB_SCHEDULER: 'jobScheduler/CREATE_OR_UPDATE_JOB_SCHEDULER',
  CANCEL_JOB_SCHEDULER: 'jobScheduler/CANCEL_JOB_SCHEDULER',
  RESET: 'jobScheduler/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  totalItems: 0,
  result: false,
  jobSchedulerResponse: defaultJobSchedulerDetailsResponse
};

export type JobSchedulerDetailsState = Readonly<typeof initialState>;

// Reducer
export default (state: JobSchedulerDetailsState = initialState, action): JobSchedulerDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ALL_JOB_SCHEDULERS):
    case REQUEST(ACTION_TYPES.CREATE_OR_UPDATE_JOB_SCHEDULER):
    case REQUEST(ACTION_TYPES.CANCEL_JOB_SCHEDULER):
      return {
        ...state,
        loading: true
      };

    case FAILURE(ACTION_TYPES.FETCH_ALL_JOB_SCHEDULERS):
    case FAILURE(ACTION_TYPES.CREATE_OR_UPDATE_JOB_SCHEDULER):
    case FAILURE(ACTION_TYPES.CANCEL_JOB_SCHEDULER):
      return {
        ...state,
        loading: false,
        errorMessage: action.payload
      };

    case SUCCESS(ACTION_TYPES.FETCH_ALL_JOB_SCHEDULERS):
      return {
        ...state,
        loading: false,
        jobSchedulerResponse: action.payload.data,
        totalItems: action.payload.data.totalItems
      };
    case SUCCESS(ACTION_TYPES.CREATE_OR_UPDATE_JOB_SCHEDULER):
      return {
        ...state,
        loading: false,
        result: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CANCEL_JOB_SCHEDULER):
      return {
        ...state,
        loading: false,
        result: action.payload.data
      };
    case ACTION_TYPES.RESET:
      return {
        ...state,
        loading: false,
        jobSchedulerResponse: defaultJobSchedulerDetailsResponse
      };
    default:
      return state;
  }
};

export const getAllJobSchedulers = (jobName, priority, startIndex, pageSize) => {
  let requestUrl = `${baseUrl}/fetchAllJobSchedulers?startIndex=${startIndex}&pageSize=${pageSize}`;
  if (jobName) {
    requestUrl += `&jobName=${jobName}`;
  }
  if (priority) {
    requestUrl += `&priority=${priority}`;
  }

  return {
    type: ACTION_TYPES.FETCH_ALL_JOB_SCHEDULERS,
    payload: axios.get<IJobSchedulerDetails>(requestUrl)
  };
};

export const createOrUpdateScheduler: ICrudPutAction<IJobSchedulerDetails> = entity => async dispatch => {
  const requestUrl = `${baseUrl}/createOrUpdateScheduler`;
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OR_UPDATE_JOB_SCHEDULER,
    payload: axios.post(requestUrl, entity)
  });
  return result;
};

export const cancelJobScheduler: ICrudPutAction<IJobSchedulerDetails> = entity => async dispatch => {
  const requestUrl = `${baseUrl}/cancelJobScheduler`;
  const result = await dispatch({
    type: ACTION_TYPES.CANCEL_JOB_SCHEDULER,
    payload: axios.post(requestUrl, entity)
  });
  return result;
};
