import ArtistCard from './ArtistCard'
import { Artists } from '../page'

type filteredListProps = {
  options: string[]
  artists: Artists[]
}
export default function Filteredlist({ options, artists }: filteredListProps): JSX.Element {
  const filterbyAll = artists.filter(
    (artist) => artist.location === options[0] && artist.creatorArtCategory === options[1],
  )
  const filterbyLocation = artists.filter((artist) => artist.location === options[0])
  const filterbyCategory = artists.filter((artist) => artist.creatorArtCategory === options[1])

  return (
    <div>
      <div className="my-2 text-left text-2xl">ARTISTS</div>
      <div className="grid grid-cols-3 place-content-center gap-4">
        {options[0] === 'All' && options[1] === 'All'
          ? artists.map((artist) => <ArtistCard artist={artist} key={artist.artistId} />)
          : options[0] !== 'All' && options[1] !== 'All'
          ? filterbyAll.map((artist) => <ArtistCard artist={artist} key={artist.artistId} />)
          : options[0] !== 'All' && options[1] === 'All'
          ? filterbyLocation.map((artist) => <ArtistCard artist={artist} key={artist.artistId} />)
          : options[0] === 'All' && options[1] !== 'All'
          ? filterbyCategory.map((artist) => <ArtistCard artist={artist} key={artist.artistId} />)
          : null}
      </div>
    </div>
  )
}
