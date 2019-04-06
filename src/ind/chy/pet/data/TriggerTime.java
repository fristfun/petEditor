package ind.chy.pet.data;

import java.util.ArrayList;

import ind.chy.pet.data.EventItemData.EventTriggerData;

public class TriggerTime extends TriggerData{
	
	private ArrayList<TriggerTimeData> timeNextEventList=new ArrayList<>();	//时间停止后跳转动作
	
	public static class TriggerTimeData{
		private int timeMin=0,timeMax;					//时长
		public int actualTime=0;						//实际触发时间
		public boolean bActualTrigger;					//是否已触发过
		private EventTriggerData toEvent;
		public int getTimeMin() {
			return timeMin;
		}
		public void setTimeMin(int timeMin) {
			this.timeMin = timeMin;
		}
		public int getTimeMax() {
			return timeMax;
		}
		public void setTimeMax(int timeMax) {
			this.timeMax = timeMax;
		}
		public EventTriggerData getToEvent() {
			return toEvent;
		}
		public void setToEvent(EventTriggerData toEvent) {
			this.toEvent = toEvent;
		}
		
		public String[] toStringArrays(){
			String[] tmp={toEvent.getEvent(),(timeMin/1000)+"",(timeMax/1000)+"",toEvent.isbChangeDirct()+"",toEvent.getChance()+""};
			return tmp;
		}
	}

	public ArrayList<TriggerTimeData> getTimeNextEventList() {
		return timeNextEventList;
	}

	public void setTimeNextEventList(ArrayList<TriggerTimeData> timeNextEventList) {
		this.timeNextEventList = timeNextEventList;
	}
	

}
