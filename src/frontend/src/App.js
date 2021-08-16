import React from 'react';
import './css/App.scss';
import {MedalStandingTable} from "./pages/MedalStandingsPage";
import {BrowserRouter as Router, Route} from 'react-router-dom';
import {AthleteTable} from "./pages/AthleteTable";
import {NavBar} from "./components/NavBar";
import {CountryListPage} from "./pages/CountryListPage";
import {SportSelectionPage} from "./pages/SportSelectionPage";
import {SportInformationPage} from "./pages/SportInformationPage";
import {AthletePage} from "./pages/AthletePage";
import {HomePage} from "./pages/HomePage";
import {CountryInfoPage} from "./pages/CountryInfoPage";

function App() {
    return (
        <div className="App">
            <NavBar/>
            <Router>
                <Route exact path="/">
                    <HomePage />
                </Route>
                <Route path="/:sport/standings">
                    <MedalStandingTable />
                </Route>
                <Route path="/:sport/information">
                    <SportInformationPage />
                </Route>
                <Route path="/athlete/:athleteName">
                    <AthletePage />
                </Route>
                <Route path="/country/:countryProfileId/profile">
                    <CountryInfoPage />
                </Route>
                <Route path="/athletes">
                    <AthleteTable />
                </Route>
                <Route path="/countries">
                    <CountryListPage />
                </Route>
                <Route path={"/sports"}>
                    <SportSelectionPage />
                </Route>

            </Router>
            <footer className="card-footer">Data provided by <a href={"https://olympics.com"}>Olympics.com</a>
            </footer>
        </div>
    );
}

export default App;
