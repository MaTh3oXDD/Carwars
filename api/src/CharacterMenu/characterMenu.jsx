import React, { useState, useEffect } from 'react';
import './characterMenu.css';
import maleAvatar from '../assets/img/mechanics/true.png';
import femaleAvatar from '../assets/img/mechanics/false.png';

const CharacterMenu = () => {
    const [username, setUsername] = useState('');
    const [gender, setGender] = useState(null);

    useEffect(() => {
        const storedUsername = localStorage.getItem('username');
        console.log('Stored username:', storedUsername);
        if (storedUsername) {
            setUsername(storedUsername);
            fetchGender(storedUsername);
        }
    }, []);

    const fetchGender = async (username) => {
        try {
            const response = await fetch(`http://localhost:8080/user/get-gender/${username}`);
            if (response.ok) {
                const data = await response.text(); // zmiana z response.json() na response.text()
                console.log('Otrzymane dane:', data);
                setGender(data === 'true'); // konwersja string na boolean
            } else {
                console.error('Błąd podczas pobierania płci:', response.status);
            }
        } catch (error) {
            console.error('Błąd:', error);
        }
    };

    const getAvatarImage = () => {
        console.log('Aktualna płeć:', gender);
        if (gender === true) {
            return maleAvatar;
        } else if (gender === false) {
            return femaleAvatar;
        }
    };


    return (
        <div className="container">
            <div className="welcome-text">
                {username ? (
                    <h2>Witaj, {username}!</h2>
                ) : (
                    <h2>Witaj, Gościu!</h2>
                )}
            </div>
            <div className="avatar-container">
                {gender !== null && (
                    <img
                        src={getAvatarImage()}
                        alt="Avatar"
                        className="avatar-image"
                    />
                )}
            </div>
        </div>
    );
};

export default CharacterMenu;
