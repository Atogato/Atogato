'use client'
import React, { useEffect, useState } from 'react'
import ReactQuill, { Quill } from 'react-quill'

// @ts-ignore
import quillEmoji from 'quill-emoji'

import 'react-quill/dist/quill.snow.css'

import 'quill-emoji/dist/quill-emoji.css'
import './Editor.css'

const { EmojiBlot, ShortNameEmoji, ToolbarEmoji } = quillEmoji

Quill.register(
  {
    'formats/emoji': EmojiBlot,
    'modules/emoji-shortname': ShortNameEmoji,
    'modules/emoji-toolbar': ToolbarEmoji,
  },
  true,
)

type EdtiorProps = {
  className?: string
  onEditorUpdated?: (value: string) => void
}
export default function Editor(props: EdtiorProps) {
  const { className, onEditorUpdated } = props
  const modules = {
    // toolbar 옵션 선택 Design 완료 전까지 화면에 toolbar를 표시하지 않음
    // toolbar: [
    //   [{ header: [1, 2, false] }],
    //   ['bold', 'italic', 'underline', 'strike', 'blockquote'],
    //   [{ list: 'ordered' }, { list: 'bullet' }, { indent: '-1' }, { indent: '+1' }],
    //   ['emoji'],
    // ],
    toolbar: false,
    'emoji-toolbar': false,
    'emoji-shortname': false,
  }
  const formats = [
    'font',
    'header',
    'bold',
    'italic',
    'underline',
    'strike',
    'blockquote',
    'color',
    'background',
    'list',
    'indent',
    'align',
    'link',
    'emoji',
  ]

  const [value, setValue] = useState('')

  useEffect(() => {
    if (onEditorUpdated) {
      onEditorUpdated(value)
    }
  }, [value])

  return (
    <ReactQuill
      placeholder="내용을 입력해주세요."
      className={className}
      modules={modules}
      formats={formats}
      value={value}
      onChange={setValue}
    />
  )
}
