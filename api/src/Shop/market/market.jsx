import React, { useState, useEffect } from "react";
import {
    Select,
    SelectTrigger,
    SelectValue,
    SelectContent,
    SelectItem,
} from "@/components/ui/select"; // Radix Select + ShadCN wrapper
import {
    Table,
    TableHeader,
    TableRow,
    TableHead,
    TableBody,
    TableCell,
} from "@/components/ui/table"; // ShadCN UI Table
import { Input } from "@/components/ui/input"; // ShadCN UI Input
import { Button } from "@/components/ui/button"; // ShadCN UI Button
import { Card } from "@/components/ui/card"; // ShadCN UI Card for layout

const Market = () => {
    const [searchTerm, setSearchTerm] = useState(""); // Search term
    const [sortBy, setSortBy] = useState("none"); // Selected sort option
    const [orders, setOrders] = useState([]); // Original orders
    const [filteredOrders, setFilteredOrders] = useState([]); // Filtered and sorted orders
    const [message, setMessage] = useState(""); // Feedback message to the user
    const username = localStorage.getItem("username"); // Get username from localStorage

    // Sort options
    const sortOptions = [
        { value: "none", label: "None" },
        { value: "price-asc", label: "Price: Low to High" },
        { value: "price-desc", label: "Price: High to Low" },
        { value: "name", label: "Name" },
    ];

    // Fetch orders from backend
    const fetchData = async () => {
        try {
            const response = await fetch("http://localhost:8080/orders/summaries");
            const data = await response.json();
            setOrders(data);
            setFilteredOrders(data);
        } catch (error) {
            console.error("Error fetching orders:", error);
        }
    };

    // Fetch data when the component mounts
    useEffect(() => {
        fetchData();
    }, []);

    // Update filtered orders when search term, sort option, or orders change
    useEffect(() => {
        let updatedOrders = [...orders];

        // Filter by search term
        if (searchTerm.trim() !== "") {
            updatedOrders = updatedOrders.filter((order) =>
                order.itemName.toLowerCase().includes(searchTerm.toLowerCase())
            );
        }

        // Apply sorting
        if (sortBy === "price-asc") {
            updatedOrders.sort((a, b) => a.unitPrice - b.unitPrice);
        } else if (sortBy === "price-desc") {
            updatedOrders.sort((a, b) => b.unitPrice - a.unitPrice);
        } else if (sortBy === "name") {
            updatedOrders.sort((a, b) => a.itemName.localeCompare(b.itemName));
        }

        setFilteredOrders(updatedOrders);
    }, [searchTerm, sortBy, orders]);

    // Handle purchase action
    const handlePurchase = async (order) => {
        if (!username) {
            setMessage("User not logged in");
            setTimeout(() => setMessage(""), 5000);
            return;
        }

        try {
            const requestBody = JSON.stringify({
                username,
                itemId: order.itemId,
                quantity: order.quantity,
                unitPrice: order.unitPrice,
            });

            const response = await fetch("http://localhost:8080/market/buy", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: requestBody,
            });

            if (response.ok) {
                setMessage("Purchase successful!");
                fetchData();
            } else {
                const errorData = await response.json();
                setMessage(`Purchase failed: ${errorData.error || "Unknown error"}`);
            }
        } catch (error) {
            console.error("Error during purchase:", error);
            setMessage("Purchase failed: Unable to connect to the server.");
        }
        setTimeout(() => setMessage(""), 5000);
    };

    return (
        <Card className="p-8 bg-gray-100 dark:bg-gray-800 rounded-lg shadow-md">
            {/* Controls Section: Search and Sort */}
            <div className="mb-5 flex flex-col md:flex-row items-center justify-between gap-4">
                {/* Search Input */}
                <Input
                    placeholder="Search by item name"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="w-full md:w-1/2"
                />

                {/* Sort Dropdown */}
                <Select onValueChange={(value) => setSortBy(value)} className="w-full md:w-1/3">
                    <SelectTrigger>
                        <SelectValue placeholder="Sort by" />
                    </SelectTrigger>
                    <SelectContent>
                        {sortOptions.map((option) => (
                            <SelectItem key={option.value} value={option.value}>
                                {option.label}
                            </SelectItem>
                        ))}
                    </SelectContent>
                </Select>
            </div>

            {/* Feedback Message */}
            {message && (
                <div className="mb-4 p-3 rounded-md bg-blue-100 text-blue-700 dark:bg-blue-900 dark:text-blue-200">
                    {message}
                </div>
            )}

            {/* Orders Table */}
            <div className="overflow-x-auto">
                <Table className="w-full text-sm">
                    <TableHeader className="bg-gray-200 dark:bg-gray-700">
                        <TableRow>
                            <TableHead className="p-4 text-left">Order ID</TableHead>
                            <TableHead className="p-4 text-left">Item Name</TableHead>
                            <TableHead className="p-4 text-left">Quantity</TableHead>
                            <TableHead className="p-4 text-left">Unit Price</TableHead>
                            <TableHead className="p-4 text-left">Actions</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {filteredOrders.length > 0 ? (
                            filteredOrders.map((order, index) => (
                                <TableRow
                                    key={order.orderId}
                                    className={
                                        index % 2 === 0
                                            ? "bg-gray-100 dark:bg-gray-700"
                                            : "bg-white dark:bg-gray-800"
                                    }
                                >
                                    <TableCell className="p-4">{order.orderId}</TableCell>
                                    <TableCell className="p-4">{order.itemName}</TableCell>
                                    <TableCell className="p-4">{order.quantity}</TableCell>
                                    <TableCell className="p-4">${order.unitPrice.toFixed(2)}</TableCell>
                                    <TableCell className="p-4">
                                        <Button
                                            variant="primary"
                                            size="sm"
                                            onClick={() => handlePurchase(order)}
                                        >
                                            Buy
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan="5" className="p-4 text-center">
                                    No orders found
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </div>
        </Card>
    );
};

export default Market;
