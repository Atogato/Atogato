import { NextRequest, NextResponse } from 'next/server'
import fs from 'fs'
import path from 'path'

const filePath = path.join(process.cwd(), '/src/data/projects.json')

export async function GET(request: NextRequest) {
  const fileData = fs.readFileSync(filePath).toString('utf-8')
  const data = JSON.parse(fileData)
  return NextResponse.json(data)
}

export async function POST(request: Request) {
  const formData = await request.formData()
  const requestedData = JSON.parse(formData.get('info')?.toString() || '{}')
  const requestedFiles = JSON.parse(formData.get('images')?.toString() || '{}')
  const saveData = {
    ...requestedData,
    images: [...requestedFiles],
  }
  const fileData = fs.readFileSync(filePath).toString('utf-8')
  const data = JSON.parse(fileData)
  const updatedData = [...data, saveData]
  fs.writeFileSync(filePath, JSON.stringify(updatedData))
  return NextResponse.json({ message: 'success' })
}

// binary file to buffer 테스트용
async function toBuffer(file: File) {
  const bytes = await file.arrayBuffer()
  const buffer = Buffer.from(bytes)
  return buffer
}
