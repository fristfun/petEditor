package ind.chy.pet.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import ind.chy.pet.data.ActData;
import ind.chy.pet.data.EventData;
import ind.chy.pet.data.EventItemData.EventTriggerData;
import ind.chy.pet.data.TriggerAct.TriggerActData;
import ind.chy.pet.data.TriggerCollision.TriggerCollisionData;
import ind.chy.pet.data.TriggerDrag.TriggerDragData;
import ind.chy.pet.data.TriggerDst.TriggerDstData;
import ind.chy.pet.data.TriggerTime.TriggerTimeData;
import ind.chy.pet.data.TriggerTouch.TriggerTouchData;
import ind.chy.pet.model.DesktopWorld;
import ind.chy.pet.model.DesktopWorld.Vec2;

public class ViewTils {
	
	private static JFileChooser jfc=new JFileChooser();
	private static String APPPATH=null;
	
	public static void init(String appPath){
		APPPATH=appPath;
		jfc.setCurrentDirectory(new File(appPath));
		jfc.setMultiSelectionEnabled(true);
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}
	
	public static JFileChooser getFileChooser(){
		jfc.setFileFilter(null);
		return jfc;
	}
	
	public static JFileChooser getFileChooser(FileFilter filter){
		jfc.setFileFilter(filter);
		return jfc;
	}
	
	public static String getFileChooserResult(Component component,String title,FileFilter filter){
		jfc.setFileFilter(filter);
		int state=jfc.showDialog(component, title);
		File file=jfc.getSelectedFile();
		if(file==null||state==JFileChooser.CANCEL_OPTION)
			return null;
		return getAppResourcePath(file.getAbsolutePath());
	}
	
	public static String getAppResourcePath(String resPath) {
		String tmp=null;
		StringBuffer buf=new StringBuffer(resPath);
		int index=buf.indexOf(APPPATH);
		if(index<0)
			return null;
		tmp=buf.substring(APPPATH.lastIndexOf("\\"));
		return tmp;
	}
	
	public static JPanel createTitlePanel(String title) {
		JLabel lb=new JLabel(title);
		return createTitlePanel(lb);
	}
	
	public static JPanel createTitlePanel(JLabel lb) {
		JPanel titlePanel=new JPanel();
		titlePanel.setLayout(new BorderLayout());
		lb.setHorizontalAlignment(JLabel.CENTER);
		lb.setFont(new Font("宋体", Font.BOLD, 18));
		JPanel tmp=new JPanel(new BorderLayout());
		tmp.add(lb,BorderLayout.CENTER);
		tmp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		titlePanel.add(tmp,BorderLayout.NORTH);
		return titlePanel;
	}
	
	public static JPanel createTitlePanel(JPanel centerPanel,String title) {
		JPanel titlePanel=createTitlePanel(title);
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		titlePanel.add(centerPanel,BorderLayout.CENTER);
		return titlePanel;
	}
	
	public static JPanel createTitlePanel(JPanel centerPanel,JLabel lb) {
		JPanel titlePanel=createTitlePanel(lb);
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		titlePanel.add(centerPanel,BorderLayout.CENTER);
		return titlePanel;
	}
	
	public static JTextField createTitileTextField(int column,String title,String tip){
		JTextField tmp=new JTextField(column);
		tmp.setBorder(BorderFactory.createTitledBorder(title));
		if(tip==null)
			return tmp;
		tmp.setToolTipText(tip);
		return tmp;
	}
	
	public static JTextField createTitileTextField(int column,String title){
		return createTitileTextField(column,title,null);
	}
	
	public static Vec2 getTextFieldVecPercent(JComponent jComponent,JTextField fieldX,JTextField fieldY){
		Vec2 tmp=null;
		String strX=fieldX.getText();
		String strY=fieldY.getText();
		try {
			tmp=new Vec2(Float.parseFloat(strX), Float.parseFloat(strY));
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(jComponent,"参数错误", "错误", JOptionPane.CLOSED_OPTION);
		}
		return tmp;
	}
	
