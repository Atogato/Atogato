'use client'
import dynamic from 'next/dynamic'
import { FormTypes, FormUpdateTypes } from '@/types/formTypes'

// TODO: Form type 관련 리펙토링 필요
export type EntireFormTypes = FormTypes | FormUpdateTypes

type FormProps = {
  classes?: string
  type: EntireFormTypes
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
      {contentType === 'update' ? <FormUpdateContent type={type} /> : <FormContent type={type} />}
    </FormContainer>
  )
}
