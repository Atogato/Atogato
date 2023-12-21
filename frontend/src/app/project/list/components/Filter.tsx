'use client'
import { useState } from 'react'
import Filteredlist from './Filteredlist'
import { Projects } from '../page'
import ProjectSection2 from '@/components/ProjectSection2'

type projectsProps = {
  data: Projects[]
}

export default function Filter({ data }: projectsProps): JSX.Element {
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
    <div>
      {/* <div className="flex">
        <div className="relative mr-3">
          <button
            onClick={() => {
              handleToggle(0)
            }}
            className="rounded-md bg-gray-200 px-4 py-2 text-gray-800 focus:outline-none"
          >
            지역
          </button>
          {isActive[0] && (
            <div className="absolute mt-2 w-20 rounded-md bg-white text-center shadow-lg">
              <ul className="py-2">
                <li onClick={() => handleOptionSelect(0, 'All')} className="px-2 py-2 hover:bg-gray-100">
                  All
                </li>
                <li onClick={() => handleOptionSelect(0, '서울')} className="px-2 py-2 hover:bg-gray-100">
                  서울
                </li>
                <li onClick={() => handleOptionSelect(0, '경기')} className="px-2 py-2 hover:bg-gray-100">
                  경기
                </li>
                <li onClick={() => handleOptionSelect(0, '부산')} className="px-2 py-2 hover:bg-gray-100">
                  부산
                </li>
              </ul>
            </div>
          )}
        </div>
        <div className="relative mr-3">
          <button
            onClick={() => {
              handleToggle(1)
            }}
            className="rounded-md bg-gray-200 px-4 py-2 text-gray-800 focus:outline-none"
          >
            장르
          </button>
          {isActive[1] && (
            <div className="absolute mt-2 w-20 rounded-md bg-white text-center shadow-lg">
              <ul className="py-2">
                <li onClick={() => handleOptionSelect(1, 'All')} className="px-2 py-2 hover:bg-gray-100">
                  All
                </li>
                <li onClick={() => handleOptionSelect(1, 'Option 1')} className="px-2 py-2 hover:bg-gray-100">
                  Option 1
                </li>
                <li onClick={() => handleOptionSelect(1, 'Option 2')} className="px-2 py-2 hover:bg-gray-100">
                  Option 2
                </li>
                <li onClick={() => handleOptionSelect(1, 'Option 3')} className="px-2 py-2 hover:bg-gray-100">
                  Option 3
                </li>
              </ul>
            </div>
          )}
        </div>
      </div> */}
      <ProjectSection2 />
      {/* <Filteredlist options={selectedOptions} data={data} /> */}
    </div>
  )
}
