import Image from 'next/image'

export default function Gallery2({ images = [] }: { images: string[] }) {
  if (images.length === 0) {
    return <div></div>
  }
  const selectedImages = images.slice(0, 9)
  return (
    <ul className="grid auto-rows-[1px] grid-cols-3 gap-5">
      {selectedImages.map((image, idx) => {
        let rowSpan = 'row-end-[span_15]'
        switch (idx) {
          case 1:
          case 6:
          case 7:
            rowSpan = 'row-end-[span_20]'
        }

        const className = `${rowSpan} relative w-full overflow-hidden`
        return (
          <li key={`project-image-${idx}`} className={className}>
            <div className="group relative h-full w-full duration-100 ease-out hover:cursor-pointer">
              <figure className="h-full w-full">
                <Image
                  src={image}
                  alt={`project image`}
                  width={464}
                  height={464}
                  className="h-full w-full group-hover:blur-[4px]"
                ></Image>
                <div
                  className="absolute inset-0 flex flex-col justify-end bg-white/30 p-5
                opacity-0 transition-all duration-200 ease-out  group-hover:opacity-100"
                >
                  <div className="ml-[30px] mt-[374px] text-[28px] font-semibold text-[#171616]">
                    여러가지 무늬를 그린 그림
                  </div>
                  <div className="ml-[30px] mt-[6px] text-[18px] font-semibold text-[#171616]/75">김작가&이화가</div>
                  <div className="mt-[57px] text-[20px] font-bold">
                    여러가지 추상적인 그림을 그려서 프로젝트를 만들어 팀원들과 진행해가고 있습니다.
                  </div>
                  <div className="mt-[8px] text-[14px] opacity-70">
                    모든 팀원들이 힘을 합쳐 프로젝트를 열심히 진행하고 있습니다. 모든 팀원 들이 힘을 합쳐 프로젝트를
                    열심히 진행하고 있습니다. 모든 팀원들이 힘을 합쳐 프로젝트를 열심히 진행하고 있습니다. 모든 팀원들이
                    힘을 합쳐 프로 젝트를 열심히 진행하고 있습니다. 모든 팀원들이 힘을 합쳐 프로젝트를{' '}
                  </div>
                  <div className="mt-[24px] text-[13px] opacity-30">웹툰 2023.08.11~2023.08.14 서울</div>
                  <div className="mt-[24px] text-[14px] text-[#7960BE]"># 예술 # 일러스트레이션 # 파인아트</div>
                  <div className="mt-[24px]">
                    <div className="float-left">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth="1.5"
                        stroke="currentColor"
                        className="h-6 w-6"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z"
                        />
                      </svg>
                      15
                    </div>
                    <div className="float-right mb-[30px]">
                      <div className="relative w-28">
                        <div className="absolute right-[3px] z-40 flex h-[32px] w-[32px] items-center justify-center rounded-full border-2 border-white bg-[#7960BE] text-white">
                          +5
                        </div>
                        <div className="absolute right-[17px] z-30 rounded-full border-2 border-white">
                          <Image
                            src={image}
                            alt={`project image`}
                            width={28}
                            height={28}
                            className="h-[28px] w-[28px] rounded-full"
                          ></Image>
                        </div>
                        <div className="absolute right-[31px] z-20 rounded-full border-2 border-white">
                          <Image
                            src={image}
                            alt={`project image`}
                            width={28}
                            height={28}
                            className="h-[28px] w-[28px] rounded-full"
                          ></Image>
                        </div>
                        <div className="absolute right-[45px] z-10 rounded-full border-2 border-white">
                          <Image
                            src={image}
                            alt={`project image`}
                            width={28}
                            height={28}
                            className="h-[28px] w-[28px] rounded-full"
                          ></Image>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="absolute top-0 h-full w-full bg-gradient-to-t from-black opacity-70	"></div>
              </figure>
            </div>
          </li>
        )
      })}
    </ul>
  )
}
