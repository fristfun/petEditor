package ind.chy.pet.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ind.chy.App.Launch.AppLaunch;
import ind.chy.pet.data.ActData;
import ind.chy.pet.data.ActItemData;
import ind.chy.pet.data.ActItemData.PicData;
import ind.chy.pet.data.DataTils;
import ind.chy.pet.data.EventData;
import ind.chy.pet.data.EventItemData;
import ind.chy.pet.data.EventItemData.EventTriggerData;
import ind.chy.pet.data.TriggerAct;
import ind.chy.pet.data.TriggerAct.TriggerActData;
import ind.chy.pet.data.TriggerDst;
import ind.chy.pet.data.TriggerDst.TriggerDstData;
import ind.chy.pet.data.TriggerCollision;
import ind.chy.pet.data.TriggerCollision.TriggerCollisionData;
import ind.chy.pet.data.TriggerData;
import ind.chy.pet.data.TriggerDefault;
import ind.chy.pet.data.TriggerDrag;
import ind.chy.pet.data.TriggerDrag.TriggerDragData;
import ind.chy.pet.data.TriggerTime;
import ind.chy.pet.data.TriggerTime.TriggerTimeData;
import ind.chy.pet.data.TriggerTouch;
import ind.chy.pet.data.TriggerTouch.TriggerTouchData;
import ind.chy.pet.model.DesktopWorld.Vec2;
import ind.chy.pet.view.PetWin;
import ind.chy.pet.view.ViewTils;

public class DesktopPet extends DesktopObject{
	
	/*
	 * 桌宠模型
	 * */
	//由于动图大小使窗体大小变化,则检测面积需动态调整

	protected ActData act;
	protected EventData event;
	
	protected int win_x=0;
	protected int win_y=0;
	
	protected ActItemData runAct;		//当前运行动作
	protected ActItemData dragNextAct=null;
	protected ActItemData dragOldAct=null;
	protected EventItemData runEvent;	//当前运行事件
	protected int mActPicIndex;			//图片下标
	protected int mActPicSize;			//图片数量
	protected int mActPicDelayCnt;		//累计延时
	protected int mActPicDelay;			//图片暂留时间
	protected int mEventTimeCnt;		//事件计时
	protected int mSecondCnt=0;			//秒级
	
	protected EventItemData evDefault=null;		//默认事件
	protected TriggerDefault tDefault=null;		//默认触发器
	
	//全局触发器
	protected TriggerDst tDstGlobal=null;
	protected TriggerCollision tCollisionGlobal=null;
	protected TriggerTime tLongGlobal=null;
	protected TriggerTouch tTouchGlobal=null;
	protected TriggerDrag tDragGlobal=null;
	//局部触发器
	protected TriggerDst tDst=null;
	protected TriggerCollision tCollision=null;
	protected TriggerTime tLong=null;
	protected TriggerTouch tTouch=null;
	protected TriggerAct tAct=null;
	protected TriggerDrag tDrag=null;
	protected boolean isTop;
	//方向标识
	protected boolean bForward=true;
	//时间相关触发器,碰撞响应后需有最小的位移时间等
	protected ArrayList<TriggerData> triggerTimeDatas=new ArrayList<>();
	
	private DesktopPet(PetWin win) {
		super(win);
		// TODO Auto-generated constructor stub
	}
	
	public static DesktopPet createPet(PetWin win,ActData act,EventData event){
		DesktopPet pet=new DesktopPet(win);
		pet.name=event.getPetName();
		pet.act=act;
		pet.event=event;
		pet.initGlobalEvent(event);
		if(pet.evDefault==null){
			JOptionPane.showConfirmDialog(null, "未找到默认事件","错误",JOptionPane.CLOSED_OPTION);
			return null;
		}
		pet.initEventItem(pet.evDefault);
		Vec2 worldpos=null;
		if(pet.tDefault.isbRandom()){
			float posPercent=DataTils.getRandom(50, 950)*1.0f/1000;
			worldpos=new Vec2(AppLaunch.SCREEN.width*posPercent, AppLaunch.SCREEN.height*-0.1f);
		}else{
			worldpos=new Vec2(AppLaunch.SCREEN.width*pet.tDefault.getPosition().x/100,AppLaunch.SCREEN.height*pet.tDefault.getPosition().y/100);
		}
		pet.setWinTop(true);
		pet.setUpdatePos(worldpos);
		return pet;
	}
	
