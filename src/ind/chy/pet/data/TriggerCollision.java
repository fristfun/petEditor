package ind.chy.pet.data;

import java.util.ArrayList;

import ind.chy.pet.data.EventItemData.EventTriggerData;

public class TriggerCollision extends TriggerData{
	private ArrayList<TriggerCollisionData> collisionEventList=new ArrayList<>();	//碰撞事件
	
	
	public static class TriggerCollisionData{
		private String objName;			//碰撞物体名
		private EventTriggerData toEvent;	//响应事件
		public String getObjName() {
			return objName;
		}
		public void setObjName(String objName) {
			this.objName = objName;
		}
		public EventTriggerData getToEvent() {
			return toEvent;
		}
		public void setToEvent(EventTriggerData toEvent) {
			this.toEvent = toEvent;
		}
		
		public String[] toStringArrays(){
			String[] rowData={toEvent.getEvent(),objName,toEvent.isbChangeDirct()+"",toEvent.getChance()+""};
			return rowData;
		}
	}


	public ArrayList<TriggerCollisionData> getCollisionEventList() {
		return collisionEventList;
	}


	public void setCollisionEventList(ArrayList<TriggerCollisionData> collisionEventList) {
		this.collisionEventList = collisionEventList;
	}
	
	
}
