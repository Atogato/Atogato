type messageProps = {
  message: string
}

export default function ReceiverBubble({ message }: messageProps): JSX.Element {
  return (
    <div className="mb-2 ml-auto rounded-3xl bg-white p-4">
      <div style={{ display: 'inline-block', maxWidth: '100%' }}>{message}</div>
    </div>
  )
}
