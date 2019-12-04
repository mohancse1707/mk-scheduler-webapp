import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Badge, Button, Collapse, Form, Input, Row, Table, UncontrolledAlert } from 'reactstrap';
import { getPaginationItemsNumber, getSortState, JhiPagination, TextFormat } from 'react-jhipster';
import { IRootState } from 'app/shared/reducers';
import { getAllJobSchedulers, createOrUpdateScheduler, cancelJobScheduler } from './job-scheduler.reducer';
import BlockUi from 'react-block-ui';

import { defaultValue, IJobSchedulerDetails } from 'app/modules/projects/job-scheduler/job-scheduler.model';
import { JobSchedulerCreateUpdate } from 'app/modules/projects/job-scheduler/job-scheduler-create-update';
import { Messages } from 'app/shared/model/messages-model';

export const APP_TIMESTAMP_FORMAT = 'DDMMMYY HH:mm:ss';

export interface IJobSchedulerProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

export interface IJobSchedulerPageState {
  startIndex: number;
  pageSize: number;
  totalItems: number;
  jobName: string;
  priority: number;
  showModal: boolean;
  collapse: boolean;
  errorMessages: Messages[];
  isNewJobScheduler: boolean;
  isChangeJob: boolean;
  isCancelJob: boolean;
  selectedJobScheduler: IJobSchedulerDetails;
  jobSchedulerDetailsDTOList: IJobSchedulerDetails[];
}

export class JobScheduler extends React.Component<IJobSchedulerProps, IJobSchedulerPageState> {

  constructor(props) {
    super(props);
    this.state = ({
      startIndex: 1,
      pageSize: 5,
      totalItems: 0,
      showModal: false,
      collapse: true,
      isNewJobScheduler: false,
      isChangeJob: false,
      isCancelJob: false,
      priority: 0,
      jobName: '',
      selectedJobScheduler: defaultValue,
      jobSchedulerDetailsDTOList: this.props.jobSchedulerDetailsDTOList,
      errorMessages: [] as Messages[]
    } as any) as IJobSchedulerPageState;
    this.searchJobSchedulerData = this.searchJobSchedulerData.bind(this);
    this.toggle = this.toggle.bind(this);
    this.resetSearchComponent = this.resetSearchComponent.bind(this);
  }

  componentDidMount() {
    this.getAllJobSchedulers();
  }

  componentDidUpdate(prevProps: IJobSchedulerProps, prevState) {
    if (this.props.jobSchedulerDetailsDTOList !== prevProps.jobSchedulerDetailsDTOList) {
      this.setState({
        jobSchedulerDetailsDTOList: this.props.jobSchedulerDetailsDTOList,
        totalItems: this.props.totalItems,
        errorMessages: []
      });
    }
  }

  getAllJobSchedulers = () => {
    const {
      jobName,
      priority,
      startIndex,
      pageSize
    } = this.state;

    this.props.getAllJobSchedulers(jobName, priority, startIndex, pageSize);
  };

  updatePagination() {
    this.getAllJobSchedulers();
  }

  searchJobSchedulerData() {
    this.setState({
      startIndex: 1
    }, () => this.getAllJobSchedulers());

  }

  resetSearchComponent() {
    this.setState({
      jobName: '',
      priority: 0,
      jobSchedulerDetailsDTOList: [],
      totalItems: 0,
      startIndex: 1,
      errorMessages: []
    });
  }

  changeJobScheduler = data => {
    this.setState({
      showModal: true,
      isNewJobScheduler: false,
      isChangeJob: true,
      isCancelJob: false,
      selectedJobScheduler: data,
      errorMessages: []
    });
  };

  addNewJobScheduler = () => {
    this.setState({
      showModal: true,
      isNewJobScheduler: true,
      isChangeJob: false,
      isCancelJob: false,
      selectedJobScheduler: defaultValue,
      errorMessages: []
    });
  };

  loadCancelPopUp = data => {
    this.setState({
      showModal: true,
      isNewJobScheduler: false,
      isChangeJob: false,
      isCancelJob: true,
      selectedJobScheduler: data,
      errorMessages: []
    });
  };

  createOrUpdateScheduler = selectedJSObject => {

    if (this.validateJS(selectedJSObject)) {
      this.props.createOrUpdateScheduler(selectedJSObject);

      const messageObject = {
        message: this.state.isNewJobScheduler ? 'Job created successfully.' : 'Job changed successfully',
        color: 'green'
      };
      const message = [] as Messages[];
      message.push(messageObject);

      this.setState({
        errorMessages: message
      });
    }
  };

