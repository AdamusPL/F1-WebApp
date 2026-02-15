import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Home from './pages/Home.jsx';
import Rules from './pages/Rules.jsx';
import About from './pages/About.jsx';
import SignIn from './pages/SignIn.jsx';
// import Register from './pages/Register.jsx';
import NavigationBar from './components/NavigationBar.jsx';
import Results from './pages/Results.jsx';
import { YearProvider } from './components/YearProvider.jsx';
import { CookiesProvider } from 'react-cookie';

function App() {
  return (
    <>
      <YearProvider>
        <BrowserRouter>
          <CookiesProvider>
            <NavigationBar />
            <Routes>
              <Route path='/' element={<Home />}></Route>
              <Route path='/rules' element={<Rules />}></Route>
              <Route path='/about' element={<About />}></Route>
              <Route path='/sign-in' element={<SignIn />}></Route>
              {/* <Route path='/register' element={<Register />}></Route> */}
              <Route path='/results' element={<Results />}></Route>
            </Routes>
          </CookiesProvider>
        </BrowserRouter>
      </YearProvider >
    </>
  )
}

export default App
