import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    Card,
    CardHeader,
    CardTitle,
    CardDescription,
    CardContent,
    CardFooter,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { useTheme } from "@/context/ThemeProvider"; // Importuj hook motywu
import "./login.css";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const { theme, toggleTheme } = useTheme(); // Uzyskaj obecny motyw z hooka

    const handleSubmit = async (event) => {
        event.preventDefault();

        const credentials = {
            email: email.trim(),
            password: password.trim(),
        };

        try {
            // Logowanie użytkownika
            const response = await fetch("http://localhost:8080/user/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(credentials),
            });

            if (response.ok) {
                // Pobieranie username na podstawie e-mail
                const usernameResponse = await fetch(
                    `http://localhost:8080/user/get-userId?email=${email}` // Prefiks "/user"
                );

                if (usernameResponse.ok) {
                    const response = await usernameResponse.json(); // Zapisuje "username"
                    const username = response.username;
                    const userId = response.id;
                    localStorage.setItem("username", username); // Przechowaj w localStorage dla przyszłego użycia
                    localStorage.setItem("userId", userId);
                    console.log("Logged in as:", username);
                }

                navigate("/main"); // Przejdź po pomyślnym zalogowaniu
            } else {
                const errorText = await response.text();
                setError(`Błąd logowania: ${errorText}`);
            }
        } catch (err) {
            console.error(err);
            setError("Wystąpił błąd podczas logowania.");
        }
    };


    const goToRegister = () => {
        navigate("/register"); // Przejście do rejestracji
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100 dark:bg-gray-900">
            <Card className="w-full max-w-md shadow-lg border rounded-md bg-card dark:bg-gray-800 dark:text-white">
                <CardHeader>
                    <div className="flex justify-between items-center">
                        <div>
                            <CardTitle className="text-foreground dark:text-white">Logowanie</CardTitle>
                            <CardDescription className="text-gray-600 dark:text-gray-400">
                                Proszę wprowadzić swoje dane
                            </CardDescription>
                        </div>
                        {/* Przycisk przełączający motyw */}
                        <Button size="sm" variant="outline" onClick={toggleTheme}>
                            {theme === "dark" ? "☀️ Jasny Motyw" : "🌙 Ciemny Motyw"}
                        </Button>
                    </div>
                </CardHeader>

                <CardContent>
                    {error && (
                        <Alert variant="destructive">
                            <AlertTitle className="text-destructive-foreground">Błąd</AlertTitle>
                            <AlertDescription>{error}</AlertDescription>
                        </Alert>
                    )}
                    <form onSubmit={handleSubmit}>
                        <div className="space-y-4">
                            <div className="space-y-1">
                                <Label htmlFor="email" className="text-foreground dark:text-white">
                                    Email
                                </Label>
                                <Input
                                    type="email"
                                    id="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    placeholder="Wpisz swój e-mail"
                                    required
                                    className="bg-white dark:bg-gray-700 dark:text-white"
                                />
                            </div>
                            <div className="space-y-1">
                                <Label htmlFor="password" className="text-foreground dark:text-white">
                                    Hasło
                                </Label>
                                <div className="flex items-center space-x-2">
                                    <Input
                                        type={showPassword ? "text" : "password"}
                                        id="password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        placeholder="Wpisz swoje hasło"
                                        required
                                        className="bg-white dark:bg-gray-700 dark:text-white"
                                    />
                                    <Button
                                        type="button"
                                        variant="outline"
                                        size="icon"
                                        onClick={() => setShowPassword(!showPassword)}
                                        className="dark:bg-gray-800 dark:text-white"
                                    >
                                        {showPassword ? "🙈" : "👁️"}
                                    </Button>
                                </div>
                            </div>
                        </div>
                        <CardFooter className="flex justify-between mt-4">
                            <Button type="submit">Logowanie</Button>
                            <Button variant="outline" onClick={goToRegister}>
                                Rejestracja
                            </Button>
                        </CardFooter>
                    </form>
                </CardContent>
            </Card>

        </div>
    );
};

export default Login;
