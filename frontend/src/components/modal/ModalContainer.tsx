'use client'

import DefaultModal from './DefaultModal'
import { useModalState } from '@/hooks/modal/useModal'

export const MODAL_CONTENT_TYPES = {
  default: DefaultModal,
}

export default function ModalContainer() {
  const { type, isOpen } = useModalState()
  const Modal = MODAL_CONTENT_TYPES[type]

  return (
    <>
      <Modal
        className="fixed left-1/2 top-1/2 z-50 flex h-1/2 w-1/2 -translate-x-1/2 -translate-y-1/2 flex-col justify-center bg-white text-center"
        isOpen={isOpen}
      />
    </>
  )
}
