'use client'

import { useParams } from 'next/navigation'
import { useEffect, useState } from 'react'
import { localStorage } from '@/app/storage'

import ReceiverBubble from '../../components/ReceiverBubble'
import SenderBubble from '../../components/SenderBubble'

type Messages = {
  id: number
  content: string
  senderName: string
  receiverName: string
  roomId: number
  createdDate: string
}

export default function Room() {
  const urlId = useParams()
  const id = urlId.roomId

  const [token, setToken] = useState<string | null>()
  const [result, setResult] = useState<Messages[]>([])
  const [message, setMessage] = useState<string>('')

  const getMessages = async () => {
    try {
      const data = await fetch(`http://localhost:7072/api/messages/${id}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      const jsonData = await data.json()
      setResult(jsonData.data)
    } catch (error) {
      console.error('Error fetching data:', error)
    }
  }
  const rid = '2'

  useEffect(() => {
    const user = localStorage.getItem('token')
    setToken(user)
    if (token) {
      getMessages()
    }
  }, [token])

  const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    try {
      const response = await fetch(`http://localhost:7072/api/messages?receiverId=${rid}`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(message),
      })
      if (response.ok) {
        window.alert('Success!')
        setMessage('')
        getMessages()
      } else {
        window.alert('Error')
      }
    } catch (error) {
      window.alert('error')
    }
    getMessages()
  }
  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const commentValue = event.target.value
    setMessage(commentValue)
  }

  return (
    <div className="mx-56 my-16 bg-gray-200 p-8">
      <div className="grid-rows-3">
        <div className="m-4">room.id</div>
        <div>message</div>
        {result.map((message) => {
          if (rid === message.receiverName) {
            return <SenderBubble key={message.id} message={message.content} />
          } else {
            return <ReceiverBubble key={message.id} message={message.content} />
          }
        })}
        <div>
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
