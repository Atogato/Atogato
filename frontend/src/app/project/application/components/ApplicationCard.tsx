import Image from 'next/image'
type MessageType = {
  id: number
  userName: string
  chat: string
}
interface MessageProps {
  message: MessageType
}

export default function ApplicationCard({ message }: MessageProps): JSX.Element {
  return (
    <div className="rounded-1xl mx-4 my-1 grid grid-cols-4 bg-white">
      <div className="col-span-1 m-2 flex items-center">
        <Image width={400} height={400} alt="name" src="/images/sample/image.png" className="h-10 w-10 rounded-full" />
      </div>
      <div className="col-span-2 flex items-center">{message.chat}</div>
      <button className="col-span-1 m-2 flex items-center justify-center bg-gray-200">
        <div>button</div>
      </button>
    </div>
  )
}
