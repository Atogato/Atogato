'use client'

export default function LinkButton({ socialType }: { socialType: string }) {
  const oauth_uri = process.env.BACKEND_OAUTH_URL + `${socialType}?redirect_uri=http://localhost:3000/auth/login/oauth`
  return (
    <a href={oauth_uri} className="border-2 hover:bg-gray-500">
      {socialType}
    </a>
  )
}
