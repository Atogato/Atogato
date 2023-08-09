import Image from 'next/image'
import Link from 'next/link'

export interface ProjectType {
  className?: string
  src: string
  title: string
  description: string
}

export default function ProjectCard({ className, src, title, description }: ProjectType) {
  const customClassName = `flex w-full ${className}`
  return (
    <Link href="/project/detail/1">
      <div className={customClassName}>
        <div className="w-3/12 rounded-l-lg border-2 border-s-0">
          <Image className="h-full w-full rounded-l-lg" src={src} alt={title} width={300} height={300} />
        </div>
        <div className="flex-1 border-y-2 border-r-2">
          <figcaption className="font-medium">
            <div className="text-sm text-slate-700 dark:text-slate-500">{title}</div>
          </figcaption>
          <p className="text-sm"> {description} </p>
        </div>
      </div>
    </Link>
  )
}
