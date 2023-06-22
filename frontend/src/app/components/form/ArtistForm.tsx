'use client'
import ImageUploader from '@/components/uploader/ImageUploader'
import Editor from '@/components/editor/Editor'
import { SyntheticEvent, useEffect } from 'react'

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

  // TODO: 서버로 FormData 전송
  const submitHandler = (e: SyntheticEvent) => {
    e.preventDefault()
  }

  const editorHandler = () => {}
  const dateHandler = () => {}

  useEffect(() => {}, [])

  return (
    // TODO: 일반 문자로 처리한 부분을 next-translate 사용해서 t()형태로 변환
    // TODO: 반복되는 input label 컴포넌트화 및 재활용
    <form className="flex flex-col space-y-4 pb-10" onSubmit={submitHandler}>
      <div>
        <h2> 아티스트 장르 </h2>
        <div>
          {genreRange.map((elem, idx) => {
            return (
              <div key={`artist-${idx}`}>
                <input className="mr-1.5" type="radio" id={`artist-${elem.genre}`} name="genre" />
                <label htmlFor={`artist-${elem.genre}`}>{elem.label}</label>
              </div>
            )
          })}
        </div>
      </div>
      <div>
        <h2> 프로젝트 이름 </h2>
        <input className="w-full border-2" type="text" />
      </div>
      <div className="h-52">
        <h2> 프로젝트 소개 </h2>
        <Editor onEditorUpdated={editorHandler} />
      </div>
      <div>
        <h2> 모집 인원 </h2>
        <input className="w-5 border-2" type="text" name="people" id="people" />
        <label htmlFor="people"> 명 </label>
      </div>
      <div>
        <h2> 활동 지역</h2>
        <select className="border-2" name="area">
          <option value="seoul">서울</option>
          <option value="busan">부산</option>
          <option value="goyang">고양</option>
          <option value="incheon">인천</option>
        </select>
      </div>
      <div>
        <h2> 소개 이미지 </h2>
        <ImageUploader onImageUpload={(image) => {}} />
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
