import Link from "next/link"

export default function Detail(){
    return(
        <div>
            <div className="mb-10 mt-3 relative">
                <div className="text-5xl text-center">
                    프로젝트 이름
                </div>
                <div className="absolute top-0 left-2">
                    모집중/마감
                </div>
                <div className="absolute top-0 right-2">
                    <Link prefetch={false} href={`/`} >
                        수정하기
                    </Link>
                </div>
            </div>
            <hr className="mb-8 mx-40 border-black" />
            <div className="bg-gray-200 mx-40 relative">
                <div className="text-2xl text-left px-10 pt-1 pb-3">
                    프로젝트 정보 요약
                </div>
                <hr className="my-1 border-black w-full" />
                <div className="text-2xl text-left px-10 pt-1 pb-10">
                    프로젝트 소개
                </div>
                <div className="absolute bottom-2 left-2">
                    참여자 수 5/10
                </div>
                <div className="flex absolute bottom-2 right-2">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
                    </svg> 
                    <div>
                        3
                    </div>
                </div>
            </div>
            <div className="flex justify-center mt-5">
                <button className="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-5 px-10 rounded mx-40">
                    참여하기/수정하기
                </button>
            </div>
            <div className="mx-40 mt-5 mb-10">
                <div className="my-5">
                    <div className="my-2">
                        리더
                    </div>
                    <a href="/" className="block relative">
                        <img alt="name" src="/image.png" className="h-20 w-20 rounded-full" />
                    </a>
                </div>
                <div className="my-2">
                    참여자 목록
                </div>
                <div className="flex">
                    <a href="/" className="mr-6">
                        <img alt="name" src="/image.png" className="h-20 w-20 rounded-full" />
                    </a>
                    <a href="/" className="mr-6">
                        <img alt="name" src="/image.png" className="h-20 w-20 rounded-full" />
                    </a>
                    <a href="/" className="mr-6">
                        <img alt="name" src="/image.png" className="h-20 w-20 rounded-full" />
                    </a>
                    <a href="/" className="mr-6">
                        <img alt="name" src="/image.png" className="h-20 w-20 rounded-full" />
                    </a>
                </div>
            </div>
        </div>
    )
}