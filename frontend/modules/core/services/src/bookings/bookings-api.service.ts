export class BookingsApiService {
  private static instance: BookingsApiService = new BookingsApiService();
  private readonly API_BASE_URL = import.meta.env.VITE_API_BASE;

  public constructor() {}

  public static getBookingsApiFactory() {
    return this.instance;
  }

  public getBookings = async () => {
    return await fetch(`${this.API_BASE_URL}/tutoring/bookings`, {
      method: "GET",
    });
  };

  public addBookings = async (availabilityIds: number[]) => {
    return await fetch(`${this.API_BASE_URL}/tutoring/booking`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(availabilityIds),
    });
  };

  public cancelBooking = async (b_id: number) => {
    return await fetch(`${this.API_BASE_URL}/tutoring/cancel/booking/${b_id}`, {
      method: "PUT",
    });
  };
}
