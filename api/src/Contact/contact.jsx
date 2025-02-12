import React from 'react';
import Dropdown from '../Navbar/navbar.jsx';
import Form from './Form/form.jsx';
import './contact.css';
const Contact = () => {
    return (
        <div className="contact-layout">
            <div className="navbar-container">
                <Dropdown />
            </div>
            <div className="contact-container">
                <Form />
            </div>
        </div>
    );
};

export default Contact;