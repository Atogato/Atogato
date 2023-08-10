import { Suspense } from 'react'
import ArtistDetailContainer from './ArtistDetailContainer'
import ArtistDetailContents from './ArtistDetailContents'

export default async function ArtistDetailPage({ params }: { params: { artist_id: string } }) {
  const artistData = await fetch(process.env.BACKEND_API_URL + `artists/${params.artist_id}`, { cache: 'no-store' })
  const artistInfo = await artistData.json()
  return (
    <ArtistDetailContainer>
      <Suspense>
        <ArtistDetailContents artist={artistInfo} />
      </Suspense>
    </ArtistDetailContainer>
  )
}
