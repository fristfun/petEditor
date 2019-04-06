package ind.chy.pet.data;

import java.awt.Rectangle;
import java.util.ArrayList;

import ind.chy.pet.data.EventItemData.EventTriggerData;

public class TriggerTouch extends TriggerData{
	
	public static class TriggerTouchData{
		private Rectangle rect;//左上,右下
		private EventTriggerData toEvent;
		
		public Rectangle getRect() {
			return rect;
		}
		public void setRect(Rectangle rect) {
			this.rect = rect;
		}
		public EventTriggerData getToEvent() {
			return toEvent;
		}
		public void setToEvent(EventTriggerData toEvent) {
			this.toEvent = toEvent;
		}
		
		public String[] toStringArrays(){
			String[] arrs={toEvent.getEvent(),"("+rect.x+","+rect.y+")"+",("+(rect.x+rect.getWidth())+","+(rect.y+rect.getHeight())+")",toEvent.isbChangeDirct()+"",toEvent.getChance()+""};
			return arrs;
		}
		
	}
	
	private ArrayList<TriggerTouchData> touchList=new ArrayList<>();		//区域点击

	public ArrayList<TriggerTouchData> getTouchList() {
		return touchList;
	}

	public void setTouchList(ArrayList<TriggerTouchData> touchList) {
		this.touchList = touchList;
	}
	
	
	
}
