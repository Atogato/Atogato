'use client'
import { ModalProvider } from '@/context/ModalProvider'
import ModalContainer from '@/components/modal/ModalContainer'
export default function App({ children }: { children: React.ReactNode }) {
  return (
    <ModalProvider>
      <div className="pt-[100px]">{children}</div>
      <ModalContainer />
    </ModalProvider>
  )
}
