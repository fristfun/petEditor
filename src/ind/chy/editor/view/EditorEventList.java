package ind.chy.editor.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ind.chy.App.Launch.AppLaunch;
import ind.chy.pet.data.ActData;
import ind.chy.pet.data.AppVector;
import ind.chy.pet.data.EventData;
import ind.chy.pet.data.EventItemData;
import ind.chy.pet.model.DesktopPet;
import ind.chy.pet.model.DesktopWorld;
import ind.chy.pet.model.DesktopWorld.Vec2;
import ind.chy.pet.view.PetWin;
import ind.chy.pet.view.ViewTils;

public class EditorEventList extends EditorActList{

	private EventData data;
	private EditorTriggerList trigger;
	private EditorEventPara para;
	private boolean bDebugRun=false;
	private EditorEventList(String title) {
		super(title);
	}
	
	public EditorEventList(String title,EditorTriggerList triggerList,EditorEventPara para) {
		super(title);
		this.trigger=triggerList;
		this.para=para;
		para.eventDebug.addActionListener(this);
		super.mouseMenuInit();
	}
	
	private void dataInit(){
		data=EventData.GetData();
		ArrayList<String> stringList=data.getStringList();
		if(stringList==null)
			return;
		for(String tmp:stringList){
			showData.addElement(tmp);
		}
		listComponent.updateUI();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object ob=e.getSource();
		if(data==null) {
			JOptionPane.showConfirmDialog(this.panel, "请导入或创建新桌宠", "警告", JOptionPane.CLOSED_OPTION);
			return;
		}
		if(e.getButton()==MouseEvent.BUTTON3){
			if(listComponent.isSelectionEmpty()){
				rangMenu.show((Component)ob, e.getX(), e.getY());
			}else{
				selectMenu.show((Component)ob, e.getX(), e.getY());
			}
		}else if(e.getButton()==MouseEvent.BUTTON1&&listComponent.getSelectedIndex()!=-1){
			EventItemData eventItem=data.getListEvent().get(listComponent.getSelectedIndex());
			trigger.setTriggerData(eventItem);
			para.setEvent(eventItem);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateData() {
		dataInit();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj=arg0.getSource();
		if(obj==menuRangAdd||obj==menuSelectAdd) {
			String input=JOptionPane.showInputDialog("输入事件名称");
			if(input==null)
				return;
			if(data.getListEvent()==null)
				data.setListEvent(new AppVector<>());
			data.getListEvent().add(new EventItemData(input));
			showData.addElement(input);
		}else {
			int index=listComponent.getSelectedIndex();
			if(obj==menuRemove){
				data.getListEvent().remove(index);
				showData.remove(index);
			}else if(obj==menuInsert){
				String input=JOptionPane.showInputDialog("输入事件名称");
				if(input==null)
					return;
				data.getListEvent().insertData(new EventItemData(input), index);
				showData.insertData(input, index);
			}else if(obj==menuUp){
				data.getListEvent().moveUp(index);
				showData.moveUp(index);
			}else if(obj==menuDown){
				data.getListEvent().moveDown(index);
				showData.moveDown(index);
			}else if(obj==para.eventDebug) {
				if(!bDebugRun) {
					Vec2 pos=null;
					pos=ViewTils.getTextFieldVecPercent(para.centerPanel,para.eventDebugPosX, para.eventDebugPosY);
					if(pos==null)
						return;
					bDebugRun=true;
					para.eventDebug.setText("停止测试");
					debugEvent(data.getListEvent().get(index),pos);
				}else {
					bDebugRun=false;
					para.eventDebug.setText("测试事件");
				}
			}
		}
	}
	
	private void debugEvent(EventItemData debugEvent,Vec2 pos) {
		DesktopWorld debugWorld=DesktopWorld.createWorld(
				AppLaunch.SCREEN, AppLaunch.SCREEN_DOUNDS);
		DesktopPet debugPet=new DesktopPet(new PetWin(), ActData.GetData(), data, debugEvent,pos);
		debugWorld.addObject(debugPet);
		Runnable debugRun=new Runnable() {
			
			@Override
			public void run() {
				while(bDebugRun) {
					debugWorld.update(5);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				debugWorld.destroyObject(debugPet);
				debugWorld.update(5);
				
			}
		};
		Thread debugThread=new Thread(debugRun);
		debugThread.start();
	}

}
