import { ProjectGroup, ProjectCard } from '../../components/Group'
import Button from '../../components/Button'
import Link from 'next/link'
export default function MyHistoryPage() {
  const projects = [
    {
      id: 1,
      title: 'Project 1',
      description: 'This is the description for Project 1',
    },
    {
      id: 2,
      title: 'Project 2',
      description: 'This is the description for Project 2',
    },
    {
      id: 3,
      title: 'Project 3',
      description: 'This is the description for Project 3',
    },
  ]
  return (
    <div>
      <h1> 프로젝트 히스토리 </h1>
      <ProjectGroup className="flex flex-col gap-5">
        {projects.map((project, key) => {
          return (
            <div key={project.id} className="flex">
              <ProjectCard
                className="hover:bg-gray-400"
                src="/images/sample/image.png"
                title={project.title}
                description={project.description}
              />
              <div className="ml-3 flex flex-col justify-center gap-4">
                <Link href="/">
                  <Button className="block bg-[#F6EDED]" text="신청자 관리" />
                </Link>
                <Link href="/">
                  <Button className="block bg-[#8DA6CA]" text="수정하기" />
                </Link>
              </div>
            </div>
          )
        })}
      </ProjectGroup>
    </div>
  )
}
