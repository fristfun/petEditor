package ind.chy.pet.data;

import java.util.ArrayList;

import ind.chy.pet.data.EventItemData.EventTriggerData;

public class TriggerAct extends TriggerData {

	public int runActCnt;
	private ArrayList<TriggerActData> actCompleteList=new ArrayList<>();
	
	public static class TriggerActData{
		public int actualTimes;
		private int minTimes,maxTimes;
		private EventTriggerData toEvent;
		public int getMinTimes() {
			return minTimes;
		}
		public void setMinTimes(int minTimes) {
			this.minTimes = minTimes;
		}
		public int getMaxTimes() {
			return maxTimes;
		}
		public void setMaxTimes(int maxTimes) {
			this.maxTimes = maxTimes;
		}
		public EventTriggerData getToEvent() {
			return toEvent;
		}
		public void setToEvent(EventTriggerData toEvent) {
			this.toEvent = toEvent;
		}
		
		public String[] toStringArrays(){
			String[] arr={toEvent.getEvent(),minTimes+"",maxTimes+"",toEvent.isbChangeDirct()+"",toEvent.getChance()+""};
			return arr;
		}
		
	}

	public ArrayList<TriggerActData> getActCompleteList() {
		return actCompleteList;
	}

	public void setActCompleteList(ArrayList<TriggerActData> actCompleteList) {
		this.actCompleteList = actCompleteList;
	}
	
	
}
