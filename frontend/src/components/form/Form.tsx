import dynamic from 'next/dynamic'
import { redirect } from 'next/navigation'

type FormProps = {
  classes?: string
  type: 'project' | 'artist' | 'signup' | 'login'
  children?: React.ReactNode
}

export function FormContainer(props: Pick<FormProps, 'classes' | 'children'>) {
  const { children } = props
  return <div className="flex flex-col	items-center">{children}</div>
}

const FormContent = dynamic(() => import('./FormContent'), { ssr: false, loading: () => <></> })

export function Form(props: FormProps) {
  const { type } = props

  return (
    <FormContainer>
      <FormContent type={type} />
    </FormContainer>
  )
}
