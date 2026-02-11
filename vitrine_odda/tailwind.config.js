/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/**/*.{html,ts}",
    ],
    theme: {
        extend: {
            colors: {
                'odd-blue': '#1a367c', // Deep blue from image
                'odd-dark-blue': '#162a64',
                'odd-orange': '#ff7a1a', // Vibrant orange from image
                'odd-gray': '#94a3b8',
            },
            fontFamily: {
                inter: ['Inter', 'sans-serif'],
            },
        },
    },
    plugins: [],
}
