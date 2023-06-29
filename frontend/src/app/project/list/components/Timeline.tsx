import Link from 'next/link'
export default function Timeline() {
  const projects = [
    {
      id: 1,
      projectName: 'Project 1',
      startDate: '2023-06-26',
      endDate: '2023-06-28',
    },
    {
      id: 2,
      projectName: 'Project 2',
      startDate: '2023-06-20',
      endDate: '2023-06-27',
    },
    {
      id: 3,
      projectName: 'Project 3',
      startDate: '2023-06-27',
      endDate: '2023-06-29',
    },
  ]
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
      {projects.map((project, i) => {
        const d2 = new Date(project.endDate)
        const dl = d2.getDay()
        let d3
        {
          dl >= thisWeek[0].day ? (d3 = dl - thisWeek[0].day) : (d3 = 7 - thisWeek[0].day + dl)
        }
        console.log(d3)
        return (
          <div className={`col-start-1 ${ColSpan[d3]} bg-slate-600`} key={i}>
            <div className="ml-3 flex justify-center">
              <Link className="text-white" prefetch={false} href={`/project/detail/${project.id}`}>
                {project.projectName}
              </Link>
            </div>
          </div>
        )
      })}
    </div>
  )
}
