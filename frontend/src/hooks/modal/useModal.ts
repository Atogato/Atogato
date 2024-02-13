import { useContext } from 'react'
import { ModalStateContext, ModalSetterContext } from '@/context/ModalProvider'

// Modal state custom hook
export function useModalState() {
  const state = useContext(ModalStateContext)
  if (!state) throw new Error('Cannot find ModalProvider')
  return state
}

// Modal Dispatch custom hook
export function useModalDispatch() {
  const dispatch = useContext(ModalSetterContext)

  function openModal() {
    if (!dispatch) throw new Error('Cannot find ModalProvider')
    dispatch({ type: 'OPEN' })
  }
  function closeModal() {
    if (!dispatch) throw new Error('Cannot find ModalProvider')
    dispatch({ type: 'CLOSE' })
  }

  return { openModal, closeModal }
}
