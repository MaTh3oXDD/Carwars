import { useState, useEffect } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import "./racetrack.css";

// Import obrazów
import a4 from "../../assets/img/cars/a4.png";
import alfaromeo159 from "../../assets/img/cars/alfaromeo159.png";
import audiq5 from "../../assets/img/cars/audiq5.png";
import bmwe36 from "../../assets/img/cars/bmwe36.png";
import fiatpanda from "../../assets/img/cars/fiatpanda.png";
import golf4 from "../../assets/img/cars/golf4.png";
import lamborghini from "../../assets/img/cars/lamborghini.png";

const Racetrack = () => {
    const username = localStorage.getItem("username"); // Pobieranie nazwy użytkownika
    const [myCar, setMyCar] = useState(""); // Zapis nazwy samochodu użytkownika
    const [mySpeed, setMyCarSpeed] = useState(null); // Prędkość samochodu użytkownika
    const [randomCar, setRandomCar] = useState(""); // Zapis nazwy losowego samochodu
    const [randomCarSpeed, setRandomCarSpeed] = useState(null); // Prędkość losowego samochodu
    const [winner, setWinner] = useState(""); // Wynik wyścigu

    // Mapowanie nazw samochodów na obrazy
    const carImages = {
        A4: a4,
        "Alfa Romeo 159": alfaromeo159,
        "Audi Q5": audiq5,
        "BMW E36": bmwe36,
        "Fiat Panda": fiatpanda,
        "Golf 4": golf4,
        Lamborghini: lamborghini,
    };

    // Funkcja do pobierania samochodu użytkownika
    const fetchData = async () => {
        try {
            const response = await fetch(
                `http://localhost:8080/user/get-selected-car?username=${username}`
            );

            if (!response.ok) {
                console.error(`HTTP error! status: ${response.status}`);
                return;
            }

            const data = await response.json();
            setMyCarSpeed(data.speed); // Ustawienie prędkości samochodu użytkownika
            setMyCar(data.name); // Ustawienie nazwy samochodu użytkownika
        } catch (error) {
            console.error("Error fetching character:", error);
        }
    };
    const prize = async () => {
        try {
            // Wyślij żądanie POST
            const response = await fetch("http://localhost:8080/user/update-money", {
                method: "POST", // Ustawienie metody POST
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded", // Wskazujemy typ treści
                },
                body: new URLSearchParams({
                    username: username, // Przesyłamy nazwę użytkownika
                    money: "50", // Przesyłamy wartość nagrody (50 jednostek)
                }),
            });

            // Obsługa odpowiedzi
            if (!response.ok) {
                console.error(`HTTP error! Status: ${response.status}`);
                const errorText = await response.text();
                console.error("Server Response:", errorText); // Opcjonalne debugowanie odpowiedzi serwera
                return;
            }

            console.log("Prize successfully awarded!");
        } catch (error) {
            console.error("Error updating prize:", error);
        }
    };



    // Funkcja do pobierania losowego samochodu
    const fetchRandomCar = async () => {
        try {
            const response = await fetch("http://localhost:8080/cars/random");

            if (!response.ok) {
                console.error(`HTTP error! status: ${response.status}`);
                return;
            }

            const data = await response.json();
            setRandomCarSpeed(data.speed); // Ustawienie prędkości losowego samochodu
            setRandomCar(data.name); // Ustawienie nazwy losowego samochodu
        } catch (error) {
            console.error("Error fetching random car:", error);
        }
    };

    // Funkcja, która określa zwycięzcę
    const win = () => {
        if (mySpeed > randomCarSpeed) {
            setWinner("You win!");
            prize();
        } else if (mySpeed < randomCarSpeed) {
            setWinner("You lose!");
        } else {
            setWinner("It's a tie!");
        }
    };

    // Pobieranie danych tylko raz po załadowaniu komponentu
    useEffect(() => {
        fetchData();
        fetchRandomCar();
    }, []);

    // Wywoływanie funkcji win, gdy obie prędkości są ustawione
    useEffect(() => {
        if (mySpeed !== null && randomCarSpeed !== null) {
            win(); // Funkcja zostanie wywołana dopiero, gdy obie prędkości zostaną pobrane
        }
    }, [mySpeed, randomCarSpeed]); // Monitorowanie zmian w mySpeed i randomCarSpeed

    return (
        <div className="racetrack-container">
            <h1>Racetrack</h1>

            {/* Kontener obrazów */}
            <div className="racetrack-images">
                {/* Twoje auto */}
                <Card className="car-card">
                    <CardHeader>
                        <CardTitle>Your Car</CardTitle>
                    </CardHeader>
                    <CardContent>
                        {myCar ? (
                            <img
                                src={carImages[myCar]}
                                alt={myCar}
                                className="car-image"
                            />
                        ) : (
                            <p>Loading your car...</p>
                        )}
                        <p>Speed: {mySpeed}</p>
                    </CardContent>
                </Card>

                {/* Auto przeciwnika */}
                <Card className="car-card">
                    <CardHeader>
                        <CardTitle>Opponent's Car</CardTitle>
                    </CardHeader>
                    <CardContent>
                        {randomCar ? (
                            <img
                                src={carImages[randomCar]}
                                alt={randomCar}
                                className="car-image"
                            />
                        ) : (
                            <p>Loading opponent's car...</p>
                        )}
                        <p>Speed: {randomCarSpeed}</p>
                    </CardContent>
                </Card>
            </div>

            {/* Wynik wyścigu */}
            <h2 className="racetrack-result">{winner}</h2>
            <Button
            onClick={fetchRandomCar}
            >
                Race again
            </Button>
        </div>
    );
};

export default Racetrack;
