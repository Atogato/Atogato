'use client'

import { useEffect, useState } from 'react'

import Phone from './Phone'

export type DataObject = {
  userId: number
  userName: string
  description: string
  location: string
  genre: string
}

export default function SwipeArtist() {
  const endofDataset = [
    {
      userId: 1,
      userName: 'End',
      description: 'No more users',
      location: '//',
      genre: '//',
    },
  ]
  const dataset = [
    {
      userId: 1,
      userName: 'John Doe',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      location: 'New York',
      genre: 'Rock',
    },
    {
      userId: 2,
      userName: 'Jane Smith',
      description: 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',
      location: 'Los Angeles',
      genre: 'Pop',
    },
    {
      userId: 3,
      userName: 'Mike Johnson',
      description: 'Ut rhoncus turpis velit, ac gravida enim condimentum ut.',
      location: 'Chicago',
      genre: 'Hip Hop',
    },
    {
      userId: 4,
      userName: 'Emily Davis',
      description: 'Sed fermentum dolor ut arcu fringilla, at varius odio vestibulum.',
      location: 'Miami',
      genre: 'Indie',
    },
    {
      userId: 5,
      userName: 'Alex Thompson',
      description:
        'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Cras tristique tempus pulvinar.',
      location: 'San Francisco',
      genre: 'Indie',
    },
  ]

  const [currentIndex, setCurrentIndex] = useState(0)
  const [chosenGenre, setChosenGenre] = useState('All')
  const [filteredUsers, setFilteredUsers] = useState<DataObject[]>([])

  const handleNext = () => {
    const currentObject = dataset[currentIndex]
    setCurrentIndex((prevIndex) => prevIndex + 1)
  }

  useEffect(() => {
    if (chosenGenre === 'All') {
      setFilteredUsers([...dataset])
    } else {
      setFilteredUsers([...dataset.filter((user) => user.genre === chosenGenre)])
    }
  }, [chosenGenre])
  //     const filteredUsers: DataObject[] = chosenGenre === 'All'
  //   ? dataset
  //   : dataset.filter((user) => user.genre === chosenGenre);

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
          onClick={handleNext}
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
          onClick={handleNext}
        ></div>
      </div>
    </div>
  )
}
