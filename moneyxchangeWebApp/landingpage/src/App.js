import React, { Component } from 'react';
import './App.css';
import NumberFormat from 'react-number-format';

import { ToastContainer, toast } from 'react-toastify';
import {reactLocalStorage} from 'reactjs-localstorage';
import {HistoricPrices} from './HistoricPrices.js';
import {CurrencyExchange} from './CurrencyExchange.js';



let BACKEND_URL="http://localhost:8080";
let SERVICE_URL="/auth";

class StaticApp extends Component {

   constructor() {
    super();


    const auUser = reactLocalStorage.get('authUser', null);
    const cToken = reactLocalStorage.get('token', null);
    this.state = {
      authUser : auUser,
      //having issues getting the /auth response with CORS
      token: cToken,
      username:'',
      password:''
    };
   }
  handleChangeUser(event) {
    this.setState({username: event.target.value});
  }
  handleChangePass(event) {
    this.setState({password: event.target.value});
  }

  handleSubmit(event) {
    const user = this.state.username;
    const pass = this.state.password;
    const url = BACKEND_URL + SERVICE_URL;

    fetch(url, {
      credentials:true,
      crossDomain:true,
      method: 'POST',
      headers: {'Content-Type':'application/json'},
      body: JSON.stringify({
        username: user,
        password: pass,
      })
    }).then((responseJson) => {
      console.log(responseJson);
      let tokenInfo = responseJson.token;

      tokenInfo = 'test'; //To work around CORS not returning jwt
      if(tokenInfo !== undefined) {
        if(responseJson.status === 200){
          //Cache the response data to remain authenticated
          //Usually, we would cache the token and the authenticated user here
          this.setState({
            authUser:user
          });
          reactLocalStorage.set('jwtToken', tokenInfo);
          reactLocalStorage.set('authUser', user);
        }
      } else {

      console.error(responseJson);
          toast.error("Authentication failed. Error: "+ responseJson.status, {
            position: toast.POSITION.TOP_CENTER
          });
      }

    })
    .catch((error) => {
      console.error(error);
      toast.error("An error occurred while trying to perform the action. Please try again later.", {
        position: toast.POSITION.TOP_CENTER
      });
    });
  }
  render() {
    const authUser = this.state.authUser;
    if(authUser === null) {
      //prompt login
      return (
        <div className="login-page">
      <ToastContainer />
          <div className="form">
            <img src="/img/logo.png" className="logo"/>
              <input type="text" placeholder="Username" value={this.state.username}  onChange={this.handleChangeUser.bind(this)}/>
              <input type="password" placeholder="Password" value={this.state.password}  onChange={this.handleChangePass.bind(this)}/>
              <button type="submit" onClick={this.handleSubmit.bind(this)}>Login</button>
          </div>
        </div>
        );
    }
    else {
    return (
<div className="container-fluid">
   <div className="row">
      <div className="col text-center">
         <img src="/img/logo.png" className="logo"/>
         <div><p>Welcome {authUser}!.</p> <a href="/" onClick={
    reactLocalStorage.clear()
         }>Logout</a></div>
      </div>
   </div>
   <div className="row nav">
      <div className="col"></div>
      <div className="col text-center nav-inner">
         <a href="/" className="nav-link">Home</a>
      </div>
      <div className="col text-center nav-inner">
         <a href="/" className="nav-link">Prices</a>
      </div>
      <div className="col text-center nav-inner">
         <a href="/" className="nav-link">About</a>
      </div>
      <div className="col"></div>
   </div>
   <div className="row">
      <div className="col division">
      </div>
   </div>
   <CurrencyExchange/>
   <div className="row">
      <div className="col division">
      </div>
   </div>
   <div className="row">
      <div className="col-2"></div>
      <div className="col-6 section-title-container">
         <span className="section-title">HISTORIC PRICE (USD)</span>
      </div>
   </div>
   <HistoricPrices/>
   <div className="row">
      <div className="col-12 pre-footer"></div>
   </div>
   <div className="row">
      <div className="col-12 footer">
         <footer>
            <div className="row">
               <div className="col-1">
               </div>
               <div className="col-4 footer-div text-left">
                  <div className="footer-title">Home</div>
                  <div className="footer-sub-title">
                     <ul className="filler-footer list-unstyled">
                        <li>Lorem ipsum dolor sit amet.</li>
                        <li>Duis aliquam urna id felis.</li>
                     </ul>
                  </div>
               </div>
               <div className="col-4 footer-div text-left">
                  <div className="footer-title">About</div>
                  <div className="footer-sub-title">
                     <ul className="filler-footer list-unstyled">
                        <li>Lorem ipsum dolor sit amet.</li>
                        <li>Duis aliquam urna id felis.</li>
                     </ul>
                  </div>
               </div>
               <div className="col-3 footer-div">
                  <a href="/"><img src="http://via.placeholder.com/80x100"/></a>
               </div>
            </div>
         </footer>
      </div>
   </div>
</div>
    );
  }
  }
}


export default StaticApp;
