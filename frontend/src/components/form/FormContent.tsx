import ArtistForm from './ArtistForm'
import ProjectForm from './ProjectForm'
import SignupForm from './SignupForm'
import { EntireFormTypes } from './Form'

export default function FormContent({ type }: { type: Omit<EntireFormTypes, 'user'> }) {
  if (type === 'artist') {
    return <ArtistForm />
  } else if (type === 'project') {
    return <ProjectForm />
  } else if (type === 'signup') {
    return <SignupForm />
  }
  return <></>
}
