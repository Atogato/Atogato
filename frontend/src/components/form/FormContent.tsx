import ArtistForm from './ArtistForm'
import ProjectForm from './ProjectForm'
import LoginForm from './LoginForm'
import { FormType } from '@/types/form'

const FORM_CONTENT_TYPES = {
  artist: ArtistForm,
  project: ProjectForm,
  login: LoginForm,
  signup: () => <></>,
}

export default function FormContent({ type }: { type: FormType }) {
  const Content = FORM_CONTENT_TYPES[type]

  return <Content />
}
