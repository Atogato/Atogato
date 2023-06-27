'use client'
import {useState} from 'react'
import Head from "next/head";
interface CommentParams {
    id: string;
    created_at: string;
    updated_at: string;
    username: string;
    payload: string;
    reply_of?: string;
  }
export default function Comment(){
    const [comment, setComment] = useState<string>("");
    // const [commentList, setCommentList] = useState<CommentParams[]>([])
    const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const commentValue = event.target.value;
        setComment(commentValue);
      };
    const onSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
        console.log(comment);
    };
    const commentList =[
        {
          id:1,
          username: 'user 1',
          payload: 'first comment',
          created_at: '2023-06-26',
        },
        {
          id:2,
          username: 'user 2',
          payload: 'second comment',
          created_at: '2023-06-24',
        },
        {
          id:3,
          username: 'user 3',
          payload: 'thrid comment',
          created_at: '2023-06-25',
        },
      ];

    return(
        <div>
        <Head>
            <title>Comments Page</title>
        </Head>
        <div className="flex flex-col gap-4 pt-12 ">
        {commentList
              .sort((a, b) => {
                const aDate = new Date(a.created_at);
                const bDate = new Date(b.created_at);
                return +aDate - +bDate;
              })
              .map((comment) => (
          <div key={comment.id} className="border rounded-md p-4">
            <p className="font-semibold mb-2">{comment.username}</p>
            <p className="font-light">{comment.payload}</p>
          </div>
        ))}
      </div>
        <div className="flex justify-center pt-12">
            <div className="min-w-[600px]">
            <h1 className="text-3xl">댓글</h1>
            <form  onSubmit={onSubmit} className="mt-8 flex gap-8">
                <input 
                onChange={onChange} 
                type="text" 
                placeholder="Add a comment" 
                className="p-2 border-b focus:border-b-gray-700 w-full outline-none"
                />
                <button 
                className="px-4 py-2 bg-blue-500 rounded-lg text-white">
                    Submit
                </button>
            </form>
            </div>
      </div>
    </div>
    )
}