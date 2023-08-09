import { Suspense } from 'react'

export default function TextSkeleton({
  className,
  children,
  lines = 3,
}: {
  className?: string
  children?: React.ReactNode | React.ReactNode[]
  lines: number
}) {
  return (
    <div className={className}>
      <Suspense fallback={<div className="flex w-full animate-pulse flex-col gap-4">{getLines(lines)}</div>}>
        {children}
      </Suspense>
    </div>
  )
}

function getLines(lines: number) {
  const textLines = Array(lines).fill('_')

  return textLines.map((_, idx) => (
    <p key={idx} className="h-full w-full rounded-md bg-gray-200">
      <span className="hide"> Loading... </span>
    </p>
  ))
}
