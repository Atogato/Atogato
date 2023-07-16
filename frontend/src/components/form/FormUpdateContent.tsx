import UserUpdateForm from './UserUpdateForm'
import { EntireFormTypes } from './Form'

export default function FormUpdateContent({ type }: { type: Omit<EntireFormTypes, 'login' | 'signup'> }) {
  if (type === 'user') {
    return <UserUpdateForm />
  }
  return <></>
}
