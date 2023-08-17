import Image from 'next/image'
import Link from 'next/link'
import Ellipse from '@/assets/ellipse.svg'

function LinkButton({ className, text }: { className?: string; text?: string }) {
  return (
    <Link href="/artist/form" className={`${className || ''}`}>
      <div className="relative flex items-center">
        <div className="absolute w-[30%]">
          <b className="ps-1 text-[#171616]/80"> {text || '텍스트를 입력해주세요.'} </b>
          <div className="mt-1 h-[10px] w-full skew-x-[-45deg] border-r border-t border-black bg-transparent "></div>
        </div>
        <Ellipse width="7rem" height="7rem" className="absolute start-24" />
      </div>
    </Link>
  )
}

export default function Banner({ className }: { className?: string }) {
  const customClassName = `bg-banner-1 bg-cover bg-center ${className}`

  return (
    <div className={customClassName}>
      <div className="flex w-full	justify-center gap-4 px-[100px] py-[140px]">
        <div className="flex w-[45%] flex-col flex-wrap	gap-48">
          <div>
            <p className="poppins text-2xl font-semibold text-[#7960BE]/70"> Atogato Artist </p>
            <p className="text-5xl font-semibold leading-snug text-[#171616]/30">
              <strong className="poppins font-semibold text-[#171616]"> Atogato </strong>와 함께 할 아티스트를 찾습니다.
            </p>
          </div>
          <LinkButton />
        </div>
        <div className="grow">
          <Image src="/intro.png" alt="intro" width={400} height={400} className="h-full	w-full object-cover"></Image>
        </div>
      </div>
    </div>
  )
}
