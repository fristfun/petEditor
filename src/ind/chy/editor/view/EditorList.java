package ind.chy.editor.view;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ind.chy.pet.view.ViewTils;

public abstract class EditorList implements MouseListener{
	
	public AppListModel<String> showData=new AppListModel<>();
	public JList<String> listComponent=new JList<>(showData);
	public JPanel panel;		//容器,全
	public JPanel centerPanel;	//使用中部容器
	
	public class AppListModel<E> extends DefaultListModel<E>{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void insertData(E o,int index){
			if(index<size())
				insertElementAt(o, index);
			else
				addElement(o);
		}
		
		public void moveDown(int index){
			E tmp=get(index);
			remove(index);
			insertData(tmp, index+1);
		}
		
		public void moveUp(int index){
			E tmp= get(index);
			remove(index);
			insertData(tmp, index-1);
			
		}
		
		public void replaceIndex(E obj,int index){
			remove(index);
			insertData(obj, index);
		}
	}
	
	public EditorList(String title) {
		centerPanel=new JPanel(new BorderLayout());
		panel=ViewTils.createTitlePanel(centerPanel,title);
		JScrollPane jp=new JScrollPane(listComponent);
		centerPanel.add(jp,BorderLayout.CENTER);
		listComponent.addMouseListener(this);
	}
	
	public abstract void updateData();

}
