import React, { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";

const Bag = () => {
    const [items, setItems] = useState([]); // Lista przedmiotów w worku
    const [loading, setLoading] = useState(true); // Stan ładowania
    const [error, setError] = useState(null); // Obsługa błędów
    const username = localStorage.getItem("username"); // Pobieramy username z localStorage

    // Funkcja do pobierania przedmiotów
    const fetchBagItems = async () => {
        if (!username) {
            setError("No username found in localStorage");
            setLoading(false);
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/bags/${username}/items`, { method: "GET" });

            if (!response.ok) {
                throw new Error("Failed to fetch bag items");
            }

            const data = await response.json();
            setItems(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    // Funkcja do sprzedaży przedmiotu
    const handleSellItem = async (bagItem) => {
        try {
            const response = await fetch(`http://localhost:8080/bags/sell-item/${bagItem.id}`, { // Użyj bagItem.id
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error("Failed to sell item.");
            }

            alert(`Item "${bagItem.item.name}" sold successfully!`);

            // Usuń element z widoku frontendowego
            setItems((prevItems) => prevItems.filter((i) => i.id !== bagItem.id));
        } catch (err) {
            alert(`Problem while selling item: ${err.message}`);
        }
    };



    // Pobieramy dane po załadowaniu komponentu
    useEffect(() => {
        fetchBagItems();
    }, []);

    return (
        <div className="p-8 max-w-4xl mx-auto">
            <h1 className="text-2xl font-bold mb-4">Items in Your Bag</h1>

            {/* Komponent ładowania */}
            {loading && (
                <div className="flex items-center justify-center">
                    <div className="loader border-t-2 border-b-2 border-gray-900 rounded-full w-6 h-6 animate-spin"></div>
                    <p className="ml-2">Loading items...</p>
                </div>
            )}

            {/* Komponent błędu */}
            {error && (
                <Alert variant="destructive" className="mb-4">
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>{error}</AlertDescription>
                </Alert>
            )}

            {/* Pusta lista */}
            {!loading && !error && items.length === 0 && (
                <div className="text-center text-gray-500">Your bag is empty.</div>
            )}

            {/* Tabela wyników */}
            {!loading && !error && items.length > 0 && (
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>ID</TableHead>
                            <TableHead>Name</TableHead>
                            <TableHead>Weight</TableHead>
                            <TableHead>Actions</TableHead> {/* Kolumna przycisków */}
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {items.map((item) => (
                            <TableRow key={item.id}>
                                <TableCell>{item.id}</TableCell>
                                <TableCell>{item.name}</TableCell>
                                <TableCell>{item.weight}</TableCell>
                                <TableCell>
                                    <Button variant="primary" onClick={() => handleSellItem(item)}>Sell</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>

                </Table>
            )}

            <Button onClick={fetchBagItems} className="mt-4">
                Refresh Items
            </Button>
        </div>
    );
};

export default Bag;
