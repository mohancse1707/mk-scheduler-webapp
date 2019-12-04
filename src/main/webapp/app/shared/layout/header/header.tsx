
import './header.scss';
import { NavLink as Link } from 'react-router-dom';
import * as React from 'react';
import { Navbar, Nav, NavbarToggler, NavbarBrand, Collapse, Dropdown, DropdownItem, DropdownToggle, DropdownMenu } from 'reactstrap';

export interface IHeaderProps {
  currentLocale: string;
  onLocaleChange: Function;
}

export interface IHeaderState {
  menuOpen: boolean;
  dropdownOpen: boolean;
}

export default class Header extends React.Component<IHeaderProps, IHeaderState> {
  state: IHeaderState = {
    menuOpen: false,
    dropdownOpen: false
  };

  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
  }
  toggle() {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  }
  handleLocaleChange = event => {
    this.props.onLocaleChange(event.target.value);
  };

  toggleMenu = () => {
    this.setState({ menuOpen: !this.state.menuOpen });
  };

  componentDidMount() {
    const script = document.createElement('script');
    script.src = './static/js/toggle-menu.js';
    script.async = true;
    document.body.appendChild(script);
  }

  render() {
    const homeUrl = process.env.NODE_ENV === 'development' ? '/' : '/scheduler/#/';

    return (
      <div className="header">
        <div className="contentBg">
          <div className="logoDesign">
            <span>
              Scheduler Application
            </span>
          </div>
          <div className="menu ">
            <div className="row">
              <div className="col-sm-8">
                <nav className="navbar navbar-expand-lg navbar-light">
                  <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav  mr-auto">
                      <li className="nav-item menuHeader active">
                        <a className="nav-link" href={homeUrl}>
                          <img src="./static/images/icon-home.png" />{' '}
                        </a>
                      </li>
                    </ul>
                  </div>
                </nav>
              </div>
              <div className="col-sm-4 ">
                <div className="WelecomeMessage">
                  <span className="WelecomeNote">
                    Welcome User
                  </span>
                  <span className="HelpIcon">
                    <a href="#"> Help </a>{' '}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