	/*
	 * 翻转,图片工具
	 * */
	public static AffineTransform getFlip(ImageIcon icon,boolean bMirrorX,boolean bMirrorY){
		Image img=icon.getImage();
		AffineTransform transform=new AffineTransform();
		transform.scale(bMirrorX==true?-1:1, bMirrorY==true?-1:1);
		transform.translate(bMirrorX==true?-1*img.getWidth(null):0,bMirrorY==true?-1*img.getHeight(null):0);
		return transform;
	}
	
//	private static EventTriggerData eventTriggerData=null;
//	public static EventTriggerData showEventTriggerDialog(Component component,EventData data){
//		eventTriggerData=null;
//		JDialog jDialog=new JDialog();
//		jDialog.setLocationRelativeTo(component);
//		Point point=jDialog.getLocation();
//		jDialog.setTitle("事件选择");
//		jDialog.setModal(true);
//		JComboBox<Object> jComboBox=new JComboBox<>(data.getStringList().toArray());
//		jDialog.getContentPane().setLayout(new FlowLayout());
//		jDialog.add(jComboBox);
//		JLabel jLabel=new JLabel("概率:");
//		jDialog.add(jLabel);
//		JTextField jTextField=new JTextField(6);
//		jDialog.add(jTextField);
//		JButton jButton=new JButton("添加");
//		jButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				float chance=0f;
//				try {
//					chance=Float.parseFloat(jTextField.getText());
//				} catch (Exception e) {
//					return;
//				}
//				eventTriggerData=new EventTriggerData();
//				eventTriggerData.setChance(chance);
//				eventTriggerData.setEvent(data.getStringList().get(jComboBox.getSelectedIndex()));
//			}
//		});
//		jDialog.add(jButton);
//		jDialog.pack();
//		jDialog.setLocation(point.x-jDialog.getWidth()/2, point.y-jDialog.getHeight()/2);
//		jDialog.setResizable(false);
//		jDialog.setVisible(true);
//		return eventTriggerData;
//	}

