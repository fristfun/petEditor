package ind.chy.pet.data;

import ind.chy.pet.model.DesktopWorld.Vec2;

public class TriggerDefault extends TriggerData{
	
	private Vec2 position;		//默认触发器,初始化坐标
	private String icon;		//托盘图标
	private boolean bRandom=true;	//是否随机,随机时从顶部随机掉落

	
	
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
