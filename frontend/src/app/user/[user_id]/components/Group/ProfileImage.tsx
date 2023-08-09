import Image from 'next/image'
import Link from 'next/link'

export interface ProfileType {
  className?: string
  src: string
  title: string
}

export default function ProfileImage({ className, src, title }: ProfileType) {
  const customClassName = `relative ${className}`

  return (
    <Link href="/artist/detail/1">
      <div className={customClassName}>
        <Image className="h-full w-full rounded-full" fill={true} src={src} alt={title} />
      </div>
    </Link>
  )
}
