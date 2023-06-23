'use client'
import React, { useState } from 'react'
import Calendar from 'react-calendar'
import 'react-calendar/dist/Calendar.css'

type Range<T> = [T, T]
type ValuePiece = Date | null
type Value = ValuePiece | Range<ValuePiece>

export default function CalenderUi() {
  const [value, onChange] = useState<Value>(new Date())

  return (
    <div>
      <Calendar locale="ko" onChange={onChange} value={value} />
    </div>
  )
}
