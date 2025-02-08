import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
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

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const credentials = {
            email: email.trim(),
            password: password.trim(),
        };

        try {
            const response = await fetch('http://localhost:8080/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(credentials),
            });

            if (response.ok) {
                const usernameResponse = await fetch(`http://localhost:8080/get-username?email=${email}`);
                if (usernameResponse.ok) {
                    const username = await usernameResponse.text();
                    localStorage.setItem('username', username);
                }
                navigate('/characterMenu');
            } else {
                const errorText = await response.text();
                setError(`Błąd logowania: ${errorText}`);
            }
        } catch (err) {
            console.error(err);
            setError('Wystąpił błąd podczas logowania.');
        }
    };

    const goToRegister = () => {
        navigate('/register');
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100">
            <Card className="w-full max-w-md">
                <CardHeader>
                    <CardTitle>Logowanie</CardTitle>
                    <CardDescription>Proszę wprowadzić swoje dane</CardDescription>
                </CardHeader>

                <CardContent>
                    {error && (
                        <Alert variant="destructive">
                            <AlertTitle>Błąd</AlertTitle>
                            <AlertDescription>{error}</AlertDescription>
                        </Alert>
                    )}
                    <form onSubmit={handleSubmit}>
                        <div className="space-y-4">
                            <div>
                                <Label htmlFor="email">Email</Label>
                                <Input
                                    type="email"
                                    id="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    placeholder="Wpisz swój e-mail"
                                    required
                                />
                            </div>
                            <div>
                                <Label htmlFor="password">Hasło</Label>
                                <div className="flex items-center space-x-2">
                                    <Input
                                        type={showPassword ? 'text' : 'password'}
                                        id="password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        placeholder="Wpisz swoje hasło"
                                        required
                                    />
                                    <Button
                                        type="button"
                                        variant="secondary"
                                        size="icon"
                                        onClick={() => setShowPassword(!showPassword)}
                                    >
                                        {showPassword ? '🙈' : '👁️'}
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
