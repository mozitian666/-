const API_BASE = '/api';

async function fetchJson(url, options = {}) {
    const response = await fetch(url, options);
    if (response.status === 401 || response.status === 403) {
        window.location.href = 'login.html';
        return;
    }
    const contentType = response.headers.get("content-type");
    if (contentType && contentType.indexOf("application/json") !== -1) {
        const data = await response.json();
        if (!response.ok) {
            throw new Error(data.message || JSON.stringify(data) || 'API Error');
        }
        return data;
    } else {
        const text = await response.text();
        if (!response.ok) {
            throw new Error(text || 'API Error');
        }
        return text;
    }
}

async function login(username, password) {
    const formData = new URLSearchParams();
    formData.append('username', username);
    formData.append('password', password);
    
    const response = await fetch(API_BASE + '/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData
    });
    
    if (response.ok) {
        return true;
    } else {
        throw new Error('Invalid credentials');
    }
}

async function register(user) {
    return fetchJson(API_BASE + '/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    });
}

async function logout() {
    await fetch(API_BASE + '/auth/logout', { method: 'POST' });
    window.location.href = 'login.html';
}

async function getCurrentUser() {
    return fetchJson(API_BASE + '/auth/me');
}