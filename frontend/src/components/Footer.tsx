export default function Footer() {
  return (
    <div>
      <footer className="rounded-lg bg-white p-4 shadow dark:bg-gray-800 md:px-6 md:py-8">
        <div className="sm:flex sm:items-center sm:justify-between">
          <a href="#" target="_blank" className="mb-4 flex items-center sm:mb-0">
            <span className="self-center whitespace-nowrap text-xl font-semibold dark:text-white">Project</span>
          </a>
          <ul className="mb-6 flex flex-wrap items-center sm:mb-0">
            <li>
              <a href="#" className="mr-4 text-sm text-gray-500 hover:underline dark:text-gray-400 md:mr-6">
                About
              </a>
            </li>
            <li>
              <a href="#" className="mr-4 text-sm text-gray-500 hover:underline dark:text-gray-400 md:mr-6">
                Privacy Policy
              </a>
            </li>
            <li>
              <a href="#" className="mr-4 text-sm text-gray-500 hover:underline dark:text-gray-400 md:mr-6">
                Licensing
              </a>
            </li>
            <li>
              <a href="#" className="text-sm text-gray-500 hover:underline dark:text-gray-400">
                Contact
              </a>
            </li>
          </ul>
        </div>
        <hr className="my-2 border-gray-200 dark:border-gray-700 sm:mx-auto lg:my-8" />
      </footer>
    </div>
  )
}
