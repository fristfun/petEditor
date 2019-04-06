package ind.chy.pet.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ind.chy.pet.model.DesktopWorld.Vec2;
import ind.chy.pet.view.PetWin;

public abstract class DesktopObject implements MouseListener,MouseMotionListener{
	/*
	 * �����������ģ��
	 * */
	//��������
	protected String name=null;
	protected int height=0;
	protected int width=0;
	protected Vec2 lastPos=new Vec2(0, 0);						//���������קʱ�����ٶ�
	protected Vec2 updatePos=new Vec2(0,0);						//���ڸ���ʱ��λ��
	protected Vec2 speed=new Vec2(0, 0);						//�����˶��ٶ�
	protected PetWin win;										//����
	protected DesktopWorld world;								//����
	protected boolean bDrag=false;								//���
	protected boolean bSimulate=true;							//�ɹرշ���
	protected Vec2 force=null;									//������յ�������
	public static final Vec2 DEFAULT_FORCE=new Vec2(0, 0);		//Ĭ�ϲ�����������
	public DesktopObject(PetWin win){
		this.win=win;
		win.addMouseMotionListener(this);
		win.addMouseListener(this);
	}
	
	public PetWin getWin() {
		return win;
	}
	
	protected void initSize(PetWin win){
		width=win.getWidth();
		height=win.getHeight();
	}
	
	protected Point getWinPos() {
		return win.getLocation();
	}
	
	protected Vec2 getPos() {		//����ģ��ʹ��
		return updatePos;
	}
	
	protected void setUpdatePos(Vec2 pos){
		updatePos=pos;
		win.setLocation((int)(pos.x-width/2),(int)(pos.y-height));
	}
	
	protected int getEdgeUp() {
		return win.getLocation().y;
	}
	
	protected void setEdgeUp(int y) {
		updatePos.y=y+height;
	}
	
	protected int getEdgeDown() {
		return (int) updatePos.y;
	}
	
	protected void setEdgeDown(int y) {
		updatePos.y=y;
	}
	
	protected int getEdgeLeft() {
		return win.getLocation().x;
	}
	
	protected void setCenterX(int x) {
		updatePos.x=x;
	}
	
	protected void setCenterY(int y) {
		updatePos.y=y+height/2;
	}
	
	protected int getEdgeRight() {
		return win.getLocation().x+width;
	}
	
	protected int getCenterX() {
		return win.getLocation().x+width/2;
	}
	
	protected void setDesktopWorld(DesktopWorld world) {
		this.world=world;
	}
	
	protected Vec2 getObjectForce(){	//����������
		if(force==null)
			return DEFAULT_FORCE;
		return force;
	}
	
	public String getName() {
		return name;
	}

	//��ȡ�����
	public abstract Rectangle getAround();
	
	//��������
	public abstract boolean triggerCollisionObject(String name);
	public abstract boolean triggerAroundObject(String name,Point pos);
	public abstract void triggerDragAction(Vec2 speed);
	
	//��������
	public abstract void Update(int tick);		//����ͼ��
	

}
