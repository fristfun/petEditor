package ind.chy.pet.data;

import ind.chy.pet.model.DesktopWorld.Vec2;

public class TriggerDefault extends TriggerData{
	
	private Vec2 position;		//Ĭ�ϴ�����,��ʼ������
	private String icon;		//����ͼ��
	private boolean bRandom=true;	//�Ƿ����,���ʱ�Ӷ����������

	
	
	public boolean isbRandom() {
		return bRandom;
	}
	public void setbRandom(boolean bRandom) {
		this.bRandom = bRandom;
	}
	public Vec2 getPosition() {
		return position;
	}
	public void setPosition(Vec2 position) {
		this.position = position;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}	
	
	
}
