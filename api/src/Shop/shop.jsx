import React from 'react';
import Dropdown from '../Navbar/navbar.jsx';
import CharacterMenu from '../CharacterMenu/characterMenu.jsx';
import Market from "./market/market.jsx";
import './shop.css';

const Shop = () => {
    return (
        <div className="shop-layout">
            {/* Górna nawigacja */}
            <div className="navbar-container">
                <Dropdown />
            </div>

            {/* Główna sekcja */}
            <div className="content-container">
                {/* Lewa kolumna: CharacterMenu */}
                <div className="character-menu-container">
                    <CharacterMenu />
                </div>

                {/* Prawa kolumna: Market */}
                <div className="shop-container">
                    <Market />
                </div>
            </div>
        </div>
    );
};

export default Shop;
