import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavBar from './AppNavBar';
import { Link } from 'react-router-dom';

class SessionList extends Component {

  constructor(props) {
    super(props);
    this.state = {sessions: [], isLoading: true};
    //this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('/api/sessions', {method: 'GET',
  headers:{
    'X-Requested-With': 'XMLHttpRequest'
  }})
      .then(response => response.json())
      .then(data => this.setState({sessions: data, isLoading: false}));
  }

  
  async remove(id) {
    await fetch(`/api/sessions/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedSessions = [...this.state.sessions].filter(i => i.id !== id);
      this.setState({sessions: updatedSessions});
    });
  }
  
  render() {
    const {sessions, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading Sessions...</p>;
    }
	
	const options = { weekday: 'long', hour: 'numeric', minute: 'numeric'};
	
    const sessionList = sessions.map(session => {
		const today = Date.now();
      return <tr key={session.id}>
        <td style={{whiteSpace: 'nowrap'}}>{session.track}</td>
		<td>{session.description}</td>
        <td>{session.startTime}</td>
		<td>{session.endTime}</td>
		<td>{session.duration}min</td>
		<td>
          <ButtonGroup>
            <Button size="sm" color="danger" onClick={() => this.remove(session.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavBar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/sessions/new">Add Session</Button>
          </div>
          <h3>Session List</h3>
          <Table className="mt-4">
            <thead>
            <tr>
			  <th>Day</th>
              <th width="20%">Session</th>
              <th>Start Time</th>
			  <th>End Time</th>
              <th width="10%">Duration</th>
			  <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {sessionList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default SessionList;