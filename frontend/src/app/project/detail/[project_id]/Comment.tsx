'use client'
import { useEffect, useState, KeyboardEvent } from 'react'
import Head from 'next/head'
import { localStorage } from '@/app/storage'

interface IdType {
  id: string
}

type Comments = {
  commentId: number
  projectId: number
  commentUserId: string
  comment: string
  createdDate: string
}

export default function Comment({ id }: IdType): JSX.Element {
  const idInt = parseInt(id, 10)
  const [token, setToken] = useState<string | null>()
  const [comment, setComment] = useState<string>('')
  const [result, setResult] = useState<Comments[]>([])
  const [editComment, setEditComment] = useState<[number, string]>([0, ''])
  const [currentCommentId, setCurrentCommentId] = useState<number>()

  //  const user = localStorage.removeItem('token')

  useEffect(() => {
    const user = localStorage.getItem('token')
    setToken(user)
  }, [token])
  console.log('token', token)

  // const getComment = async () => {
  //   const data = await fetch(`http://localhost:7072/api/projects/comments/${id}`, {
  //     method: 'GET',
  //   })
  //   const jsonData = await data.json()
  //   setResult(jsonData)
  // }
  // useEffect(() => {
  //   getComment()
  // }, [])
  // console.log(result)

  const onChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const commentValue = event.target.value
    setComment(commentValue)
  }
  const onChangeEditComment = (event: React.ChangeEvent<HTMLInputElement>) => {
    const payload = event.target.value
    setEditComment([editComment[0], editComment[1]])
  }
  const confirmEdit = () => {
    window.alert('Confirm edit comment')
  }
  const handleKeyDown: React.KeyboardEventHandler<HTMLTextAreaElement> = (event) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault()
      handleSubmit()
    }
  }
  const handleSubmit = async () => {
    const formData = new FormData()
    formData.append('projectId', id)
    formData.append('comment', comment)
    console.log(id, comment)
    try {
      const response = await fetch(process.env.BACKEND_API_URL + `api/projects/comments/`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: formData,
      })
      if (response.ok) {
        window.alert('Success!')
        setComment('')
        // getComment()
      } else {
        window.alert('Error')
      }
    } catch (error) {
      window.alert('error')
    }
  }

  const handleDelete = async (commentId: number) => {
    try {
      const response = await fetch(process.env.BACKEND_API_URL + `api/projects/comments/${commentId}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      if (response.ok) {
        window.alert('Success!')
        // getComment()
      } else {
        window.alert('Error')
      }
    } catch (error) {
      window.alert('error')
    }
  }
  return (
    <div>
      <div className="mb-[40px] flex">
        <div className="">
          <form onSubmit={handleSubmit} className="h-[302px] w-[1116px] rounded-md border border-[#EBEBEB]">
            <textarea
              onChange={onChange}
              onKeyDown={handleKeyDown}
              placeholder="댓글을 작성하려면 로그인 해주세요"
              className="h-full w-full resize-none rounded-md bg-[#FAFAFA] p-2 pl-2 pt-2"
            />
          </form>
          {/* <button className="rounded-lg bg-[#7960BE] mt-4 px-4 py-2 text-white flex float-right">Submit</button> */}
        </div>
      </div>
      {/* example comment start*/}
      <h1 className="pretendard mb-[22px] text-[16px] font-medium">댓글 3</h1>
      <div className="mb-[40px] flex">
        <div>
          <img
            alt="name"
            src="/images/sample/image.png"
            className="mr-[16px] mt-[3.5px] h-[60px] w-[60px] rounded-full"
          />
        </div>
        <div>
          <p className="pretendard mb-[6px] text-[16px] font-medium">Karina Kim</p>
          <div className="pretendard text-[16px]">그림이 너무 이뻐요.</div>
          <div className="pretendard text-[14px] text-[#171616]/50">{new Date().toLocaleDateString()}</div>
        </div>
        <div className=" flex flex-grow justify-end">
          <button
            type="button"
            // onClick={() => setEditComment([comment.commentId, comment.comment])}
            className="text-[#171616]/50"
          >
            수정
          </button>
          <button
            type="button"
            // onClick={() => {
            //   handleDelete(comment.commentId)
            // }}
            className="ml-[8px] text-[#171616]/50"
          >
            삭제
            {currentCommentId}
          </button>
        </div>
      </div>
      <div className="mb-[40px] flex">
        <div>
          <img
            alt="name"
            src="/images/sample/image.png"
            className="mr-[16px] mt-[3.5px] h-[60px] w-[60px] rounded-full"
          />
        </div>
        <div>
          <p className="pretendard mb-[6px] text-[16px] font-medium">Karina Kim</p>
          <div className="pretendard text-[16px]">노란옷을 입은 어린아이가 인상적이에요.</div>
          <div className="pretendard text-[14px] text-[#171616]/50">{new Date().toLocaleDateString()}</div>
        </div>
        <div className=" flex flex-grow justify-end">
          <button
            type="button"
            // onClick={() => setEditComment([comment.commentId, comment.comment])}
            className="text-[#171616]/50"
          >
            수정
          </button>
          <button
            type="button"
            // onClick={() => {
            //   handleDelete(comment.commentId)
            // }}
            className="ml-[8px] text-[#171616]/50"
          >
            삭제
            {currentCommentId}
          </button>
        </div>
      </div>
      <div className="mb-[40px] flex">
        <div>
          <img
            alt="name"
            src="/images/sample/image.png"
            className="mr-[16px] mt-[3.5px] h-[60px] w-[60px] rounded-full"
          />
        </div>
        <div>
          <p className="pretendard mb-[6px] text-[16px] font-medium">Karina Kim</p>
          <div className="pretendard text-[16px]">그림이 너무 이뻐요.</div>
          <div className="pretendard text-[14px] text-[#171616]/50">{new Date().toLocaleDateString()}</div>
        </div>
        <div className=" flex flex-grow justify-end">
          <button
            type="button"
            // onClick={() => setEditComment([comment.commentId, comment.comment])}
            className="text-[#171616]/50"
          >
            수정
          </button>
          <button
            type="button"
            // onClick={() => {
            //   handleDelete(comment.commentId)
            // }}
            className="ml-[8px] text-[#171616]/50"
          >
            삭제
            {currentCommentId}
          </button>
        </div>
      </div>
      {/* example comment end */}
      <div className="flex flex-col gap-4 pt-12 ">
        {result
          .sort((a, b) => {
            const aDate = new Date(a.createdDate)
            const bDate = new Date(b.createdDate)
            return +aDate - +bDate
          })
          .map((comment) => (
            <div key={comment.commentId} className="rounded-md border p-4">
              <p className="mb-2 font-semibold">{comment.commentUserId}</p>
              {comment.commentId === editComment[0] ? (
                <input
                  type="text"
                  value={editComment[1]}
                  onChange={onChangeEditComment}
                  className="w-full border-b pb-1"
                />
              ) : (
                <p className="font-light">{comment.comment}</p>
              )}
              {editComment[0] === comment.commentId ? (
                <div className="flex gap-2">
                  <button type="button" onClick={confirmEdit} className="text-green-500">
                    Confirm
                  </button>
                  <button type="button" onClick={() => setEditComment([0, ''])} className="text-gray-500">
                    Cancel
                  </button>
                </div>
              ) : (
                <button
                  type="button"
                  onClick={() => setEditComment([comment.commentId, comment.comment])}
                  className="text-green-500"
                >
                  Edit
                </button>
              )}
              <button
                type="button"
                onClick={() => {
                  handleDelete(comment.commentId)
                }}
                className="text-red-500"
              >
                Delete
                {currentCommentId}
              </button>
            </div>
          ))}
      </div>
    </div>
  )
}
