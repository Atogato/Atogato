import ChatCard from './ChatCard'
export default function ChatList() {
  const chatRooms = [
    {
      id: 1,
      senderId: 11,
      receiverId: 2,
      recentMessage: 'Hello, how are you?',
      date: '2022-01-01',
    },
    {
      id: 2,
      senderId: 33,
      receiverId: 1,
      recentMessage: "I'm doing great, thanks!",
      date: '2022-01-02',
    },
    {
      id: 3,
      senderId: 22,
      receiverId: 3,
      recentMessage: "Let's meet tomorrow.",
      date: '2022-01-03',
    },
  ]
  return (
    <div className="relative mx-56 my-16 bg-gray-200 pb-4">
      <div className="py-4 text-center text-2xl">채팅방</div>
      {chatRooms.map((room) => (
        <ChatCard room={room} key={room.id} />
      ))}
    </div>
  )
}
