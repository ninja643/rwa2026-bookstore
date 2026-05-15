document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);

    if (params.get('error') === 'true') {
        document.getElementById('errorMessage').classList.remove('hidden');
    }

    if (params.get('logout') === 'true') {
        document.getElementById('logoutMessage').classList.remove('hidden');
    }
});
