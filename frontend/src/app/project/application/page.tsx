import Applicants from './components/Applicants'
import Members from './components/Members'
import Information from './components/Information'

export default function Application() {
  return (
    <div className="mx-60 my-10 flex justify-center">
      <div className="grid w-full grid-rows-3 gap-4">
        <div className="row-span-1 mt-4">
          <Information />
        </div>
        <div className="row-span-2">
          <div className="grid grid-cols-2 gap-12">
            <div>
              <Members />
            </div>
            <div>
              <Applicants />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
