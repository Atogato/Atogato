'use client'

import { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { localStorage } from '@/app/storage'
const REDIRECT_URL = '/project/list'

export default function Redirect({ token }: { token?: string }) {
  const router = useRouter()
  useEffect(() => {
    if (typeof window != undefined && token !== undefined) {
      localStorage.setItem('token', token)
      router.replace(REDIRECT_URL)
    }
  }, [])

  return <></>
}
