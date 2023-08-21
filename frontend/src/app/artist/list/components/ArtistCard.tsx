import Link from 'next/link'
import { Artists } from '../page'

type artistsProps = {
  artist: Artists
}

export default function ArtistCard({ artist }: artistsProps): JSX.Element {
  return (
    <div className="flex rounded-lg bg-slate-100">
      <img className="md:w-40 md:rounded-l-lg" src="/image.png" width="300" height="300" />
      <div className="relative space-y-2 text-center md:p-3 md:text-left">
        <figcaption className="font-medium">
          <div className="mr-4 text-sky-500 dark:text-sky-400">{artist.artistName}</div>
          <div className="text-sm text-slate-700 dark:text-slate-500">{artist.creatorArtCategory}</div>
        </figcaption>
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
          {artist.liked}
        </div>
        <p className="text-sm text-slate-700 dark:text-slate-500">{artist.description}</p>
        <div className="absolute bottom-2 right-2">
          <Link prefetch={false} href={`/artist/detail/${artist.artistId}`}>
            <button className="rounded border border-blue-500 bg-transparent px-2 py-1 text-sm text-blue-700 hover:border-transparent hover:bg-blue-500 hover:text-white">
              더보기
            </button>
          </Link>
        </div>
      </div>
    </div>
  )
}
