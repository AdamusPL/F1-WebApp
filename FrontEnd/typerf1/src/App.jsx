import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Home from './pages/Home.jsx';
import Rules from './pages/Rules.jsx';
import About from './pages/About.jsx';
import SignIn from './pages/SignIn.jsx';
import NavigationBar from './components/NavigationBar.jsx';
import Results from './pages/Results.jsx';
import Standings from './pages/Standings.jsx';
import WorldRecords from './pages/WorldRecords.jsx';
import PersonalBest from './pages/PersonalBest.jsx';
import Participants from './pages/Participants.jsx';
import Predict from './pages/Predict.jsx';
import Register from './pages/Register.jsx';
import { YearProvider } from './components/YearProvider.jsx';
import { ParticipantProvider } from './components/ParticipantProvider.jsx';
import { CookiesProvider } from 'react-cookie';
import { GrandPrixProvider } from './components/GrandPrixProvider.jsx';
import { SessionProvider } from './components/SessionProvider.jsx';
import { PredictionProvider } from './components/PredictionProvider.jsx';

function App() {
  return (
    <>
      <PredictionProvider>
        <SessionProvider>
          <GrandPrixProvider>
            <ParticipantProvider>
              <YearProvider>
                <BrowserRouter>
                  <CookiesProvider>
                    <NavigationBar />
                    <Routes>
                      <Route path='/' element={<Home />}></Route>
                      <Route path='/rules' element={<Rules />}></Route>
                      <Route path='/about' element={<About />}></Route>
                      <Route path='/sign-in' element={<SignIn />}></Route>
                      <Route path='/register' element={<Register />}></Route>
                      <Route path='/results' element={<Results />}></Route>
                      <Route path='/standings' element={<Standings />}></Route>
                      <Route path='/world-records' element={<WorldRecords />}></Route>
                      <Route path='/personal-best' element={<PersonalBest />}></Route>
                      <Route path='/participants' element={<Participants />}></Route>
                      <Route path='/predict' element={<Predict />}></Route>
                    </Routes>
                  </CookiesProvider>
                </BrowserRouter>
              </YearProvider >
            </ParticipantProvider>
          </GrandPrixProvider>
        </SessionProvider>
      </PredictionProvider>
    </>
  )
}

export default App
