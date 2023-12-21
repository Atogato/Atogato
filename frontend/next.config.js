/** @type {import('next').NextConfig} */
const nextConfig = {
  webpack(config) {
    config.module.rules.push({
      test: /\.svg$/i,
      use: ['@svgr/webpack'],
    })
    return config
  },
  env: {
    BACKEND_API_URL: process.env.BACKEND_API_URL,
    BACKEND_OAUTH_URL: process.env.BACKEND_OAUTH_URL,
    OAUTH_REDIRECT_URL: process.env.OAUTH_REDIRECT_URL,
  },
}

module.exports = nextConfig
