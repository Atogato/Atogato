'use client'
import { useEffect, useState } from 'react'

import Newproject from './components/Newproject'
import Timeline from './components/Timeline'
import Filter from './components/Filter'

export type Projects = {
  projectId: number
  genre: string
  projectName: string
  location: string
  creatorArtCategory: string
  createdDate: [number, number, number]
  deadline: [number, number, number]
  requiredPeople: number
  description: string
  remoteStatus: string
}
export default function List() {
  const [result, setResult] = useState<Projects[]>([])

  useEffect(() => {
    const api = async () => {
      const data = await fetch('http://localhost:7072/api/projects', {
        method: 'GET',
      })
      const jsonData = await data.json()
      setResult(jsonData)
    }

    api()
  }, [])
  console.log(result)

  const recentProject = result
    .sort((a, b) => {
      const aDeadline = a.deadline.join('')
      const bDeadline = b.deadline.join('')
      return aDeadline.localeCompare(bDeadline)
    })
    .slice(0, 3)
  console.log(recentProject)

  const newProject = result
    .sort((a, b) => {
      const aDeadline = a.deadline.join('')
      const bDeadline = b.deadline.join('')
      return bDeadline.localeCompare(aDeadline)
    })
    .slice(0, 3)
  console.log(newProject)
  return (
    <div>
      <Newproject data={newProject} />
      <div className="mx-auto max-w-5xl">
        <div className="my-2 text-left text-2xl">Timeline</div>
        <Timeline data={recentProject} />
      </div>
      <div className="mx-auto my-5 max-w-5xl">
        <div className="my-2 text-left text-2xl">Filters</div>
        <Filter data={result} />
      </div>
    </div>
  )
}
