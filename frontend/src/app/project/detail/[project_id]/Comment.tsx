'use client'
import { useEffect, useState } from 'react'
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

  const getComment = async () => {
    const data = await fetch(`http://localhost:7072/api/projects/comments/${id}`, {
      method: 'GET',
    })
    const jsonData = await data.json()
    setResult(jsonData)
  }
  useEffect(() => {
    getComment()
  }, [])
  console.log(result)

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
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
  const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const formData = new FormData()
    formData.append('projectId', id)
    formData.append('comment', comment)
    try {
      const response = await fetch(`http://localhost:7072/api/projects/comments/`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: formData,
      })
      if (response.ok) {
        window.alert('Success!')
        setComment('')
        getComment()
      } else {
        window.alert('Error')
      }
    } catch (error) {
      window.alert('error')
    }
  }

  const handleDelete = async (commentId) => {
    try {
      const response = await fetch(`http://localhost:7072/api/projects/comments/${commentId}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      if (response.ok) {
        window.alert('Success!')
        getComment()
      } else {
        window.alert('Error')
      }
    } catch (error) {
      window.alert('error')
    }
  }
  return (
    <div>
      <Head>
        <title>Comments Page</title>
      </Head>
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
      <div className="flex justify-center pt-12">
        <div className="min-w-[600px]">
          <h1 className="text-3xl">댓글</h1>
          <form onSubmit={onSubmit} className="mt-8 flex gap-8">
            <input
              onChange={onChange}
              type="text"
              placeholder="Add a comment"
              className="w-full border-b p-2 outline-none focus:border-b-gray-700"
            />
            <button className="rounded-lg bg-blue-500 px-4 py-2 text-white">Submit</button>
          </form>
        </div>
      </div>
    </div>
  )
}
