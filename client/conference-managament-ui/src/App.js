import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import SessionList from './SessionList';
import SessionAdd from './SessionAdd';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/sessions' exact={true} component={SessionList}/>
		  <Route path='/sessions/new' exact={true} component={SessionAdd}/>
        </Switch>
      </Router>
    )
  }
}

export default App;