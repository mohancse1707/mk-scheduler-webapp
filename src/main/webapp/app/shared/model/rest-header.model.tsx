export class RestHeader {
  totalRecordList?: number;
  pageSize?: number;
  pageIndex?: number;
  message?: string;

  constructor() {
    this.message = '';
    this.totalRecordList = null;
    this.pageSize = null;
    this.pageIndex = null;
  }
}
