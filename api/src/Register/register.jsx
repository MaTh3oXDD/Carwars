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
import { useTheme } from "@/context/ThemeProvider"; // Import hook useTheme

const Register = () => {
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [gender, setGender] = useState(null);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const navigate = useNavigate();
    const { theme, toggleTheme } = useTheme(); // Używamy hooka useTheme

    const goToLogin = () => {
        navigate("/");
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError("");

        if (password !== passwordRepeat) {
            setError("Hasła muszą być takie same!");
            return;
        }

        const userRequest = {
            email: email.trim(),
            username: username.trim(),
            password: password.trim(),
            gender: gender,
            selectedCarId: 1,
            bag: { capacity: 600 },
        };

        try {
            const response = await fetch("http://localhost:8080/user/createuser", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(userRequest),
            });

            if (response.ok) {
                setSuccess("Rejestracja zakończona sukcesem!");
                navigate("/login");
            } else {
                const error = await response.text();
                setError(`Błąd: ${error}`);
            }
        } catch (err) {
            console.error(err);
            setError("Wystąpił błąd podczas rejestracji.");
        }
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100 dark:bg-gray-900">
            <Card className="w-full max-w-md shadow-lg border rounded-md bg-white dark:bg-gray-800 dark:text-white">
                <CardHeader>
                    <div className="flex justify-between items-center">
                        <CardTitle>Rejestracja</CardTitle>
                        <Button size="sm" variant="outline" onClick={toggleTheme}>
                            {theme === "dark" ? "☀️ Jasny Motyw" : "🌙 Ciemny Motyw"}
                        </Button>
                    </div>
                    <CardDescription>Wypełnij poniższe pola, aby założyć konto</CardDescription>
                </CardHeader>
                <CardContent className="space-y-4">
                    {error && (
                        <Alert variant="destructive">
                            <AlertTitle>Błąd</AlertTitle>
                            <AlertDescription>{error}</AlertDescription>
                        </Alert>
                    )}
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div className="space-y-1">
                            <Label htmlFor="email">Email:</Label>
                            <Input
                                type="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                placeholder="Wpisz swój e-mail"
                                required
                            />
                        </div>
                        <div className="space-y-1">
                            <Label htmlFor="username">Nazwa użytkownika:</Label>
                            <Input
                                type="text"
                                id="username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                placeholder="Wpisz swoją nazwę użytkownika"
                                required
                            />
                        </div>
                        <div className="space-y-1">
                            <Label htmlFor="password">Hasło:</Label>
                            <Input
                                type="password"
                                id="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                placeholder="Wpisz swoje hasło"
                                required
                            />
                        </div>
                        <div className="space-y-1">
                            <Label htmlFor="passwordRepeat">Powtórz hasło:</Label>
                            <Input
                                type="password"
                                id="passwordRepeat"
                                value={passwordRepeat}
                                onChange={(e) => setPasswordRepeat(e.target.value)}
                                placeholder="Wpisz ponownie swoje hasło"
                                required
                            />
                        </div>
                        <CardFooter className="flex justify-between">
                            <Button type="button" variant="outline" onClick={goToLogin}>
                                Logowanie
                            </Button>
                            <Button type="submit">Zarejestruj</Button>
                        </CardFooter>
                    </form>
                </CardContent>
            </Card>
        </div>
    );
};

export default Register;
