package ind.chy.editor.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ind.chy.App.Launch.AppLaunch;
import ind.chy.editor.view.PetEditorWin.DrawPanel;
import ind.chy.pet.data.ActItemData;
import ind.chy.pet.data.ActItemData.PicData;
import ind.chy.pet.data.DataTils;
import ind.chy.pet.view.ViewTils;

public class EditorPicList extends EditorActList{
	
	public ActItemData data;
	public DrawPanel drawPanel;
	private EditorActPara para;
	private String appDIR=AppLaunch.APP_PATH;
	private EditorPicList(String title) {
		super(title);
	}
	
	public EditorPicList(String title,EditorActPara para,DrawPanel draw) {
		super(title);
		drawPanel=draw;
		this.para=para;
		para.picOK.addActionListener(this);
		super.mouseMenuInit();
	}
	
	public void setPicList(ActItemData data){
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
	public void mouseClicked(MouseEvent e) {
		Object ob=e.getSource();
		if(data==null)
			return;
		if(e.getButton()==MouseEvent.BUTTON3){
			if(listComponent.isSelectionEmpty()){
				rangMenu.show((Component)ob, e.getX(), e.getY());
			}else{
				selectMenu.show((Component)ob, e.getX(), e.getY());
			}
		}else if(e.getButton()==MouseEvent.BUTTON1){
			if(listComponent.isSelectionEmpty())
				return;
			PicData pic=data.getListPic().get(listComponent.getSelectedIndex());
			ImageIcon img=new ImageIcon(appDIR+pic.getPicName());
			drawPanel.drawImage(img);
			para.setImagePara(img,pic);
		}
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj=arg0.getSource();
		if(obj==menuRangAdd||obj==menuSelectAdd){
			JFileChooser jFileChooser=ViewTils.getFileChooser();
			int stat=jFileChooser.showDialog(this.panel, "选择图片");
			if(stat!=JFileChooser.APPROVE_OPTION)
				return;
			File[] file=jFileChooser.getSelectedFiles();
			boolean ok=true;
			for(File f:file){
				String picName=ViewTils.getAppResourcePath(f.getAbsolutePath());
				
				if(picName==null) {
					ok=false;
					break;
				}
				showData.addElement(picName);
				PicData pictmp=new PicData();
				pictmp.setPicName(picName);
				data.getListPic().add(pictmp);
			}
			if(!ok) {
				JOptionPane.showConfirmDialog(this.panel, "请将素材放在本程序目录下", "错误", JOptionPane.CLOSED_OPTION);
			}
		}else {
			int index=listComponent.getSelectedIndex();
			if(obj==menuRemove){
				int[] arrIndex=listComponent.getSelectedIndices();
				Object[] dataRemove=new Object[arrIndex.length];
				Object[] showRemove=new Object[arrIndex.length];
				for(int i=0;i<arrIndex.length;i++){
					dataRemove[i]=data.getListPic().get(arrIndex[i]);
					showRemove[i]=showData.get(arrIndex[i]);
				}
				for(Object tmpObj:dataRemove){
					data.getListPic().remove(tmpObj);
				}
				for(Object tmpObj:showRemove){
					showData.removeElement(tmpObj);
				}
			}else if(obj==menuInsert){
				int stat=ViewTils.getFileChooser().showDialog(this.panel, "选择图片");
				File[] file=ViewTils.getFileChooser().getSelectedFiles();
				if(file==null||stat==JFileChooser.CANCEL_OPTION)
					return ;
				boolean ok=true;
				for(File f:file){
					String picName=ViewTils.getAppResourcePath(f.getAbsolutePath());
					if(picName==null){
						ok=false;
						break;
					}
					showData.indexOf(picName, index);
					PicData pictmp=new PicData();
					pictmp.setPicName(picName);
					data.getListPic().insertData(pictmp, index);
					index++;
				}
				if(!ok) {
					JOptionPane.showConfirmDialog(this.panel, "请将素材放在本程序目录下", "错误", JOptionPane.CLOSED_OPTION);
				}
			}else if(obj==menuUp){
				data.getListPic().moveUp(index);
				showData.moveUp(index);
			}else if(obj==menuDown){
				data.getListPic().moveDown(index);
				showData.moveDown(index);
			}else if(obj==para.picOK) {
				PicData pic=data.getListPic().get(index);
				try {
					pic.setPicDelay(Integer.parseInt(para.picDelay.getText()));
					pic.setbUsePicPara(para.picUsePara.isSelected());
					pic.setSpeed(DataTils.getShowSpeed(
							para.picDirect.getSelectedIndex(), 
							Integer.parseInt(para.picSpeed.getText())));
				} catch (Exception e) {
					JOptionPane.showConfirmDialog(this.panel, "参数错误", "错误", JOptionPane.CLOSED_OPTION);
				}
			}
		}
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		
	}

}
