'use client'

import useIsLogin from '@/hooks/auth/useIsLogin'
import { useEffect } from 'react'
import { useRouter } from 'next/navigation'
export default function LoginContainer({ children }: { children: JSX.Element[] }) {
  // TODO: login 여부에 따른 RouteGuard 제작(페이지에 진입하기 전에 로그인 여부를 판단)
  const { isLogin } = useIsLogin()
  const router = useRouter()

  useEffect(() => {
    if (isLogin) {
      router.replace('/project/list')
    }
  }, [isLogin])
  return <div className="relative">{children}</div>
}
