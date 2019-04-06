package ind.chy.pet.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Vector;

public class DesktopWorld {
	
	/*
	 * 
	 * ��������ģ��
	 * */
	//������ ��������������
	public final static String ENV_FLOOR="����";
	public final static String ENV_WALL="ǽ��";
	public final static String ENV_TOP="�ݶ�";
	public final static String[] ENV_TYPE= {ENV_FLOOR,ENV_WALL,ENV_TOP};
	//���Ƽ� �����˶�
	private ArrayList<DesktopObject> objectList=new ArrayList<>();
	private ArrayList<DesktopObject> destoryObjectList=new ArrayList<>();
	private Rectangle screen_bounds;
	private Dimension screen;
	private int randomUpdateSpan=0;
	
	private final static int SPEED_X_MAX=10;
	private final static int SPEED_Y_MAX=10;
	
	private static DesktopWorld world=null;
	
	private DesktopWorld(Dimension screen,Rectangle screen_bounds){	//���������˶��ٶ�
		this.screen_bounds=screen_bounds;
		this.screen=screen;
		
	}
	
	
	public static class Vec2{
		public float x;
		public float y;
		
		public Vec2(){
			
		}
		public Vec2(float x,float y){
			this.x=x;
			this.y=y;
		}
		
		public Vec2(Point point){
			this.x=point.x;
			this.y=point.y;
		}
		
		public Vec2(Vec2 v){
			this.x=v.x;
			this.y=v.y;
		}
		
		public void reverse(){
			this.x*=-1;
			this.y*=-1;
		}
		
		/*
		 * λ�ñȽ�ֻ�Ƚ�X���Y��
		 * */
		public boolean equalsPostion(Vec2 dst,float delta) {
			if((dst.x-delta)<x&&x<(dst.x+delta))
				return true;
			if((dst.y-delta)<y&&y<(dst.y+delta))
				return true;
			return false;
		}
		
		@Override
		public String toString() {
			return "("+x+","+y+")";
		}
	}

	
	public static DesktopWorld createWorld(Dimension screen,Rectangle screen_bounds) {
		if(world==null)
			world=new DesktopWorld(screen,screen_bounds);
		return world;
	}
	
	public static DesktopWorld getWorld() {
		return world;
	}
	
	//��ǰ����posת���ɰٷֱ�pos
	public Vec2 getPosWorld2Percent(Vec2 pos){
		return new Vec2(pos.x*100/screen.width, pos.y*100/screen.height);
	}
	
	//��ǰ�ٷֱ�posת��������pos
	public Vec2 getPosPercent2World(Vec2 percentPos){
		return new Vec2(percentPos.x*screen.width/100, percentPos.y*screen.height/100);
	}
	
	public void addObject(DesktopObject object){
		object.setDesktopWorld(this);
		objectList.add(object);
		System.out.println("add:"+object.name+"   size:"+objectList.size());
	}
	
	public boolean isEmpty(){
		return objectList.isEmpty();
	}
	
	private static Vec2 pos;
	private static boolean bMove;
	private static int updateIndex;
	private static int updateSize;
	public void update(int time){	//����ʱ�� ms
		
		for(DesktopObject object:objectList){
			pos=object.getPos();
			if(object.bDrag) {
				object.speed=calcSpeed(pos, object.lastPos, time);	//��קʱ,ֻ������ק��
				object.lastPos=new Vec2(pos);
				object.triggerDragAction(object.speed);
				continue;
			}
			bMove=true;	//�����ƶ�Ч������
			
			if(!object.bSimulate) {
				object.speed.x=0;
				object.speed.y=0;
				bMove=false;
			}
			
			
			if(object.getEdgeDown()>=screen_bounds.height) {
				//����
				if(object.triggerCollisionObject(ENV_FLOOR)){
					object.setEdgeDown(screen_bounds.height);
				}else {

				}
			}else if(object.getEdgeDown()>=screen.height) {
				//����
				if(object.triggerCollisionObject(ENV_FLOOR)){
					object.setEdgeDown(screen.height);
				}else {

				}
			}

			if(object.getCenterX()<=0) {
				//��ǽ
				if(object.triggerCollisionObject(ENV_WALL)){
					object.setCenterX(0);
				}else {

				}
			}else if(object.getCenterX()>=screen.width){
				//��ǽ
				if(object.triggerCollisionObject(ENV_WALL)){
					object.setCenterX(screen.width);
				}else {

				}
			}
			
			if(object.getEdgeUp()<=0){
				//����
				if(object.triggerCollisionObject(ENV_TOP)){
					object.setCenterY(0);
				}else {

				}
			}
			
			if(!bMove)
				continue;
			//�����ٶ� �������ۼ�λ��,���ٶȹ�Сʱ,λ��ֹͣ
			if(Math.abs(object.speed.x)>SPEED_X_MAX){			//X����ٶ�
				object.speed.x=object.speed.x>SPEED_X_MAX?SPEED_X_MAX:object.speed.x;
				object.speed.x=object.speed.x<-SPEED_X_MAX?-SPEED_X_MAX:object.speed.x;
			}
			
			Vec2 Niu=object.getObjectForce();
			float negX=Niu.x*time/1000;
			object.speed.x+=object.speed.x<-1?negX:0;
			object.speed.x-=object.speed.x>1?negX:0;
			object.speed.y+=object.speed.y<SPEED_Y_MAX?Niu.y*time/1000:0;	
			object.updatePos=calcPostion(pos, object.speed);	//����λ��
		}
		
		updateSize=objectList.size();
		for(updateIndex=0;updateIndex<updateSize;updateIndex++){
			objectList.get(updateIndex).Update(time);
			
		}

		if(!destoryObjectList.isEmpty()){
			for(DesktopObject object: destoryObjectList){
				object.win.dispose();
				objectList.remove(object);
			}
			destoryObjectList.clear();
		}
	}
	
	private Vec2 calcPostion(Vec2 pos,Vec2 speed) {
		Vec2 newPos=new Vec2();
		newPos.x=pos.x+speed.x;
		newPos.y=pos.y+speed.y;
		if(newPos.y>screen.height)
			newPos.y=screen.height;
		return newPos;
	}
	
	private Vec2 calcSpeed(Vec2 pos,Vec2 lastPos,int time) {
		Vec2 speed=new Vec2();
		speed.x=pos.x-lastPos.x;
		speed.y=pos.y-lastPos.y;
		return speed;
	}
	
	public void destroyObject(DesktopObject object){
		destoryObjectList.add(object);
	}
	
}
