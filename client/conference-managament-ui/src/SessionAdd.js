import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavBar from './AppNavBar';

class SessionAdd extends Component {

  emptyItem = {
    description: '',
    duration: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }


  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/sessions', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/sessions');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{'Add Group'}</h2>;

    return <div>
      <AppNavBar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="description">Session Description</Label>
            <Input type="text" name="description" id="description" 
                   onChange={this.handleChange} autoComplete="description"/>
          </FormGroup>
          <FormGroup>
            <Label for="duration">Duration</Label>
            <Input type="number" name="duration" id="duration" 
                   onChange={this.handleChange} autoComplete="duration"/>
          </FormGroup>          
		  <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/sessions">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(SessionAdd);