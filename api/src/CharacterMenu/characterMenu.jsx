import React, { useState, useEffect } from "react";
import {
    Card,
    CardHeader,
    CardTitle,
    CardDescription,
    CardContent,
} from "@/components/ui/card";
import { Alert, AlertTitle, AlertDescription } from "@/components/ui/alert";
import maleAvatar from "../assets/img/mechanics/true.png";
import femaleAvatar from "../assets/img/mechanics/false.png";
import "./characterMenu.css";

const CharacterMenu = () => {
    const [username, setUsername] = useState(null); // Stan dla username
    const [gender, setGender] = useState(null); // Stan dla płci
    const [error, setError] = useState(null); // Stan błędu

    // Odczyt `username` z localStorage i pobranie danych o płci
    useEffect(() => {
        const storedUsername = localStorage.getItem("username"); // Pobranie username z localStorage
        console.log("Debug: Odczytano username z localStorage:", storedUsername);

        if (storedUsername && storedUsername.trim() !== "") {
            setUsername(storedUsername.trim()); // Ustaw username w stanie
            fetchGender(storedUsername.trim()); // Pobierz gender na podstawie username
        } else {
            console.warn('Nie znaleziono poprawnego "username" w localStorage.');
            setError("Nie znaleziono poprawnego username.");
        }
    }, []);

    // Funkcja do pobrania płci z backendu
    const fetchGender = async (username) => {
        try {
            console.log(`Debug: Wywołanie API dla username: ${username}`);
            const response = await fetch(
                `http://localhost:8080/user/get-gender?username=${username}`, // Endpoint backendu
            );

            if (response.ok) {
                const isMale = await response.json(); // Backend zwraca `true`/`false`
                console.log("Debug: Wynik gender (true dla męskiego):", isMale);
                setGender(isMale); // Ustaw gender w stanie
            } else {
                console.error(
                    "Błąd podczas pobierania płci, Status HTTP:",
                    response.status,
                );
                setError("Nie udało się pobrać płci użytkownika.");
            }
        } catch (error) {
            console.error("Błąd podczas próby połączenia z backendem:", error);
            setError("Wystąpił problem z połączeniem z serwerem.");
        }
    };

    // Funkcja do wyboru odpowiedniego avatara na podstawie płci
    const getAvatarImage = () => {
        if (gender === true) {
            return maleAvatar; // Avatar męski
        } else if (gender === false) {
            return femaleAvatar; // Avatar żeński
        }
        return null; // Jeśli gender jest `null` (brak danych)
    };

    return (
        <Card className="max-w-md w-full shadow-lg bg-white dark:bg-gray-800 dark:text-white">
            <CardHeader>
                <div className="text-center">
                    <CardTitle>Witaj w Menu Postaci!</CardTitle>
                    <CardDescription>
                        Zarządzaj swoim profilem postaci.
                    </CardDescription>
                </div>
            </CardHeader>

            <CardContent className="flex flex-col items-center space-y-4">
                {/* Wyświetlenie alertu błędu */}
                {error && (
                    <Alert variant="destructive">
                        <AlertTitle>Błąd</AlertTitle>
                        <AlertDescription>{error}</AlertDescription>
                    </Alert>
                )}

                {/* Wyświetlenie avatara */}
                <div>
                    {!error && gender !== null ? (
                        <img
                            src={getAvatarImage()}
                            alt="Avatar"
                            className="rounded-full w-32 h-32"
                        />
                    ) : (
                        <p className="text-gray-600">Ładowanie danych...</p>
                    )}
                </div>

                {/* Wyświetlenie username */}
                <div className="text-center">
                    <h2 className="text-xl font-semibold">
                        {username ? `Witaj, ${username}!` : "Gość"}
                    </h2>
                </div>
            </CardContent>
        </Card>
    );
};

export default CharacterMenu;
