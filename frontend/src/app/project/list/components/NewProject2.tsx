import { Projects } from '../page'
import Image from 'next/image'
type filteredListProps = {
  data: Projects[]
}
export default function Newproject({ data }: filteredListProps): JSX.Element {
  console.log(data)
  return (
    <div className="">
      <div className="mb-[114px] ml-[239px] mt-[140px] flex flex-col text-left text-2xl">
        <div className="poppins mb-[8px] text-2xl font-semibold text-[#7960BE]/70">New Project</div>
        <div className="h-[70px] text-5xl font-semibold leading-snug text-[#171616]/30">
          <span className="font-semibold text-[#171616]">새로운 프로젝트</span>를 탐색해보세요
        </div>
      </div>
      <div className="mx-[240px]">
        <div className="flex flex-row gap-x-[24px]">
          {data.map((project) => {
            return (
              <div className="h-[851px] w-[464px]">
                <div
                  className="relative h-[464px] w-[464px]"
                  style={{ backgroundImage: `url('/images/sample/image.png')` }}
                  key={project.projectId}
                >
                  <div className="poppin float-right mr-[20px] mt-[16px] bg-white/70 px-[14px] py-[4px] text-[13px] font-bold text-[#7960BE]">
                    모집중
                  </div>
                  <div className="absolute">
                    <div className="ml-[20px] mt-[374px] text-[28px] font-semibold text-white">
                      {project.projectName}
                    </div>
                    <div className="ml-[20px] mt-[6px] text-[18px] font-semibold text-white/75">{project.userId}</div>
                  </div>
                </div>
                <div className="mt-[32px] text-[20px] font-bold">{project.projectName}</div>
                <div className="mt-[8px] text-[14px] opacity-70">{project.description}</div>
                <div className="mt-[24px] text-[13px] opacity-30">
                  {project.createdDate}~{project.applicationDeadline}
                </div>
                <div className="mt-[24px] text-[14px] text-[#7960BE]">#{project.genre}</div>
                <div className="mt-[24px]">
                  <div className="float-left">
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
                    {project.liked}
                  </div>
                  {/* <div className="float-right">{project.participantId}</div> */}
                  <div className="float-right">
                    <div className="relative w-28">
                      <div className="absolute right-[3px] z-40 flex h-[32px] w-[32px] items-center justify-center rounded-full border-2 border-white bg-[#7960BE] text-white">
                        +5
                      </div>
                      <div className="absolute right-[17px] z-30 rounded-full border-2 border-white">
                        <Image
                          src={'/images/sample/image.png'}
                          alt={`project image`}
                          width={28}
                          height={28}
                          className="h-[28px]  w-[28px] rounded-full"
                        ></Image>
                      </div>
                      <div className="absolute right-[31px] z-20 rounded-full border-2 border-white">
                        <Image
                          src={'/images/sample/image.png'}
                          alt={`project image`}
                          width={28}
                          height={28}
                          className="h-[28px]  w-[28px] rounded-full"
                        ></Image>
                      </div>
                      <div className="absolute right-[45px] z-10 rounded-full border-2 border-white">
                        <Image
                          src={'/images/sample/image.png'}
                          alt={`project image`}
                          width={28}
                          height={28}
                          className="h-[28px] w-[28px] rounded-full"
                        ></Image>
                      </div>
                    </div>
                  </div>
                </div>
                <button className="bottom:0 mt-[22px] h-[56px] w-[464px] bg-[#7960BE] font-bold text-[16] text-white hover:border-transparent">
                  참여하기
                </button>
              </div>
            )
          })}
        </div>
      </div>
    </div>
  )
}
