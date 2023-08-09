import UserUpdateForm from './UserUpdateForm'
import { FormUpdateType } from '@/types/form'

const FORM_UPDATE_CONTENT_TYPES = {
  user: UserUpdateForm,
  artist: () => <></>,
  project: () => <></>,
}

export default function FormUpdateContent({ type }: { type: FormUpdateType }) {
  const Content = FORM_UPDATE_CONTENT_TYPES[type]
  return <Content />
}
