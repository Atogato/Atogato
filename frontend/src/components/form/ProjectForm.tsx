import ImageUploader from '@/components/uploader/ImageUploader'
import Editor from '@/components/editor/Editor'
import { SyntheticEvent, ChangeEvent, useState, useRef, MutableRefObject, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { localStorage } from '@/app/storage'
import Image from 'next/image'

const BACKEND_API = process.env.BACKEND_API_URL + 'projects/'

type Genre = {
  genre: string
  label: string
}

export default function ProjectForm() {
  const [token, setToken] = useState<string | null>()
  const genreRange: Genre[] = [
    {
      genre: '공연',
      label: '공연',
    },
    {
      genre: '전시',
      label: '전시',
    },
    {
      genre: '제작',
      label: '제작',
    },
    {
      genre: '기획',
      label: '기획',
    },
    {
      genre: '취미',
      label: '취미',
    },
  ]

  const router = useRouter()
  // TODO: 여러 ref 객체 관리하는 util 함수 정의(refactoring)
  const projectGenre = useRef('')
  const projectName = useRef('')
  const projectContent = useRef('')
  const requiredPeople = useRef('')
  const requiredGenre = useRef<string[]>([])

  const startPjtDate = useRef('')
  const endPjtDate = useRef('')
  const selectedArea = useRef('seoul')

  const startRequiredDate = useRef('')
  const endRequiredDate = useRef('')

  const [imageFiles, setImageFiles] = useState<File[]>([])

  const submitHandler = async (e: SyntheticEvent) => {
    e.preventDefault()
    const formData = new FormData()

    formData.append('projectArtCategory', projectGenre.current)
    formData.append('projectName', projectName.current)
    formData.append('location', selectedArea.current)
    formData.append('projectDeadline', endPjtDate.current)
    formData.append('applicationDeadline', endRequiredDate.current)
    formData.append('requiredPeople', requiredPeople.current)
    formData.append('description', projectContent.current)

    requiredGenre.current.forEach((genre) => {
      formData.append('requiredCategory', genre)
    })

    imageFiles.forEach((image, idx) => {
      formData.append('image', image, `pjtImage${idx}`)
    })

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

  useEffect(() => {
    const user = localStorage.getItem('token')
    setToken(user)
    router.prefetch('/project/list')
  }, [router, token])

  const onChangeHandler = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>, refObj: MutableRefObject<string>) => {
    refObj.current = e.target.value
  }

  const editorHandler = (value: string) => {
    projectContent.current = value
  }

  return (
    // TODO: 일반 문자로 처리한 부분을 next-translate 사용해서 t()형태로 변환
    // TODO: 반복되는 input label 컴포넌트화 및 재활용
    <form className="flex max-w-md flex-col space-y-4 pb-10" encType="multipart/form-data" onSubmit={submitHandler}>
      <div>
        <h2> 프로젝트 장르 </h2>
        <div className="flex gap-3">
          {genreRange.map((elem, idx) => {
            return (
              <div key={`pjt-${idx}`}>
                <input
                  className="mr-1.5"
                  type="radio"
                  id={`pjt-${elem.genre}`}
                  name="pjt-genre"
                  onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    onChangeHandler(e, projectGenre)
                  }}
                  value={elem.genre}
                />
                <label htmlFor={`pjt-${elem.genre}`}>{elem.label}</label>
              </div>
            )
          })}
        </div>
      </div>
      <div>
        <h2> 프로젝트 이름 </h2>
        <input
          className="w-full border-2"
          type="text"
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            onChangeHandler(e, projectName)
          }}
        />
      </div>
      <div>
        <h2> 프로젝트 소개 </h2>
        <Editor onEditorUpdated={editorHandler} />
      </div>
      <div>
        <h2> 프로젝트 기간 </h2>
        <p>
          <label htmlFor="pjt-start-period"> 시작 날짜: </label>
          <input
            className="select-all"
            type="date"
            id="pjt-start-period"
            name="pjt-start-period"
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeHandler(e, startPjtDate)
            }}
          />
        </p>
        <p>
          <label htmlFor="pjt-end-period"> 종료 날짜: </label>
          <input
            className="select-all"
            type="date"
            id="pjt-end-period"
            name="pjt-end-period"
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeHandler(e, endPjtDate)
            }}
          />
        </p>
      </div>
      <div>
        <h2> 모집 기간 </h2>
        <p>
          <label htmlFor="required-start-period"> 시작 날짜: </label>
          <input
            type="date"
            id="required-start-period"
            name="required-start-period"
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeHandler(e, startRequiredDate)
            }}
          />
        </p>
        <p>
          <label htmlFor="required-end-period"> 종료 날짜: </label>
          <input
            type="date"
            id="required-end-period"
            name="required-end-period"
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              onChangeHandler(e, endRequiredDate)
            }}
          />
        </p>
      </div>
      <div>
        <h2> 모집 인원 </h2>
        <input
          className="w-5 border-2"
          type="text"
          name="people"
          id="people"
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            onChangeHandler(e, requiredPeople)
          }}
        />
        <label htmlFor="people"> 명 </label>
      </div>
      <div>
        <h2> 활동 지역</h2>
        <select
          className="border-2"
          name="area"
          defaultValue={selectedArea.current}
          onChange={(e: ChangeEvent<HTMLSelectElement>) => {
            onChangeHandler(e, selectedArea)
          }}
        >
          <option defaultChecked value="seoul">
            서울
          </option>
          <option value="busan">부산</option>
          <option value="goyang">고양</option>
          <option value="incheon">인천</option>
        </select>
      </div>
      <div>
        <h2> 필요한 역할 </h2>
        <div className="flex gap-3">
          {genreRange.map((elem, idx) => {
            return (
              <div key={`required-${idx}`}>
                <input
                  className="mr-1.5"
                  type="checkbox"
                  id={`required-${elem.genre}`}
                  name="required-genre"
                  onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    requiredGenre.current.push(e.target.value)
                  }}
                  value={elem.genre}
                />
                <label htmlFor={`required-${elem.genre}`}>{elem.label}</label>
              </div>
            )
          })}
        </div>
      </div>
      <div className="w-full">
        <h2> 소개 이미지 </h2>
        <div className="flex flex-wrap gap-4">
          {imageFiles.map((imageFile, idx) => {
            const previewUrl = URL.createObjectURL(imageFile)
            return (
              <div key={`preview-image-${idx}`} className="aspect-square w-2/12">
                <Image
                  className="object-fit h-full w-full"
                  src={previewUrl}
                  alt={`preview-image-${idx}`}
                  width={300}
                  height={300}
                ></Image>
              </div>
            )
          })}
          <ImageUploader
            className="aspect-square w-2/12"
            onImageUpload={(image) => {
              setImageFiles((prev) => [...prev, image])
            }}
          />
        </div>
      </div>
      <button className="border-2"> 등록하기 </button>
    </form>
  )
}
