import './footer.scss';

import * as React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <div className="col-sm-9">
        <div className="copyRight">
          <p>Powerd by MOHANKUMAR. All rights reserved.</p>
        </div>
      </div>
      <div className="col-sm-3">
        <div className="rightLogoFooter">
          <h3 className="theMohanGroup" />
        </div>
      </div>
    </Row>
  </div>
);

export default Footer;
