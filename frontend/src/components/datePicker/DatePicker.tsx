import CalenderUi from './CalenderUi'
import { useEffect, useMemo, useState } from 'react'
import dateFormatter from '@/utils/DateFormat'
import CalenderIcon from '@/icons/calender.svg'

type Range<T> = [T, T]
type ValuePiece = Date | null
type Value = ValuePiece | Range<ValuePiece>

export default function DatePicker({
  id,
  name,
  required = false,
  placeholder = '날짜를 선택해주세요.',
  onChange,
}: {
  id: string
  name: string
  required?: boolean
  placeholder?: string
  onChange: (value: string) => void
}) {
  const [showCalendar, setShowCalendar] = useState(false)
  const [selectedDate, setSelectedDate] = useState<Value>(null)

  const inputValue = useMemo(() => {
    return selectedDate ? dateFormatter(selectedDate as Date) : placeholder
  }, [selectedDate])

  useEffect(() => {
    if (selectedDate) {
      onChange(dateFormatter(selectedDate as Date))
      setShowCalendar(false)
    }
  }, [selectedDate])

  return (
    <div className="relative">
      <div
        className="relative flex h-[48px] w-[450px] rounded border border-[#EBEBEB] p-2 hover:cursor-pointer"
        onClick={() => {
          setShowCalendar(!showCalendar)
        }}
      >
        <input
          type="button"
          className="hover:cursor-pointer"
          id={id}
          name={name}
          required={required}
          value={inputValue}
        />
        <CalenderIcon className="absolute right-2 top-0" />
      </div>
      {showCalendar && (
        <CalenderUi
          date={selectedDate}
          className="absolute top-10 z-50"
          onSelectDate={(date: Value) => {
            setSelectedDate(date)
          }}
        />
      )}
    </div>
  )
}
