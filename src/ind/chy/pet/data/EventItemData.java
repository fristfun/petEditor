package ind.chy.pet.data;

import java.util.ArrayList;

import ind.chy.pet.model.DesktopWorld.Vec2;

public class EventItemData {
	
	/*
	 * �¼�����
	 * 
	 * �¼����԰����������Ч��
	 * 
	 * ������ĳ������ʱ�Զ���ת�������¼�����
	 * 
	 * �¼������ڶ���
	 * 
	 * 
	 * */
	private String name=null;
	private String act=null;
	private Vec2 initSpeed=null;
	
	private AppVector<TriggerData> tiggerList=new AppVector<>();
	
	public static class EventTriggerData{
		private String event;		//�¼�
		private boolean bChangeDirct=false;	//ת�䷽��
		private float chance;		//��������
		
		public String getEvent() {
			return event;
		}
		public void setEvent(String event) {
			this.event = event;
		}
		public float getChance() {
			return chance;
		}
		public void setChance(float chance) {
			this.chance = chance;
		}
		public boolean isbChangeDirct() {
			return bChangeDirct;
		}
		public void setbChangeDirct(boolean bChangeDirct) {
			this.bChangeDirct = bChangeDirct;
		}
	}

	public EventItemData(){

	}
	
	public EventItemData(String name) {
		this.name=name;
	}
	
	
	public AppVector<TriggerData> getTiggerList() {
		return tiggerList;
	}

	public void setTiggerList(AppVector<TriggerData> tiggerList) {
		this.tiggerList = tiggerList;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}

	public Vec2 getInitSpeed() {
		return initSpeed;
	}

	public void setInitSpeed(Vec2 initSpeed) {
		this.initSpeed = initSpeed;
	}

	public ArrayList<String> getStringList(){
		if(tiggerList==null||tiggerList.size()==0)
			return null;
		ArrayList<String> tmp=new ArrayList<>();
		for(TriggerData item:tiggerList){
			tmp.add(item.getTrigger_type());
		}
		return tmp;
	}
	
}
