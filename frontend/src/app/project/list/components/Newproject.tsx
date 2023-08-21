import { Projects } from '../page'

type filteredListProps = {
  data: Projects[]
}
export default function Newproject({ data }: filteredListProps): JSX.Element {
  console.log(data)
  return (
    <div className="mx-auto max-w-5xl">
      <div className="my-2 text-left text-2xl">NEW</div>
      <div className="grid grid-cols-3 place-content-center gap-4">
        {data.map((project) => {
          return (
            <div
              className="relative h-80 bg-cover bg-center"
              style={{ backgroundImage: `url('/images/sample/image.png')` }}
              key={project.projectId}
            >
              <div className="absolute inset-x-0 bottom-0 space-y-2 text-center md:p-3 md:text-left">
                <div className="absolute left-0 top-0 h-full w-full bg-white opacity-70"></div>
                <div className="font-medium ">
                  <div className="relative z-10 mr-4 text-sky-500 dark:text-sky-400">{project.projectName}</div>
                  <div className="relative z-10 text-sm text-slate-700 dark:text-slate-500">name</div>
                </div>
                <div className="absolute right-2 top-1">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth="1.5"
                    stroke="currentColor"
                    className="h-6 w-6"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z"
                    />
                  </svg>
                  {project.liked}
                </div>
                <p className="relative z-10 text-sm text-slate-700 dark:text-slate-500">{project.description}</p>
                <div className="relative z-10 text-sm text-slate-700 dark:text-slate-500">모집인원 분야</div>
                <div className="absolute bottom-2 right-2">
                  <button className="relative z-10 rounded border border-blue-500 bg-transparent px-2 py-1 text-sm text-blue-700 hover:border-transparent hover:bg-blue-500 hover:text-white">
                    더보기
                  </button>
                </div>
              </div>
            </div>
          )
        })}
      </div>
    </div>
  )
}
