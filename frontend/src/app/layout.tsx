import './globals.css'
import Footer from '@/components/Footer'
import Navbar from '@/components/Navbar'
import App from './App'
import { poppins, pretendard } from './fonts'

export const metadata = {
  title: 'Atogato',
  description: 'Artists & Projects ',
}

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="ko">
      <body className={`relative ${pretendard.className} ${poppins.variable} mx-auto min-w-[1280px] max-w-[1920px]`}>
        <Navbar />
        <App>{children}</App>
        <Footer />
      </body>
    </html>
  )
}
