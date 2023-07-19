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
  if (!dispatch) throw new Error('Cannot find ModalProvider')
  return dispatch
}

export function useModal() {
  const state = useModalState()
  const disPatch = useModalDispatch()

  function openModal() {
    disPatch({ type: 'OPEN' })
  }
  function closeModal() {
    disPatch({ type: 'CLOSE' })
  }

  function toggleModal() {
    if (state.isOpen) {
      closeModal()
    } else {
      openModal()
    }
  }

  return { state, openModal, closeModal, toggleModal }
}
