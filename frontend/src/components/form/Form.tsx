'use client'
import dynamic from 'next/dynamic'
import { FormType, FormUpdateType } from '@/types/form'

type FormProps = {
  classes?: string
  type: FormType | FormUpdateType
  children?: React.ReactNode
  contentType?: 'create' | 'update'
}

export function FormContainer(props: Pick<FormProps, 'classes' | 'children'>) {
  const { children } = props
  return <div className="flex flex-col	items-center">{children}</div>
}

const FormContent = dynamic(() => import('./FormContent'), { ssr: false, loading: () => <></> })
const FormUpdateContent = dynamic(() => import('./FormUpdateContent'), { ssr: false, loading: () => <></> })

export function Form(props: FormProps) {
  const { type, contentType } = props

  return (
    <FormContainer>
      {contentType === 'update' ? (
        <FormUpdateContent type={type as FormUpdateType} />
      ) : (
        <FormContent type={type as FormType} />
      )}
    </FormContainer>
  )
}
