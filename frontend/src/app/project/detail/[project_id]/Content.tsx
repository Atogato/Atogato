'use client'
import Link from 'next/link'
import Comment from './Comment'
import { useParams } from 'next/navigation'
import { useEffect, useState } from 'react'
import Image from 'next/image'

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
        const response = await fetch(process.env.BACKEND_API_URL + `api/projects/${id}`)
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
    <div className="bg-white">
      <div className="relative p-1">
        {/* <div className="mt-[68px] ml-[40px] text-[28px] text-[#171616]">{projectData ? projectData.projectName : 'loading'}</div> */}
        <div className="text-semibold ml-[40px] mt-[68px] text-[28px] text-[#171616]">
          가을 하늘 아래에 풀밭에 있는 단발머리 여자아이의와 추상적 도형들
        </div>
        <div className="poppins absolute left-[40px] top-[24px] bg-[#7960BE] px-[14px] py-[4px] text-[13px] font-bold text-white">
          {/* {projectData ? (projectData.ongoingStatus ? '모집중' : '모집마감') : 'loading'} */}
          모집중
        </div>
        <div className="absolute right-[40px] top-[24px]">
          <div className="flex">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <g clip-path="url(#clip0_601_1274)">
                <path
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M22.6372 8.2392C23.3111 7.57382 23.3119 6.49381 22.6389 5.8275L18.1238 1.35718C17.4515 0.69149 16.361 0.690741 15.6877 1.35551L3.04497 13.8382C2.70674 14.1721 2.4687 14.5923 2.35729 15.052L0.906503 21.0389C0.598001 22.3121 1.79492 23.4398 3.06513 23.0728L8.86074 21.3985C9.27662 21.2783 9.65531 21.0567 9.96199 20.7539L22.6372 8.2392ZM17.541 10.8624L8.74477 19.5472C8.64259 19.6481 8.51634 19.722 8.3777 19.7621L3.57524 21.1495C3.13269 21.2773 2.71711 20.8794 2.82559 20.4317L4.03293 15.4494C4.07007 15.2963 4.14942 15.1562 4.26216 15.0449L13.0246 6.39341L17.541 10.8624ZM18.7607 9.65822L14.2443 5.18918L16.3019 3.15762C16.6361 2.82769 17.1735 2.82806 17.5072 3.15846L20.804 6.42256C21.1431 6.7583 21.1427 7.30634 20.8031 7.6416L18.7607 9.65822Z"
                  fill="#171616"
                  fill-opacity="0.9"
                />
              </g>
              <defs>
                <clipPath id="clip0_601_1274">
                  <rect width="24" height="24" fill="white" />
                </clipPath>
              </defs>
            </svg>
            <div className="poppins ml-[8px] mt-[1.5px] text-[14px] font-medium text-[#171616]/50">
              <Link prefetch={false} href={`/`}>
                수정하기
              </Link>
            </div>
          </div>
        </div>
      </div>
      <div className="mx-[40px] mt-[70px] flex">
        <Image
          src={'/images/sample/projects/project1.png'}
          alt={`project image`}
          width={464}
          height={464}
          className="object-cover"
        ></Image>
        <div className="ml-[24px] mr-[40px] w-[628px]">
          <div className="mb-[38px] flex">
            <div>
              <div className="mb-[8px] text-[16px] text-[#171616]/30">프로젝트 장르</div>
              <div className="text-[16px] text-[#171616]">일러스트</div>
            </div>
            <div className="absolute right-[591px]">
              <div className="mb-[8px] text-[16px] text-[#171616]/30">프로젝트 활동지역</div>
              <div className="text-[16px] text-[#171616]">서울</div>
            </div>
          </div>
          <div className="mb-[38px] flex">
            <div>
              <div className="mb-[8px] text-[16px] text-[#171616]/30">프로젝트 모집인원</div>
              <div className="text-[16px] text-[#171616]">5명</div>
            </div>
            <div className="absolute right-[591px]">
              <div className="mb-[8px] text-[16px] text-[#171616]/30">프로젝트 필요역할</div>
              <div className="text-[16px] text-[#171616]">5명</div>
            </div>
          </div>
          <div className="mb-[38px]">
            <div className="mb-[8px] text-[16px] text-[#171616]/30">프로젝트 기간</div>
            <div className="text-[16px] text-[#171616]">2023.08.11 ~ 2023.08.14</div>
          </div>
          <div>
            <div className="text-[16px] text-[#171616]/30">프로젝트 소개</div>
            <div className="text-[14px] text-[#171616]">
              여러가지 추상적인 그림을 그려서 프로젝트를 만들어 팀원들과 진행해가고 있습니다. 모든 팀원들이 힘을 합쳐
              프로젝트를 열심히 진행하고 있습니다. 모든 팀원들이 힘을 합쳐 프로젝트를 열심히 진행하고 있습니다. 모든
              팀원들이 힘을 합쳐 프로젝트를 열심히 진행하고 있습니다. 모든 팀원들이 힘을 합쳐 프로젝트를 열심히 진행하고
              있습니다. 모든 팀원들이 힘을 합쳐 프로젝트를 열심히 진행하고 있습니다. 모든 팀원들이 힘을 합쳐 프로젝트를
              열심히 진행하고 있습니다. 모든 팀원들이 힘을 합쳐 프로젝트를 열심히 진행하고 있습니다. 모든 팀원들이 힘을
              합쳐 프로젝트를 열심히 진행하고 있습니다. 모든 팀원들이 힘을 합쳐 프로젝트를 열심히 진행하고 있습니다.
            </div>
          </div>
        </div>
      </div>
      <div className="relative ml-[528px] mt-[42px]">
        <div>현재 인원 3명</div>
        <div className="absolute right-[40px] top-[0px]">
          <div className="flex h-[24px] w-[48px]">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <g clip-path="url(#clip0_601_1266)">
                <path
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M6.85714 0C8.2878 0 9.6162 0.44463 10.7154 1.20515C11.1897 1.53331 11.6213 1.92028 12 2.35573C12.3787 1.92028 12.8103 1.53331 13.2846 1.20515C14.3838 0.44463 15.7122 0 17.1429 0C20.93 0 24 3.11539 24 6.95841C24 11.6204 21.2697 16.1581 12.6855 23.7523C12.2987 24.0945 11.7153 24.0802 11.3435 23.7212C3.57029 16.2161 0 12.4596 0 6.95841C0 3.11539 3.07005 0 6.85714 0ZM10.5005 3.69874L11.2455 4.55523C11.6441 5.01354 12.3559 5.01354 12.7545 4.55523L13.4995 3.69874C14.3924 2.67212 15.6924 2.02954 17.1429 2.02954C19.8254 2.02954 22 4.23627 22 6.95841C22 10.4335 20.1642 14.1947 12.7507 20.9817C12.3631 21.3366 11.7656 21.3258 11.389 20.9593C8.1819 17.8384 5.90173 15.4961 4.36637 13.3837C2.6874 11.0736 2 9.1716 2 6.95841C2 4.23627 4.17462 2.02954 6.85714 2.02954C8.3076 2.02954 9.6076 2.67212 10.5005 3.69874Z"
                  fill="#E23232"
                />
              </g>
              <defs>
                <clipPath id="clip0_601_1266">
                  <rect width="24" height="24" fill="white" />
                </clipPath>
              </defs>
            </svg>
            <div className="my-[3.5px] ml-[8px] text-[14px] font-bold text-[#171616]">15</div>
          </div>
        </div>
      </div>
      <div className="mt-5 flex h-[64px] justify-center">
        <button className="pretendard mx-[40px] w-full rounded bg-[#7960BE] px-[479.5px] py-[15.5px] text-[20px] font-bold text-white">
          참여하기
        </button>
      </div>
      {/* <div className="relative mx-40 bg-gray-200">
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
      </div> */}
      <div className="mx-[40px] mb-[64px] mt-[86px]">
        <div className="my-[50px]">
          <div className="pretendard text-[16px mb-[20px] text-[#171616]/50">프로젝트 리더</div>
          <a href="/" className="relative block w-[100px]">
            <img alt="name" src="/images/sample/image.png" className="mb-[12px] h-[100px] w-[100px] rounded-full" />
            <div className="pretendard text-center text-[16px] text-[#171616]">Laymond</div>
          </a>
        </div>
        <div className="pretendard text-[16px mb-[20px] text-[#171616]/50">프로젝트 참여자</div>
        <div className="flex">
          <a href="/" className="mr-[20px] w-[100px]">
            <img alt="name" src="/images/sample/image.png" className="mb-[12px] h-[100px] w-[100px] rounded-full" />
            <div className="pretendard text-center text-[16px] text-[#171616]">Laymond</div>
          </a>
          <a href="/" className="mr-[20px] w-[100px]">
            <img alt="name" src="/images/sample/image.png" className="mb-[12px] h-[100px] w-[100px] rounded-full" />
            <div className="pretendard text-center text-[16px] text-[#171616]">Laymond</div>
          </a>
          <a href="/" className="mr-[20px] w-[100px]">
            <img alt="name" src="/images/sample/image.png" className="mb-[12px] h-[100px] w-[100px] rounded-full" />
            <div className="pretendard text-center text-[16px] text-[#171616]">Laymond</div>
          </a>
          <a href="/" className="w-[100px]">
            <img alt="name" src="/images/sample/image.png" className="mb-[12px] h-[100px] w-[100px] rounded-full" />
            <div className="pretendard text-center text-[16px] text-[#171616]">Laymond</div>
          </a>
        </div>
      </div>
      <hr className="mx-[40px] mb-[70px] border-[#EFEFEF]" />
      <div className="mx-[40px] mb-[80px]">
        <Comment id={id} />
      </div>
    </div>
  )
}
