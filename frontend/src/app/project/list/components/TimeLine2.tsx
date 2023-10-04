'use client'
import Link from 'next/link'
import { localStorage } from '@/app/storage'
import { Projects } from '../page'

type projectsProps = {
  data: Projects[]
}
type ProjectsForDay = Projects[]

export default function Timeline({ data }: projectsProps): JSX.Element {
  console.log(data)
  const AllDays = ['Sun', 'Mon', 'Tues', 'Wed', 'Thur', 'Fri', 'Sat', 'Sun']
  const ColStart = [
    'col-start-1',
    'col-start-2',
    'col-start-3',
    'col-start-4',
    'col-start-5',
    'col-start-6',
    'col-start-7',
  ]
  const thisWeek: { day: number; date: number; projectsForDay: ProjectsForDay }[] = []
  const d = new Date()
  d.setDate(d.getDate() - 1)
  for (let i = 0; i < 7; i++) {
    d.setDate(d.getDate() + 1)
    const day = d.getDay()
    const date = d.getDate()
    const projectsForDay: ProjectsForDay = data.filter((project) => {
      const projectDate = project.applicationDeadline[2]
      return projectDate === date
    })
    thisWeek.push({ day, date, projectsForDay })
  }

  return (
    <div className="mx-[240px] overflow-y-scroll">
      <div className="flex flex-row gap-x-[24px]">
        {/* 나중에 고쳐야함, instead of slice, change number of days saved to this week */}
        {thisWeek.slice(0, 6).map(({ day, date, projectsForDay }, index) => (
          <div key={date} className={`col-span-1 w-[220px]`}>
            <div className="w-[220px]">
              <div className="mb-6 h-[196px] rounded-lg bg-white px-[70px] py-[42px]">
                <div className="text-center">
                  <div className="Poppins mt-[10px] text-[22px] font-medium text-[#919095]">{AllDays[day]}</div>
                  <div className="Poppins mt-[8px] text-[44px] font-semibold text-[#171616]">{date}</div>
                </div>
              </div>
              <div>
                {projectsForDay.map((project) => (
                  <div key={project.projectId} className="group mb-4 h-[112px] rounded bg-white hover:bg-[#7960BE]">
                    <div className="inline-grid w-[220px]  text-[#171616]">
                      <Link
                        prefetch={false}
                        href={`/project/detail/${project.projectId}`}
                        className="Poppins ml-[12px] mt-[16px] text-[16px] font-semibold text-[#171616] group-hover:text-white"
                      >
                        {project.projectName}
                      </Link>
                      <div className="Poppins ml-[12px] mt-[4px] text-[12px] font-medium text-[#171616]/40 group-hover:text-white">
                        #{project.genre}
                      </div>
                      <div className="mb-[16px] mt-[16px] group-hover:text-white">
                        <div className="Poppins float-left ml-[12px] text-[12px] text-[#171616]/60 group-hover:text-white">
                          user: {project.userId}
                        </div>
                        <div className="Poppins float-right mr-[16px] text-[12px] text-[#171616]/40 group-hover:text-white">
                          {project.participantId.length}/{project.requiredPeople}
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
