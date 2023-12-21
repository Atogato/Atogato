'use client'
import React, { useCallback } from 'react'
import { useDropzone, FileRejection } from 'react-dropzone'
import AttachImage from '@/icons/attach-image.svg'

interface ImageUploaderProps {
  className?: string
  onImageUpload: (image: File) => void
}

export default function ImageUploader(props: ImageUploaderProps) {
  const { onImageUpload, className } = props

  const handleDrop = (acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) {
      const imageFile = acceptedFiles[0]
      onImageUpload(imageFile)
    }
  }

  const handleRejected = useCallback((fileRejections: FileRejection[]) => {
    alert('jpg, png, webp 확장자 이미지만 업로드 가능합니다!')
  }, [])

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    accept: {
      'image/jpeg': ['.jpg', '.jpeg'],
      'image/png': ['.png'],
      'image/webp': ['.webp'],
    },
    noKeyboard: false,
    onDrop: handleDrop,
    onDropRejected: handleRejected,
  })

  const customClassName = `${isDragActive ? 'active bg-gray-400' : ''} ${className}`

  return (
    <>
      <div {...getRootProps()} className={customClassName}>
        <div className="dropzone flex h-[216px] w-full items-center justify-center border-2">
          <input {...getInputProps()} />
          {isDragActive ? (
            <p className="text-white">이미지 업로드 중...</p>
          ) : (
            <div className="flex flex-col items-center gap-5">
              <AttachImage width={61} height={53} />
              <div className="flex flex-col items-center gap-2">
                <p className="text-lg">
                  이미지를 드래그하거나 <strong className="text-[#7960BE]"> 파일을 업로드 </strong> 하세요
                </p>
                <p className="text-base"> 지원 확장자: jpeg, png, webp</p>
              </div>
            </div>
          )}
        </div>
      </div>
      <div {...getRootProps()} className="mx-auto">
        <input {...getInputProps()} />
        <button
          className="w-[312px] rounded bg-[#7960BE] px-4 py-4 text-[#FFF]"
          onClick={(e) => {
            e.preventDefault()
          }}
        >
          파일 선택
        </button>
      </div>
    </>
  )
}
