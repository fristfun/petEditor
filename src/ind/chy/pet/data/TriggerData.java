package ind.chy.pet.data;

import java.lang.reflect.Type;

public class TriggerData {
	/*
	 * �¼����� ����һһ��Ӧ,����GSON�����쳣
	 * 
	 * 
	 * 
	 * */
	
	public static long GLOBAL_TRIGGER_MAX=3600*1000;	//Сʱ
	public static long TRIGGER_INIT_MIN=300;				//�����¼��� 1���ڲ��ڴ����¼�
	
	public final static String[] TRIGGER_TYPE={
			"Ĭ�ϴ�����","Ŀ�Ĵ�����","ʱ��������","���������","��ײ������","����������","��ק������"};
	
	public final static Type[] TRIGGER_CLASS={
			TriggerDefault.class,TriggerDst.class,TriggerTime.class,TriggerTouch.class,TriggerCollision.class,TriggerAct.class,TriggerDrag.class};
	
	private String trigger_type;
	protected boolean bGlobal=false;
	public long triggerCnt=0;		//����
	public long triggerInitCnt=0;
	
	public TriggerData(){
		this.setTrigger_type(getStringFromType(this.getClass()));
	}
	
	public static String getStringFromType(Type t){
		int i=0;
		for(Type c:TRIGGER_CLASS) {
			if(c.equals(t)) {
				return TRIGGER_TYPE[i];
			}
			i++;
		}
		return null;
	}
	
	public static Type getTypeFromString(String type){
		int i=DataTils.findStringInArr(TRIGGER_TYPE, type);
		if(i<0)
			return null;
		return TRIGGER_CLASS[i];
	}
	
	public static TriggerData getInstance(String type) {
		int i=DataTils.findStringInArr(TRIGGER_TYPE, type);
		if(i<0)
			return null;
		TriggerData tmp=null;
		String name=TRIGGER_CLASS[i].getTypeName();
		try {
			Class clz = Class.forName(name);
			tmp=(TriggerData)clz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return tmp;
	}

	

	public String getTrigger_type() {
		return trigger_type;
	}

	public void setTrigger_type(String trigger_type) {
		this.trigger_type = trigger_type;
	}

	public boolean isbGlobal() {
		return bGlobal;
	}

	public void setbGlobal(boolean bGlobal) {
		this.bGlobal = bGlobal;
	}
	
	
}
