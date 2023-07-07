'use client'

import {DataObject} from  '../page'

interface DataProps {
    user: DataObject;
  }

export default function Phone({ user }: DataProps): JSX.Element{
    console.log(user)
    return(
        <div className="bg-gray-200 rounded-3xl w-full h-full min-w-[320px] max-w-[350px] min-h-[600px] max-h-[650px] mx-auto relative overflow-auto  border-4 border-zinc-400 " >
            <img alt="name" src="/images/sample/image.png" className=" w-full h-full object-cover rounded-t-2xl" />
            <div className="absolute inset-0 bg-gradient-to-b from-transparent to-black opacity-100 rounded-t-3xl"></div>
            <div className="absolute  h-full w-full bg-gradient-to-b from-black to-transparent opacity-100"></div>
            <div className="absolute bottom-0 left-0 right-0 top-0">
                <div className="h-4/6"></div>
                <div className="p-4 text-white">
                    <div className='my-3 space-y-2'>
                        <h2 className="text-xl font-bold">{user.userName}</h2>
                        <p className="text-sm">{user.genre}</p>
                        <p className="text-sm">{user.location}</p>
                    </div>
                    <p className="text-sm">"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</p>
                </div>
            </div>
            <div className='bg-white h-full w-full'>

            </div>
            <img alt="name" src="/images/sample/image.png" className="mb-2" />
            <img alt="name" src="/images/sample/image.png" className="mb-2" />
            <img alt="name" src="/images/sample/image.png" className="rounded-b-2xl" />
        </div>
    )
}