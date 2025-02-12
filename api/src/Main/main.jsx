import React from "react";
import Dropdown from "../Navbar/navbar.jsx";
import CharacterMenu from "../CharacterMenu/characterMenu.jsx";
import Search from "./Search/search.jsx";
import { Card } from "@/components/ui/card"; // ShadCN Card
import "./main.css";
import Market from "@/Shop/market/market.jsx";

const MainLayout = () => {
    return (
        <div className="min-h-screen flex flex-col bg-gray-50"> {/* Flexbox i jasne tło */}
            {/* Navbar */}
            <div className="w-full z-50 bg-white shadow-md">
                <Dropdown/>
            </div>

            {/* Content */}
            <div className="content-container">
                {/* Lewa kolumna: CharacterMenu */}
                <div className="character-menu-container">
                    <CharacterMenu/>
                </div>

                {/* Prawa kolumna: Market */}
                <div className="search-container">
                    <Search/>
                </div>
            </div>
        </div>
    );
};

export default MainLayout;
