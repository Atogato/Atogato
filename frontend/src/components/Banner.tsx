import Image from 'next/image'
import LinkButton from './LinkButton'

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
          <LinkButton link="/artist/form" className="w-[450px]" />
        </div>
        <div className="grow">
          <Image src="/intro.png" alt="intro" width={400} height={400} className="h-full	w-full object-cover"></Image>
        </div>
      </div>
    </div>
  )
}
