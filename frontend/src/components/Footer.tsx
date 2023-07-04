export default function Footer(){
    return(
        <div>
            <footer className="p-4 bg-white rounded-lg shadow md:px-6 md:py-8 dark:bg-gray-800">
                <div className="sm:flex sm:items-center sm:justify-between">
                    <a href="#" target="_blank" className="flex items-center mb-4 sm:mb-0">
                        <span className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">Project</span>
                    </a>
                    <ul className="flex flex-wrap items-center mb-6 sm:mb-0">
                        <li>
                            <a href="#" className="mr-4 text-sm text-gray-500 hover:underline md:mr-6 dark:text-gray-400">About</a>
                        </li>
                        <li>
                            <a href="#" className="mr-4 text-sm text-gray-500 hover:underline md:mr-6 dark:text-gray-400">Privacy
                                Policy</a>
                        </li>
                        <li>
                            <a href="#"
                                className="mr-4 text-sm text-gray-500 hover:underline md:mr-6 dark:text-gray-400">Licensing</a>
                        </li>
                        <li>
                            <a href="#" className="text-sm text-gray-500 hover:underline dark:text-gray-400">Contact</a>
                        </li>
                    </ul>
                </div>
                <hr className="my-2 border-gray-200 sm:mx-auto dark:border-gray-700 lg:my-8" />
            </footer>
        </div>
    )
}