'use client'
import Calendar from 'react-calendar'
import { MouseEvent } from 'react'
import 'react-calendar/dist/Calendar.css'
import './Calender.css'

type Range<T> = [T, T]
type ValuePiece = Date | null
type Value = ValuePiece | Range<ValuePiece>

export default function CalenderUi({
  date,
  className,
  onSelectDate,
}: {
  date?: Value
  className?: string
  onSelectDate: (value: Value, event: MouseEvent<HTMLButtonElement, globalThis.MouseEvent>) => void
}) {
  return (
    <div className={className}>
      <div className="react-calender__navigation" />
      <Calendar
        locale="en"
        calendarType="US"
        formatShortWeekday={(_, date) => ['S', 'M', 'T', 'W', 'T', 'F', 'S'][date.getDay()]}
        onChange={onSelectDate}
        showNavigation={false}
        value={date}
      />
    </div>
  )
}
