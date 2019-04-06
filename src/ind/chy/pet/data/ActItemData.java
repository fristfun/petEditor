package ind.chy.pet.data;

import java.util.ArrayList;

import ind.chy.pet.model.DesktopWorld.Vec2;

public class ActItemData {
	/*
	 * �༭ʱ,ֻ�е�����
	 * ��������˶������붯��������о����Զ�����
	 * 
	 * ����:
	 * 1.�ƶ�����,����Ϊ�� ���˶�����Ϊ��ʱ,ʹ�þ�����
	 * 2.��ǽ����,����Ϊ�� ���˶�����Ϊ��ʱ,ʹ�þ�����,ʵ��λ��Ϊ����
	 * 
	 * �ƶ�����,���˶���������ƶ�,�����δ�������з����ƶ�
	 * 1.�ƶ�����������,�ٴλ�ȡ���ƶ�����,���ƶ����з���
	 * 2.��ǽ����,�ٴλ�ȡ����ǽ����ʱ,Ҳ�ᷴ��,������ǽʱ�����о��񴥷�
	 * 
	 * */
	private String name;	//��������
	
	private int picDelay=0;
	
	private boolean bSimulation=false;	//����Ĭ�ϲ���������Ӱ��
	
	private Vec2 worldForce=null;				//��������Ӱ��ʱ,��������С,��ʾΪ����������
	
	private Vec2 speed=new Vec2(0, 0);	//�ƶ�����,����,XΪX�����������,YΪY����������
	
	private AppVector<PicData> listPic=new AppVector<>();	//����ͼƬ
	
	public static class PicData {
		
		private boolean bUsePicPara=false;
		/*
		 * �ƶ�����,����,XΪX�����������,YΪY����������
		 * �泯��,ǰ��
		 * */
		private Vec2 speed=new Vec2(0, 0);	
												
		
		private int picDelay=0;	//����
		
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
