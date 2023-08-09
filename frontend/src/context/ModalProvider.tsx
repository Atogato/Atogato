'use client'

import { useReducer, createContext, ReactNode, Dispatch, useContext } from 'react'

export type ModalState = {
  type: 'default'
  isOpen: boolean
}

type Action = { type: 'OPEN' } | { type: 'CLOSE' }

type ToggleDispatch = Dispatch<Action>

export const ModalStateContext = createContext<ModalState | null>(null)
export const ModalSetterContext = createContext<ToggleDispatch | null>(null)

// reducer
function reducer(state: ModalState, action: Action): ModalState {
  switch (action.type) {
    case 'OPEN':
      return {
        ...state,
        isOpen: true,
      }
    case 'CLOSE':
      return {
        ...state,
        isOpen: false,
      }
    default:
      throw new Error('unhandled action')
  }
}

export function ModalProvider({ children }: { children: ReactNode }) {
  const [state, dispatch] = useReducer(reducer, { type: 'default', isOpen: false })

  return (
    <ModalStateContext.Provider value={state}>
      <ModalSetterContext.Provider value={dispatch}>{children}</ModalSetterContext.Provider>
    </ModalStateContext.Provider>
  )
}
