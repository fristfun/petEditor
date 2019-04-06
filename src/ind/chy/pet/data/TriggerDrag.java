package ind.chy.pet.data;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gson.annotations.Expose;

import ind.chy.pet.model.DesktopWorld.Vec2;

public class TriggerDrag extends TriggerData {

	public static class TriggerDragData{
		public Vec2 ac;		//加速度,入口条件
		public Vec2 speed;	//动作的速度环境
		public String act;	//动作
		public Vec2 getAc() {
			return ac;
		}
		public void setAc(Vec2 ac) {
			this.ac = ac;
		}
		public Vec2 getSpeed() {
			return speed;
		}
		public void setSpeed(Vec2 speed) {
			this.speed = speed;
		}
		public String getAct() {
			return act;
		}
		public void setAct(String act) {
			this.act = act;
		}
		
		public String[] toStringArrays(){
			String[] arrys={ac.toString(),speed.toString(),act};
			return arrys;
		}
	}
	
	private class DragActComparator implements Comparator<TriggerDragData>{

		@Override
		public int compare(TriggerDragData o1, TriggerDragData o2) {
			if(o1.ac.x>o2.ac.x)
				return 1;
			return -1;
		}
		
	}
	
	private String toEvent=null;	//退出拖拽事件
	private ArrayList<TriggerDragData> dragActList=new ArrayList<>();

	public String getToEvent() {
		return toEvent;
	}
	public void setToEvent(String toEvent) {
		this.toEvent = toEvent;
	}
	public ArrayList<TriggerDragData> getDragActList() {
		return dragActList;
	}
	public void setDragActList(ArrayList<TriggerDragData> dragActList) {
		this.dragActList = dragActList;
	}
	
	private transient ArrayList<TriggerDragData> sortList=null;
	public ArrayList<TriggerDragData> getSortAcList(){
		if(sortList!=null){
			return sortList;
		}
		sortList=new ArrayList<>();
		sortList.addAll(dragActList);
		sortList.sort(new DragActComparator());
		return sortList;
	}
	
}
