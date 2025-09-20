export const useDate = () => {

    const formatDate = (dateISOStr: string, pattern: string) => {
        const date = new Date(dateISOStr)
        const dd = date.getDate().toString().padStart(2, "0")
        const MM = (date.getMonth() + 1).toString().padStart(2, "0")
        const yyyy = date.getFullYear().toString()

        return pattern.replace("dd", dd).replace("MM", MM).replace("yyyy", yyyy)
    }

    return { formatDate }
}
