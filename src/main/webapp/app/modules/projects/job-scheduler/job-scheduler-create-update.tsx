
import * as React from 'react';
import { Button, CustomInput, Input, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import DatePicker from 'react-datepicker';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { TextFormat } from 'react-jhipster';
import * as moment from 'moment';
import { IJobSchedulerDetails } from 'app/modules/projects/job-scheduler/job-scheduler.model';
import { Messages } from 'app/shared/model/messages-model';

export interface IJobSchedulerCreateUpdateProps {
  showModal: boolean;
  handleClose: any;
  isNewJobScheduler: boolean;
  isChangeJob: boolean;
  isCancelJob: boolean;
  selectedJobScheduler: IJobSchedulerDetails;
  createOrUpdateSchedulerFunction: Function;
  cancelJobSchedulerFunction: Function;
  errorMessages: Messages[];
}

export interface IJobSchedulerCreateUpdateState {
  selectedJobScheduler: IJobSchedulerDetails;
}

export class JobSchedulerCreateUpdate extends React.Component<IJobSchedulerCreateUpdateProps, IJobSchedulerCreateUpdateState> {

  state: IJobSchedulerCreateUpdateState = {
    selectedJobScheduler: this.props.selectedJobScheduler
  };

  constructor(props) {
    super(props);
    this.createOrUpdateOrCancelJS = this.createOrUpdateOrCancelJS.bind(this);
    this.onChangeFields = this.onChangeFields.bind(this);
  }

  createOrUpdateOrCancelJS() {
    if (this.props.isCancelJob) {
      const { cancelJobSchedulerFunction } = this.props;
      cancelJobSchedulerFunction(this.state.selectedJobScheduler);
    } else {
      const { createOrUpdateSchedulerFunction } = this.props;
      createOrUpdateSchedulerFunction(this.state.selectedJobScheduler);
    }
  }

  onChangeFields = evt => {
    const selectedJobScheduler: IJobSchedulerDetails = { ...this.state.selectedJobScheduler };
    selectedJobScheduler[evt.target.name] = evt.target.value;
    this.setState({ selectedJobScheduler });
  };

  render() {
    const {
    selectedJobScheduler
    } = this.state;

    const {
      showModal,
      isCancelJob,
      isChangeJob,
      isNewJobScheduler,
      handleClose,
      errorMessages
    } = this.props;

    const divDesign = { width: '178px', padding: '4px 0px 0px 0px' };

    return (
      <Modal isOpen={showModal} className="modal-lg">
        <ModalHeader toggle={handleClose}> { isNewJobScheduler ? 'Create Job Scheduler' : (isChangeJob ? 'Change Job Scheduler' : 'Cancel Job Scheduler') }</ModalHeader>
        <ModalBody>
          <div className="row">
            <div className="col-sm-4">
              {errorMessages.length > 0 ? (
                <div className=" rf-msgs-err">
                  {errorMessages.map((msg, i) => (
                    <div className="rf-msgs" key={`entity-${i}`} style={ { color: msg.color, whiteSpace: 'nowrap' } }>
                      {msg.message}
                    </div>
                  ))}
                </div>
              ) : null}
            </div>
          </div>
          <div className="modalForm">
            { isCancelJob ? (
             <div>
              <span className="infoMessage">
                Do you want to cancel the Job?
              </span>
            </div>
            ) : (
             <div>
            <span className="infoMessage">
              All fields marked with <span className="redFont">*</span> are mandatory{' '}
            </span>
               <div className="form-row form-part">
                 <div className="form-group  col-sm-6 col-xs-12 form-row">
                   <label className="col-sm-5 col-form-label">Job Name<label className="redFont">*</label></label>
                   <div className="col-sm-7 controlDiv">
                     <span className="columnText"> : </span>
                     <div style={divDesign}>
                       <input
                         id="appSupportSearch"
                         type="text"
                         name="jobName"
                         disabled={isChangeJob}
                         onChange={this.onChangeFields}
                         value={selectedJobScheduler.jobName}
                         className="form-control"
                       />
                     </div>
                   </div>
                 </div>
                 <div className="form-group  col-sm-6 col-xs-12 form-row">
                   <label className="col-sm-5 col-form-label">Job Group<label className="redFont">*</label></label>
                   <div className="col-sm-7 controlDiv">
                     <span className="columnText"> : </span>
                     <div style={divDesign}>
                       <input
                         id="appSupportSearch"
                         type="text"
                         name="jobGroup"
                         disabled={isChangeJob}
                         onChange={this.onChangeFields}
                         value={selectedJobScheduler.jobGroup}
                         className="form-control"
                       />
                     </div>
                   </div>
                 </div>
                 <div className="form-group  col-sm-6 col-xs-12 form-row">
                   <label className="col-sm-5 col-form-label">Job Class</label>
                   <div className="col-sm-7 controlDiv">
                     <span className="columnText"> : </span>
                     <div style={divDesign}>
                       <input
                         id="appSupportSearch"
                         type="text"
                         title={selectedJobScheduler.jobClass}
                         disabled
                         value={selectedJobScheduler.jobClass}
                         className="form-control"
                       />
                     </div>
                   </div>
                 </div>
                 <div className="form-group  col-sm-6 col-xs-12 form-row">
                   <label className="col-sm-5 col-form-label">Frequency(corn expression)<label className="redFont">*</label></label>
                   <div className="col-sm-7 controlDiv">
                     <span className="columnText"> : </span>
                     <div style={divDesign}>
                       <Input
                         type="select"
                         name="frequency"
                         onChange={this.onChangeFields}
                         value={selectedJobScheduler.frequency}
                         className="form-control text-right"
                       >
                         <option value="">Please Select</option>
                         <option value="1/10 * * ? * *">10 Seconds</option>
                         <option value="1/30 * * ? * *">30 Seconds</option>
                         <option value="0 * * ? * *">1 Minute</option>
                         <option value="0 */2 * ? * *">2 minute</option>
                         <option value="0 */5 * ? * *">5 Minute</option>
                         <option value="0 */10 * ? * *">10 Minute</option>
                       </Input>
                     </div>
                   </div>
                 </div>
                 <div className="form-group  col-sm-6 col-xs-12 form-row">
                   <label className="col-sm-5 col-form-label">Job Priority<label className="redFont">*</label></label>
                   <div className="col-sm-7 controlDiv">
                     <span className="columnText"> : </span>
                     <div style={divDesign}>
                       <input
                         type="number"
                         min={0}
                         name="priority"
                         onChange={this.onChangeFields}
                         value={selectedJobScheduler.priority}
                         className="form-control text-right"
                       />
                     </div>
                   </div>
                 </div>
               </div>
            </div>
            )}
          </div>
        </ModalBody>
        <ModalFooter className="model-panel-buttons">
          <Button type="submit" onClick={this.createOrUpdateOrCancelJS} className="bsearch"> {isCancelJob ? 'CANCEL' : 'SAVE' } </Button>
          <Button type="reset" onClick={handleClose} className="bsearch">
            CLOSE
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}
