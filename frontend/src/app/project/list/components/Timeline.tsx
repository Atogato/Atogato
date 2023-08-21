'use client'
import Link from 'next/link'
import { localStorage } from '@/app/storage'
import { Projects } from '../page'

type projectsProps = {
  data: Projects[]
}

export default function Timeline({ data }: projectsProps): JSX.Element {
  console.log(data)
  const AllDays = ['Sun', 'Mon', 'Tues', 'Wed', 'Thur', 'Fri', 'Sat', 'Sun']
  const ColSpan = ['col-span-1', 'col-span-2', 'col-span-3', 'col-span-4', 'col-span-5', 'col-span-6', 'col-span-7']
  const thisWeek: { day: number; date: number }[] = []
  const d = new Date()
  d.setDate(d.getDate() - 1)
  for (let i = 0; i < 7; i++) {
    d.setDate(d.getDate() + 1)
    const day = d.getDay()
    const date = d.getDate()
    thisWeek.push({ day, date })
  }

  return (
    <div className="grid grid-cols-7 gap-4">
      {thisWeek.map((item, i) => (
        <div
          className={`col-start-${i + 1} col-end-${i + 2} flex justify-center ${
            item.day === thisWeek[0].day ? 'bg-orange-200' : 'bg-teal-200'
          }`}
          key={i}
        >
          {AllDays[item.day]}, <span className="ml-1">{item.date}</span>
        </div>
      ))}
      {data.map((project, i) => {
        const d2 = new Date(
          project.applicationDeadline[0],
          project.applicationDeadline[1] - 1,
          project.applicationDeadline[2],
        )
        const dl = d2.getDay()
        let d3
        {
          dl >= thisWeek[0].day ? (d3 = dl - thisWeek[0].day) : (d3 = 7 - thisWeek[0].day + dl)
        }
        return (
          <div className={`col-start-1 ${ColSpan[d3]} bg-slate-600`} key={i}>
            <div className="ml-3 flex justify-center">
              <Link className="text-white" prefetch={false} href={`/project/detail/${project.projectId}`}>
                {project.projectName}
              </Link>
            </div>
          </div>
        )
      })}
    </div>
  )
}
