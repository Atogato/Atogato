import { PROJECT_GENRES } from '@/app/common/genres'
import Gallery from './Gallery'
import LinkButton from './LinkButton'
export default function ProjectSection() {
  const gneres = ['전체', ...PROJECT_GENRES]
  const images = [
    '/images/sample/projects/project1.png',
    '/images/sample/projects/project2.png',
    '/images/sample/projects/project3.png',
    '/images/sample/projects/project4.png',
    '/images/sample/projects/project5.png',
    '/images/sample/projects/project6.png',
    '/images/sample/projects/project7.png',
    '/images/sample/projects/project8.png',
    '/images/sample/projects/project9.png',
  ]

  return (
    <article className="bg-[#7750E426] px-64 py-16">
      <header>
        <p className="poppins text-2xl font-semibold text-[#7960BE]/70"> Project </p>
        <h1 className="text-5xl font-semibold leading-snug text-[#171616]/30">
          <strong className="font-semibold text-[#171616]">프로젝트</strong>를 탐색해보세요.
        </h1>
      </header>
      <div className="mt-12 flex flex-col gap-8">
        <ul className="flex gap-5">
          {gneres.map((genre, idx) => {
            return (
              <li key={idx}>
                <button className="rounded-[100px] border border-transparent bg-[#1D1B201F] px-6  py-2.5	 hover:border-[#17161680]">
                  {genre}
                </button>
              </li>
            )
          })}
        </ul>
        <Gallery images={images} />
      </div>
      <LinkButton
        link="/project/list"
        circleSize="5rem"
        className="poppins mx-auto block h-[150px] w-[200px]"
        text="View More"
      />
    </article>
  )
}
