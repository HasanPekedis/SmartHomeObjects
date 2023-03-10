//Hasan Pekedis 150120068


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SmartLight extends SmartObject implements LocationControl, Programmable {
	private boolean hasLightTurned;
	private Calendar programTime;
	private boolean programAction;

	public SmartLight(String alias, String macId) {
		super();
		setMacID(macId);
		setAlias(alias);

	}
	
	public void turnOnLight() {
		if (!isHasLightTurned()) {
			setHasLightTurned(true);
			System.out.println("Smart Light -" + getAlias() + " is turned on now " + currentTime());
		} 
		else {
			System.out.println("Smart Light - " + getAlias() + " has been already turned on");
		}
	}
	
	public void turnOffLight() {
		if (isHasLightTurned()) {
			setHasLightTurned(false);
			System.out.println("Smart Light - " + getAlias() + " is turned     off now" + currentTime());
		} 
		else {
			System.out.println(" Smart Light - " + getAlias() + " has been already turned off");
		}
	}


	@Override
	public boolean testObject() {
		if (isConnectionStatus()) {
			SmartObjectToString();
			turnOnLight();
			turnOffLight();
			System.out.println("Test completed for SmartLight");
		}
		return isConnectionStatus();
	}

	@Override
	public boolean shutDownObject() {
		if (isConnectionStatus()) {
			turnOffLight();
			SmartObjectToString();
			
		}
		return isConnectionStatus();
	}
	public void onLeave() {
		if (isConnectionStatus()) {
			setHasLightTurned(false);
			System.out.println("On Leave -> Smart Light - " + getAlias());
			System.out.println("Smart Light - " + getAlias() + " is turned off now " + currentTime());
		}

	}

	@Override
	public void onCome() {
		if (isConnectionStatus()) {
			setHasLightTurned(true);
			System.out.println("On Come -> Smart Light - " + getAlias());
			System.out.println("Smart Light - " + getAlias() + " is turned on now " + currentTime());
		}

	}

	@Override
	public void setTimer(int seconds) {
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.SECOND, seconds);
		setProgramTime(calender);
		if (isConnectionStatus()) {
			if (isHasLightTurned()) {
				System.out.println("Smart light - " + getAlias() + " will be turned off " + seconds + " seconds later! "
						+ currentTime());
			} 
			else {
				System.out.println("Smart light - 	" + getAlias() + " will be turned on " + seconds
						+ " seconds later! " + currentTime());
			}
		}

	}

	@Override
	public void cancelTimer() {
		if (isConnectionStatus()) {
			setProgramTime(null);
		}

	}
	
	@Override
	public void runProgram() {
		if (isConnectionStatus()) {
			if (getProgramTime() != null) {
				if (isSameDateTime(getProgramTime(), Calendar.getInstance())) {
					System.out.println("RunProgram -> Smart Light - " + getAlias());
					if (isHasLightTurned()) {
						turnOffLight();
					} 
					else {
						turnOnLight();
					}
					setProgramTime(null);
				}
			}

		}
	}

	public boolean isSameDateTime(Calendar cal1, Calendar cal2) {
		
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)
				&& cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)
				&& cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND));
	}


	
	public String currentTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		String currentDate = simpleDateFormat.format(Calendar.getInstance().getTime());
		return "(Current time: " + currentDate + ")";
	}
	
	
	public boolean isHasLightTurned() {
		return hasLightTurned;
	}

	public void setHasLightTurned(boolean hasLightTurned) {
		this.hasLightTurned = hasLightTurned;
	}

	public Calendar getProgramTime() {
		return programTime;
	}

	public void setProgramTime(Calendar programTime) {
		this.programTime = programTime;
	}

	public boolean isProgramAction() {
		return programAction;
	}

	public void setProgramAction(boolean programAction) {
		this.programAction = programAction;
	}

	

}