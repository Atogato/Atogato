'use client'

import Image from 'next/image'
import { Artists } from './SwipeArtist'

interface ArtistProps {
  user: Artists
}

export default function Phone({ user }: ArtistProps): JSX.Element {
  return (
    <div className="relative mx-auto h-full max-h-[650px] min-h-[600px] w-full min-w-[320px] max-w-[350px] overflow-auto rounded-3xl border-4  border-zinc-400 bg-gray-200 ">
      <div style={{ width: '100%', height: '100%' }}>
        <Image
          fill={true}
          alt="name"
          src="/images/sample/image.png"
          className=" h-full w-full rounded-t-2xl object-cover"
        />
      </div>
      <div className="absolute inset-0 rounded-t-3xl bg-gradient-to-b from-transparent to-black opacity-100"></div>
      <div className="absolute  h-full w-full bg-gradient-to-b from-black to-transparent opacity-100"></div>
      <div className="absolute bottom-0 left-0 right-0 top-0">
        <div className="h-4/6"></div>
        <div className="p-4 text-white">
          <div className="my-3 space-y-2">
            <h2 className="text-xl font-bold">{user.artistName}</h2>
            <p className="text-sm">{user.creatorArtCategory}</p>
            <p className="text-sm">{user.location}</p>
          </div>
          <p className="text-sm">
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex
            ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat
            nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit
            anim id est laborum."
          </p>
        </div>
      </div>
      <div className="h-full w-full bg-white"></div>
      <Image alt="name" width={400} height={400} src="/images/sample/image.png" className="mb-2" />
      <Image alt="name" width={400} height={400} src="/images/sample/image.png" className="mb-2" />
      <Image alt="name" width={400} height={400} src="/images/sample/image.png" className="rounded-b-2xl" />
    </div>
  )
}
