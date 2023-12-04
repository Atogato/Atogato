export default function dateFormatter(date: Date) {
  const dateFormat =
    (date.getMonth() + 1 < 9 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) +
    '/' +
    (date.getDate() < 9 ? '0' + date.getDate() : date.getDate()) +
    '/' +
    date.getFullYear()
  return dateFormat
}
