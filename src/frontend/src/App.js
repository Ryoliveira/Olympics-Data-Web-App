import React from 'react';
import './App.scss';
import {MedalStandingTable} from "./pages/MedalStandingsPage";
import {BrowserRouter as Router, Route} from 'react-router-dom';
import {AthleteTable} from "./pages/AthleteTable";
import {NavBar} from "./components/NavBar";

function App() {
    return (
        <div className="App">
            <NavBar/>
            <Router>
                <Route path="/:sport/standings">
                    <MedalStandingTable />
                </Route>
                <Route path="/athletes">
                    <AthleteTable />
                </Route>
            </Router>
        </div>
    );
}

export default App;
