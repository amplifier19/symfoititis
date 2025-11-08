export const useDate = () => {
  const formatDate = (dateISOStr: string, pattern: string) => {
    const date = new Date(dateISOStr);
    const dd = date.getDate().toString().padStart(2, "0");
    const MM = (date.getMonth() + 1).toString().padStart(2, "0");
    const yyyy = date.getFullYear().toString();

    return pattern.replace("dd", dd).replace("MM", MM).replace("yyyy", yyyy);
  };

  const getWeekDay = (date: string) => {
    const weekDays = [
      "Κυριακή",
      "Δευτέρα",
      "Τρίτη",
      "Τετάρτη",
      "Πέμπτη",
      "Παρασκευή",
      "Σάββατο",
    ];
    const bookingDate = new Date(date);
    return weekDays[bookingDate.getDay()];
  };

const getDateDiffString = (dateISO: string, time: number) => {
  const HHmmss =
    time.toString().padStart(2, "0") + ":00:00";
  const date = new Date(`${dateISO}T${HHmmss}`);
  const diffMs = date.getTime() - Date.now();
  const diffSeconds = Math.floor(diffMs / 1000);
  const diffMinutes = Math.floor(diffSeconds / 60);
  const diffHours = Math.floor(diffMinutes / 60);
  const diffDays = Math.floor(diffHours / 24);
  const HHmm = HHmmss.slice(0, 5);
  if (diffDays > 0) {
    return `Σε ${diffDays} ημέρες`;
  } else if (diffDays < 0) {
    return formatDate(dateISO, "dd/MM");
  } else {
    return `Σήμερα ${HHmm}`;
  }
}

  return { formatDate, getWeekDay, getDateDiffString };
};
