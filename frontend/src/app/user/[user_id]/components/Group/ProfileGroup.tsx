import { MouseEvent, ReactNode } from 'react'

export interface ProfileGroupType {
  className?: string
  onClick?: (e: MouseEvent) => void
  children?: ReactNode | ReactNode[]
}

export default function ProfileGroup({ className, onClick, children }: ProfileGroupType) {
  return (
    <div className={className} onClick={onClick}>
      {children}
    </div>
  )
}