  validateJS = selectedJSObject => {
    if (selectedJSObject.jobName === '' || selectedJSObject.jobGroup === ''
      || selectedJSObject.frequency === '' || selectedJSObject.frequency === undefined
      || selectedJSObject.priority === '' || selectedJSObject.priority === undefined) {
      const messageObject = {
        message: 'Please enter all mandatory fields.',
        color: 'red'
      };
      const message = [] as Messages[];
      message.push(messageObject);
      this.setState({
        errorMessages: message
      });

    } else if (this.state.isNewJobScheduler && this.checkIFJobExits(selectedJSObject.jobName)) {
      const messageObject = {
        message: 'Job Name is already exist. please try adding new Job Name',
        color: 'red'
      };
      const message = [] as Messages[];
      message.push(messageObject);
      this.setState({
        errorMessages: message
      });
      return false;
    } else {
      this.setState({
        errorMessages: []
      });
      return true;
    }
  };

  checkIFJobExits = jobName => {
    let count = 0;
    this.props.jobSchedulerDetailsDTOList.forEach(value => {
        if (value.jobStatus !== 'CANCELLED' && value.jobName === jobName) {
          count = 1;
        }
      }
    );

    if (count === 1) {
      return true;
    } else {
      return false;
    }
  };

  cancelJobScheduler = selectedJSObject => {
    this.props.cancelJobScheduler(selectedJSObject);
    const messageObject = {
      message: 'Job cancelled successfully.',
      color: 'green'
    };
    const message = [] as Messages[];
    message.push(messageObject);

    this.setState({
      errorMessages: message
    });
  };

  handleClose = () => {
    this.setState({
      showModal: false,
      isNewJobScheduler: false,
      isChangeJob: false,
      isCancelJob: false,
      selectedJobScheduler: defaultValue
    }, () => this.getAllJobSchedulers());
  };

  renderModalPanel = () => {
    if (this.state.showModal) {
      return (
        <JobSchedulerCreateUpdate
        handleClose={this.handleClose}
        isCancelJob={this.state.isCancelJob}
        isChangeJob={this.state.isChangeJob}
        isNewJobScheduler={this.state.isNewJobScheduler}
        showModal={this.state.showModal}
        selectedJobScheduler={this.state.selectedJobScheduler}
        createOrUpdateSchedulerFunction={this.createOrUpdateScheduler}
        cancelJobSchedulerFunction={this.cancelJobScheduler}
        errorMessages={this.state.errorMessages}
        />
      );
    }
  };

  handlePagination = startIndex => this.setState({ startIndex }, () => this.updatePagination());

  toggle() {
    this.setState(state => ({ collapse: !state.collapse }));
  }

  onChangeJobName = evt => {
    this.setState({
      jobName: evt.target.value
    });
  };

  onChangePriority = evt => {
    this.setState({
      priority: evt.target.value
    });
  };

