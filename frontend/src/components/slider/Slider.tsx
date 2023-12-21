'use client'

import Flicking from '@egjs/react-flicking'
import '@egjs/react-flicking/dist/flicking.css'
import Image from 'next/image'

export default function Slider() {
  const images = [
    '/images/sample/projects/project1.png',
    '/images/sample/projects/project2.png',
    '/images/sample/projects/project3.png',
    '/images/sample/projects/project4.png',
    '/images/sample/projects/project5.png',
    '/images/sample/projects/project6.png',
    '/images/sample/projects/project7.png',
    '/images/sample/projects/project8.png',
    '/images/sample/projects/project9.png',
  ]

  return (
    <Flicking
      style={{ minHeight: '450px', overflow: 'visible', overflowX: 'hidden', display: 'flex', alignItems: 'center' }}
      align="prev"
      cameraClass="flex gap-5 animate-[scroll_25s_linear_infinite]"
      preventDefaultOnDrag={true}
      disableOnInit={true}
    >
      {images.map((image, idx) => {
        return (
          <div key={idx} className="h-[300px] w-[300px]">
            <Image className="h-full w-full" src={image} alt="image" width={400} height={400}></Image>
          </div>
        )
      })}
      {images.map((image, idx) => {
        return (
          <div key={`${idx}-2`} className="h-[300px] w-[300px]">
            <Image className="h-full w-full" src={image} alt="image" width={400} height={400}></Image>
          </div>
        )
      })}
    </Flicking>
  )
}
