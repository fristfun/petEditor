package ind.chy.pet.data;

import java.util.ArrayList;

import ind.chy.pet.model.DesktopWorld.Vec2;

public class ActItemData {
	/*
	 * 编辑时,只有单方向
	 * 桌宠根据运动方向与动作方向进行镜像自动调整
	 * 
	 * 例如:
	 * 1.移动动作,方向为左 当运动方向为右时,使用镜像表达
	 * 2.爬墙动作,方向为左 当运动方向为右时,使用镜像表达,实际位移为上下
	 * 
	 * 移动动作,以运动方向进行移动,当二次触发后进行方向移动
	 * 1.移动动作过程中,再次获取到移动动作,则移动进行返向
	 * 2.爬墙动作,再次获取到爬墙动作时,也会反向,所以爬墙时不进行镜像触发
	 * 
	 * */
	private String name;	//动作名称
	
	private int picDelay=0;
	
	private boolean bSimulation=false;	//动作默认不受世界力影响
	
	private Vec2 worldForce=null;				//受世界力影响时,世界力大小,表示为阻力和重力
	
	private Vec2 speed=new Vec2(0, 0);	//移动方向,像素,X为X轴的正负方向,Y为Y轴正负方向
	
	private AppVector<PicData> listPic=new AppVector<>();	//动作图片
	
	public static class PicData {
		
		private boolean bUsePicPara=false;
		/*
		 * 移动方向,像素,X为X轴的正负方向,Y为Y轴正负方向
		 * 面朝向,前后
		 * */
		private Vec2 speed=new Vec2(0, 0);	
												
		
		private int picDelay=0;	//毫秒
		
		private String picName;


		public int getPicDelay() {
			return picDelay;
		}

		public void setPicDelay(int picDelay) {
			this.picDelay = picDelay;
		}

		public boolean isbUsePicPara() {
			return bUsePicPara;
		}

		public void setbUsePicPara(boolean bUsePicPara) {
			this.bUsePicPara = bUsePicPara;
		}

		public String getPicName() {
			return picName;
		}

		public void setPicName(String picName) {
			this.picName = picName;
		}

		public Vec2 getSpeed() {
			return speed;
		}

		public void setSpeed(Vec2 speed) {
			this.speed = speed;
		}

		
	}
	
	public ActItemData(String s){
		name=s;
	}
	
	

	public ArrayList<String> getStringList(){
		if(listPic==null||listPic.size()==0)
			return null;
		ArrayList<String> tmp=new ArrayList<>();
		for(PicData item:listPic){
			tmp.add(item.getPicName());
		}
		return tmp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AppVector<PicData> getListPic() {
		return listPic;
	}

	public void setListPic(AppVector<PicData> listPic) {
		this.listPic = listPic;
	}

	public int getPicDelay() {
		return picDelay;
	}



	public void setPicDelay(int picDelay) {
		this.picDelay = picDelay;
	}

	

	public Vec2 getSpeed() {
		return speed;
	}

	public void setSpeed(Vec2 speed) {
		this.speed = speed;
	}

	public boolean isbSimulation() {
		return bSimulation;
	}

	public void setbSimulation(boolean bSimulation) {
		this.bSimulation = bSimulation;
	}

	public Vec2 getWorldForce() {
		return worldForce;
	}

	public void setWorldForce(Vec2 worldForce) {
		this.worldForce = worldForce;
	}

}
