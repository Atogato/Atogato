/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,ts,jsx,tsx,mdx}'],
  theme: {
    extend: {
      backgroundImage: {
        'banner-1': "url('/assets/bg-banner-1.png')",
      },
    },
  },
  plugins: [],
}
