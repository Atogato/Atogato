export default function DefaultModal({ ...props }) {
  const { isOpen, className } = props
  if (!isOpen) {
    return
  }
  return (
    <div className={className}>
      <h1> 모달 제목 </h1>
      <p> 모달 내용 </p>
    </div>
  )
}
