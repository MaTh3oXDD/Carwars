import './register.css';
import { useState } from "react";
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [passwordRepeat, setPasswordRepeat] = useState('');
    const [gender, setGender] = useState(null);

    const navigate = useNavigate();

    const goToLogin = () => {
        navigate('/');
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Walidacja hasła
        if (password !== passwordRepeat) {
            alert("Hasła muszą być takie same!");
            return;
        }

        // Tworzenie obiektu użytkownika do wysłania
        const userRequest = {
            email: email.trim(),
            username: username.trim(),
            password: password.trim(),
            gender: gender,
            selectedCarId: 1, // Przykład, możesz ustawić domyślną wartość lub pobrać ją z formularza
            bag: {
                capacity: 600
            }
        };

        try {
            const response = await fetch('http://localhost:8080/user/createuser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userRequest),
            });

            if (response.ok) {
                alert('Rejestracja zakończona sukcesem!');
                navigate('/login'); // Przekierowanie na stronę logowania
            } else {
                const error = await response.text();
                alert(`Błąd: ${error}`);
            }
        } catch (err) {
            console.error(err);
            alert('Wystąpił błąd podczas rejestracji.');
        }
    };

    return (
        <div className="register-container">
            <div className="register-form-div">
                <h2>Rejestracja</h2>
                <form onSubmit={handleSubmit} className="register-form">
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
                        <label htmlFor="username">Nazwa użytkownika:</label>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                            placeholder="Wpisz swoją nazwę użytkownika"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Hasło:</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            placeholder="Wpisz swoje hasło"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="passwordRepeat">Powtórz hasło:</label>
                        <input
                            type="password"
                            id="passwordRepeat"
                            value={passwordRepeat}
                            onChange={(e) => setPasswordRepeat(e.target.value)}
                            required
                            placeholder="Wpisz ponownie swoje hasło"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="gender">Wybierz płeć:</label>
                        <div>
                            <label
                                htmlFor="male">Mężczyzna
                            </label>

                            <input className="inputRadio"  type="radio" id="male" name="gender" value={true} checked={gender === true} onChange={() => setGender(true)}/>
                        </div>
                        <div>
                            <label
                                htmlFor="female">Kobieta
                            </label>
                            <input  className="inputRadio" type="radio" id="female" name="gender" value={false} checked={gender === false} onChange={() => setGender(false)}/>

                        </div>
                    </div>
                    <div className="button-div">
                        <button type="button" className="button" onClick={goToLogin}>Logowanie</button>
                        <button type="submit" className="button">Zarejestruj</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Register;
