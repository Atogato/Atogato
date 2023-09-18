'use client'

import { useEffect, useState } from 'react'
import { localStorage } from '@/app/storage'
import Phone from './Phone'

export type Artists = {
  artistId: number
  artistName: string
  location: string
  description: string
  creatorArtCategory: string
}

export default function SwipeArtist() {
  const [result, setResult] = useState<Artists[]>([])
  const [dataLoaded, setDataLoaded] = useState(false)
  const [token, setToken] = useState<string | null>()

  useEffect(() => {
    const user = localStorage.getItem('token')
    setToken(user)
  }, [token])

  useEffect(() => {
    const api = async () => {
      const data = await fetch('http://localhost:7072/api/artists', {
        method: 'GET',
      })
      const jsonData = await data.json()
      setResult(jsonData)
      setDataLoaded(true)
    }

    api()
  }, [])
  const endofDataset = [
    {
      artistId: 1,
      artistName: 'End',
      description: 'No more users',
      location: '//',
      creatorArtCategory: '//',
    },
  ]

  const [currentIndex, setCurrentIndex] = useState(0)
  const [chosenGenre, setChosenGenre] = useState('All')
  const [filteredUsers, setFilteredUsers] = useState<Artists[]>([])

  const handleRight = async () => {
    try {
      const response = await fetch(
        `http://localhost:7072/api/artists/swipe/like?receiverId=${filteredUsers[currentIndex].artistId}`,
        {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      )
    } catch (error) {
      console.error('Error sending message:', error)
    }
    setCurrentIndex((prevIndex) => prevIndex + 1)
  }
  const handleLeft = async () => {
    try {
      const data = {
        receiverId: filteredUsers[currentIndex].artistId.toString(),
      }
      const response = await fetch('http://localhost:7072/api/artists/swipe/reject', {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(data),
      })
    } catch (error) {
      console.error('Error sending message:', error)
    }
    setCurrentIndex((prevIndex) => prevIndex + 1)
  }

  useEffect(() => {
    if (chosenGenre === 'All') {
      setFilteredUsers([...result])
    } else {
      setFilteredUsers([...result.filter((user) => user.creatorArtCategory === chosenGenre)])
    }
  }, [chosenGenre, dataLoaded])

  const isEndOfDataset = currentIndex >= filteredUsers.length
  const user = isEndOfDataset ? endofDataset[0] : filteredUsers[currentIndex]
  return (
    <div className="grid grid-cols-6">
      <div className="col-span-1 col-start-1">
        <div
          onClick={() => setChosenGenre('All')}
          className={`m-4 h-14 w-14 rounded-full ${
            chosenGenre === 'All' ? 'bg-lime-500' : 'bg-zinc-500 hover:bg-lime-500'
          } flex items-center justify-center text-white`}
        >
          <span className="text-lg font-bold">All</span>
        </div>
        <div
          onClick={() => setChosenGenre('Rock')}
          className={`m-4 h-14 w-14 rounded-full ${
            chosenGenre === 'Rock' ? 'bg-lime-500' : 'bg-zinc-500 hover:bg-lime-500'
          } flex items-center justify-center text-white`}
        >
          <span className="text-lg font-bold">Rock</span>
        </div>
        <div
          onClick={() => setChosenGenre('Pop')}
          className={`m-4 h-14 w-14 rounded-full ${
            chosenGenre === 'Pop' ? 'bg-lime-500' : 'bg-zinc-500 hover:bg-lime-500'
          } flex items-center justify-center text-white`}
        >
          <span className="text-lg font-bold">Pop</span>
        </div>
        <div
          onClick={() => setChosenGenre('Indie')}
          className={`m-4 h-14 w-14 rounded-full ${
            chosenGenre === 'Indie' ? 'bg-lime-500' : 'bg-zinc-500 hover:bg-lime-500'
          } flex items-center justify-center text-white`}
        >
          <span className="text-lg font-bold">Indie</span>
        </div>
      </div>
      <div className="col-span-1 col-start-2 mr-2 flex items-center justify-self-end">
        <div
          className="h-0 w-0 
                        border-b-[15px] border-r-[27px]
                        border-t-[15px] border-b-transparent
                        border-r-red-400 border-t-transparent"
          onClick={handleLeft}
        ></div>
      </div>
      <div className="col-span-2 col-start-3 flex items-center justify-center">
        <Phone user={user} />
      </div>
      <div className="ml-2 flex items-center">
        <div
          className="h-0 w-0 
                    border-b-[15px] border-l-[27px]
                    border-t-[15px] border-b-transparent
                    border-l-blue-400 border-t-transparent"
          onClick={handleRight}
        ></div>
      </div>
    </div>
  )
}
