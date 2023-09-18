import { Suspense } from 'react'

export default function BoxSkeleton({
  className,
  children,
}: {
  className?: string
  children?: React.ReactNode | React.ReactNode[]
}) {
  return (
    <div className={className}>
      <Suspense
        fallback={
          <div className="h-full w-full animate-pulse">
            <p className="h-full w-full rounded-md bg-gray-200">
              <span className="hide"> Loading... </span>
            </p>
          </div>
        }
      >
        {children}
      </Suspense>
    </div>
  )
}
