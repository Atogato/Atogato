'use client'

import ArtistForm from './ArtistForm'
import ProjectForm from './ProjectForm'

type FormContentType = 'project' | 'artist' | 'signup' | 'login'

export default function FormContent({ type }: { type: FormContentType }) {
  if (type === 'artist') {
    return <ArtistForm />
  } else if (type === 'project') {
    return <ProjectForm />
  }
  return <></>
}