	private static TriggerDstData triggerDstData=null;
	public static TriggerDstData showTriggerDstDataDialog(Component component,EventData data){
		if(data==null)
			return null;
		triggerDstData=null;
		JDialog jDialog=new JDialog();
		jDialog.setLocationRelativeTo(component);
		Point point=jDialog.getLocation();
		jDialog.setTitle("事件选择");
		jDialog.setModal(true);
		
		jDialog.getContentPane().setLayout(new GridLayout(2, 1));
		JTextField txDstX=new JTextField(6);
		txDstX.setBorder(BorderFactory.createTitledBorder("目的地X:"));
		JTextField txDstY=new JTextField(6);
		txDstY.setBorder(BorderFactory.createTitledBorder("目的地Y:"));
		JTextField txDelt=new JTextField(6);
		txDelt.setBorder(BorderFactory.createTitledBorder("偏差范围:"));
		JCheckBox chChangeDirect=new JCheckBox("是否改变方向");
		JTextField txChance=new JTextField(6);
		txChance.setBorder(BorderFactory.createTitledBorder("概率:"));
		JPanel tmp=new JPanel(new GridLayout(1, 5));
		tmp.add(txDstX);
		tmp.add(txDstY);
		tmp.add(txDelt);
		tmp.add(chChangeDirect);
		tmp.add(txChance);
		jDialog.add(tmp);
		tmp=new JPanel(new GridLayout(1, 3));
		JLabel jLabel=new JLabel("触发事件:");
		tmp.add(jLabel);
		JComboBox<Object> jComboBox=new JComboBox<>(data.getStringList().toArray());
		tmp.add(jComboBox);
		JButton jButton=new JButton("添加");
		tmp.add(jButton);
		jDialog.add(tmp);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Vec2 dst=null;
				float delt;
				float chance;
				try {
					dst=new Vec2(Float.parseFloat(txDstX.getText()), Float.parseFloat(txDstY.getText()));
					delt=Float.parseFloat(txDelt.getText());
					chance=Float.parseFloat(txChance.getText());
				} catch (Exception e) {
					return;
				}
				triggerDstData=new TriggerDstData();
				triggerDstData.setDst(dst);
				triggerDstData.setRange(delt);
				EventTriggerData tmpData=new EventTriggerData();
				tmpData.setChance(chance);
				tmpData.setbChangeDirct(chChangeDirect.isSelected());
				tmpData.setEvent(jComboBox.getSelectedItem().toString());
				triggerDstData.setToEvent(tmpData);
				jDialog.dispose();
			}
		});
		jDialog.pack();
		jDialog.setLocation(point.x-jDialog.getWidth()/2, point.y-jDialog.getHeight()/2);
		jDialog.setResizable(false);
		jDialog.setVisible(true);
		return triggerDstData;
	}
	
	private static TriggerCollisionData triggerCollisionData=null;
	public static TriggerCollisionData showTriggerCollisionDataDialog(Component component,EventData data){
		if(data==null)
			return null;
		triggerCollisionData=null;
		JDialog jDialog=new JDialog();
		jDialog.setLocationRelativeTo(component);
		Point point=jDialog.getLocation();
		jDialog.setTitle("事件选择");
		jDialog.setModal(true);
		
		jDialog.getContentPane().setLayout(new GridLayout(2, 1));
		JComboBox<String> jEnv=new JComboBox<>(DesktopWorld.ENV_TYPE);
		JTextField txObjName=new JTextField(6);
		txObjName.setBorder(BorderFactory.createTitledBorder("自定义物体名:"));
		JTextField txChance=new JTextField(6);
		JCheckBox chChangeDirect=new JCheckBox("是否改变方向");
		txChance.setBorder(BorderFactory.createTitledBorder("概率:"));
		JPanel tmp=new JPanel(new GridLayout(1, 4));
		tmp.add(jEnv);
		tmp.add(txObjName);
		tmp.add(chChangeDirect);
		tmp.add(txChance);
		jDialog.add(tmp);
		tmp=new JPanel(new GridLayout(1, 3));
		JLabel jLabel=new JLabel("触发事件:");
		tmp.add(jLabel);
		JComboBox<Object> jComboBox=new JComboBox<>(data.getStringList().toArray());
		tmp.add(jComboBox);
		JButton jButton=new JButton("添加");
		tmp.add(jButton);
		jDialog.add(tmp);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				float chance;
				try {
					chance=Float.parseFloat(txChance.getText());
				} catch (Exception e) {
					return;
				}
				String objName=txObjName.getText();
				if(objName.length()==0){
					objName=jEnv.getSelectedItem().toString();
				}
				triggerCollisionData=new TriggerCollisionData();
				triggerCollisionData.setObjName(objName);
				EventTriggerData tmpData=new EventTriggerData();
				tmpData.setChance(chance);
				tmpData.setbChangeDirct(chChangeDirect.isSelected());
				tmpData.setEvent(jComboBox.getSelectedItem().toString());
				triggerCollisionData.setToEvent(tmpData);
				jDialog.dispose();
			}
		});
		jDialog.pack();
		jDialog.setLocation(point.x-jDialog.getWidth()/2, point.y-jDialog.getHeight()/2);
		jDialog.setResizable(false);
		jDialog.setVisible(true);
		return triggerCollisionData;
	}
	
	private static TriggerTimeData triggerTimeData=null;
	public static TriggerTimeData showTriggerTimeDataDialog(Component component,EventData data){
		if(data==null)
			return null;
		triggerTimeData=null;
		JDialog jDialog=new JDialog();
		jDialog.setLocationRelativeTo(component);
		Point point=jDialog.getLocation();
		jDialog.setTitle("事件选择");
		jDialog.setModal(true);
		
		jDialog.getContentPane().setLayout(new GridLayout(2, 1));
		JTextField txTimeMin=new JTextField(6);
		txTimeMin.setBorder(BorderFactory.createTitledBorder("最小时间"));
		JTextField txTimeMax=new JTextField(6);
		txTimeMax.setBorder(BorderFactory.createTitledBorder("最大时间"));
		JCheckBox chChangeDirect=new JCheckBox("是否改变方向");
		JTextField txChance=new JTextField(6);
		txChance.setBorder(BorderFactory.createTitledBorder("概率:"));
		JPanel tmp=new JPanel(new GridLayout(1, 4));
		tmp.add(txTimeMin);
		tmp.add(txTimeMax);
		tmp.add(chChangeDirect);
		tmp.add(txChance);
		jDialog.add(tmp);
		
		tmp=new JPanel(new GridLayout(1, 3));
		JLabel jLabel=new JLabel("触发事件:");
		tmp.add(jLabel);
		JComboBox<Object> jComboBox=new JComboBox<>(data.getStringList().toArray());
		tmp.add(jComboBox);
		JButton jButton=new JButton("添加");
		tmp.add(jButton);
		jDialog.add(tmp);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				float chance;
				int timeMin;
				int timeMax;
				try {
					chance=Float.parseFloat(txChance.getText());
					timeMin=Integer.parseInt(txTimeMin.getText());
					timeMax=Integer.parseInt(txTimeMax.getText());
				} catch (Exception e) {
					return;
				}
				timeMin*=1000;
				timeMax*=1000;
				triggerTimeData=new TriggerTimeData();
				triggerTimeData.setTimeMin(timeMin);
				triggerTimeData.setTimeMax(timeMax);
				EventTriggerData triggerData=new EventTriggerData();
				triggerData.setChance(chance);
				triggerData.setbChangeDirct(chChangeDirect.isSelected());
				triggerData.setEvent(jComboBox.getSelectedItem().toString());
				triggerTimeData.setToEvent(triggerData);
				jDialog.dispose();
			}
		});
		jDialog.pack();
		jDialog.setLocation(point.x-jDialog.getWidth()/2, point.y-jDialog.getHeight()/2);
		jDialog.setResizable(false);
		jDialog.setVisible(true);
		return triggerTimeData;
	}
	
	private static TriggerActData triggerActData=null;
	public static TriggerActData showTriggerActDataDialog(Component component,EventData data){
		if(data==null)
			return null;
		triggerActData=null;
		JDialog jDialog=new JDialog();
		jDialog.setLocationRelativeTo(component);
		Point point=jDialog.getLocation();
		jDialog.setTitle("事件选择");
		jDialog.setModal(true);
		
		jDialog.getContentPane().setLayout(new GridLayout(2, 1));
		JTextField txTimeMin=new JTextField(6);
		txTimeMin.setBorder(BorderFactory.createTitledBorder("最小次数"));
		JTextField txTimeMax=new JTextField(6);
		txTimeMax.setBorder(BorderFactory.createTitledBorder("最大次数"));
		JCheckBox chChangeDirect=new JCheckBox("是否改变方向");
		JTextField txChance=new JTextField(6);
		txChance.setBorder(BorderFactory.createTitledBorder("概率:"));
		JPanel tmp=new JPanel(new GridLayout(1, 4));
		tmp.add(txTimeMin);
		tmp.add(txTimeMax);
		tmp.add(chChangeDirect);
		tmp.add(txChance);
		jDialog.add(tmp);
		
		tmp=new JPanel(new GridLayout(1, 3));
		JLabel jLabel=new JLabel("触发事件:");
		tmp.add(jLabel);
		JComboBox<Object> jComboBox=new JComboBox<>(data.getStringList().toArray());
		tmp.add(jComboBox);
		JButton jButton=new JButton("添加");
		tmp.add(jButton);
		jDialog.add(tmp);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				float chance;
				int timeMin;
				int timeMax;
				try {
					chance=Float.parseFloat(txChance.getText());
					timeMin=Integer.parseInt(txTimeMin.getText());
					timeMax=Integer.parseInt(txTimeMax.getText());
				} catch (Exception e) {
					return;
				}
				triggerActData=new TriggerActData();
				triggerActData.setMinTimes(timeMin);
				triggerActData.setMaxTimes(timeMax);
				EventTriggerData triggerData=new EventTriggerData();
				triggerData.setChance(chance);
				triggerData.setbChangeDirct(chChangeDirect.isSelected());
				triggerData.setEvent(jComboBox.getSelectedItem().toString());
				triggerActData.setToEvent(triggerData);
				jDialog.dispose();
			}
		});
		jDialog.pack();
		jDialog.setLocation(point.x-jDialog.getWidth()/2, point.y-jDialog.getHeight()/2);
		jDialog.setResizable(false);
		jDialog.setVisible(true);
		return triggerActData;
	}
	
	private static TriggerTouchData triggerTouchData=null;
	public static TriggerTouchData showTriggerTouchDataDialog(Component component,EventData data){
		if(data==null)
			return null;
		triggerTouchData=null;
		JDialog jDialog=new JDialog();
		jDialog.setLocationRelativeTo(component);
		Point point=jDialog.getLocation();
		jDialog.setTitle("事件选择");
		jDialog.setModal(true);
		
		jDialog.getContentPane().setLayout(new GridLayout(3, 1));
		
		JPanel tmp=new JPanel(new GridLayout(1, 6));
		tmp.setBorder(BorderFactory.createTitledBorder("左上"));
		JTextField txlx=new JTextField(6);
		txlx.setBorder(BorderFactory.createTitledBorder("左X"));
		tmp.add(txlx);
		
		JTextField txly=new JTextField(6);
		txly.setBorder(BorderFactory.createTitledBorder("左Y"));
		tmp.add(txly);
		tmp.add(new JLabel());
		tmp.add(new JLabel());
		tmp.add(new JLabel());
		jDialog.add(tmp);
		
		tmp=new JPanel(new GridLayout(1, 6));
		tmp.setBorder(BorderFactory.createTitledBorder("右下"));
		tmp.add(new JLabel());
		tmp.add(new JLabel());
		tmp.add(new JLabel());
		JTextField txrx=new JTextField(6);
		txrx.setBorder(BorderFactory.createTitledBorder("右X"));
		tmp.add(txrx);
		
		JTextField txry=new JTextField(6);
		txry.setBorder(BorderFactory.createTitledBorder("右Y"));
		tmp.add(txry);
		
		jDialog.add(tmp);
		
		tmp=new JPanel(new GridLayout(1, 5));
		JLabel jLabel=new JLabel("触发事件:");
		JComboBox<Object> jComboBox=new JComboBox<>(data.getStringList().toArray());
		JCheckBox chChangeDirect=new JCheckBox("是否改变方向");
		JTextField txChance=new JTextField(6);
		txChance.setBorder(BorderFactory.createTitledBorder("概率:"));
		JButton jButton=new JButton("添加");
		
		tmp.add(jLabel);
		tmp.add(jComboBox);
		tmp.add(chChangeDirect);
		tmp.add(txChance);
		tmp.add(jButton);
		jDialog.add(tmp);

		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				float chance;
				int lx,ly,rx,ry;
				Rectangle rectangle;
				try {
					chance=Float.parseFloat(txChance.getText());
					lx=Integer.parseInt(txlx.getText());
					ly=Integer.parseInt(txly.getText());
					rx=Integer.parseInt(txrx.getText());
					ry=Integer.parseInt(txry.getText());
				} catch (Exception e) {
					return;
				}
				if(lx>=rx||ly>=ry)
					return;
				triggerTouchData=new TriggerTouchData();
				rectangle=new Rectangle(lx, ly, rx-lx, ry-ly);
				triggerTouchData.setRect(rectangle);
				EventTriggerData triggerData=new EventTriggerData();
				triggerData.setEvent(jComboBox.getSelectedItem().toString());
				triggerData.setChance(chance);
				triggerData.setbChangeDirct(chChangeDirect.isSelected());
				triggerTouchData.setToEvent(triggerData);
				jDialog.dispose();
			}
		});
		jDialog.pack();
		jDialog.setLocation(point.x-jDialog.getWidth()/2, point.y-jDialog.getHeight()/2);
		jDialog.setResizable(false);
		jDialog.setVisible(true);
		return triggerTouchData;
	}
	
	private static TriggerDragData triggerDragData=null;
	public static TriggerDragData showTriggerDragDataDialog(Component component,ActData data){
		if(data==null)
			return null;
		triggerDragData=null;
		JDialog jDialog=new JDialog();
		jDialog.setLocationRelativeTo(component);
		Point point=jDialog.getLocation();
		jDialog.setTitle("事件选择");
		jDialog.setModal(true);
		
		jDialog.getContentPane().setLayout(new GridLayout(1, 4));
		
		JPanel tmp=new JPanel(new GridLayout(1, 2));
		tmp.setBorder(BorderFactory.createTitledBorder("加速度"));
		JTextField acX=new JTextField(6);
		acX.setBorder(BorderFactory.createTitledBorder("加速度x"));
		tmp.add(acX);
		
		JTextField acY=new JTextField(6);
		acY.setBorder(BorderFactory.createTitledBorder("加速度y"));
		tmp.add(acY);
		jDialog.add(tmp);
		
		tmp=new JPanel(new GridLayout(1, 2));
		tmp.setBorder(BorderFactory.createTitledBorder("速度"));
		JTextField speedX=new JTextField(6);
		speedX.setBorder(BorderFactory.createTitledBorder("速度X"));
		tmp.add(speedX);
		
		JTextField speedY=new JTextField(6);
		speedY.setBorder(BorderFactory.createTitledBorder("速度Y"));
		tmp.add(speedY);
		jDialog.add(tmp);
		
		JComboBox<Object> act=new JComboBox<>(data.getStringList().toArray());
		jDialog.add(act);
		
		JButton jButton=new JButton("添加");
		jDialog.add(jButton);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				float aX,aY,sX,sY;
				try {
					aX=Float.parseFloat(acX.getText());
					aY=Float.parseFloat(acY.getText());
					sX=Float.parseFloat(speedX.getText());
					sY=Float.parseFloat(speedY.getText());
				} catch (Exception e) {
					return;
				}
				triggerDragData=new TriggerDragData();
				triggerDragData.setAc(new Vec2(aX, aX));
				triggerDragData.setSpeed(new Vec2(sX, sY));
				triggerDragData.setAct(act.getSelectedItem().toString());
				jDialog.dispose();
			}
		});
		
		
		
		jDialog.pack();
		jDialog.setLocation(point.x-jDialog.getWidth()/2, point.y-jDialog.getHeight()/2);
		jDialog.setResizable(false);
		jDialog.setVisible(true);
		return triggerDragData;
	}
}
