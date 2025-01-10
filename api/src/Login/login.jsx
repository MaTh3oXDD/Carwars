import './login.css';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
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
                    console.log('Zapisywanie do localStorage:', username); // Debugowanie
                    localStorage.setItem('username', username);
                }
                navigate('/characterMenu');
            } else {
                const error = await response.text();
                alert(`Błąd logowania: ${error}`);
            }
        } catch (err) {
            console.error(err);
            alert('Wystąpił błąd podczas logowania.');
        }
    };

    const goToRegister = () => {
        navigate('/register');
    };

    return (
        <div className="login-container">
            <div className="login-form-div">
                <h2>Logowanie</h2>
                <form onSubmit={handleSubmit} className="login-form">
                    <div className="form-group">
                        <label htmlFor="email">Email:</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            placeholder="Wpisz swój e-mail"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Hasło:</label>
                        <div className="password-div">
                            <input
                                type={showPassword ? 'text' : 'password'}
                                id="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                placeholder="Wpisz swoje hasło"
                            />
                            <button
                                type="button"
                                className="password-icon"
                                onClick={() => setShowPassword(!showPassword)}
                            >
                                {showPassword ? '🙈' : '👁️'}
                            </button>
                        </div>
                    </div>
                    <div className="button-div">
                        <button type="submit" className="button">Logowanie</button>
                        <button type="button" className="button" onClick={goToRegister}>Rejestracja</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;
