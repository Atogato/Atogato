'use client'
import Filter from './components/Filter';
import { useEffect, useState } from 'react';

export type Artists = {
    artistId: number, 
    artistName: string, 
    location: string, 
    description: string,
    creatorArtCategory: string, 
  }

  
export default function List(){
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
      <div className="max-w-5xl mx-auto my-5">
          <div className="text-2xl text-left my-2">
              Filters
          </div>
          <Filter data={result}/>
      </div>
  </div>
);
};

