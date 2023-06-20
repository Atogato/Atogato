type Props = {
  classes?: string
  children?: React.ReactNode
}

export default function ProjectFormContainer(props: Props) {
  const { children } = props
  return (
    <div className="flex flex-col	items-center">
      <h1> Project Form </h1>
      {children}
    </div>
  )
}
