import ApplicationCard from './ApplicationCard'

export default function Applicants() {
  return (
    <div className="bg-gray-200 p-2">
      <div className="p-2">신청자</div>
      <div className="h-56 overflow-auto">
        <ApplicationCard />
        <ApplicationCard />
        <ApplicationCard />
        <ApplicationCard />
        <ApplicationCard />
      </div>
    </div>
  )
}
