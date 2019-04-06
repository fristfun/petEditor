package ind.chy.editor.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ind.chy.pet.data.EventItemData;
import ind.chy.pet.data.TriggerData;


public class EditorTriggerList extends EditorActList{

	private EventItemData data;
	private EditorEventPara para;
	private EditorTriggerList(String title) {
		super(title);
	}
	
	public EditorTriggerList(String title,EditorEventPara para) {
		super(title);
		this.para=para;
		super.mouseMenuInit();
	}
	
	
	public void setTriggerData(EventItemData data) {
		this.data=data;
		showData.removeAllElements();
		ArrayList<String> tmp=data.getStringList();
		if(tmp==null||tmp.size()==0){
			listComponent.updateUI();
			return;
		}
		for(String s:tmp)
			showData.addElement(s);
		listComponent.updateUI();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object ob=e.getSource();
		if(data==null) {
			JOptionPane.showConfirmDialog(this.centerPanel, "请导入或创建新桌宠", "警告", JOptionPane.CLOSED_OPTION);
			return;
		}
		if(e.getButton()==MouseEvent.BUTTON3) {
			if(listComponent.isSelectionEmpty()){
				rangMenu.show((Component)ob, e.getX(), e.getY());
			}else{
				selectMenu.show((Component)ob, e.getX(), e.getY());
			}
		}else if(e.getButton()==MouseEvent.BUTTON1&&listComponent.getSelectedIndex()!=-1){
			TriggerData triggerData=data.getTiggerList().get(listComponent.getSelectedIndex());
			para.setTrigger(triggerData);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj=arg0.getSource();
		if(obj==menuRangAdd||obj==menuSelectAdd) {
			String result = (String) JOptionPane.
					showInputDialog(null,"事件类型:\n",
					"添加", JOptionPane.PLAIN_MESSAGE,
					null, TriggerData.TRIGGER_TYPE, null);
			if(result==null)
				return;
			System.out.println("trigger add:"+result);
			TriggerData tmp=TriggerData.getInstance(result);
			if(tmp==null)
				return;
			data.getTiggerList().add(tmp);
			showData.addElement(result);
		}else {
			int index=listComponent.getSelectedIndex();
			if(obj==menuRemove){
				data.getTiggerList().remove(index);
				showData.remove(index);
			}else if(obj==menuInsert){
				String result = (String) JOptionPane.
						showInputDialog(null,"事件类型:\n",
						"添加", JOptionPane.PLAIN_MESSAGE,
						null, TriggerData.TRIGGER_TYPE, null);
				if(result==null)
					return;
				TriggerData tmp=TriggerData.getInstance(result);
				if(tmp==null)
					return;
				data.getTiggerList().insertData(tmp, index);
				showData.insertData(result, index);
			}else if(obj==menuUp){
				data.getTiggerList().moveUp(index);
				showData.moveUp(index);
			}else if(obj==menuDown){
				data.getTiggerList().moveDown(index);
				showData.moveDown(index);
			}
		}
	}

}
