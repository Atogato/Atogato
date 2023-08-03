import { notFound } from 'next/navigation'
import { Form } from '@/components/form/Form'

export const FORM_TYPES = ['artist', 'project', 'signup', 'login']

export default function FormPage({ params }: { params: { slug: 'artist' | 'project' | 'signup' | 'login' } }) {
  const { slug } = params
  if (!FORM_TYPES.includes(slug)) {
    notFound()
  }
  return <Form type={slug} />
}
