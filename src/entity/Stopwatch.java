package entity;

import java.util.TimerTask;

public class Stopwatch extends TimerTask{
	
	private int hours, minutes, seconds;
	private boolean timing;
	
	public Stopwatch() {
		
		
		
	}
	
	public void run() {
		
		if(!this.timing)
			return;
		
		this.seconds ++;
		
		if(this.seconds == 60) {
			
			this.minutes ++;
			this.seconds = 0;
			
		}
		
		if(this.minutes == 60) {
			
			this.hours ++;
			this.minutes = 0;
			
		}
		
	}
	
	public String getTimeAsString() {
		
		String hrs, mins, secs;
		
		if(this.hours < 10)
			hrs = "0" + this.hours;
		
		else
			hrs = Integer.toString(hours);
		
		if(this.minutes < 10)
			mins = "0" + this.minutes;
		
		else
			mins = Integer.toString(this.minutes);
		
		if(this.seconds < 10)
			secs = "0" + this.seconds;
		
		else
			secs = Integer.toString(this.seconds);
		
		return hrs + ":" + mins + ":" + secs;
		
	}
	
	public void setTime(String s) {
		
		String[] time = s.split(":");
		
		this.hours = Integer.parseInt(time[0]);
		this.minutes = Integer.parseInt(time[1]);
		this.seconds = Integer.parseInt(time[2]);
		
	}
	
	public void setTiming(boolean b) {
		
		this.timing = b;
		
	}
	
	public void changeTiming() {
		
		this.timing ^= true;
		
	}
	
	public void equals(Stopwatch stopwatch) {
		
		
		
	}
	
}
