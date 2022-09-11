package assign1;
public class Weather {
	
	private	double tempC; 		//Temp in °C
	private	double windSpeed; 	//wind speed in km/h
	private	double windChill; 	//wind chill factor
	private	boolean isSevere; 	//indicates if the coldness is sever
	
	public Weather(){
		tempC = 20.0;
		windSpeed = 0.0;
		setWindChill();
		setSeverity();
	}
	
	public Weather(double t, double w){
		tempC = checkTempC( t );
		windSpeed = checkWindSpeed( w );
		setWindChill();
		setSeverity();
	}
	
	public void setTempC(double t){
		if( isValidTemperature(t) ){
			tempC = t;
			setWindChill();
			setSeverity();
		}
	}
	
	public double getTempC(){
		return tempC;
	}
	
	public void setWindSpeed(double w){
		if( isValidWindSpeed(w) ){
			windSpeed = w;
			setWindChill();
			setSeverity();
		}
	}
	
	public double getWindSpeed(){
		return windSpeed;
	}
	
	private boolean isValidTemperature(double t){
		return  ( t >= -45.0 && t <= 65.0 );
	}
	
	private boolean isValidWindSpeed(double w){
		return  ( w >= 0.0 );
	}
	
	private double checkTempC(double t){
		if(isValidTemperature(t))
			return t;
		return 20.0;
	}
	
	private double checkWindSpeed(double w){
		if(isValidWindSpeed(w))
			return w;
		return 0;
	}
	
	private void setWindChill(){
		if(getTempC() <= 10.0 && getWindSpeed() >= 5.0 ){
			windChill = 13.12 + 0.6215 * getTempC() - 
				    	11.37 * Math.pow(getWindSpeed(), 0.16) + 
				    	0.3965 * getTempC() * Math.pow(getWindSpeed(), 0.16);
		}
		else 
			windChill = getTempC();
	}
	
	private void setSeverity(){
		isSevere = (windChill <= -26.0);
	}
	
	public double getWindChill(){
		return windChill;
	}
	
	public boolean getSeverity(){
		return isSevere;
	}
	
	public String toString(){
		String severity = getSeverity() ? " (severely cold)" : "";
		return "Current temperature is " + (int)getTempC() + " °C, feels like " + (int)getWindChill() + " °C" + severity +", with a wind speed of " + (int)getWindSpeed() + " km/h";
	}
}
