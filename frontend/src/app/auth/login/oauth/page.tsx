import Redirect from './redirect'
import { redirect } from 'next/navigation'
export default function OauthRedirectPage({ searchParams }: { searchParams: { [key: string]: string } }) {
  if (searchParams.token === undefined) {
    redirect('/auth/login')
  }

  return <Redirect token={searchParams.token} />
}
