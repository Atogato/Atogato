import { MouseEvent, ReactNode } from 'react'

export interface ProjectGroupType {
  className?: string
  onClick?: (e: MouseEvent) => void
  children?: ReactNode | ReactNode[]
}

export default function ProjectGroup({ className, onClick, children }: ProjectGroupType) {
  return (
    <div className={className} onClick={onClick}>
      {children}
    </div>
  )
}
