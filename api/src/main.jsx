import React, { StrictMode, useState } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './index.css';
import Login from './Login/login.jsx';
import Register from './Register/register.jsx';
import Navbar from "./Navbar/navbar.jsx";
import CharacterMenu from "./CharacterMenu/characterMenu.jsx";
import Search from "./Main/Search/search.jsx";
import MainLayout from "./Main/main.jsx";
import Market from "./Shop/market/market.jsx";
import Shop from "./Shop/shop.jsx";
import Contact from "./Contact/contact.jsx";
import { ThemeProvider } from "./context/ThemeProvider";
import Account from "@/Account/account.jsx";
import Race from "@/Race/race.jsx";
const App = () => {
    const [nickname, setNickname] = useState('');

    return (
        <StrictMode>
            <ThemeProvider>
                <Router>
                    <Routes>
                        <Route path="/" element={<Login setNickname={setNickname} />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/navbar" element={<Navbar nickname={nickname} />} />
                        <Route path="/characterMenu" element={<CharacterMenu nickname={nickname} />} />
                        <Route path="/search" element={<Search/>} />
                        <Route path="/main" element={<MainLayout/>} />
                        <Route path="/market" element={<Market/>} />
                        <Route path="/shop" element={<Shop/>} />
                        <Route path="/contact" element={<Contact/>} />
                        <Route path="/account" element={<Account/>} />
                        <Route path="/race" element={<Race/>} />
                    </Routes>
                </Router>
            </ThemeProvider>
        </StrictMode>

    );
};

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <App />
    </StrictMode>
);
