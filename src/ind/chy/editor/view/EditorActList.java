package ind.chy.editor.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import ind.chy.editor.view.PetEditorWin.DrawPanel;
import ind.chy.pet.data.ActData;
import ind.chy.pet.data.ActItemData;
import ind.chy.pet.data.AppVector;
import ind.chy.pet.data.DataTils;
import ind.chy.pet.model.DesktopWorld.Vec2;


public class EditorActList extends EditorList implements ActionListener{

	private ActData data;
	private EditorPicList picList;
	private EditorActPara para;
	private DrawPanel draw;
	protected JPopupMenu selectMenu;
	protected JPopupMenu rangMenu;
	protected JMenuItem menuRangAdd;
	protected JMenuItem menuSelectAdd;
	protected JMenuItem menuRemove;
	protected JMenuItem menuInsert;
	protected JMenuItem menuUp;
	protected JMenuItem menuDown;
	public EditorActList(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	public EditorActList(String title,EditorPicList picList,EditorActPara paraPanel,DrawPanel draw) {
		super(title);
		this.picList=picList;
		this.para=paraPanel;
		this.draw=draw;
		mouseMenuInit();
		para.actPlay.addActionListener(this);
	}
	
	protected void mouseMenuInit(){
		rangMenu=new JPopupMenu();
		menuRangAdd=new JMenuItem("+");
		menuRangAdd.addActionListener(this);
		rangMenu.add(menuRangAdd);
		
		selectMenu=new JPopupMenu();
		menuSelectAdd=new JMenuItem("+");
		menuSelectAdd.addActionListener(this);
		selectMenu.add(menuSelectAdd);
		
		menuRemove=new JMenuItem("-");
		menuRemove.addActionListener(this);
		selectMenu.add(menuRemove);
		
		menuInsert=new JMenuItem("insert");
		menuInsert.addActionListener(this);
		selectMenu.add(menuInsert);
		
		menuUp=new JMenuItem("Up");
		menuUp.addActionListener(this);
		selectMenu.add(menuUp);
		
		menuDown=new JMenuItem("Down");
		menuDown.addActionListener(this);
		selectMenu.add(menuDown);
	}
	
	private void dataInit(){
		data=ActData.GetData();
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
			ActItemData actItem=data.getActList().get(listComponent.getSelectedIndex());
			picList.setPicList(actItem);
			para.setActPara(actItem);
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
	public void actionPerformed(ActionEvent arg0) {
		Object obj=arg0.getSource();
		if(obj==menuRangAdd||obj==menuSelectAdd){
			String input=JOptionPane.showInputDialog("输入动作名称");
			if(input==null)
				return;
			if(data.getActList()==null)
				data.setActList(new AppVector<>());
			data.getActList().add(new ActItemData(input));
			showData.addElement(input);
		}else {
			int index=listComponent.getSelectedIndex();
			if(obj==menuRemove){
				int[] arrIndex=listComponent.getSelectedIndices();
				Object[] dataRemove=new Object[arrIndex.length];
				Object[] showRemove=new Object[arrIndex.length];
				for(int i=0;i<arrIndex.length;i++){
					dataRemove[i]=data.getActList().get(arrIndex[i]);
					showRemove[i]=showData.get(arrIndex[i]);
				}
				for(Object tmpObj:dataRemove){
					data.getActList().remove(tmpObj);
				}
				for(Object tmpObj:showRemove){
					showData.removeElement(tmpObj);
				}
			}else if(obj==menuInsert){
				String input=JOptionPane.showInputDialog("输入动作名称");
				if(input==null)
					return;
				data.getActList().insertData(new ActItemData(input), index);
				showData.insertData(input, index);
			}else if(obj==menuUp){
				data.getActList().moveUp(index);
				showData.moveUp(index);
			}else if(obj==menuDown){
				data.getActList().moveDown(index);
				showData.moveDown(index);
			}else if(obj==para.actPlay) {
				if(!draw.isPlay()) {
					ActItemData actData=data.getActList().get(index);
					try {
						actData.setPicDelay(Integer.parseInt(para.actFrame.getText()));
						actData.setSpeed(DataTils.getShowSpeed(
								para.actDirect.getSelectedIndex(), 
								Float.parseFloat(para.actSpeed.getText())));
						boolean bSimulation=para.actSimulationCheck.isSelected();
						if(bSimulation){
							float x=Float.parseFloat(para.actSimulationXNiu.getText());
							float y=Float.parseFloat(para.actSimulationYNiu.getText());
							actData.setWorldForce(new Vec2(x, y));
						}else{
							actData.setWorldForce(null);
						}
						actData.setbSimulation(bSimulation);
					} catch (Exception e) {
						JOptionPane.showConfirmDialog(para.centerPanel, "参数错误", "错误", JOptionPane.CLOSED_OPTION);
						return;
					}
					draw.playAction(actData);
					para.actPlay.setText("停止");
				}else {
					para.actPlay.setText("播放");
					draw.stopPlay();
				}
				
			}
		}
	}

	@Override
	public void updateData() {
		dataInit();
		
	}



}