	public void setWinTop(boolean setTop){
		this.isTop=setTop;
		win.setAlwaysOnTop(isTop);
	}
	
	public boolean getWinTop(){
		return isTop;
	}
	
	public String getTrayIcon(){
		return tDefault.getIcon();
	}
	
	public DesktopPet(PetWin win,ActData act,EventData event,EventItemData debugEvent,Vec2 posPercent){	//debug
		super(win);
		this.act=act;
		this.event=event;
		this.name=event.getPetName();
		
		initGlobalEvent(event);
		initEventItem(debugEvent.getName());
//		initTrigger(debugEvent);
		
		setWinTop(true);

		Vec2 worldpos=new Vec2(AppLaunch.SCREEN.width*posPercent.x/100, AppLaunch.SCREEN.height*posPercent.y/100);
//		System.out.println("world pos="+worldpos.toString());
		setUpdatePos(worldpos);
	}
	
	
	protected ActItemData getActFromString(String sAct){
		int index=act.getStringList().indexOf(sAct);
		return act.getActList().get(index);
	}
	
	protected void initAct(String setAct){
		initAct(getActFromString(setAct));
	}
	
	protected void initAct(ActItemData setAct){
		runAct=setAct;
		mActPicIndex=0;
		bSimulate=runAct.isbSimulation();
		mActPicDelayCnt=0;
		mActPicSize=runAct.getListPic().size();
		PicData tmpPicData=getPicDataIndex(0);
		mActPicDelay=getPicDelay(tmpPicData);
		force=runAct.getWorldForce();
		win.drawImage(getImageIcon(tmpPicData), null);
		initSize(win);
	}
	
	//初始化 全局触发器,全局触发器唯一
	protected void initGlobalEvent(EventData setEvent){
		for(EventItemData eventItemData:setEvent.getListEvent()){
			for(TriggerData triggerData:eventItemData.getTiggerList()){
				if(triggerData.isbGlobal()){
					String type=triggerData.getTrigger_type();
					if(TriggerData.TRIGGER_TYPE[0].equals(type)){
						evDefault=eventItemData;
						tDefault=(TriggerDefault) triggerData;
					}else if(TriggerData.TRIGGER_TYPE[1].equals(type)){
						tDstGlobal=(TriggerDst)triggerData;
					}else if(TriggerData.TRIGGER_TYPE[2].equals(type)){
						tLongGlobal=(TriggerTime)triggerData;
					}else if(TriggerData.TRIGGER_TYPE[3].equals(type)){
						tTouchGlobal=(TriggerTouch) triggerData;
					}else if(TriggerData.TRIGGER_TYPE[4].equals(type)){
						tCollisionGlobal=(TriggerCollision) triggerData;
					}else if(TriggerData.TRIGGER_TYPE[6].equals(type)){
						tDragGlobal=(TriggerDrag) triggerData;
					}
				}
			}
		}
	}
	
	//初始化本地触发器,当有本地触发器时,触发对应事件,不触发全局触发触发器
	
	//必需在global初始化之后
	protected void initTrigger(EventItemData setEvent){
		for(TriggerData triggerData:setEvent.getTiggerList()){
			if(!triggerData.isbGlobal()){
				triggerData.triggerCnt=TriggerCollision.GLOBAL_TRIGGER_MAX;
				triggerData.triggerInitCnt=0;
				String type=triggerData.getTrigger_type();
				if(TriggerData.TRIGGER_TYPE[1].equals(type)){
					tDst=(TriggerDst)triggerData;
					for(TriggerDstData dstData:tDst.getDstList()){
						if(-0.1<dstData.getRange()&&dstData.getRange()<0.1)
							continue;
						float tmp=DataTils.getRandom((int)(dstData.getRange()*-1), (int)dstData.getRange());
						dstData.actualDst=new Vec2(dstData.getDst().x+tmp, dstData.getDst().y+tmp);
					}
				}else if(TriggerData.TRIGGER_TYPE[2].equals(type)){
					tLong=(TriggerTime)triggerData;
					for(TriggerTimeData timeData:tLong.getTimeNextEventList()){
						timeData.actualTime=DataTils.getRandom(timeData.getTimeMin(), timeData.getTimeMax());
						System.out.println("actual time:"+timeData.actualTime);
						timeData.bActualTrigger=false;
					}
					
				}else if(TriggerData.TRIGGER_TYPE[3].equals(type)){
					tTouch=(TriggerTouch) triggerData;
				}else if(TriggerData.TRIGGER_TYPE[4].equals(type)){
					tCollision=(TriggerCollision) triggerData;
				}else if(TriggerData.TRIGGER_TYPE[5].equals(type)){
					tAct=(TriggerAct)triggerData;
					tAct.runActCnt=0;
					for(TriggerActData actData:tAct.getActCompleteList()){
						actData.actualTimes=DataTils.getRandom(actData.getMinTimes(), actData.getMaxTimes());
					}
				}else if(TriggerData.TRIGGER_TYPE[6].equals(type)){
					tDrag=(TriggerDrag) triggerData;
				}
			}
		}
		triggerTimeDatas.clear();
		triggerTimeDatas.add(tCollision);
		triggerTimeDatas.add(tCollisionGlobal);
		this.runEvent=setEvent;
		initAct(setEvent.getAct());
	}
	
