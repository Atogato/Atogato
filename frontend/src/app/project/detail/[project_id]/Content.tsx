'use client'
import Link from 'next/link'
import Comment from './Comment'
import { useParams } from 'next/navigation'
import { useEffect, useState } from 'react'

interface DataType {
  userId: string
  projectId: number
  ongoingStatus: boolean
  remoteStatus: string
  projectName: string
  createdDate: number[]
  projectArtCategory: string
  location: string
  projectDeadline: number[]
  applicationDeadline: number[]
  requiredPeople: number
  requiredCategory: string[]
  swipeAlgorithm: boolean
  image: string
  liked: number
  description: string
  participantId: number
}
export default function Content() {
  const params = useParams()
  const id = params.project_id
  console.log(id)

  const [projectData, setProjectData] = useState<DataType>()
  useEffect(() => {
    const fetchProjectData = async () => {
      try {
        const response = await fetch(`http://localhost:7072/api/projects/${id}`)
        if (response.ok) {
          const jsonData = await response.json()
          setProjectData(jsonData)
        } else {
          console.error('Error fetching project data:', response.status)
        }
      } catch (error) {
        console.error('Error fetching project data:', error)
      }
    }

    fetchProjectData()
  }, [])
  console.log('project:', projectData)

  return (
    <div>
      <div className="relative mb-10 mt-3">
        <div className="text-center text-5xl">{projectData ? projectData.projectName : 'loading'}</div>
        <div className="absolute left-2 top-0">
          {projectData ? (projectData.ongoingStatus ? 'Open' : 'Closed') : 'loading'}
        </div>
        <div className="absolute right-2 top-0">
          <Link prefetch={false} href={`/`}>
            수정하기
          </Link>
        </div>
      </div>
      <hr className="mx-40 mb-8 border-black" />
      <div className="relative mx-40 bg-gray-200">
        <div className="px-10 pb-3 pt-1 text-left text-2xl">프로젝트 정보 요약</div>
        <hr className="my-1 w-full border-black" />
        <div className="px-10 pb-10 pt-1 text-left text-2xl">{projectData ? projectData.description : 'loading'}</div>
        {projectData ? (
          <div className="absolute bottom-2 left-2">
            참여자 수 {projectData.participantId}/{projectData.requiredPeople}
          </div>
        ) : (
          'loading'
        )}
        <div className="absolute bottom-2 right-2 flex">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth="1.5"
            stroke="currentColor"
            className="h-6 w-6"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z"
            />
          </svg>
          <div>{projectData ? projectData.liked : 'loading'}</div>
        </div>
      </div>
      <div className="mt-5 flex justify-center">
        <button className="mx-40 w-full rounded bg-blue-500 px-10 py-5 font-bold text-white hover:bg-blue-700">
          참여하기/수정하기
        </button>
      </div>
      <div className="mx-40 mb-10 mt-5">
        <div className="my-5">
          <div className="my-2">리더</div>
          <a href="/" className="relative block">
            <img alt="name" src="/images/sample/image.png" className="h-20 w-20 rounded-full" />
          </a>
        </div>
        <div className="my-2">참여자 목록</div>
        <div className="flex">
          <a href="/" className="mr-6">
            <img alt="name" src="/images/sample/image.png" className="h-20 w-20 rounded-full" />
          </a>
          <a href="/" className="mr-6">
            <img alt="name" src="/images/sample/image.png" className="h-20 w-20 rounded-full" />
          </a>
          <a href="/" className="mr-6">
            <img alt="name" src="/images/sample/image.png" className="h-20 w-20 rounded-full" />
          </a>
          <a href="/" className="mr-6">
            <img alt="name" src="/images/sample/image.png" className="h-20 w-20 rounded-full" />
          </a>
        </div>
      </div>
      <div className="mx-40 mb-10 mt-5">
        <Comment id={id} />
      </div>
    </div>
  )
}
