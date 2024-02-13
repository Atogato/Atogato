'use client'
import Filter from './components/Filter'
import { useEffect, useState } from 'react'

export type Artists = {
  artistId: number
  artistName: string
  location: string
  description: string
  creatorArtCategory: string
  liked: number
}

export default function List() {
  const [result, setResult] = useState<Artists[]>([])

  useEffect(() => {
    const api = async () => {
      const data = await fetch(process.env.BACKEND_API_URL + 'artists', {
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
      <div>
        <Filter data={result} />
      </div>
    </div>
  )
}
