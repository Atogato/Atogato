import Link from "next/link"
import Filter from "./Filter"
import Newproject from "./Newproject"
export default function List() {
    return (
        <div>
            <Newproject />
            {/* <div className="max-w-5xl mx-auto">
                timeline
            </div> */}
            <div className="max-w-5xl mx-auto my-5">
                <div className="text-2xl text-left my-2">
                    Filters
                </div>
                <Filter />
            </div>      
            <div className="max-w-5xl mx-auto my-5">
                <div className="text-2xl text-left my-2">
                    PROJECTS
                </div>
                <div className="grid grid-cols-3 gap-4 place-content-center">
                    <div className="flex bg-slate-100 rounded-lg">
                        <img className="md:w-40 md:rounded-l-lg" src="/image.png" width="300" height="300" />
                        <div className="md:p-3 text-center md:text-left space-y-2 relative">
                            <figcaption className="font-medium">
                                <div className="text-sky-500 dark:text-sky-400 mr-4">
                                    Project Name
                                </div>
                                <div className="text-sm text-slate-700 dark:text-slate-500">
                                    Poster Name
                                </div>
                            </figcaption>
                            <div className="absolute top-1 right-2">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
                                </svg> 
                            </div>
                            <p className="text-slate-700 text-sm dark:text-slate-500">
                                This is the project description
                            </p>
                            <div className="text-sm text-slate-700 dark:text-slate-500 absolute bottom-3 left-3">
                                모집인원 분야
                            </div>
                            <div className="absolute bottom-2 right-2">
                                <button className="text-sm bg-transparent hover:bg-blue-500 text-blue-700 hover:text-white py-1 px-2 border border-blue-500 hover:border-transparent rounded">
                                    더보기
                                </button>
                            </div>
                        </div>
                    </div>
                    <div className="flex bg-slate-100 rounded-lg">
                        <img className="md:w-40 md:rounded-l-lg" src="/image.png" width="300" height="300" />
                        <div className="md:p-3 text-center md:text-left space-y-2 relative">
                            <figcaption className="font-medium">
                                <div className="text-sky-500 dark:text-sky-400 mr-4">
                                    Project Name
                                </div>
                                <div className="text-sm text-slate-700 dark:text-slate-500">
                                    Poster Name
                                </div>
                            </figcaption>
                            <div className="absolute top-1 right-2">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
                                </svg> 
                            </div>
                            <p className="text-slate-700 text-sm dark:text-slate-500">
                                This is the project description
                            </p>
                            <div className="text-sm text-slate-700 dark:text-slate-500 absolute bottom-3 left-3">
                                모집인원 분야
                            </div>
                            <div className="absolute bottom-2 right-2">
                                <button className="text-sm bg-transparent hover:bg-blue-500 text-blue-700 hover:text-white py-1 px-2 border border-blue-500 hover:border-transparent rounded">
                                    더보기
                                </button>
                            </div>
                        </div>
                    </div>
                    <div className="flex bg-slate-100 rounded-lg">
                        <img className="md:w-40 md:rounded-l-lg" src="/image.png" width="300" height="300" />
                        <div className="md:p-3 text-center md:text-left space-y-2 relative">
                            <figcaption className="font-medium">
                                <div className="text-sky-500 dark:text-sky-400 mr-4">
                                    Project Name
                                </div>
                                <div className="text-sm text-slate-700 dark:text-slate-500">
                                    Poster Name
                                </div>
                            </figcaption>
                            <div className="absolute top-1 right-2">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
                                </svg> 
                            </div>
                            <p className="text-slate-700 text-sm dark:text-slate-500">
                                This is the project description
                            </p>
                            <div className="text-sm text-slate-700 dark:text-slate-500 absolute bottom-3 left-3">
                                모집인원 분야
                            </div>
                            <div className="absolute bottom-2 right-2">
                                <button className="text-sm bg-transparent hover:bg-blue-500 text-blue-700 hover:text-white py-1 px-2 border border-blue-500 hover:border-transparent rounded">
                                    더보기
                                </button>
                            </div>
                        </div>
                    </div>
                    <div className="flex bg-slate-100 rounded-lg">
                        <img className="md:w-40 md:rounded-l-lg" src="/image.png" width="300" height="300" />
                        <div className="md:p-3 text-center md:text-left space-y-2 relative">
                            <figcaption className="font-medium">
                                <div className="text-sky-500 dark:text-sky-400 mr-4">
                                    Project Name
                                </div>
                                <div className="text-sm text-slate-700 dark:text-slate-500">
                                    Poster Name
                                </div>
                            </figcaption>
                            <div className="absolute top-1 right-2">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
                                </svg> 
                            </div>
                            <p className="text-slate-700 text-sm dark:text-slate-500">
                                This is the project description
                            </p>
                            <div className="text-sm text-slate-700 dark:text-slate-500 absolute bottom-3 left-3">
                                모집인원 분야
                            </div>
                            <div className="absolute bottom-2 right-2">
                                <Link prefetch={false} href={`/project/detail/1`}>
                                    <button className="text-sm bg-transparent hover:bg-blue-500 text-blue-700 hover:text-white py-1 px-2 border border-blue-500 hover:border-transparent rounded">
                                        더보기
                                    </button>
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
  }
  