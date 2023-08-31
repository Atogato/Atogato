'use client'

import Link from 'next/link'
import Logo from '@/logo.svg'
import { useEffect } from 'react'

const links = {
  About: '/',
  Project: '/project/list',
  Artist: '/artist/list',
}

type Menu = 'About' | 'Project' | 'Artist'

export function MenuItem({ content }: { content: Menu }) {
  const link = links[content]

  if (!link) {
    return <></>
  }

  return (
    <Link
      href={link}
      className="poppins mr-4 mt-4 block text-[#171616]/50 hover:text-[#171616] hover:opacity-100 lg:mt-0 lg:inline-block"
    >
      {content}
    </Link>
  )
}

export default function Navbar() {
  const menus: Menu[] = ['About', 'Project', 'Artist']

  function scrollHandler() {
    if (document) {
      const navBar = document.querySelector('#navigation')
      const navBarContainer = document.querySelector('#navigation-container')
      if (!navBar || !navBarContainer) {
        return
      }

      if (window.scrollY > 0) {
        navBar.classList.add('mt-6')

        navBarContainer.classList.remove('border-b', 'w-full')
        navBarContainer.classList.add(
          'rounded',
          'w-[90%]',
          'z-50',
          'rounded-full',
          'mx-auto',
          'shadow-[0_4px_24px_0_rgba(154,136,206,0.3)]',
        )
      } else {
        navBar.classList.remove('mt-6')

        navBarContainer.classList.remove(
          'rounded',
          'z-50',
          'rounded-full',
          'w-[90%]',
          'mx-auto',
          'shadow-[0_4px_24px_0_rgba(154,136,206,0.3)]',
        )
        navBarContainer.classList.add('border-b', 'w-full')
      }
    }
  }
  useEffect(() => {
    window.addEventListener('scroll', scrollHandler)
    return () => {
      window.removeEventListener('scroll', scrollHandler)
    }
  }, [])

  return (
    <nav id="navigation" className="fixed z-50 flex h-[100px] w-full min-w-[1290px] max-w-[1920px] justify-center">
      <div
        id="navigation-container"
        className="flex w-full flex-wrap items-center border-b border-[#171616]/30 bg-white px-36 duration-300 ease-in-out"
      >
        <Link href="/" className="hidden lg:block">
          <Logo className="mb-[30px] mt-[30px]" width="151px" height="45px" color="#171616" />
        </Link>
        <div className="block flex flex-grow items-center">
          <div className="flex flex-grow justify-center gap-24">
            {menus.map((content, idx) => (
              <MenuItem key={idx} content={content} />
            ))}
          </div>
          <div className="rounded-[40px] px-[36px] py-[16px] text-right text-[15px] leading-none text-[#171616]/50 hover:border-transparent hover:bg-[#7960BE]/75 hover:text-white">
            <Link href="/auth/login">로그인</Link>
          </div>
        </div>
      </div>
    </nav>
  )
}
