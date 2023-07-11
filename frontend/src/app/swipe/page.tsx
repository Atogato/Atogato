import SwipeArtist from './component/SwipeArtist'
export default function Swipe() {
  return (
    <div className="h-screen">
      <div className="my-4 text-center">
        <button className="rounded-full bg-gray-500 px-6 py-2 text-white hover:bg-lime-500">switch to projects</button>
      </div>
      <SwipeArtist />
    </div>
  )
}
