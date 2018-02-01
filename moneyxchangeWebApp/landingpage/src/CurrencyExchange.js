import React, { Component } from 'react';
import './App.css';
import NumberFormat from 'react-number-format';

import { ToastContainer, toast } from 'react-toastify';
import {reactLocalStorage} from 'reactjs-localstorage';

let BACKEND_URL="http://localhost:8080";
let SERVICE_URL="/rates/exchange";
let MAX_CACHE_AGE = 10;

export class CurrencyExchange extends Component {
   constructor() {
    super();
    this.state = {
      usdValue : '',
      eurValue : ''
    };
   }
   onButtonClick(event) {
    //Execute call to Backend and use the exch rate to calculate the EUR value
    if(this.state.usdValue === null || this.state.usdValue.trim()==="" || this.state.usdValue.trim() === "0") {
        toast.error("Please enter a valid USD value", {
          position: toast.POSITION.TOP_CENTER
      });
      return;
    }
    
    //Attempt to retrieve cached values from a past request
    let tempExchRate = reactLocalStorage.get('usdEurExchRate', null);

    if(tempExchRate !== null) {
      let lastRequest = reactLocalStorage.get('cacheAge', null);
      if(lastRequest !== null) {
        let rn = new Date().getTime();
        var diffMs = rn - lastRequest;
        let cacheAgeMinutes = Math.round(((diffMs % 86400000) % 3600000) / 60000);
        console.log(`Cached data is ${cacheAgeMinutes} minutes old`);

        if(cacheAgeMinutes <= MAX_CACHE_AGE) {
          console.log(`Cached data found, not going to server. ${MAX_CACHE_AGE-cacheAgeMinutes} minutes left.`);
          this.setState({eurValue:this.state.usdValue * tempExchRate});
          return;
        } else {
          console.log(`Cached data found, but data is too old. Going to the server.`);
        }
      }
    }

    //Cache retrieval failed, proceed with new request
    const url = BACKEND_URL + SERVICE_URL + "?base=USD&symbols=EUR";
    let usdEurExchRate=0;
    let lastRequestTime = new Date();

    fetch(url, {
      //headers: {"Authorization": "Bearer " + reactLocalStorage.get("jwtToken",null)}
    }).then((response) => response.json())
    .then((responseJson) => {
      usdEurExchRate=responseJson.rates.EUR;
      this.setState({eurValue:this.state.usdValue * usdEurExchRate});

      //Cache the  data to reduce server hits
      reactLocalStorage.set('usdEurExchRate', usdEurExchRate);
      reactLocalStorage.set('cacheAge', lastRequestTime.getTime());

    })
    .catch((error) => {
      console.error(error);
      toast.error("An error occurred while trying to perform the action. Please try again later.", {
        position: toast.POSITION.TOP_CENTER
      });
    });
    

   }
   handleChange(event){

    this.setState({usdValue: event.target.value});

   }
   render() {
    return (
      <div>
      <ToastContainer />
        <div className="row er-main">
        <div className="col-1"></div>
        <div className="col-5 text-right">
           <p className="text-center pUsd">USD</p>
           <NumberFormat value={this.state.usdValue} className="amountTb" thousandSeparator={true} prefix={'$'} decimalScale={4} onValueChange={(values) => {
    const {formattedValue, value} = values;
    // formattedValue = $2,223
    // value ie, 2223
    this.setState({usdValue: value})
  }} />
        </div>
        <div className="col-5">
           <p>EUR</p>
           <NumberFormat value={this.state.eurValue} className="amountTb" thousandSeparator={true} prefix={'€'} decimalScale={4} readOnly onValueChange={(values) => {
    const {formattedValue, value} = values;
    // formattedValue = $2,223
    // value ie, 2223
    this.setState({eurValue: value})
  }} />
        </div>
        <div className="col-1"></div>
     </div>
     <div className="row er-secondary">
        <div className="col  text-center">
           <a href="#" className="calcEr" onClick={this.onButtonClick.bind(this)}>Calculate</a>
        </div>
     </div>
   </div>
      );
   }
}
