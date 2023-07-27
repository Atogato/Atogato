import ApplicationCard from './ApplicationCard'

export default function Applicants() {
  const messages = [
    {
      id: 1,
      userName: 'name 1',
      chat: 'message 1',
    },
    {
      id: 2,
      userName: 'name 2',
      chat: 'message 2',
    },
    {
      id: 3,
      userName: 'name 3',
      chat: 'message 3',
    },
  ]
  return (
    <div className="bg-gray-200 p-2">
      <div className="p-2">신청자</div>
      <div className="h-56 overflow-auto">
        {messages.map((message) => (
          <ApplicationCard message={message} key={message.id} />
        ))}
      </div>
    </div>
  )
}
