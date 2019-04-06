package ind.chy.pet.data;

import java.util.ArrayList;

import ind.chy.pet.data.EventItemData.EventTriggerData;
import ind.chy.pet.model.DesktopWorld.Vec2;

public class TriggerDst extends TriggerData{
	/*
	 * �¼��������ʱ,���ñ�������
	 * 
	 * �������е�Ŀ�ĵ�
	 * */
	private ArrayList<TriggerDstData> dstList=new ArrayList<>();	//�¼���
	
	public static class TriggerDstData{
		private Vec2 dst;		//Ŀ�ĵ�
		private float range;	//���ֵ
		public Vec2 actualDst;	//����ʱ,ʵ�ʲ�ֵ
		private EventTriggerData toEvent=null;	//�¼�
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
