import ImageUploader from '@/components/uploader/ImageUploader'
import Editor from '@/components/editor/Editor'
import { SyntheticEvent, ChangeEvent, useEffect, useRef, MutableRefObject, useState } from 'react'
import { useRouter } from 'next/navigation'
import { localStorage } from '@/app/storage'
import Check from '@/icons/check.svg'

const BACKEND_API = process.env.BACKEND_API_URL + 'artists/'

type Genre = {
  genre: string
  label: string
}

async function requestPOST(formData: FormData, url: string, token: string | null | undefined) {
  const res = await fetch(url, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData,
  })
  return res
}

export default function ArtistForm() {
  // TODO: data fetching으로 장르 정보 불러오기
  const genreRange: Genre[] = [
    {
      genre: '연기',
      label: '연기',
    },
    {
      genre: '노래',
      label: '노래',
    },
    {
      genre: '춤',
      label: '춤',
    },
    {
      genre: '제작',
      label: '제작',
    },
    {
      genre: '작곡',
      label: '작곡',
    },
  ]

  const router = useRouter()
  // TODO: 여러 ref 객체 관리하는 util 함수 정의(refactoring)
  const artistGenre = useRef('')
  const artistName = useRef('')
  const artistContent = useRef('')
  const artistInterestGenreFirst = useRef('')
  const artistInterestGenreSecond = useRef('')
  const artistInterestGenreThird = useRef('')

  const [imageFile, setImageFile] = useState<File>()
  const [token, setToken] = useState<string | null>(null)

  // TODO: 서버로 FormData 전송
  const submitHandler = async (e: SyntheticEvent) => {
    e.preventDefault()
    const formData = new FormData()
    formData.append('creatorArtCategory', artistGenre.current)
    formData.append('artistName', artistName.current)
    formData.append('description', artistContent.current)
    formData.append('interestCategory', artistInterestGenreFirst.current)
    if (imageFile) {
      formData.append('mainImage', imageFile)
    }

    // TODO: refresh token 전송 및 access Token 재발급 custom hook
    const res = await requestPOST(formData, BACKEND_API, token)
    switch (res.status) {
      case 200:
        router.replace('/project/list')
        return
      case 401:
        const refreshed = await fetch('/api/auth/refresh', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        const refreshData = await refreshed.json()
        if (refreshData.status === 200 && refreshData.token) {
          localStorage.setItem('token', refreshData.token)
          setToken(refreshData.token)
        }
        const refreshResponse = await requestPOST(formData, BACKEND_API, refreshData.token)
        if (refreshResponse.ok) {
          router.replace('/artist/list')
        } else {
          localStorage.removeItem('token')
          router.replace('/auth/login')
        }
        return
      default:
        console.error('ProjectForm request failed')
    }
  }

  const onChangeHandler = (refObj: MutableRefObject<string>, value: string) => {
    refObj.current = value
  }

  const editorHandler = (value: string) => {
    artistContent.current = value
  }
  useEffect(() => {
    const user = localStorage.getItem('token')
    if (user) {
      setToken(user)
      router.prefetch('/project/list')
      return
    }
    router.replace('/auth/login/')
  }, [router, token])

  return (
    // TODO: 일반 문자로 처리한 부분을 next-translate 사용해서 t()형태로 변환
    // TODO: 반복되는 input label 컴포넌트화 및 재활용
    <form
      className="flex w-[1024px] flex-col space-y-24 pb-10 ps-[40px] pt-[36px]"
      encType="multipart/form-data"
      onSubmit={submitHandler}
    >
      <div>
        <h2 className="text-2xl text-[#171616]"> 아티스트 장르 </h2>
        <fieldset className="mt-5 flex gap-14">
          {genreRange.map((elem, idx) => {
            return (
              <div key={`pjt-${idx}`} className="group/checkbox flex items-center">
                <input
                  className="peer hidden"
                  type="radio"
                  id={`artist-${elem.genre}`}
                  name="artist-genre"
                  onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    onChangeHandler(artistGenre, e.target.value)
                  }}
                />
                <label
                  className="mr-4 inline-block h-7 w-7 rounded border border-solid border-[#E1E1E1] hover:cursor-pointer peer-checked:hidden"
                  htmlFor={`artist-${elem.genre}`}
                />
                <label
                  className="relative mr-4 hidden h-7 w-7 rounded border border-solid border-[#E1E1E1] hover:cursor-pointer peer-checked:inline-block peer-checked:bg-[#7960BE]"
                  htmlFor={`artist-${elem.genre}`}
                >
                  <Check
                    width={15}
                    height={15}
                    className="absolute left-2/4 top-2/4 translate-x-[-50%] translate-y-[-50%]"
                  />
                </label>
                <label htmlFor={`artist-${elem.genre}`} className="text-base">
                  {elem.label}
                </label>
              </div>
            )
          })}
        </fieldset>
      </div>
      <div className="flex flex-col gap-4">
        <div className="flex gap-3">
          <h2 className="inline-block text-2xl text-[#171616]"> 관심 세부 분야 1 </h2>
          <input
            type="text"
            id="artist-interest-genre-1"
            name="artist-interest-genre-1"
            className="grow border"
            placeholder="예시)힙합"
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeHandler(artistInterestGenreFirst, e.target.value)
            }}
          />
        </div>
        <div className="flex gap-3">
          <h2 className="inline-block text-2xl text-[#171616]"> 관심 세부 분야 2 </h2>
          <input
            type="text"
            id="artist-interest-genre-2"
            name="artist-interest-genre-2"
            className="grow border"
            placeholder="예시)힙합"
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeHandler(artistInterestGenreSecond, e.target.value)
            }}
          />
        </div>
        <div className="flex gap-3">
          <h2 className="inline-block text-2xl text-[#171616]"> 관심 세부 분야 3 </h2>
          <input
            type="text"
            id="artist-interest-genre-3"
            name="artist-interest-genre-3"
            className="grow border"
            placeholder="예시)힙합"
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeHandler(artistInterestGenreThird, e.target.value)
            }}
          />
        </div>
      </div>
      <div className="flex flex-col gap-4">
        <h2 className="text-2xl text-[#171616]"> 닉네임 </h2>
        <input
          className="w-full border p-4"
          type="text"
          placeholder="닉네임을 입력해주세요."
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            onChangeHandler(artistName, e.target.value)
          }}
        />
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 아티스트 소개 </h2>
        <Editor className="mt-5 h-96 w-full gap-y-4" onEditorUpdated={editorHandler} />
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 메인 이미지 </h2>
        <div className="mt-4 flex flex-wrap gap-4">
          <ImageUploader
            className="flex w-full flex-col gap-4"
            onImageUpload={(image) => {
              setImageFile(image)
            }}
          />
        </div>
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 보조 이미지 </h2>
        <div className="mt-4 flex flex-wrap gap-4">
          <ImageUploader
            className="flex w-full flex-col gap-4"
            onImageUpload={(image) => {
              setImageFile(image)
            }}
          />
        </div>
      </div>
      <button className="border-2"> 등록하기 </button>
    </form>
  )
}
