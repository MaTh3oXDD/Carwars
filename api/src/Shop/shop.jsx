import React from 'react';
import Dropdown from '../Navbar/navbar.jsx';
import CharacterMenu from '../CharacterMenu/characterMenu.jsx';
import Market from "./market/market.jsx";
import './shop.css';
const Shop = () => {
    return (
        <div className="shop-layout">
            <div className="navbar-container">
                <Dropdown />
            </div>
            <div className="content-container">
                <div className="character-menu-container">
                    <CharacterMenu />
                </div>
                <div className="shop-container">
                    <Market />
                </div>
            </div>
        </div>
    );
}
export default Shop;