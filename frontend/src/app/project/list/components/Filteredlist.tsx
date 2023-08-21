import ProjectCard from './ProjectCard'
import { Projects } from '../page'

type filteredListProps = {
  options: string[]
  data: Projects[]
}

export default function Filteredlist({ options, data }: filteredListProps): JSX.Element {
  const filterbyAll = data.filter((project) => project.location === options[0] && project.genre === options[1])
  const filterbyLocation = data.filter((project) => project.location === options[0])
  const filterbyCategory = data.filter((project) => project.genre === options[1])

  return (
    <div>
      <div className="my-2 text-left text-2xl">PROJECTS</div>
      <div role="project-card" className="grid grid-cols-3 place-content-center gap-4">
        {options[0] === 'All' && options[1] === 'All'
          ? data.map((project) => <ProjectCard project={project} key={project.projectId} />)
          : options[0] !== 'All' && options[1] !== 'All'
          ? filterbyAll.map((project) => <ProjectCard project={project} key={project.projectId} />)
          : options[0] !== 'All' && options[1] === 'All'
          ? filterbyLocation.map((project) => <ProjectCard project={project} key={project.projectId} />)
          : options[0] === 'All' && options[1] !== 'All'
          ? filterbyCategory.map((project) => <ProjectCard project={project} key={project.projectId} />)
          : null}
      </div>
    </div>
  )
}
