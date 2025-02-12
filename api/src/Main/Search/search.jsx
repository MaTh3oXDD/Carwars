import React, { useState, useEffect } from "react";
import Shop from "../../assets/img/search/shop.jpg";
import Car from "../../assets/img/search/car.jpg";
import Nothing from "../../assets/img/search/nothing.jpg";
import {
    Card,
    CardHeader,
    CardContent,
    CardFooter,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import "./search.css"; // Tailwind albo własne style dla shadcn-ui

const Search = () => {
    const [search, setSearch] = useState("");
    const [imageName, setImageName] = useState("");
    const [loading, setLoading] = useState(false); // Dodany stan dla ładowania

    const newSearch = async () => {
        setLoading(true); // Pokazuje loader na początku

        try {
            const response = await fetch("http://localhost:8080/search/names", {
                method: "GET",
            });

            if (response.ok) {
                const data = await response.text();
                console.log("Otrzymane dane:", data);
                setImageName(data);
                setSearch(data); // Ustawienie stanu na podstawie odpowiedzi serwera
            } else {
                console.error("Błąd podczas wyszukiwania:", response.status);
            }
        } catch (error) {
            console.error("Błąd:", error);
        } finally {
            setLoading(false); // Ukryj loader po zakończeniu zapytania
        }
    };

    const getSearch = () => {
        if (search === "shop") {
            return Shop;
        } else if (search === "car") {
            return Car;
        } else {
            return Nothing;
        }
    };

    useEffect(() => {
        newSearch();
    }, []);

    return (
            <Card className="w-96 shadow-md">
                <CardHeader>
                    <h2 className="text-lg font-semibold">
                        Wyszukiwarka
                    </h2>
                </CardHeader>
                <CardContent>
                    <div className="flex items-center justify-center p-4 h-[200px]">
                        {/* Pokazuje loader podczas ładowania */}
                        {loading ? (
                            <div className="animate-spin rounded-full h-10 w-10 border-t-4 border-blue-500 border-solid"></div>
                        ) : (
                            <img
                                src={getSearch()}
                                alt={imageName || "Brak danych"}
                                className="max-w-full h-auto rounded-md"
                            />
                        )}
                    </div>
                </CardContent>
                <CardFooter>
                    <Button
                        onClick={newSearch}
                        className="w-full mt-2 border-1 border-white bg-gray-800 text-white shadow-md hover:border-yellow-500 hover:text-yellow-500 transition-colors duration-200"
                    >
                        Wyszukaj ponownie
                    </Button>


                </CardFooter>
            </Card>

    );
};

export default Search;