	protected void toEvent(EventTriggerData eventTriggerData){
		if(eventTriggerData.isbChangeDirct()){
			bForward=!bForward;
		}
		initEventItem(eventTriggerData.getEvent());
	}
	
	protected void initEventItem(EventItemData eventItemData){
		mEventTimeCnt=0;
		initTrigger(eventItemData);
		if(eventItemData.getInitSpeed()==null){
			speed.x=0f;
			speed.y=0f;
			return;
		}
		if(runAct.isbSimulation()){
			speed.x+=eventItemData.getInitSpeed().x;
			speed.y+=eventItemData.getInitSpeed().y;
		}else{
			speed.x=eventItemData.getInitSpeed().x;
			speed.y=eventItemData.getInitSpeed().y;
		}
	}
	/*
	 * 初始化事件项
	 * */
	protected void initEventItem(String eventName){
		int index=event.getStringList().indexOf(eventName);
		if(index<0)
			return;
		initEventItem(event.getListEvent().get(index));
	}
	/*
	 * 获取图片延时
	 * */
	protected int getPicDelay(PicData tmpPicData){
		if(tmpPicData.isbUsePicPara()){
			return tmpPicData.getPicDelay();
		}else{
			return runAct.getPicDelay();
		}
	}
	/*
	 * 获取某个下标图片,用于初始化
	 * */
	public PicData getPicDataIndex(int index){
		if(index>mActPicSize)
			index=mActPicSize-1;
		return runAct.getListPic().get(index);
	}
	/*
	 * 获取下张图片数据
	 * */
	protected PicData getNextPicData(){
		mActPicIndex++;
		if(mActPicIndex==mActPicSize)
			mActPicIndex=0;
		return runAct.getListPic().get(mActPicIndex);
	}
	/*
	 * 获取图片
	 * */
	protected ImageIcon getImageIcon(PicData picData){
		return new ImageIcon(AppLaunch.APP_PATH+picData.getPicName());
	}
	/*
	 * 获取速度
	 * */
	protected Vec2 getActSpeed(PicData tmpPicData){
		return tmpPicData.isbUsePicPara()==true?tmpPicData.getSpeed():runAct.getSpeed();
	}

