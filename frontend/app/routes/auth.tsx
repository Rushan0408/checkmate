import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router';
import { useAuthStore } from '~/store/auth-store';

const Auth = () => {
  const [searchParams] = useSearchParams();
  const loginPage = searchParams.get('loginPage');

  const { setJwt } = useAuthStore();
  const navigate = useNavigate();

  const [login, setLogin] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [password2, setPassword2] = useState('');
  const [passwordError, setPasswordError] = useState('');

  useEffect(() => {
    setLogin(loginPage === 'true');
  }, []);

  const sendAuthRequest = async (url: string) => {
    try {
      const res = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });

      if (!res.ok) throw new Error(await res.text());

      const data = await res.json();
      if (data.jwt) {
        setJwt(data.jwt);
        navigate('/');
      }
    } catch (err) {
      console.error('Auth error:', err);
    }
  };

  const handleLoginSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    sendAuthRequest('http://localhost:8080/auth/login');
  };

  const handleSignUpSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (password !== password2) {
      setPasswordError('Passwords do not match');
      return;
    }

    setPasswordError('');
    sendAuthRequest('http://localhost:8080/auth/signup');
    setLogin(true);
  };

  return (
    <div className="min-h-screen bg-[#302e2b] flex items-center justify-center p-4">
      <div className="w-full max-w-md bg-gray-800/50 rounded-2xl border border-gray-700/50 p-8">
        <h1 className="text-3xl font-bold text-white text-center mb-2">
          {login ? 'Welcome Back' : 'Create Account'}
        </h1>
        <p className="text-gray-400 text-sm text-center mb-8">
          {login ? 'Sign in to continue' : 'Sign up to get started'}
        </p>

        <form
          onSubmit={login ? handleLoginSubmit : handleSignUpSubmit}
          className="space-y-6"
        >
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="w-full px-4 py-3 bg-gray-900/50 border border-gray-600 rounded-lg text-white"
            required
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
              setPasswordError('');
            }}
            className="w-full px-4 py-3 bg-gray-900/50 border border-gray-600 rounded-lg text-white"
            required
          />

          {!login && (
            <input
              type="password"
              placeholder="Confirm Password"
              value={password2}
              onChange={(e) => {
                setPassword2(e.target.value);
                setPasswordError('');
              }}
              className="w-full px-4 py-3 bg-gray-900/50 border border-gray-600 rounded-lg text-white"
              required
            />
          )}

          {!login && passwordError && (
            <p className="text-sm text-red-400 text-center">
              {passwordError}
            </p>
          )}

          <button
            type="submit"
            className="w-full py-3 bg-blue-600 hover:bg-blue-700 rounded-lg text-white font-semibold"
          >
            {login ? 'Sign In' : 'Sign Up'}
          </button>
        </form>

        <p className="text-gray-400 text-sm text-center mt-6">
          {login ? "Don't have an account?" : 'Already have an account?'}
          <button
            type="button"
            onClick={() => setLogin((p) => !p)}
            className="ml-2 text-blue-400 hover:text-blue-300 font-semibold"
          >
            {login ? 'Sign Up' : 'Sign In'}
          </button>
        </p>
      </div>
    </div>
  );
};

export default Auth;
