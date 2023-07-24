'use client'
import Filter from './components/Filter'
import { useEffect, useState } from 'react'

export type Artists = {
  artistId: number
  artistName: string
  location: string
  description: string
  creatorArtCategory: string
}

export default function List() {
  const [result, setResult] = useState<Artists[]>([])

  useEffect(() => {
    const api = async () => {
      const data = await fetch('http://localhost:7072/api/artists', {
        method: 'GET',
      })
      const jsonData = await data.json()
      setResult(jsonData)
    }

    api()
  }, [])
  console.log(result)
  return (
    <div>
      <div className="mx-auto my-5 max-w-5xl">
        <div className="my-2 text-left text-2xl">Filters</div>
        <Filter data={result} />
      </div>
    </div>
  )
}
