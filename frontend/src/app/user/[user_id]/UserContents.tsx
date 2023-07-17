import { ProfileImage, ProfileGroup, ProjectGroup, ProjectCard } from './components/Group'
import Link from 'next/link'
import Button from './components/Button'

export function UserInfo() {
  const user = {
    name: 'test',
    email: 'test@test.com',
  }

  return (
    <div className="flex justify-between">
      <div>
        <ul>
          <li> 이름: {user.name} </li>
          <li> 이메일: {user.email} </li>
        </ul>
      </div>
      <Link href="user/1/update">
        <Button className="bg-gray-200 px-3" text="개인정보 수정" />
      </Link>
    </div>
  )
}

export default function UserContents() {
  const profiles = [
    { title: 'artist1', src: '/images/sample/artist1.jpeg' },
    { title: 'artist2', src: '/images/sample/artist2.jpg' },
    { title: 'artist3', src: '/images/sample/artist3.jpg' },
    { title: 'artist4', src: '/images/sample/artist4.jpg' },
    { title: 'artist5', src: '/images/sample/artist5.jpg' },
  ]

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
  // TODO: 각 그룹의 컴포넌트 내부에서 데이터 패칭(관심사 분리) Refactoring
  return (
    <div className="divide-y">
      <UserInfo />
      <div className="mt-4 pb-5">
        <h1 className="my-2 text-left text-2xl"> 매치된 아티스트 </h1>
        <ProfileGroup className="flex flex-none flex-wrap justify-between">
          {profiles.map((profile, key) => {
            const { title, src } = profile
            return (
              <div key={`artist-profile-${key}`}>
                <ProfileImage src={src} title={title} className="h-24 w-24" />
                <p className="text-center"> {title} </p>
              </div>
            )
          })}
        </ProfileGroup>
      </div>
      <div className="pb-5">
        <h1 className="my-2 text-left text-2xl"> 프로젝트 히스토리 </h1>
        <ProjectGroup className="flex flex-col gap-5">
          {projects.map((project, key) => {
            return (
              <ProjectCard
                key={`project-history-${key}`}
                className="hover:cursor-pointer hover:bg-gray-300"
                src="/images/sample/image.png"
                title={project.title}
                description={project.description}
              ></ProjectCard>
            )
          })}
        </ProjectGroup>
      </div>
      <div className="pb-5">
        <h1 className="my-2 text-left text-2xl"> 관심 아티스트 </h1>
        <ProfileGroup className="flex flex-none flex-wrap justify-between">
          {profiles.map((profile, key) => {
            const { title, src } = profile
            return (
              <div key={`artist-favorite-${key}`}>
                <ProfileImage src={src} title={title} className="h-24 w-24" />
                <p className="text-center"> {title} </p>
              </div>
            )
          })}
        </ProfileGroup>
      </div>
      <div className="pb-5">
        <h1 className="my-2 text-left text-2xl"> 관심 프로젝트 </h1>
        <ProjectGroup className="flex flex-col gap-5">
          {projects.map((project, key) => {
            return (
              <ProjectCard
                key={`project-favorite-${key}`}
                className="hover:bg-gray-300"
                src="/images/sample/image.png"
                title={project.title}
                description={project.description}
              ></ProjectCard>
            )
          })}
        </ProjectGroup>
      </div>
    </div>
  )
}
