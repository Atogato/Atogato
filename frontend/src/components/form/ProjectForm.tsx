import ImageUploader from '@/components/uploader/ImageUploader'
import Editor from '@/components/editor/Editor'
import { SyntheticEvent, ChangeEvent, useState, useRef, MutableRefObject, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { localStorage } from '@/app/storage'
import Check from '@/icons/check.svg'
import DatePicker from '../datePicker/DatePicker'
const BACKEND_API = process.env.BACKEND_API_URL + 'projects/'

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

  const roles: Genre[] = [
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
  const projectGenre = useRef('')
  const projectName = useRef('')
  const projectContent = useRef('')
  const requiredPeople = useRef('')
  const requiredGenre = useRef<string[]>([])

  const startPjtDate = useRef('')
  const endPjtDate = useRef('')
  const selectedArea = useRef('seoul')
  const selectedContected = useRef('online')

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
          router.replace('/project/list')
        } else {
          localStorage.removeItem('token')
          router.replace('/auth/login')
        }
        return
      default:
        console.error('ProjectForm request failed')
    }
  }

  useEffect(() => {
    const user = localStorage.getItem('token')
    if (user) {
      setToken(user)
      router.prefetch('/project/list')
    } else {
      router.replace('/auth/login/')
    }
  }, [router, token])

  const onChangeHandler = (refObj: MutableRefObject<string>, value: string) => {
    refObj.current = value
  }

  const editorHandler = (value: string) => {
    projectContent.current = value
  }

  return (
    // TODO: 일반 문자로 처리한 부분을 next-translate 사용해서 t()형태로 변환(로컬라이제이션)
    <form
      className="flex w-[1024px] flex-col space-y-24 pb-10 ps-[40px] pt-[36px]"
      encType="multipart/form-data"
      onSubmit={submitHandler}
    >
      <div>
        <h2 className="text-2xl text-[#171616]">
          <span> 프로젝트 장르 </span>
          <span className="ml-[16px] text-lg text-[#171616]/50"> 한 가지만 선택해주세요. </span>
        </h2>
        <fieldset className="mt-5 flex gap-14">
          {genreRange.map((elem, idx) => {
            return (
              <div key={`pjt-${idx}`} className="group/checkbox flex items-center">
                <input
                  className="peer hidden"
                  type="radio"
                  id={`pjt-${elem.genre}`}
                  name="pjt-genre"
                  onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    onChangeHandler(projectGenre, e.target.value)
                  }}
                />
                <label
                  className="mr-4 inline-block h-7 w-7 rounded border border-solid border-[#E1E1E1] hover:cursor-pointer peer-checked:hidden"
                  htmlFor={`pjt-${elem.genre}`}
                />
                <label
                  className="relative mr-4 hidden h-7 w-7 rounded border border-solid border-[#E1E1E1] hover:cursor-pointer peer-checked:inline-block peer-checked:bg-[#7960BE]"
                  htmlFor={`pjt-${elem.genre}`}
                >
                  <Check
                    width={15}
                    height={15}
                    className="absolute left-2/4 top-2/4 translate-x-[-50%] translate-y-[-50%]"
                  />
                </label>
                <label htmlFor={`pjt-${elem.genre}`} className="text-base">
                  {elem.label}
                </label>
              </div>
            )
          })}
        </fieldset>
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 프로젝트 이름 </h2>
        <input
          className="mt-5 w-full rounded border border-2 border-solid border-[#E1E1E1] bg-[#FAFAFA] p-4"
          type="text"
          placeholder="프로젝트를 입력해주세요."
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            onChangeHandler(projectName, e.target.value)
          }}
        />
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 프로젝트 소개 </h2>
        <Editor className="mt-5 h-96 w-full gap-y-4" onEditorUpdated={editorHandler} />
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 프로젝트 기간 </h2>
        <div className="mt-5 flex items-center gap-4">
          <DatePicker
            id="pjt-start-period"
            name="pjt-start-period"
            required={true}
            onChange={(value) => {
              onChangeHandler(startPjtDate, value)
            }}
          />
          <span> ~ </span>
          <DatePicker
            id="pjt-end-period"
            name="pjt-end-period"
            required={true}
            onChange={(value) => {
              onChangeHandler(endPjtDate, value)
            }}
          />
        </div>
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 모집 기간 </h2>
        <div className="mt-5 flex items-center gap-4">
          <DatePicker
            id="required-start-period"
            name="required-start-period"
            required={true}
            onChange={(value) => {
              onChangeHandler(startRequiredDate, value)
            }}
          />
          <span className="after:content-['~']" />
          <DatePicker
            id="required-end-period"
            name="required-end-period"
            required={true}
            onChange={(value) => {
              onChangeHandler(endRequiredDate, value)
            }}
          />
        </div>
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 활동 지역</h2>
        <div className="flex gap-4">
          <select
            className="w-64 border-2 p-2"
            name="area-1"
            defaultValue={selectedArea.current}
            onChange={(e: ChangeEvent<HTMLSelectElement>) => {
              onChangeHandler(selectedArea, e.target.value)
            }}
          >
            <option defaultChecked value="seoul">
              서울
            </option>
            <option value="busan">부산</option>
            <option value="goyang">고양</option>
            <option value="incheon">인천</option>
          </select>
          <select
            className="w-64 border-2 p-2"
            name="area-2"
            defaultValue={selectedContected.current}
            onChange={(e: ChangeEvent<HTMLSelectElement>) => {
              onChangeHandler(selectedContected, e.target.value)
            }}
          >
            <option defaultChecked value="online">
              온라인
            </option>
            <option value="offline">오프라인</option>
          </select>
        </div>
      </div>
      <div>
        <h2 className="text-2xl text-[#171616]"> 모집 인원 </h2>
        <input
          className="mt-3 w-32 border-2"
          type="text"
          name="people"
          id="people"
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            onChangeHandler(requiredPeople, e.target.value)
          }}
        />
        <label htmlFor="people"> 명 </label>
      </div>

      <div>
        <h2 className="text-2xl text-[#171616]"> 필요한 역할 </h2>
        <fieldset className="mt-5 flex gap-14">
          {roles.map((elem, idx) => {
            return (
              <div key={`required-${idx}`} className="group/checkbox flex items-center">
                <input
                  className="peer hidden"
                  type="checkbox"
                  id={`required-${elem.genre}`}
                  name="required-genre"
                  onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    requiredGenre.current.push(e.target.value)
                  }}
                  value={elem.genre}
                />
                <label
                  className="mr-4 inline-block h-7 w-7 rounded border border-solid border-[#E1E1E1] hover:cursor-pointer peer-checked:hidden"
                  htmlFor={`required-${elem.genre}`}
                />
                <label
                  className="relative mr-4 hidden h-7 w-7 rounded border border-solid border-[#E1E1E1] hover:cursor-pointer peer-checked:inline-block peer-checked:bg-[#7960BE]"
                  htmlFor={`required-${elem.genre}`}
                >
                  <Check
                    width={15}
                    height={15}
                    className="absolute left-2/4 top-2/4 translate-x-[-50%] translate-y-[-50%]"
                  />
                </label>
                <label htmlFor={`required-${elem.genre}`} className="text-base">
                  {elem.label}
                </label>
              </div>
            )
          })}
        </fieldset>
      </div>
      <div className="w-full">
        <h2 className="text-2xl text-[#171616]"> 소개 이미지 </h2>
        <div className="mt-4 flex flex-wrap gap-4">
          {/* {imageFiles.map((imageFile, idx) => {
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
          })} */}
          <ImageUploader
            className="flex w-full flex-col gap-4"
            onImageUpload={(image) => {
              setImageFiles((prev) => [...prev, image])
            }}
          />
        </div>
      </div>
      <button className="h-[64px] rounded border-2 bg-[#7960BE] py-4 text-2xl text-[#FFF]"> 등록하기 </button>
    </form>
  )
}
