type messageProps = {
  message: string
}

export default function SenderBubble({ message }: messageProps): JSX.Element {
  return (
    <div className="mb-2 w-1/2 content-end rounded-3xl bg-white p-4">
      <div>{message}</div>
    </div>
  )
}
