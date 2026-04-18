import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './css/App.css';
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
import Providers from './components/Providers.jsx';

function App() {
  return (
    <Providers>
      <BrowserRouter>
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
      </BrowserRouter>
    </Providers>
  )
}

export default App;