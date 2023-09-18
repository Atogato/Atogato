import Link from 'next/link'
import Ellipse from '@/assets/ellipse.svg'

export default function LinkButton({
  className,
  text,
  link = '/',
  circleSize = '6rem',
}: {
  className?: string
  text?: string
  link?: string
  circleSize?: '6rem' | '5rem' | '4rem'
}) {
  return (
    <Link href={link} className={`${className || ''}`}>
      <div className="relative flex h-full w-full items-center">
        <div className="absolute w-fit">
          <p className="whitespace-nowrap ps-1 text-[#171616]/80  hover:text-[#7960BE]">
            {text || '텍스트를 입력해주세요.'}
          </p>
          <div className="mt-1 h-[10px] w-full skew-x-[-45deg] border-r border-t border-black bg-transparent "></div>
        </div>
        <Ellipse width={circleSize} height={circleSize} className="absolute start-[23%]" />
      </div>
    </Link>
  )
}
