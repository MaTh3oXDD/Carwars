import React, { useState, useEffect } from "react";
import "./market.css";

const Market = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [sortBy, setSortBy] = useState('');
    const [orders, setOrders] = useState([]);
    const [filteredOrders, setFilteredOrders] = useState([]);
    const [message, setMessage] = useState('');
    const username = localStorage.getItem('username');

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

    useEffect(() => {
        fetchData();
    }, []);

    useEffect(() => {
        let updatedOrders = [...orders];

        if (searchTerm.trim() !== '') {
            updatedOrders = updatedOrders.filter(order =>
                order.itemName.toLowerCase().includes(searchTerm.toLowerCase())
            );
        }

        if (sortBy === 'price-asc') {
            updatedOrders.sort((a, b) => a.unitPrice - b.unitPrice);
        } else if (sortBy === 'price-desc') {
            updatedOrders.sort((a, b) => b.unitPrice - a.unitPrice);
        } else if (sortBy === 'name') {
            updatedOrders.sort((a, b) => a.itemName.localeCompare(b.itemName));
        }

        setFilteredOrders(updatedOrders);
    }, [searchTerm, sortBy, orders]);

    const handlePurchase = async (order) => {
        if (!username) {
            setMessage("User not logged in");
            setTimeout(() => setMessage(''), 5000);
            return;
        }
        console.log("Username to send:" + username);
        console.log("itemId to send:" + order.itemId);
        try {
            const requestBody = JSON.stringify({
                username: username,
                itemId: order.itemId,
                quantity: order.quantity,
                unitPrice: order.unitPrice,
            });
            console.log("Request body:", requestBody); // Logowanie request body

            const response = await fetch("http://localhost:8080/market/buy", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: requestBody,
            });

            console.log("Response status:", response.status);
            if (response.ok) {
                try {
                    const responseData = await response.json();
                    setMessage("Purchase successful!");
                    console.log("Response data:", responseData);
                    fetchData();
                } catch(jsonError){
                    console.error("Error parsing json after success:", jsonError)
                    setMessage("Purchase successful but failed to read response");
                }
            } else {
                try {
                    const errorData = await response.json();
                    setMessage(`Purchase failed: ${errorData.error || "Unknown error"}`);
                    console.error("Purchase failed:", errorData); // Zalogowanie błędu z serwera
                } catch(jsonError) {
                    console.error("Error parsing json after failure:", jsonError);
                    setMessage(`Purchase failed: Unable to parse error response.`);
                }

            }
        } catch (error) {
            console.error("Error during purchase:", error);
            setMessage("Purchase failed: Unable to connect to the server.");
        }
        setTimeout(() => setMessage(''), 5000);
    };


    return (
        <div className="search-container">
            <div className="search-controls">
                <input
                    type="text"
                    placeholder="Search by item name"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="search-input"
                />
                <select
                    value={sortBy}
                    onChange={(e) => setSortBy(e.target.value)}
                    className="sort-select"
                >
                    <option value="">Sort by</option>
                    <option value="price-asc">Price: Low to High</option>
                    <option value="price-desc">Price: High to Low</option>
                    <option value="name">Name</option>
                </select>
            </div>
            {message && <div className="status-message">{message}</div>}
            <div className="results-table">
                <div className="table-header">
                    <div className="header-cell">Order ID</div>
                    <div className="header-cell">Item Name</div>
                    <div className="header-cell">Quantity</div>
                    <div className="header-cell">Unit Price</div>
                    <div className="header-cell">Actions</div>
                </div>
                <div className="table-body">
                    {filteredOrders.length > 0 ? (
                        filteredOrders.map((order, index) => (
                            <div key={index} className="table-row">
                                <div className="table-cell">{order.orderId}</div>
                                <div className="table-cell">{order.itemName}</div>
                                <div className="table-cell">{order.quantity}</div>
                                <div className="table-cell">${order.unitPrice.toFixed(2)}</div>
                                <div className="table-cell">
                                    <button
                                        className="buy-button"
                                        onClick={() => handlePurchase(order)}
                                    >
                                        Buy
                                    </button>
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="no-results">No orders found</div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Market;
