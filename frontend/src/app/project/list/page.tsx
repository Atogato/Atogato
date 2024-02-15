'use client'
import { useEffect, useState } from 'react'

import Newproject from './components/Newproject'
import Newproject2 from './components/NewProject2'
import Timeline from './components/Timeline'
import TimeLine2 from './components/TimeLine2'
import Filter from './components/Filter'

export type Projects = {
  userId: number
  projectId: number
  participantId: number[]
  genre: string
  projectName: string
  location: string
  creatorArtCategory: string
  createdDate: number[]
  applicationDeadline: number[]
  requiredPeople: number
  description: string
  remoteStatus: string
  liked: number
}
export default function List() {
  const today = new Date()
  const next7Days = new Date(today)
  next7Days.setDate(today.getDate() + 7)

  //////////////////////////////
  function randomDate(startDate: Date, endDate: Date) {
    return new Date(startDate.getTime() + Math.random() * (endDate.getTime() - startDate.getTime()))
  }
  function getRandomNumber(min: number, max: number) {
    return Math.floor(Math.random() * (max - min + 1)) + min
  }
  const dummyProjects = []

  for (let i = 1; i <= 10; i++) {
    const createdDate: Date = randomDate(new Date(2023, 0, 1), new Date()) // Random date between Jan 1, 2023, and today
    const applicationDeadline: Date = randomDate(today, next7Days)
    const requiredPeople = Math.floor(Math.random() * 10) + 1
    const numParticipants = getRandomNumber(1, requiredPeople)
    const participants = []
    for (let j = 0; j < numParticipants - 1; j++) {
      participants.push(getRandomNumber(1, 100)) // Assuming participant IDs are between 1 and 100
    }
    dummyProjects.push({
      userId: i + 1,
      projectId: i,
      participantId: participants,
      genre: `Genre ${i}`,
      projectName: `Project ${i}`,
      location: `Location ${i}`,
      creatorArtCategory: `Art Category ${i}`,
      createdDate: [createdDate.getFullYear(), createdDate.getMonth() + 1, createdDate.getDate()],
      applicationDeadline: [
        applicationDeadline.getFullYear(),
        applicationDeadline.getMonth() + 1,
        applicationDeadline.getDate(),
      ],
      requiredPeople: requiredPeople, // Random number of required people
      description: `Description for project ${i}`,
      remoteStatus: Math.random() < 0.5 ? 'Remote' : 'Not Remote', // Random remote status
      liked: Math.floor(Math.random() * 100), // Random liked count
    })
  }
  console.log(dummyProjects)

  /////////////////////////////
  const [result, setResult] = useState<Projects[]>([])
  const [sorted, setSorted] = useState<Projects[]>([])
  const projectsWithin7Days = []

  useEffect(() => {
    const api = async () => {
      const data = await fetch(process.env.BACKEND_API_URL + 'projects', {
        method: 'GET',
      })
      const jsonData = await data.json()
      setResult(jsonData)
    }

    api()
  }, [])
  console.log(result)
  useEffect(() => {
    const api = async () => {
      const data = await fetch(process.env.BACKEND_API_URL + 'projects/sorted', {
        method: 'GET',
      })
      const jsonData = await data.json()
      setSorted(jsonData)
    }

    api()
  }, [])
  console.log(result)

  const recentProject = sorted.slice(0, 3)
  console.log(recentProject)

  for (const project of sorted) {
    const [year, month, day] = project.applicationDeadline
    const projectDate = new Date(year, month - 1, day)

    if (projectDate > next7Days) {
      break
    }

    if (projectDate >= today) {
      projectsWithin7Days.push(project)
    }
  }

  console.log(projectsWithin7Days)
  // const newProject = result.slice(0, 3)
  const newProject = dummyProjects.slice(0, 3)

  console.log(newProject)
  return (
    <div>
      <div>
        <Newproject2 data={newProject} />
      </div>
      <div className="flex h-[965px] flex-col  bg-[#F4F2F8]">
        <div className="mb-[114px] ml-[239px] mt-[140px] flex flex-col text-left text-2xl">
          <div className="poppins mb-[8px] text-2xl font-semibold text-[#7960BE]/70">Project</div>
          <div className="h-[70px] text-5xl font-semibold leading-snug text-[#171616]/30">
            <span className="font-semibold text-[#171616]">모집 임박 프로젝트</span>에 참여해보세요
          </div>
        </div>
        {/* <Timeline data={recentProject} /> */}
        <TimeLine2 data={dummyProjects} />
      </div>
      <Filter data={result} />
    </div>
  )
}
