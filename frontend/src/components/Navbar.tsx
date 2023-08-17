import Link from 'next/link'
import Logo from '@/logo.svg'

const links = {
  About: '/',
  Project: '/project/list',
  Artist: '/artist/list',
}

type Menu = 'About' | 'Project' | 'Artist'
export function Menu({ content }: { content: Menu }) {
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
  return (
    <nav className="flex flex-wrap items-center border-b border-[#171616]/30 bg-white px-40">
      <Link href="/" className="hidden lg:block">
        <Logo className="mb-[30px] mt-[30px]" width="151px" height="45px" color="#171616" />
      </Link>
      <div className="block w-full flex-grow lg:flex lg:w-auto lg:items-center">
        <div className="flex justify-center gap-24 lg:flex-grow">
          {menus.map((content, idx) => (
            <Menu key={idx} content={content} />
          ))}
        </div>
        <div>
          <Link
            href="/auth/login"
            className="block rounded-[40px]	px-[36px] py-[16px] text-right text-[18px] leading-none text-[#171616]/50 hover:border-transparent hover:bg-[#7960BE]/75 hover:text-white lg:inline-block"
          >
            로그인
          </Link>
        </div>
      </div>
    </nav>
  )
}
