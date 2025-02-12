import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Home from './pages/Home.jsx';
import Rules from './pages/Rules.jsx';
import About from './pages/About.jsx';
import SignIn from './pages/SignIn.jsx';
import NavigationBar from './components/NavigationBar.jsx';

function App() {
  return (
    <>
      <BrowserRouter>
        <NavigationBar>
          <Routes>
            <Route path='/' element={<Home />}></Route>
            <Route path='/rules' element={<Rules />}></Route>
            <Route path='/about' element={<About />}></Route>
            <Route path='/sign-in' element={<SignIn />}></Route>
          </Routes>
        </NavigationBar>
      </BrowserRouter>
    </>
  )
}

export default App
