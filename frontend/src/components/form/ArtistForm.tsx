import ImageUploader from '@/components/uploader/ImageUploader'
import Editor from '@/components/editor/Editor'
import { SyntheticEvent, ChangeEvent, useEffect, useRef, MutableRefObject, useState } from 'react'
import { useRouter } from 'next/navigation'
import { localStorage } from '@/app/storage'

const BACKEND_API = 'http://localhost:7072/api/artists'

type Genre = {
  genre: string
  label: string
}

export default function ArtistForm() {
  // TODO: data fetching으로 장르 정보 불러오기
  const genreRange: Genre[] = [
    {
      genre: 'acting',
      label: '연기',
    },
    {
      genre: 'making',
      label: '제작',
    },
    {
      genre: 'singing',
      label: '노래',
    },
    {
      genre: 'dancing',
      label: '춤',
    },
  ]

  const router = useRouter()
  // TODO: 여러 ref 객체 관리하는 util 함수 정의(refactoring)
  const artistGenre = useRef('')
  const artistName = useRef('')
  const artistContent = useRef('')
  const artistInterestGenre = useRef('')

  const [imageFile, setImageFile] = useState<File>()
  const [token, setToken] = useState<string | null>(null)

  // TODO: 서버로 FormData 전송
  const submitHandler = async (e: SyntheticEvent) => {
    e.preventDefault()
    const formData = new FormData()
    formData.append('creatorArtCategory', artistGenre.current)
    formData.append('artistName', artistName.current)
    formData.append('description', artistContent.current)
    formData.append('interestCategory', artistInterestGenre.current)
    if (imageFile) {
      formData.append('mainImage', imageFile)
    }

    const res = await fetch(BACKEND_API, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: formData,
    })
    if (res.ok) {
      router.replace('/project/list')
    }
  }

  const onChangeHandler = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>, refObj: MutableRefObject<string>) => {
    refObj.current = e.target.value
  }

  const editorHandler = (value: string) => {
    artistContent.current = value
  }
  useEffect(() => {
    const user = localStorage.getItem('token')
    setToken(user)
    router.prefetch('/project/list')
  }, [router, token])

  return (
    // TODO: 일반 문자로 처리한 부분을 next-translate 사용해서 t()형태로 변환
    // TODO: 반복되는 input label 컴포넌트화 및 재활용
    <form className="flex flex-col space-y-4 pb-10" encType="multipart/form-data" onSubmit={submitHandler}>
      <div>
        <h2> 아티스트 장르 </h2>
        <div>
          {genreRange.map((elem, idx) => {
            return (
              <div key={`artist-${idx}`}>
                <input
                  className="mr-1.5"
                  type="radio"
                  id={`artist-${elem.genre}`}
                  name="genre"
                  onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    onChangeHandler(e, artistGenre)
                  }}
                />
                <label htmlFor={`artist-${elem.genre}`}>{elem.label}</label>
              </div>
            )
          })}
        </div>
      </div>
      <div>
        <h2> 관심 세부 분야 </h2>
        <input
          type="text"
          id="artist-interest-genre"
          name="artist-interest-genre"
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            onChangeHandler(e, artistInterestGenre)
          }}
        />
      </div>
      <div>
        <h2> 아티스트 이름 </h2>
        <input
          className="w-full border-2"
          type="text"
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            onChangeHandler(e, artistName)
          }}
        />
      </div>
      <div className="h-52">
        <h2> 아티스트 소개 </h2>
        <Editor onEditorUpdated={editorHandler} />
      </div>
      <div>
        <h2> 메인 이미지 </h2>
        <ImageUploader
          onImageUpload={(image) => {
            setImageFile(image)
          }}
        />
      </div>
      <div>
        <h2> 참고 링크 </h2>
        <div>
          <div>
            <label className="w-1/6" htmlFor="youtube">
              유튜브{' '}
            </label>
            <input
              className="w-5/6 border-2"
              type="url"
              id="youtube"
              name="youtube"
              pattern="https://.*"
              placeholder="https://example.com"
            />
          </div>
        </div>
      </div>
      <button className="border-2"> 등록하기 </button>
    </form>
  )
}
