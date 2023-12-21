/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,ts,jsx,tsx,mdx}'],
  theme: {
    extend: {
      backgroundImage: {
        'banner-1': "url('/assets/bg-banner-1.png')",
      },
      keyframes: {
        scroll: {
          '0%': { transform: 'translateX(-300px)' },
          '100%': { transform: 'translateX(calc(-300px * 10.6))' },
        },
      },
    },
  },
  plugins: [],
}
