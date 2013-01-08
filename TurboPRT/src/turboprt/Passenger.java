package turboprt;

/**
 *
 * @author marcel
 */// passenger heeft een location van type klasse Locatoin en een destination waar naar toe gaat
public class Passenger {
	private Location location;
	private Destination destination;
	// deze geeft true als de destination aangegeven is.
	private boolean enterDestination() {
		return true;
	}
	// geeft true als de passenger zijn destination verandert
	private boolean changeDestination()	{
		return true;
	}
	// de passenger gaat in de podcar is
	public void enterPodcar() {
		
	}
	// deze geeft of van de passenger de podcar heeft uitgestopt
	public void leavePodcar() {
		
	}
	
}
