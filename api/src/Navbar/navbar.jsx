import { useEffect, useState } from 'react';
import './navbar.css';
import logo from '../assets/carwarsbeztla.png';

const Dropdown = () => {


    return (
        <nav className="dropdown-navbar">
            <div className="navbar-logo">
                <img src={logo} alt="Logo" className="logo" />
            </div>

            <div className="navbar-links">

                <div className="navbar-item">
                    <a href="#" className="navbar-link">Shop</a>
                </div>

                <div className="navbar-item">
                    <a href="#" className="navbar-link">Race</a>
                </div>

                <div className="navbar-item">
                    <a href="#" className="navbar-link">Explore</a>
                </div>

                <div className="navbar-item">
                    <a href="#" className="navbar-link">Contact</a>
                </div>

                <div className="navbar-item">
                    <a href="#" className="navbar-link">Account</a>
                </div>
            </div>
        </nav>
    );
};

export default Dropdown;
