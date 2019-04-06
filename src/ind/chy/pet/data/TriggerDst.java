package ind.chy.pet.data;

import java.util.ArrayList;

import ind.chy.pet.data.EventItemData.EventTriggerData;
import ind.chy.pet.model.DesktopWorld.Vec2;

public class TriggerDst extends TriggerData{
	/*
	 * 事件动作完成时,调用本触发器
	 * 
	 * 动作运行到目的地
	 * */
	private ArrayList<TriggerDstData> dstList=new ArrayList<>();	//事件表
	
	public static class TriggerDstData{
		private Vec2 dst;		//目的地
		private float range;	//随机值
		public Vec2 actualDst;	//运行时,实际差值
		private EventTriggerData toEvent=null;	//事件
		public Vec2 getDst() {
			return dst;
		}
		public void setDst(Vec2 dst) {
			this.dst = dst;
		}
		public float getRange() {
			return range;
		}
		public void setRange(float range) {
			this.range = range;
		}
		public EventTriggerData getToEvent() {
			return toEvent;
		}
		public void setToEvent(EventTriggerData toEvent) {
			this.toEvent = toEvent;
		}
		
		public String[] toStringArrays(){
			String[] rowData={toEvent.getEvent(),dst.toString(),range+"",toEvent.isbChangeDirct()+"",toEvent.getChance()+""};
			return rowData;
		}
	}

	public ArrayList<TriggerDstData> getDstList() {
		return dstList;
	}

	public void setDstList(ArrayList<TriggerDstData> dstList) {
		this.dstList = dstList;
	}


	
	
}
