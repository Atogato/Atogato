import Slider from './slider/Slider'

export default function RandomSection() {
  return (
    <article>
      <header className="px-64 py-16">
        <p className="poppins text-2xl font-semibold text-[#7960BE]/70"> Random Project </p>
        <h1 className="text-5xl font-semibold leading-snug text-[#171616]/30">
          <strong className="font-semibold text-[#171616]">랜덤으로 프로젝트</strong>를 발견해보세요.
        </h1>
      </header>
      <Slider />
    </article>
  )
}
