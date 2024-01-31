'use client'

export default function LinkButton({ socialType }: { socialType: string }) {
  const oauth_uri = process.env.BACKEND_OAUTH_URL + `${socialType}?redirect_uri=${process.env.OAUTH_REDIRECT_URL}`
  return (
    <a href={oauth_uri} className="border-2 hover:bg-gray-500">
      {socialType}
    </a>
  )
}