	@Override
	public Rectangle getAround() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 
	 * 碰撞触发器检测
	 * */
	@Override
	public boolean triggerCollisionObject(String name) {
		TriggerCollision collision=null;
		if(tCollision==null&&tCollisionGlobal==null){
			return false;
		}
		
		if(tCollision!=null){		//全局触发器必触发,不依赖时间
			collision=tCollision;
			if(collision.triggerInitCnt<TriggerData.TRIGGER_INIT_MIN)
				return false;
			if(collision.triggerCnt<TriggerData.GLOBAL_TRIGGER_MAX)
				return false;
		}else{
			collision=tCollisionGlobal;
		}
		//获取触发器表 累积触发 即前个事件未触发,下个事件触发率提升,即有碰墙时,0.5概率反向移动,0.5概率爬墙时,第一个事件未触发,则下个事件触发概率为1
		float addchance=0f;
		for(TriggerCollisionData collisionData:collision.getCollisionEventList()){
			if(!collisionData.getObjName().equals(name)){
				continue;
			}
			collision.triggerCnt=0;
			if(!DataTils.randomSelet(collisionData.getToEvent().getChance()+addchance)){
				addchance+=collisionData.getToEvent().getChance();
				continue;
			}
			toEvent(collisionData.getToEvent());
			return true;
		}
		return false;
	}
	/*
	 * 目的地触发器检测
	 * */
	protected void checkDstTrigger(){
		TriggerDst dstTrigger=null;
		if(tDst==null&&tDstGlobal==null)
			return;
		if(tDst!=null){
			dstTrigger=tDst;
			if(dstTrigger.triggerCnt<TriggerData.GLOBAL_TRIGGER_MAX)
				return;
		}else{
			dstTrigger=tDstGlobal;
		}
		
		for(TriggerDstData dstData:dstTrigger.getDstList()){
			Vec2 actual=world.getPosPercent2World(dstData.actualDst);
			if(!actual.equalsPostion(updatePos, 20))		//5像素之内
				continue;
			dstTrigger.triggerCnt=0;
			if(!DataTils.randomSelet(dstData.getToEvent().getChance()))
				continue;
			toEvent(dstData.getToEvent());
			return;
		}
	}
	/*
	 * 时间触发器检测
	 * */
	protected void checkTimeTrigger(){
		TriggerTime triggerTime=null;
		if(tLong==null&&tLongGlobal==null){
			return;
		}
		triggerTime=tLong==null?tLongGlobal:tLong;
		for(TriggerTimeData timeData:triggerTime.getTimeNextEventList()){
			if(timeData.bActualTrigger)
				continue;
			if(mEventTimeCnt<timeData.actualTime)
				continue;
			timeData.bActualTrigger=true;
			if(!DataTils.randomSelet(timeData.getToEvent().getChance()))
				continue;
			toEvent(timeData.getToEvent());
			return;
		}
	}
	/*
	 * 动作触发器检测
	 * */
	protected void checkActTrigger(){
		if(tAct==null)
			return;
		tAct.runActCnt++;
		float addchance=0f;
		for(TriggerActData actData:tAct.getActCompleteList()){
			if(actData.actualTimes!=tAct.runActCnt)
				continue;
			if(!DataTils.randomSelet(actData.getToEvent().getChance()+addchance)){
				addchance+=actData.getToEvent().getChance();
				continue;
			}
			toEvent(actData.getToEvent());
			return;
		}
	}

	@Override
	public boolean triggerAroundObject(String name, Point pos) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void setUpdatePos(Vec2 pos) {
		super.setUpdatePos(pos);
	}
	
