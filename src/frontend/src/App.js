import React from 'react';
import './App.css';
import {MedalStandingTable} from "./pages/medal-standings-table";
import {BrowserRouter as Router, Route} from 'react-router-dom';

function App() {
    return (
        <div className="App">
            <Router>
                <Route path="/:sport/standings">
                    <MedalStandingTable />
                </Route>
            </Router>
        </div>
    );
}

export default App;
