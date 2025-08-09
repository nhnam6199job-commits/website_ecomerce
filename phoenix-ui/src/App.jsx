import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ProtectedRoute from './components/ProtectedRoute';

// Placeholder for a protected page
const DashboardPage = () => {
    const { logout } = useAuth();
    return (
        <div>
            <h1>Welcome to your Dashboard!</h1>
            <p>This page is protected.</p>
            <button onClick={logout}>Logout</button>
        </div>
    );
};

// Placeholder for a public page
const HomePage = () => <h1>Home Page</h1>;

function App() {
    return (
        <AuthProvider>
            <Router>
                <div className="container">
                    <Toaster position="top-right" reverseOrder={false} />
                    <nav>
                        <Link to="/">Home</Link> | <Link to="/login">Login</Link> | <Link to="/register">Register</Link> | <Link to="/dashboard">Dashboard</Link>
                    </nav>
                    <div className="main-content">
                        <Routes>
                            <Route path="/" element={<HomePage />} />
                            <Route path="/login" element={<LoginPage />} />
                            <Route path="/register" element={<RegisterPage />} />
                            <Route
                                path="/dashboard"
                                element={
                                    <ProtectedRoute>
                                        <DashboardPage />
                                    </ProtectedRoute>
                                }
                            />
                        </Routes>
                    </div>
                </div>
            </Router>
        </AuthProvider>
    );
}

export default App;
