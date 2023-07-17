import { MouseEvent } from 'react'

interface ButtonProps {
  className?: string
  text: string
  onClick?: (e: MouseEvent) => void
}

export default function Button({ ...props }: ButtonProps) {
  const { className, text, onClick } = props
  return (
    <button className={className} onClick={onClick}>
      {text}
    </button>
  )
}
