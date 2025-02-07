import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

// shadcn/ui components
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Card, CardHeader, CardTitle, CardContent, CardFooter } from "@/components/ui/card";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();

    // Obsługa logowania
    const handleSubmit = async (event) => {
        event.preventDefault();

        const credentials = {
            email: email.trim(),
            password: password.trim(),
        };

        try {
            const response = await fetch("http://localhost:8080/user/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(credentials),
            });

            if (response.ok) {
                // Pobierz nazwę użytkownika
                const usernameResponse = await fetch(`http://localhost:8080/get-username?email=${email}`);
                if (usernameResponse.ok) {
                    const username = await usernameResponse.text();
                    localStorage.setItem("username", username); // Zapisz nazwę użytkownika do localStorage
                }
                navigate("/characterMenu"); // Przekierowanie
            } else {
                const error = await response.text();
                alert(`Błąd logowania: ${error}`);
            }
        } catch (err) {
            console.error(err);
            alert("Wystąpił błąd podczas logowania.");
        }
    };

    // Przejście do rejestracji
    const goToRegister = () => {
        navigate("/register");
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-50">
            <Card className="w-[400px] shadow-md">
                <CardHeader>
                    <CardTitle>Logowanie</CardTitle>
                </CardHeader>

                <CardContent>
                    <form onSubmit={handleSubmit} className="space-y-4">
                        {/* Pole email */}
                        <div>
                            <Label htmlFor="email">Email</Label>
                            <Input
                                type="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                                placeholder="Wpisz swój e-mail"
                                className="mt-1"
                            />
                        </div>

                        {/* Pole hasła */}
                        <div>
                            <Label htmlFor="password">Hasło</Label>
                            <div className="flex items-center gap-2 mt-1">
                                <Input
                                    type={showPassword ? "text" : "password"}
                                    id="password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                    placeholder="Wpisz swoje hasło"
                                />
                                <Button
                                    type="button"
                                    variant="outline"
                                    size="icon"
                                    onClick={() => setShowPassword(!showPassword)}
                                >
                                    {showPassword ? "🙈" : "👁️"}
                                </Button>
                            </div>
                        </div>
                    </form>
                </CardContent>

                <CardFooter className="flex flex-col space-y-2">
                    <Button type="submit" onClick={handleSubmit} className="w-full">
                        Zaloguj się
                    </Button>
                    <Button variant="outline" onClick={goToRegister} className="w-full">
                        Rejestracja
                    </Button>
                </CardFooter>
            </Card>
        </div>
    );
};

export default Login;
