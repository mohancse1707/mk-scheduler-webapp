import './app.scss';

import * as React from 'react';
import { connect } from 'react-redux';
import { Card } from 'reactstrap';
import { HashRouter as Router } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import { IRootState } from 'app/shared/reducers';
import { setLocale } from 'app/shared/reducers/locale';
import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import ErrorBoundary from 'app/shared/error/error-boundary';
import AppRoutes from 'app/routes';
export interface IAppProps extends StateProps, DispatchProps {}

export class App extends React.Component<IAppProps, any> {
  componentDidMount() {}

  render() {
    const paddingTop = '60px';
    return (
      <Router>
        <main>
          <ErrorBoundary>
            <Header currentLocale={this.props.currentLocale} onLocaleChange={this.props.setLocale} />
          </ErrorBoundary>
          <div className="contentBg">
            <div className="borderArround">
              <div className="app-container">
                <ToastContainer position={toast.POSITION.TOP_LEFT} className="toastify-container" toastClassName="toastify-toast" />
                <div className="container-fluid" id="app-view-container">
                  <div>
                    <ErrorBoundary>
                      <AppRoutes />
                    </ErrorBoundary>
                  </div>
                  <Footer />
                </div>
              </div>
            </div>
          </div>
        </main>
      </Router>
    );
  }
}

const mapStateToProps = ({ locale }: IRootState) => ({
  currentLocale: locale.currentLocale
});

const mapDispatchToProps = { setLocale };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(App);
