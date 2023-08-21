'use client'
import React, { useState, MouseEvent, SyntheticEvent } from 'react'
import { pdfjs, Document, Page } from 'react-pdf'
import 'react-pdf/dist/esm/Page/AnnotationLayer.css'
import 'react-pdf/dist/esm/Page/TextLayer.css'

import './sample.css'
import type { PDFDocumentProxy } from 'pdfjs-dist'

pdfjs.GlobalWorkerOptions.workerSrc = new URL('pdfjs-dist/build/pdf.worker.min.js', import.meta.url).toString()

function PdfViewer({ pdf }: { pdf?: string }) {
  const [numPages, setNumPages] = useState<number>()

  function onDocumentLoadSuccess({ numPages: nextNumPages }: PDFDocumentProxy) {
    setNumPages(nextNumPages)
  }

  return (
    <div
      className="Example"
      onContextMenu={(e: MouseEvent<HTMLDivElement>) => {
        e.preventDefault()
      }}
      onSelect={(e: SyntheticEvent) => {
        e.preventDefault()
      }}
    >
      <div className="Example__container">
        <div className="Example__container__document">
          <Document
            file={pdf}
            onLoadSuccess={onDocumentLoadSuccess}
            options={{
              cMapUrl: `https://unpkg.com/pdfjs-dist@${pdfjs.version}/cmaps/`,
              cMapPacked: true,
            }}
          >
            {Array.from(new Array(numPages), (el, index) => (
              <Page key={`page_${index + 1}`} pageNumber={index + 1} />
            ))}
          </Document>
        </div>
      </div>
    </div>
  )
}

export default PdfViewer
