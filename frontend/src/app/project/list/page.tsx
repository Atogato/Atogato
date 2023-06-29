import Newproject from './components/Newproject'
import Timeline from './components/Timeline'
import Filter from './components/Filter'

export default function List() {
  return (
    <div>
      <Newproject />
      <div className="mx-auto max-w-5xl">
        <div className="my-2 text-left text-2xl">Timeline</div>
        <Timeline />
      </div>
      <div className="mx-auto my-5 max-w-5xl">
        <div className="my-2 text-left text-2xl">Filters</div>
        <Filter />
      </div>
    </div>
  )
}
