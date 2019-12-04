
import { RestHeader } from 'app/shared/model/rest-header.model';
import { IJobSchedulerDetails } from 'app/modules/projects/job-scheduler/job-scheduler.model';

export class JobSchedulerDetailsResponse {
  restHeader?: RestHeader;
  jobSchedulerDetailsDTOList?: IJobSchedulerDetails[];
  totalItems?: number;
  result?: boolean;
}

export const defaultJobSchedulerDetailsResponse: Readonly<JobSchedulerDetailsResponse> = {
  restHeader: {},
  jobSchedulerDetailsDTOList: [] as IJobSchedulerDetails[],
  totalItems: 0,
  result: false
};
