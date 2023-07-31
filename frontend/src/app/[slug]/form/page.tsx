import { notFound } from 'next/navigation'
import { Form } from '@/components/form/Form'
import { FormType } from '@/types/form'

export const FORM_TYPES: FormType[] = ['artist', 'project', 'signup', 'login']

export default function FormPage({ params }: { params: { slug: FormType } }) {
  const { slug } = params
  if (!FORM_TYPES.includes(slug)) {
    notFound()
  }
  return <Form type={slug} />
}
