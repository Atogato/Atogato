import ProjectFormContainer from './ProjectFormContainer'
import dynamic from 'next/dynamic'

const ProjectForm = dynamic(() => import('./ProjectForm'), {
  ssr: false,
  loading: () => <></>,
})

export default function ProjectFormPage() {
  return (
    <ProjectFormContainer>
      <ProjectForm />
    </ProjectFormContainer>
  )
}
