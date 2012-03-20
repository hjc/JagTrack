import java.sql.Time;


public class Bus {
	private String bus_id;
	private int maxSeats;
	private Time arrivalTime;
	private Location busLoc;
	private Route currentRoute;
	private Time departureTime;
	private Stop nextStop;
	private int passengerCount;
}
