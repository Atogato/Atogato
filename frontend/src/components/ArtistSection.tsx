'use client'
import { PROJECT_GENRES } from '@/app/common/genres'
import LinkButton from './LinkButton'
import ArtistGallery from './ArtistGallery'
import { useState } from 'react'
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
  const [selectedOptions, setSelectedOptions] = useState<string[]>(['All', 'All'])
  const [isActive, setIsActive] = useState([false, false])

  const handleOptionSelect = (filterIndex: number, option: string) => {
    setSelectedOptions((prevOptions) => {
      const updatedOptions = [...prevOptions]
      updatedOptions[filterIndex] = option
      return updatedOptions
    })
  }

  const handleToggle = (index: number) => {
    setIsActive((prevIsActive) => {
      const updatedIsActive = [...prevIsActive]
      updatedIsActive[index] = !updatedIsActive[index]
      console.log(selectedOptions)

      return updatedIsActive
    })
  }
  return (
    <article className="bg-white px-64 py-16">
      <header>
        <p className="poppins text-2xl font-semibold text-[#7960BE]/70"> Artist </p>
        <h1 className="text-5xl font-semibold leading-snug text-[#171616]/30">
          <strong className="font-semibold text-[#171616]">아티스트</strong>를 소개해보세요
        </h1>
      </header>
      <div className="mt-12 flex flex-col gap-8">
        <div className="z-20 flex gap-2">
          <button
            onClick={() => {
              handleToggle(0)
            }}
            className="flex h-[56px] w-[200px] rounded-md border-2 border-gray-300 px-4 py-[12.5px] text-gray-800"
          >
            <div className="mt-[2px] text-[16px] text-gray-600">지역</div>
            <div className="ml-[110px]">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                className="h-6 w-6"
              >
                <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 8.25l-7.5 7.5-7.5-7.5" />
              </svg>
            </div>
          </button>
          {isActive[0] && (
            <div className="absolute mt-[60px] w-[200px] rounded-md bg-white text-center shadow-lg">
              <ul className="py-2">
                <li
                  onClick={() => handleOptionSelect(0, 'All')}
                  className="flex w-full gap-[16px] rounded-r-lg px-[12px] py-2 hover:bg-[#ECEAF3]"
                >
                  <div>
                    <label>
                      <input type="checkbox" className="accent-[#7960BE]" />
                    </label>
                  </div>
                  <div>All</div>
                </li>
                <li
                  onClick={() => handleOptionSelect(0, '서울')}
                  className="flex w-full gap-[16px] rounded-r-lg px-[12px] py-2 hover:bg-[#ECEAF3]"
                >
                  <div>
                    <label>
                      <input type="checkbox" className="accent-[#7960BE]" />
                    </label>
                  </div>
                  <div>서울</div>
                </li>
                <li
                  onClick={() => handleOptionSelect(0, '경기')}
                  className="flex w-full gap-[16px] rounded-r-lg px-[12px] py-2 hover:bg-[#ECEAF3]"
                >
                  <div>
                    <label>
                      <input type="checkbox" className="accent-[#7960BE]" />
                    </label>
                  </div>
                  <div>경기</div>
                </li>
                <li
                  onClick={() => handleOptionSelect(0, '부산')}
                  className="flex w-full gap-[16px] rounded-r-lg px-[12px] py-2 hover:bg-[#ECEAF3]"
                >
                  <div>
                    <label>
                      <input type="checkbox" className="accent-[#7960BE]" />
                    </label>
                  </div>
                  <div>부산</div>
                </li>
              </ul>
            </div>
          )}
          <button
            onClick={() => {
              handleToggle(1)
            }}
            className="flex h-[56px] w-[200px] rounded-md border-2 border-gray-300 px-4 py-[12.5px] text-gray-800"
          >
            <div className="mt-[2px] text-[16px] text-gray-600">장르</div>
            <div className="ml-[110px]">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                className="h-6 w-6"
              >
                <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 8.25l-7.5 7.5-7.5-7.5" />
              </svg>
            </div>
          </button>
          {isActive[1] && (
            <div className="absolute ml-[210px] mt-[60px] w-[200px] rounded-md bg-white text-center shadow-lg">
              <ul className="py-2">
                <li
                  onClick={() => handleOptionSelect(1, 'All')}
                  className="flex w-full gap-[16px] rounded-r-lg px-[12px] py-2 hover:bg-[#ECEAF3]"
                >
                  <div>
                    <label>
                      <input type="checkbox" className="accent-[#7960BE]" />
                    </label>
                  </div>
                  <div>All</div>
                </li>
                <li
                  onClick={() => handleOptionSelect(1, '서울')}
                  className="flex w-full gap-[16px] rounded-r-lg px-[12px] py-2 hover:bg-[#ECEAF3]"
                >
                  <div>
                    <label>
                      <input type="checkbox" className="accent-[#7960BE]" />
                    </label>
                  </div>
                  <div>option 1</div>
                </li>
                <li
                  onClick={() => handleOptionSelect(1, '경기')}
                  className="flex w-full gap-[16px] rounded-r-lg px-[12px] py-2 hover:bg-[#ECEAF3]"
                >
                  <div>
                    <label>
                      <input type="checkbox" className="accent-[#7960BE]" />
                    </label>
                  </div>
                  <div>option 2</div>
                </li>
                <li
                  onClick={() => handleOptionSelect(1, '부산')}
                  className="flex w-full gap-[16px] rounded-r-lg px-[12px] py-2 hover:bg-[#ECEAF3]"
                >
                  <div>
                    <label>
                      <input type="checkbox" className="accent-[#7960BE]" />
                    </label>
                  </div>
                  <div>option 3</div>
                </li>
              </ul>
            </div>
          )}
        </div>
        <div className="z-10">
          <ArtistGallery images={images} />
        </div>
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
