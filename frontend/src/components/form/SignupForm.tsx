import { ChangeEvent, MutableRefObject, SyntheticEvent, useRef } from 'react'

export default function SignupForm() {
  const name = useRef('')
  const nickName = useRef('')
  const email = useRef('')
  const emailConfirm = useRef('')
  const password = useRef('')
  const passwordConfirm = useRef('')

  // TODO: 서버로 FormData 전송
  const submitHandler = (e: SyntheticEvent) => {
    e.preventDefault()
  }

  const onChange = (e: ChangeEvent<HTMLInputElement>, type: MutableRefObject<string>) => {
    type.current = e.target.value
  }

  return (
    <form className="flex flex-col space-y-4 pb-10" onSubmit={submitHandler}>
      <div>
        <h2> 이름 </h2>
        <input
          onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e, name)}
          className="border-2"
          type="text"
          name="name"
          id="name"
          required
        />
      </div>
      <div>
        <h2>닉네임</h2>
        <input
          onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e, nickName)}
          className="border-2"
          type="text"
          name="nickname"
          id="nickname"
          required
        />
      </div>
      <div>
        <h2> 이메일 </h2>
        <input
          className="border-2"
          type="email"
          id="email"
          aria-describedby="email input"
          placeholder="username@example.com"
          pattern=".+@g*.*"
          required
          onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e, email)}
        />
        <p>
          <label htmlFor="email-confirm"> 이메일 인증 번호 </label>
          <input
            onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e, emailConfirm)}
            className="border-2"
            type="password"
            name="email-confirm"
            id="email-confirm"
          />
        </p>
      </div>
      <div>
        <h2> 비밀번호 </h2>
        <input
          onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e, password)}
          className="border-2"
          type="password"
          name="password"
          id="password"
        />
      </div>
      <div>
        <h2> 비밀번호 확인 </h2>
        <input
          onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e, passwordConfirm)}
          className="border-2"
          type="password"
          name="password-confirm"
          id="password-confirm"
        />
      </div>
      <button className="border-2"> 회원가입 하기 </button>
    </form>
  )
}
