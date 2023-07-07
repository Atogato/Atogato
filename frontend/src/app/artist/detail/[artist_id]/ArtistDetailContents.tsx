'use client'
import Image from 'next/image'
import dynamic from 'next/dynamic'
import { useState } from 'react'

const PdfViewer = dynamic(() => import('@/components/pdf-viewer'), { ssr: false })

export default function ArtistDetailContents() {
  const DUMMY = {
    name: 'test',
    genre: '연기',
    description: '안녕하세요. 저는 xx아티스트 입니다.',
    detail: ['무술', '액션', '웹드라마'],
    images: ['image', 'image', 'image', 'image', 'image', 'image'],
  }

  const [isOpen, setIsOpen] = useState(false)

  function toggle() {
    setIsOpen((status) => !status)
  }

  return (
    <article>
      <h1 className="hide"> 아티스트 페이지 </h1>
      <section className="flex justify-between">
        <h2 className="hide"> 아티스트 정보 </h2>
        <div className="w-full">
          <ul>
            <li> 이름: {DUMMY.name} </li>
            <li> 장르: {DUMMY.genre} </li>
          </ul>
          <div>
            <p>{DUMMY.description}</p>
          </div>
        </div>
        <div>
          <div className="relative">
            <Image
              className="h-auto w-full"
              src="/images/sample/image.png"
              alt="artist-image"
              width={300}
              height={300}
            ></Image>
            <div className="absolute bottom-1 right-2 text-white">
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
            </div>
          </div>

          <button className="block" onClick={() => {}}>
            메세지 보내기
          </button>
        </div>
      </section>
      <section>
        <h2 className="hide"> 관심 분야 </h2>
        <ul>
          <li> 관심 분야: </li>
          <li> 관심 분야: </li>
          <li> 관심 분야: </li>
        </ul>
      </section>
      <section>
        <h2 className="hide"> 아티스트 소개 이미지 </h2>
        <div>
          <ul className="gird-rows-2 grid grid-cols-3 ">
            {DUMMY.images.map((_, idx) => {
              return (
                <li key={idx} className="p-4">
                  <Image
                    className="h-auto w-full"
                    src="/images/sample/image.png"
                    alt={`artist-detail-image${idx}`}
                    width={300}
                    height={300}
                    priority
                  ></Image>
                </li>
              )
            })}
          </ul>
        </div>
      </section>
      <div className="text-center">
        <h2 className="hide"> 포트폴리오 파일 조회 </h2>
        <button
          onClick={() => {
            toggle()
          }}
          className="border-2 hover:bg-sky-400 hover:text-blue-700"
        >
          포트폴리오 보기
        </button>
        {isOpen ? <PdfViewer /> : <></>}
      </div>
      <div>
        <h2 className="hide">SNS 링크</h2>
        <ul>
          <li> 유튜브 </li>
          <li> 인스타그램 </li>
          <li> 트위터 </li>
        </ul>
      </div>
    </article>
  )
}