	/*
	 * 移动更新流程
	 * */
	@Override
	public void Update(int tick) {
		mSecondCnt+=tick;
		mActPicDelayCnt+=tick;
		
		if(mActPicDelayCnt>mActPicDelay){
			mActPicDelayCnt=0;
			PicData tmpPicData=getNextPicData();
			Vec2 picSpeed=getActSpeed(tmpPicData);
			mActPicDelay=getPicDelay(tmpPicData);
//			System.out.println("update pos:"+updatePos.toString()+" , speed:"+picSpeed.toString());
			ImageIcon tmpImageIcon=getImageIcon(tmpPicData);
			float direct=1.0f;
			if(bForward){
				win.drawImage(tmpImageIcon, null);
			}else{
				direct=-1.0f;
				win.drawImage(tmpImageIcon, ViewTils.getFlip(tmpImageIcon, true, false));
			}
			updatePos.x+=(picSpeed.x*direct);
			updatePos.y+=picSpeed.y;
			setUpdatePos(updatePos);
			if(mActPicIndex==0){
				checkActTrigger();
				if(dragNextAct!=null){
					initAct(dragNextAct);
					dragOldAct=dragNextAct;
					dragNextAct=null;
				}
			}
		}

		for(TriggerData data:triggerTimeDatas){
			if(data==null)
				continue;
			data.triggerCnt+=tick;
			data.triggerInitCnt+=tick;
		}
		
		//以下为秒级任务
		if(mSecondCnt<1000||bDrag)
			return;
		tick=mSecondCnt;
		mEventTimeCnt+=tick;
		checkTimeTrigger();
		checkDstTrigger();
		mSecondCnt=0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()!=MouseEvent.BUTTON1)
			return;
		TriggerTouch touch=null;
		if(tTouch==null&&tTouchGlobal==null)
			return;
		int clickX=e.getX(),clickY=e.getY();
		if(!bForward){
			clickX=width-clickX;
		}
		touch=tTouch==null?tTouchGlobal:tTouch;
		float addchance=0f;
		for(TriggerTouchData touchData:touch.getTouchList()){
			if(!touchData.getRect().contains(clickX, clickY))
				continue;
			if(!DataTils.randomSelet(touchData.getToEvent().getChance()+addchance)){
				addchance+=touchData.getToEvent().getChance();
				continue;
			}
			toEvent(touchData.getToEvent());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		bDrag=true;
		lastPos=new Vec2(updatePos);
		speedx_arrays_index=0;
		lastAvgSpeedX=0;
		acx_arrays_index=0;
		dragNextAct=null;
		drag_act_index=0;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		bDrag=false;
		TriggerDrag drag=null;
		dragNextAct=null;
		if(tDrag==null&&tDragGlobal==null)
			return;
		drag=tDrag==null?tDragGlobal:tDrag;
		if(Math.abs(lastAvgSpeedX)>1){
			bForward=lastAvgSpeedX<0;
		}
		System.out.println("speed:"+speed.toString()+" , lastAvgSpeedX:"+lastAvgSpeedX);
		initEventItem(drag.getToEvent());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p=win.getLocation();
		updatePos.x=p.x+e.getX();
		updatePos.y=p.y+e.getY()+height;
		setUpdatePos(updatePos);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	private float[] speedx_arrays=new float[10];
	private int speedx_arrays_index=0;
	
	private float[] acx_arrays=new float[10];
	private int acx_arrays_index=0;
	private float lastAvgSpeedX=0;
	private int drag_act_index=0;
	private int drag_act_run_index=-1;
	/*
	 * 拖拽事件实现
	 * */
	@Override
	public void triggerDragAction(Vec2 speed) {
		TriggerDrag drag=null;
		if(tDrag==null&&tDragGlobal==null)
			return;
		drag=tDrag==null?tDragGlobal:tDrag;
		
		//速度
		if(speedx_arrays_index>=speedx_arrays.length)
			speedx_arrays_index=speedx_arrays.length-1;
		speedx_arrays[speedx_arrays_index++]=speed.x;
		if(speedx_arrays_index<speedx_arrays.length)
			return;
		
		float avgSpeedX=0;
		for(float s:speedx_arrays){
			avgSpeedX+=s;
		}
		avgSpeedX=avgSpeedX/speedx_arrays_index;

		//加速度计算
		float acX=avgSpeedX-lastAvgSpeedX;
		lastAvgSpeedX=avgSpeedX;
		if(acx_arrays_index>=acx_arrays.length)
			acx_arrays_index=acx_arrays.length-1;
		acx_arrays[acx_arrays_index++]=acX;
		if(acx_arrays_index<acx_arrays.length)
			return;
		
		//动作效果
		TriggerDragData close=drag.getSortAcList().get(0);		//查找最接近的受力动作
		for(TriggerDragData dragData:drag.getSortAcList()){
			if(Math.abs(acX-close.ac.x)>Math.abs(acX-dragData.ac.x)){
				close=dragData;
			}
		}
		int index=drag.getSortAcList().indexOf(close);
		if(dragNextAct!=null){			//当在执行动作中保存相对最大幅度的动作
			drag_act_index=Math.abs(drag_act_index-drag_act_run_index)<Math.abs(index-drag_act_run_index)?index:drag_act_index;
			return;
		}else {
			if(drag_act_run_index==drag_act_index)	//当运行动作与执行动作相等后,开始更新执行动作
				drag_act_index=index;
			else									//当运行动作不等于执行动作时,先完成执行动作
				index=drag_act_index;
		}
		
		//完成动作幅度
		if(drag_act_run_index<index)
			drag_act_run_index++;
		else if(drag_act_run_index>index)
			drag_act_run_index--;
		else if(Math.abs(acX)<0.0001){
			dragNextAct=dragOldAct;
			return;
		}
		dragNextAct=getActFromString(drag.getSortAcList().get(drag_act_run_index).getAct());
	}	
	
}
