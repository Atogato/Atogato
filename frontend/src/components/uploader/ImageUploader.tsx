'use client'
import React, { useCallback } from 'react'
import { useDropzone, FileRejection } from 'react-dropzone'

interface ImageUploaderProps {
  className?: string
  onImageUpload: (image: File) => void
}

export default function ImageUploader(props: ImageUploaderProps) {
  const { onImageUpload, className } = props

  const handleDrop = useCallback(
    (acceptedFiles: File[]) => {
      if (acceptedFiles.length > 0) {
        const imageFile = acceptedFiles[0]
        onImageUpload(imageFile)
      }
    },
    [onImageUpload],
  )

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

  const customClassName = `dropzone ${isDragActive ? 'active bg-gray-400' : ''} border-2 ${className}`

  return (
    <div {...getRootProps()} className={customClassName}>
      <input {...getInputProps()} />
      {isDragActive ? (
        <p className="text-white">이미지 업로드 중...</p>
      ) : (
        <p>이미지를 드래그하거나 클릭해서 업로드 하세요</p>
      )}
    </div>
  )
}