  render() {
    const {
      totalItems,
      collapse,
      priority,
      jobName,
      jobSchedulerDetailsDTOList
    } = this.state;
    const {
      loading
    } = this.props;

    const paginationDisplay = jobSchedulerDetailsDTOList.length > 0 ? true : false;
    const arrowDown = './static/images/icon-arrow-down.png';
    const arrowUp = './static/images/icon-arrow-up.png';
    const divDesign = { width: '174px', padding: '4px 0px 0px 0px' };
    return (

      <BlockUi tag="div" blocking={loading} message="Please wait">
      <section>
        <div className="row">
          <div className="col-sm-8">
            <h2 className="mainheader">Job Scheduler</h2>
          </div>
        </div>

        <div className="row topheader">
          <div className="col-lg-11 col-xs-11 ">
            <h5>Filter Criteria</h5>
          </div>
          <div className="col-lg-1 col-xs-1 nopadding">
            <div className="arrowsDiv">
              <img alt="" onClick={this.toggle} className="ico-colps hidden" src={collapse ? arrowDown : arrowUp} />
              <img alt="" onClick={this.toggle} className="ico-exp" src={collapse ? arrowUp : arrowDown} />
            </div>
          </div>
        </div>
        <Collapse isOpen={this.state.collapse}>
          <div className="search-form-div">
            <div className="form-row form-part mt-2">

              <div className="form-group col-sm-4 col-xs-12 form-row form-margin">
                <label className="col-sm-4 col-form-label">Job Name</label>
                <div className="col-sm-8 controlDiv">
                  <span className="columnText"> : </span>
                  <Input
                    type="text"
                    id="jobNameSearchId"
                    value={jobName}
                    onChange={this.onChangeJobName}
                    className="form-control" />
                </div>
              </div>
              <div className="form-group  col-sm-4 col-xs-12 form-row form-margin">
                <label className="col-sm-4 col-form-label">Priority</label>
                <div className="col-sm-8 controlDiv">
                  <span className="columnText"> : </span>
                  <Input
                    type="number"
                    id="jobPrioritySearchId"
                    value={priority || ''}
                    min={0}
                    onChange={this.onChangePriority}
                    className="form-control text-right" />
                </div>
              </div>
              <div className="form-group  col-sm-4 col-xs-12 form-row form-margin">
                <label className="col-sm-4 col-form-label" />
                <div className="col-sm-8 controlDiv">
                  <span className="columnText" />

                </div>
              </div>
            </div>
            <div className="form-group buttons row">
              <div className="col-sm-8" />
              <div className="col-sm-4 rightAlign">
                <button type="reset" onClick={this.resetSearchComponent} className="brest rigth">
                  RESET
                </button>
                <button type="submit" onClick={this.searchJobSchedulerData} className="bsearch">
                  SEARCH
                </button>
              </div>
            </div>
          </div>
        </Collapse>
      </section>
      <section>
        <div className="tablepart">
          <div className="row">
            <div className="col-sm-8 panelheader">Showing List of Job Schedulers ({totalItems})</div>
          </div>
          <table className="table table-bordered tableWhiteSpace">
            <thead>
            <tr>
              <th scope="col">Job ID</th>
              <th scope="col">Job Name</th>
              <th scope="col">Job Group</th>
              <th scope="col">Job Class</th>
              <th scope="col">Frequency(corn exp)</th>
              <th scope="col">Priority</th>
              <th scope="col">Start Date</th>
              <th scope="col">Status</th>
              <th scope="col">Change Job</th>
              <th scope="col">Cancel Job</th>
            </tr>
            </thead>
            <tbody>
            {jobSchedulerDetailsDTOList.map((job, i) => (
              <tr key={`entity-${i}`}>
                <td>{job.id}</td>
                <td>{job.jobName}</td>
                <td>{job.jobGroup}</td>
                <td>{job.jobClass}</td>
                <td>{job.frequency}</td>
                <td>{job.priority}</td>
                <td> <TextFormat value={job.startDate} type="date" format={APP_TIMESTAMP_FORMAT} /></td>
                <td>{job.jobStatus}</td>
                <td>
                  <a>
                    <img
                      alt=""
                      onClick={this.changeJobScheduler.bind(this, job)}
                      title="Change Job"
                      src={job.jobStatus === 'CANCELLED' ? './static/images/edit-disable.png' : './static/images/edit3.png' }
                    />
                  </a>
                </td>
                <td>
                  <a>
                    <img
                      alt=""
                      onClick={this.loadCancelPopUp.bind(this, job)}
                      title="Cancel Job"
                      src={job.jobStatus === 'CANCELLED' ? './static/images/edit-disable.png' : './static/images/edit3.png' }
                    />
                  </a>

                </td>
              </tr>
            ))}
            </tbody>
          </table>
          {paginationDisplay ? (
            <div>
              <div className="customPaging justify-content-center">
                <Row className="justify-content-center">
                  <JhiPagination
                    items={getPaginationItemsNumber(totalItems, this.state.pageSize)}
                    activePage={this.state.startIndex}
                    onSelect={this.handlePagination}
                    maxButtons={3}
                  />
                </Row>
              </div>
            </div>
          ) : (
            <div />
          )}
          <div className="buttons row">
            <div className="col-sm-2 leftAlign">
              <button onClick={this.addNewJobScheduler} type="button" >
                Create New Job
              </button>
            </div>
            <div className="col-sm-10" />
          </div>
        </div>
      </section>
        {this.renderModalPanel()}
      </BlockUi>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  jobSchedulerDetailsDTOList: storeState.jobScheduler.jobSchedulerResponse.jobSchedulerDetailsDTOList,
  totalItems: storeState.jobScheduler.jobSchedulerResponse.totalItems,
  loading: storeState.jobScheduler.loading
});

const mapDispatchToProps = {
  getAllJobSchedulers,
  createOrUpdateScheduler,
  cancelJobScheduler
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(JobScheduler);
