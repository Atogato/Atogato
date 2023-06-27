import ImageUploader from '@/components/uploader/ImageUploader'
import Editor from '@/components/editor/Editor'
import { SyntheticEvent, ChangeEvent, useState, useRef, MutableRefObject, useEffect } from 'react'

// image file FormData 전송 테스트용
async function toBase64(file: File) {
  return new Promise((resolve, reject) => {
    const fileReader = new FileReader()
    fileReader.readAsDataURL(file)
    fileReader.onloadend = () => {
      resolve(fileReader.result)
    }
    fileReader.onerror = (error) => {
      reject(error)
    }
  })
}

type Genre = {
  genre: string
  label: string
}

export default function ProjectForm() {
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

  // TODO: 여러 ref 객체 관리하는 util 함수 정의(refactoring)
  const projectGenre = useRef('')
  const projectName = useRef('')
  const projectContent = useRef('')
  const requiredPeople = useRef('')
  const requiredGenre = useRef('')

  const startPjtDate = useRef('')
  const endPjtDate = useRef('')
  const selectedArea = useRef('seoul')

  const startRequiredDate = useRef('')
  const endRequiredDate = useRef('')
  const youTubeLink = useRef('')

  // const [imageFiles, setImageFiles] = useState<File[]>([])
  const [imageFiles, setImageFiles] = useState<string[]>([])

  // TODO: 서버로 FormData 전송
  const submitHandler = async (e: SyntheticEvent) => {
    e.preventDefault()
    const formData = new FormData()
    const jsonData = {
      userId: '1',
      genre: projectGenre.current,
      projectName: projectName.current,
      location: selectedArea.current,
      deadline: endRequiredDate.current,
      requiredPeople: requiredPeople.current,
      description: projectContent.current,
    }
    formData.append('info', JSON.stringify(jsonData))
    // formData.append('genre', projectGenre.current)
    // formData.append('projectName', projectName.current)
    // formData.append('location', selectedArea.current)
    // formData.append('deadline', endRequiredDate.current)
    // formData.append('requiredPeople', requiredPeople.current)
    // formData.append('description', projectContent.current)

    // TODO: image file을 실제로 보낼 때에는 File 객체 형태로 보내야 함
    // imageFiles.forEach((image, idx) => {
    //   formData.append('files', image, `pjtImage${idx}`)
    // })
    formData.append('images', JSON.stringify(imageFiles))

    await fetch('/api/projects', {
      method: 'POST',
      body: formData,
    })
  }

  const onChangeHandler = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>, refObj: MutableRefObject<string>) => {
    refObj.current = e.target.value
  }

  const editorHandler = (value: string) => {
    projectContent.current = value
  }

  return (
    // TODO: 일반 문자로 처리한 부분을 next-translate 사용해서 t()형태로 변환
    // TODO: 반복되는 input label 컴포넌트화 및 재활용
    <form className="flex flex-col space-y-4 pb-10" encType="multipart/form-data" onSubmit={submitHandler}>
      <div>
        <h2> 프로젝트 장르 </h2>
        <div>
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
      <div className="h-52">
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
        <div>
          {genreRange.map((elem, idx) => {
            return (
              <div key={`required-${idx}`}>
                <input
                  className="mr-1.5"
                  type="radio"
                  id={`required-${elem.genre}`}
                  name="required-genre"
                  onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    onChangeHandler(e, requiredGenre)
                  }}
                  value={elem.genre}
                />
                <label htmlFor={`required-${elem.genre}`}>{elem.label}</label>
              </div>
            )
          })}
        </div>
      </div>
      <div>
        <h2> 소개 이미지 </h2>
        <ImageUploader
          onImageUpload={async (image) => {
            const base64 = await toBase64(image)
            setImageFiles((prev) => [...prev, base64 as string])
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
              onChange={(e: ChangeEvent<HTMLInputElement>) => {
                onChangeHandler(e, youTubeLink)
              }}
            />
          </div>
        </div>
      </div>
      <button className="border-2"> 등록하기 </button>
    </form>
  )
}
