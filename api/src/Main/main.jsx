import React from 'react';
import Dropdown from './Navbar/navbar.jsx';
import CharacterMenu from './CharacterMenu/characterMenu.jsx';
import Search from './Search/search.jsx';
import './main.css';

const MainLayout = () => {
    return (
        <div className="main-layout">
            <div className="navbar-container">
                <Dropdown />
            </div>

            <div className="content-container">
                <div className="character-menu-container">
                    <CharacterMenu />
                </div>

                <div className="search-container">
                    <Search />
                </div>
            </div>
        </div>
    );
};

export default MainLayout;
