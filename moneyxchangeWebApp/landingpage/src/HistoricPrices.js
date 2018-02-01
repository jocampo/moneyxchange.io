import React, { Component } from 'react';
import './App.css';
import NumberFormat from 'react-number-format';
import { ToastContainer, toast } from 'react-toastify';
import {reactLocalStorage} from 'reactjs-localstorage';


let BACKEND_URL="http://localhost:8080";
let SERVICE_URL="/rates/exchange";
let MAX_CACHE_AGE = 10;

export class HistoricPrices extends Component {

   split(array){

   }
   callService(e){
    const url = BACKEND_URL + SERVICE_URL + "?base=USD";
    let rates=null;
    fetch(url).then((response) => response.json())
    .then((responseJson) => {
      rates=responseJson.rates;

    let ratesArray= [];

    for (var key in rates) {
        if (rates.hasOwnProperty(key)) {
          ratesArray.push(`${key} ${rates[key]}`)
        }
    }

    var ratesMatrix = [];
    while(ratesArray.length) ratesMatrix.push(ratesArray.splice(0,2));

    this.setState({
      exchRates:rates,
      exchRatesMatrix:ratesMatrix
    });
    })
    .catch((error) => {
      console.error(error);
      toast.error("An error occurred while trying to load the exchange rates information. Please try again later.", {
        position: toast.POSITION.TOP_CENTER
      });
    });
   }
   constructor() {
    super();

    this.state = {
      exchRates : null,
      exchRatesMatrix:null
    };

    let rates = this.callService(this);
   }
   render() {
    const ratesMatrix = this.state.exchRatesMatrix;
    if(ratesMatrix!==null) {
      return (
          <div>
        {ratesMatrix.map((array,index) => (
          <div className="row" key={index}>
             <div className="col-2"></div>
             <div className="col-4  currency-container">
                <div className="left-logo">
                   <img src="/img/generic_currency.png" className="price-image"/>
                </div>
                <div className="right-text">
                  <div className="currency">{array[0].split(" ")[0]}</div>
                  <div className="exchangeRate">{array[0].split(" ")[1]}</div>
                </div>
             </div>
             <div className="col-1">
             </div>
             {array[1]!==undefined ? (
             <div className="col-4 currency-container">
                <div className="left-logo">
                   <img src="/img/generic_currency.png" className="price-image"/>
                </div>
                  <div className="currency">{array[1].split(" ")[0]}</div>
                  <div className="exchangeRate">{array[1].split(" ")[1]}</div>
             </div>
             ):(<div></div>)}

             <div className="col-1"></div>
          </div>
          ))}

          </div>
      );
        
      }
      else {
        return null;
      }
   }
}