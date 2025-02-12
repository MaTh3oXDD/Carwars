import React, {useState} from 'react';
import Dropdown from '../Navbar/navbar.jsx';
import CharacterMenu from '../CharacterMenu/characterMenu.jsx';
import Racetrack from "@/Race/Racetrack/racetrack.jsx";
import "./race.css"
import Search from "@/Main/Search/search.jsx";

const Race = () => {

    return (
        <div className="">
            <div className="">
                <Dropdown />
            </div>
            <div className="content-container">
                {/* Lewa kolumna: CharacterMenu */}
                <div className="character-menu-container">
                    <CharacterMenu/>
                </div>

                {/* Prawa kolumna: Market */}
                <div className="search-container">
                    <Racetrack/>
                </div>
            </div>
        </div>);
};
export default Race;