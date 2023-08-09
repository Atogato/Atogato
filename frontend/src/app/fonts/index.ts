import localFont from 'next/font/local'
import { Poppins } from 'next/font/google'

export const pretendard = localFont({
  src: [
    {
      path: './PretendardVariable.woff2',
    },
  ],
  display: 'swap',
  variable: '--font-pretendard',
})

export const poppins = Poppins({
  weight: ['100', '200', '300', '400', '500', '600', '700', '800', '900'],
  subsets: ['latin'],
  display: 'swap',
  variable: '--font-poppins',
})
