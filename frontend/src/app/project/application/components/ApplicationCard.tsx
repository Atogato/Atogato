export default function ApplicationCard() {
  return (
    <div className="rounded-1xl mx-4 my-1 grid grid-cols-4 bg-white">
      <div className="col-span-1 m-2 flex items-center">
        <img alt="name" src="/images/sample/image.png" className="h-10 w-10 rounded-full" />
      </div>
      <div className="col-span-2 flex items-center">application message</div>
      <div className="col-span-1 m-2 flex items-center justify-center bg-gray-200">button</div>
    </div>
  )
}
