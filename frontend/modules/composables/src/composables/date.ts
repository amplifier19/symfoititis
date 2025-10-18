export const useDate = () => {

  const formatDate = (dateISOStr: string, pattern: string) => {
    const date = new Date(dateISOStr)
    const dd = date.getDate().toString().padStart(2, "0")
    const MM = (date.getMonth() + 1).toString().padStart(2, "0")
    const yyyy = date.getFullYear().toString()

    return pattern.replace("dd", dd).replace("MM", MM).replace("yyyy", yyyy)
  }

  const getWeekDay = (date: string) => {
    const weekDays = ['Κυριακή', 'Δευτέρα', 'Τρίτη', 'Τετάρτη', 'Πέμπτη', 'Παρασκευή', 'Σάββατο']
    const bookingDate = new Date(date)
    return weekDays[bookingDate.getDay()]
  }

  return { formatDate, getWeekDay }
}
