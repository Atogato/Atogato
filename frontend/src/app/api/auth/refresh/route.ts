import { NextRequest, NextResponse } from 'next/server'
import { cookies } from 'next/headers'
const BACKEND_API = process.env.BACKEND_API_URL + 'v1/auth/refresh'

export async function GET(request: NextRequest) {
  const REFRESH_TOKEN = cookies().get('refresh_token')
  const ACCESS_TOKEN = request.headers.get('Authorization')?.split(' ')[1]
  console.log('refresh token: ', REFRESH_TOKEN)
  console.log('access token: ', ACCESS_TOKEN)

  if (!REFRESH_TOKEN) {
    return new Response('no refresh token in cookies', { status: 401 })
  }

  const res = await fetch(BACKEND_API, {
    headers: {
      Authorization: `Bearer ${ACCESS_TOKEN}`,
      Cookie: `refresh_token=${REFRESH_TOKEN.value}`,
      credentials: 'include',
    },
    cache: 'no-store',
  })
  const refreshedToken = await res.json()
  console.log('new token: ', refreshedToken)
  if (!refreshedToken || !refreshedToken.body) {
    return new Response('refresh token failed')
  }
  const { token } = refreshedToken.body
  if (!token) {
    return new Response('no refresh token returned', { status: 500 })
  }
  cookies().set('refresh_token', token)
  console.log(token)
  return NextResponse.json({ status: 200, message: 'new token published', token })
}
