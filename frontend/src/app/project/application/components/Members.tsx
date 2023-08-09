import ProfileCard from './ProfileCard'

export default function Members() {
  const members = [
    {
      id: 1,
      userName: 'name 1',
    },
    {
      id: 2,
      userName: 'name 2',
    },
    {
      id: 3,
      userName: 'name 3',
    },
  ]
  return (
    <div className="bg-gray-200 p-2">
      <div className="p-2">참가자</div>
      <div className="h-56 overflow-auto">
        {members.map((member) => (
          <ProfileCard member={member} key={member.id} />
        ))}
      </div>
    </div>
  )
}
