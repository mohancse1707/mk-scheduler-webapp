export interface IJobSchedulerDetails {
  id?: number;
  jobName?: string;
  jobGroup?: string;
  jobClass?: string;
  frequency?: string;
  priority?: number;
  startDate?: Date;
  jobStatus?: string;
}

export const defaultValue: Readonly<IJobSchedulerDetails> = {
  id: 0,
  jobName: '',
  jobGroup: '',
  jobClass: 'com.mohan.springboot.app.jobs.SimpleJob',
  frequency: '',
  priority: 0,
  startDate: null,
  jobStatus: ''
};

export class SearchJobSchedulerDetails {
  jobName?: string;
  priority?: number;

  constructor() {
    this.jobName = '';
    this.priority = null;
  }
}
