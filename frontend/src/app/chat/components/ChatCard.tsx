import Link from 'next/link'

type RoomType = {
  id: number
  senderId: number
  receiverId: number
  recentMessage: string
  date: string
}
interface RoomProps {
  room: RoomType
}

export default function ChatCard({ room }: RoomProps): JSX.Element {
  return (
    <div className="mx-10 my-4 rounded-3xl bg-white">
      <div className="px-8 pt-4">{room.senderId}</div>
      <div className="px-10 pb-4 pt-2">{room.recentMessage}</div>
      <Link prefetch={false} href={`../chat/room/${room.id}`}>
        <button className="rounded border border-blue-500 bg-transparent px-2 py-1 text-sm text-blue-700 hover:border-transparent hover:bg-blue-500 hover:text-white">
          Enter
        </button>
      </Link>
    </div>
  )
}
