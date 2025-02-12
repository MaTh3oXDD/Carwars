import React from 'react';
import Dropdown from '../Navbar/navbar.jsx';
import Bag from "@/Account/Bag/bag.jsx";


const Account = () => {
    return (
        <div className="account-layout">
            <div className="navbar-container">
                <Dropdown />
            </div>
            <div className="content-container">
                <Bag />
            </div>
        </div>
    );
};
export default Account;