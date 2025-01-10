import React, { useState, useEffect } from 'react';
import Shop from '../../assets/img/search/shop.jpg';
import Car from '../../assets/img/search/car.jpg';
import Nothing from '../../assets/img/search/nothing.jpg';
import './search.css';

const Search = () => {
    const [search, setSearch] = useState('');
    const [imageName, setImageName] = useState('');

    const newSearch = async () => {
        try {
            const response = await fetch('http://localhost:8080/search/names', {
                method: 'GET',
            });

            if (response.ok) {
                const data = await response.text();
                console.log('Otrzymane dane:', data);
                setImageName(data);
                setSearch(data); // Dodane ustawienie search na podstawie otrzymanych danych
            } else {
                console.error('Błąd podczas wyszukiwania:', response.status);
            }
        }
        catch (error) {
            console.error('Błąd:', error);
        }
    }

    const getSearch = () => {
        if (search === 'shop') {
            return Shop;
        } else if (search === 'car') {
            return Car;
        } else {
            return Nothing;
        }
    }
    useEffect(() => {
        newSearch();
    }, []);
    return (
        <div className="main-container">
            <div className="image-container">

                {imageName && (
                    <img
                        src={getSearch()}
                        alt={imageName}
                        style={{maxWidth: '100%', height: 'auto'}}
                    />
                )}
                <button onClick={newSearch}>
                    Wyszukaj
                </button>
            </div>
        </div>
    );
}

export default Search;
