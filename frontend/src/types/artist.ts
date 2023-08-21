export type Artist = {
  userId: string
  artistId: string
  artistName: string
  location?: string
  selfIntroduction?: string
  description: string
  liked: number
  creatorArtCategory: string
  interestCategory: string | string[]
  snsLink?: string
  mainImage: string
  additionalImage?: string[]
}
