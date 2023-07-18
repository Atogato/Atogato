import ProfileCard from './ProfileCard'

export default function Members() {
  return (
    <div className="bg-gray-200 p-2">
      <div className="p-2">참가자</div>
      <div className="h-56 overflow-auto">
        <ProfileCard />
        <ProfileCard />
        <ProfileCard />
        <ProfileCard />
        <ProfileCard />
        <ProfileCard />
      </div>
    </div>
  )
}
