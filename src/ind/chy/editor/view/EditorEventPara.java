package ind.chy.editor.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import ind.chy.pet.data.ActData;
import ind.chy.pet.data.EventData;
import ind.chy.pet.data.EventItemData;
import ind.chy.pet.data.TriggerAct;
import ind.chy.pet.data.TriggerAct.TriggerActData;
import ind.chy.pet.data.TriggerDst;
import ind.chy.pet.data.TriggerDst.TriggerDstData;
import ind.chy.pet.data.TriggerTime;
import ind.chy.pet.data.TriggerTime.TriggerTimeData;
import ind.chy.pet.data.TriggerTouch;
import ind.chy.pet.data.TriggerTouch.TriggerTouchData;
import ind.chy.pet.model.DesktopWorld.Vec2;
import ind.chy.pet.data.TriggerCollision;
import ind.chy.pet.data.TriggerCollision.TriggerCollisionData;
import ind.chy.pet.data.TriggerData;
import ind.chy.pet.data.TriggerDefault;
import ind.chy.pet.data.TriggerDrag;
import ind.chy.pet.data.TriggerDrag.TriggerDragData;
import ind.chy.pet.view.ViewTils;

public class EditorEventPara {

	private final static String BLANK_PANEL="blank";
	private final static String EVENT_PANEL="event";
	
	
	public JPanel panel;
	public JPanel centerPanel;
	private TriggerData trigger;
	private EventItemData event;
	private CardLayout card=new CardLayout();
	private JLabel titleLabel=new JLabel("事件参数");
	
	public EditorEventPara(){
		centerPanel=new JPanel(card);
		panel=ViewTils.createTitlePanel(centerPanel,titleLabel);
		centerPanel.add(BLANK_PANEL,new JPanel());
		centerPanel.add(EVENT_PANEL,eventPanelInit());
		int index=0;
		centerPanel.add(TriggerData.TRIGGER_TYPE[index++],triggerDefaultInit());
		centerPanel.add(TriggerData.TRIGGER_TYPE[index++],triggerDstInit());
		centerPanel.add(TriggerData.TRIGGER_TYPE[index++],triggerTimeLongInit());
		centerPanel.add(TriggerData.TRIGGER_TYPE[index++],triggerTouchInit());
		centerPanel.add(TriggerData.TRIGGER_TYPE[index++],triggerCollisionInit());
		centerPanel.add(TriggerData.TRIGGER_TYPE[index++],triggerActInit());
		centerPanel.add(TriggerData.TRIGGER_TYPE[index++],triggerDragInit());
	}
	
	
	public JButton eventAct;
	public JCheckBox eventChangeDirct;
	public JTextField eventInitSpeedX;
	public JTextField eventInitSpeedY;
	public JButton eventOK;
	public JButton eventDebug;
	public JTextField eventDebugPosX;
	public JTextField eventDebugPosY;
	private JPanel eventPanelInit() {
		JPanel tmp=new JPanel(new GridLayout(6, 1));
		JPanel actPanel=new JPanel(new GridLayout(1, 2));
		eventAct=new JButton();
		eventAct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = (String) JOptionPane.
						showInputDialog(null,"动作:\n",
						"选择", JOptionPane.PLAIN_MESSAGE,
						null, ActData.GetData().getStringList().toArray(), null);
				if(result==null)
					return;
				eventAct.setText(result);
				event.setAct(result);
			}
		});
		JPanel initSpeedPanel=new JPanel(new GridLayout(1, 2));
		eventInitSpeedX=ViewTils.createTitileTextField(6, "初速度X:", "某些动作无速度,使用本速度进行速度初始化");
		eventInitSpeedY=ViewTils.createTitileTextField(6, "初速度Y:", "某些动作无速度,使用本速度进行速度初始化");
		initSpeedPanel.add(eventInitSpeedX);
		initSpeedPanel.add(eventInitSpeedY);
		actPanel.add(eventAct);
		eventOK=new JButton("确认参数");
		eventOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Vec2 tmp=ViewTils.getTextFieldVecPercent(centerPanel, eventInitSpeedX, eventInitSpeedY);
				if(tmp==null)
					return;
				event.setInitSpeed(tmp);
			}
		});
		eventDebugPosX=ViewTils.createTitileTextField(6,"调试初始化X坐标:","百分比");
		eventDebugPosY=ViewTils.createTitileTextField(6,"调试初始化Y坐标:","百分比");
		JPanel debugPos=new JPanel(new GridLayout(1, 2));
		debugPos.add(eventDebugPosX);
		debugPos.add(eventDebugPosY);
		eventDebug=new JButton("测试事件");
		
		tmp.add(actPanel);
		tmp.add(initSpeedPanel);
		tmp.add(eventOK);
		tmp.add(debugPos);
		tmp.add(eventDebug);
		return tmp;
	}
	
	public JCheckBox triggerDefaultRandom;
	public JTextField triggerDefaultPosX;
	public JTextField triggerDefualtPosY;
	public JTextField triggerDefaultIcon;
	public JButton triggerDefaultIconBtn;
	public JButton triggerDefaultOK;
	private JPanel triggerDefaultInit() {
		JPanel tmp=new JPanel(new GridLayout(6, 1));
		triggerDefaultRandom=new JCheckBox("随机位置");
		triggerDefaultRandom.setToolTipText("世界顶部随机位置");
		
		triggerDefaultPosX=ViewTils.createTitileTextField(6,"固定X坐标:","百分比");
		triggerDefualtPosY=ViewTils.createTitileTextField(6,"固定Y坐标:","百分比");
		
		JPanel pos=new JPanel(new GridLayout(1, 2));
		pos.add(triggerDefaultPosX);
		pos.add(triggerDefualtPosY);
		
		triggerDefaultIcon=new JTextField(6);
		triggerDefaultIcon.setBorder(BorderFactory.createTitledBorder("托盘图标"));
		
		triggerDefaultIconBtn=new JButton("选择托盘图标");
		triggerDefaultIconBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String iconPath=ViewTils.getFileChooserResult(centerPanel, "选择托盘图表", null);
				if(iconPath==null)
					return;
				triggerDefaultIcon.setText(iconPath);
			}
		});
		
		triggerDefaultOK=new JButton("参数确定");
		triggerDefaultOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TriggerDefault triggerDefault=(TriggerDefault)trigger;
				triggerDefault.setbGlobal(true);
				triggerDefault.setbRandom(triggerDefaultRandom.isSelected());
				if(triggerDefault.isbRandom())
					triggerDefault.setPosition(null);
				else
					triggerDefault.setPosition(ViewTils.getTextFieldVecPercent(centerPanel, triggerDefaultPosX,triggerDefualtPosY));
				triggerDefault.setIcon(triggerDefaultIcon.getText());
			}
		});
		
		tmp.add(triggerDefaultRandom);
		tmp.add(pos);
		tmp.add(triggerDefaultIcon);
		tmp.add(triggerDefaultIconBtn);
		tmp.add(triggerDefaultOK);
		
		return tmp;
	}
	
	public JCheckBox triggerDstGlobal;
	public JButton triggerDstAdd;
	public JButton triggerDstDelet;
	public DefaultTableModel triggerDstTable;
	public JTable triggerDstTablePanel;
	private JPanel triggerDstInit() {
		JPanel tmp=new JPanel(new BorderLayout());
		triggerDstGlobal=new JCheckBox("全局触发");
		triggerDstGlobal.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				trigger.setbGlobal(triggerDstGlobal.isSelected());
			}
		});
		tmp.add(triggerDstGlobal,BorderLayout.NORTH);
		triggerDstAdd=new JButton("添加");
		triggerDstAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TriggerDstData triggerDstData=ViewTils.showTriggerDstDataDialog(centerPanel, EventData.GetData());
				if(triggerDstData==null)
					return;
				triggerDstTable.addRow(triggerDstData.toStringArrays());
				TriggerDst triggerDst=(TriggerDst) trigger;
				triggerDst.getDstList().add(triggerDstData);
			}
		});
		triggerDstDelet=new JButton("删除");
		triggerDstDelet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex=triggerDstTablePanel.getSelectedRow();
				if(rowIndex<0)
					return;
				TriggerDst triggerDst=(TriggerDst) trigger;
				triggerDst.getDstList().remove(rowIndex);
				triggerDstTable.removeRow(rowIndex);
			}
		});
		String[] colName={"事件","目的地","范围","改变方向","概率"};
		triggerDstTable=new DefaultTableModel(colName, 0);
		triggerDstTablePanel=new JTable(triggerDstTable);
		triggerDstTablePanel.getColumnModel().getColumn(0).setPreferredWidth(40);
		triggerDstTablePanel.getColumnModel().getColumn(1).setPreferredWidth(80);
		triggerDstTablePanel.getColumnModel().getColumn(2).setPreferredWidth(10);
		triggerDstTablePanel.getColumnModel().getColumn(3).setPreferredWidth(30);
		triggerDstTablePanel.getColumnModel().getColumn(4).setPreferredWidth(10);
		
		JScrollPane scrollPane=new JScrollPane(triggerDstTablePanel);
		tmp.add(scrollPane,BorderLayout.CENTER);
		
		JPanel southPanel=new JPanel(new GridLayout(1, 2));
		southPanel.add(triggerDstAdd);
		southPanel.add(triggerDstDelet);
		tmp.add(southPanel, BorderLayout.SOUTH);
		return tmp;
	}
	
	public JCheckBox triggerTimeGlobal;
	public JButton triggerTimeAdd;
	public JButton triggerTimeDelet;
	public DefaultTableModel triggerTimeTable;
	public JTable triggerTimeTablePanel;
	private JPanel triggerTimeLongInit() {
		JPanel tmp=new JPanel(new BorderLayout());
		triggerTimeGlobal=new JCheckBox("全局触发");
		triggerTimeGlobal.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				trigger.setbGlobal(triggerTimeGlobal.isSelected());
			}
		});
		tmp.add(triggerTimeGlobal,BorderLayout.NORTH);
		triggerTimeAdd=new JButton("添加");
		triggerTimeAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TriggerTimeData triggerTimeData=ViewTils.showTriggerTimeDataDialog(centerPanel, EventData.GetData());
				if(triggerTimeData==null)
					return;
				triggerTimeTable.addRow(triggerTimeData.toStringArrays());
				TriggerTime triggerTime=(TriggerTime) trigger;
				triggerTime.getTimeNextEventList().add(triggerTimeData);
			}
		});
		triggerTimeDelet=new JButton("删除");
		triggerTimeDelet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex=triggerTimeTablePanel.getSelectedRow();
				if(rowIndex<0)
					return;
				TriggerTime triggerTime=(TriggerTime) trigger;
				triggerTime.getTimeNextEventList().remove(rowIndex);
				triggerTimeTable.removeRow(rowIndex);
			}
		});
		String[] colName={"事件","最小时间","最大时间","改变方向","概率"};
		triggerTimeTable=new DefaultTableModel(colName, 0);
		triggerTimeTablePanel=new JTable(triggerTimeTable);
		triggerTimeTablePanel.getColumnModel().getColumn(0).setPreferredWidth(40);
		triggerTimeTablePanel.getColumnModel().getColumn(1).setPreferredWidth(30);
		triggerTimeTablePanel.getColumnModel().getColumn(2).setPreferredWidth(30);
		triggerTimeTablePanel.getColumnModel().getColumn(3).setPreferredWidth(30);
		triggerTimeTablePanel.getColumnModel().getColumn(4).setPreferredWidth(20);
		
		JScrollPane scrollPane=new JScrollPane(triggerTimeTablePanel);
		tmp.add(scrollPane,BorderLayout.CENTER);
		
		JPanel southPanel=new JPanel(new GridLayout(1, 2));
		southPanel.add(triggerTimeAdd);
		southPanel.add(triggerTimeDelet);
		tmp.add(southPanel, BorderLayout.SOUTH);
		return tmp;
	}
	
	public JCheckBox triggerTouchGlobal;
	public JButton triggerTouchAdd;
	public JButton triggerTouchDelet;
	public DefaultTableModel triggerTouchTable;
	public JTable triggerTouchTablePanel;
	private JPanel triggerTouchInit() {
		JPanel tmp=new JPanel(new BorderLayout());
		triggerTouchGlobal=new JCheckBox("全局触发");
		triggerTouchGlobal.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				trigger.setbGlobal(triggerTouchGlobal.isSelected());
			}
		});
		tmp.add(triggerTouchGlobal,BorderLayout.NORTH);
		triggerTouchAdd=new JButton("添加");
		triggerTouchAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TriggerTouchData triggerTouchData=ViewTils.showTriggerTouchDataDialog(centerPanel, EventData.GetData());
				if(triggerTouchData==null)
					return;
				triggerTouchTable.addRow(triggerTouchData.toStringArrays());
				TriggerTouch triggerTouch= (TriggerTouch) trigger;
				triggerTouch.getTouchList().add(triggerTouchData);
			}
		});
		triggerTouchDelet=new JButton("删除");
		triggerTouchDelet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex=triggerTouchTablePanel.getSelectedRow();
				if(rowIndex<0)
					return;
				TriggerTouch triggerTouch=(TriggerTouch) trigger;
				triggerTouch.getTouchList().remove(rowIndex);
				triggerTouchTable.removeRow(rowIndex);
			}
		});
		String[] colName={"事件","局域","改变方向","概率"};
		triggerTouchTable=new DefaultTableModel(colName, 0);
		triggerTouchTablePanel=new JTable(triggerTouchTable);
		triggerTouchTablePanel.getColumnModel().getColumn(0).setPreferredWidth(40);
		triggerTouchTablePanel.getColumnModel().getColumn(1).setPreferredWidth(80);
		triggerTouchTablePanel.getColumnModel().getColumn(2).setPreferredWidth(30);
		triggerTouchTablePanel.getColumnModel().getColumn(3).setPreferredWidth(10);
		JScrollPane scrollPane=new JScrollPane(triggerTouchTablePanel);
		tmp.add(scrollPane,BorderLayout.CENTER);
		
		JPanel southPanel=new JPanel(new GridLayout(1, 2));
		southPanel.add(triggerTouchAdd);
		southPanel.add(triggerTouchDelet);
		tmp.add(southPanel, BorderLayout.SOUTH);
		return tmp;
	}
	
	public JCheckBox triggerCollisionGlobal;
	public JButton triggerCollisionAdd;
	public JButton triggerCollisionDelet;
	public DefaultTableModel triggerCollisionTable;
	public JTable triggerCollisionTablePanel;
	private JPanel triggerCollisionInit() {
		JPanel tmp=new JPanel(new BorderLayout());
		triggerCollisionGlobal=new JCheckBox("全局触发");
		triggerCollisionGlobal.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				trigger.setbGlobal(triggerCollisionGlobal.isSelected());
			}
		});
		tmp.add(triggerCollisionGlobal,BorderLayout.NORTH);
		triggerCollisionAdd=new JButton("添加");
		triggerCollisionAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TriggerCollisionData triggerCollisionData=ViewTils.showTriggerCollisionDataDialog(centerPanel, EventData.GetData());
				if(triggerCollisionData==null)
					return;
				triggerCollisionTable.addRow(triggerCollisionData.toStringArrays());
				TriggerCollision triggerCollision= (TriggerCollision) trigger;
				triggerCollision.getCollisionEventList().add(triggerCollisionData);
			}
		});
		triggerCollisionDelet=new JButton("删除");
		triggerCollisionDelet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex=triggerCollisionTablePanel.getSelectedRow();
				if(rowIndex<0)
					return;
				TriggerCollision triggerDst=(TriggerCollision) trigger;
				triggerDst.getCollisionEventList().remove(rowIndex);
				triggerCollisionTable.removeRow(rowIndex);
			}
		});
		String[] colName={"事件","碰撞对象","改变方向","概率"};
		triggerCollisionTable=new DefaultTableModel(colName, 0);
		triggerCollisionTablePanel=new JTable(triggerCollisionTable);
		triggerCollisionTablePanel.getColumnModel().getColumn(0).setPreferredWidth(40);
		triggerCollisionTablePanel.getColumnModel().getColumn(1).setPreferredWidth(80);
		triggerCollisionTablePanel.getColumnModel().getColumn(2).setPreferredWidth(30);
		triggerCollisionTablePanel.getColumnModel().getColumn(3).setPreferredWidth(10);
		JScrollPane scrollPane=new JScrollPane(triggerCollisionTablePanel);
		tmp.add(scrollPane,BorderLayout.CENTER);
		
		JPanel southPanel=new JPanel(new GridLayout(1, 2));
		southPanel.add(triggerCollisionAdd);
		southPanel.add(triggerCollisionDelet);
		tmp.add(southPanel, BorderLayout.SOUTH);
		return tmp;
	}
	
	
	public JButton triggerActAdd;
	public JButton triggerActDelet;
	public DefaultTableModel triggerActTable;
	public JTable triggerActTablePanel;
	private JPanel triggerActInit() {
		JPanel tmp=new JPanel(new BorderLayout());
		triggerActAdd=new JButton("添加");
		triggerActAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TriggerActData triggerActData=ViewTils.showTriggerActDataDialog(centerPanel, EventData.GetData());
				if(triggerActData==null)
					return;
				triggerActTable.addRow(triggerActData.toStringArrays());
				TriggerAct triggerAct=(TriggerAct) trigger;
				triggerAct.getActCompleteList().add(triggerActData);
			}
		});
		triggerActDelet=new JButton("删除");
		triggerActDelet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex=triggerActTablePanel.getSelectedRow();
				if(rowIndex<0)
					return;
				TriggerAct triggerAct=(TriggerAct) trigger;
				triggerAct.getActCompleteList().remove(rowIndex);
				triggerActTable.removeRow(rowIndex);
			}
		});
		String[] colName={"事件","最小次数","最大次数","改变方向","概率"};
		triggerActTable=new DefaultTableModel(colName, 0);
		triggerActTablePanel=new JTable(triggerActTable);
		triggerActTablePanel.getColumnModel().getColumn(0).setPreferredWidth(40);
		triggerActTablePanel.getColumnModel().getColumn(1).setPreferredWidth(30);
		triggerActTablePanel.getColumnModel().getColumn(2).setPreferredWidth(30);
		triggerActTablePanel.getColumnModel().getColumn(3).setPreferredWidth(30);
		triggerActTablePanel.getColumnModel().getColumn(4).setPreferredWidth(20);
		
		JScrollPane scrollPane=new JScrollPane(triggerActTablePanel);
		tmp.add(scrollPane,BorderLayout.CENTER);
		
		JPanel southPanel=new JPanel(new GridLayout(1, 2));
		southPanel.add(triggerActAdd);
		southPanel.add(triggerActDelet);
		tmp.add(southPanel, BorderLayout.SOUTH);
		return tmp;
	}
	
	public JCheckBox triggerDragGlobal;
	public JButton triggerDragToEvent;
	public DefaultTableModel triggerDragTable;
	public JTable triggerDragTablePanel;
	public JButton triggerDragAdd;
	public JButton triggerDragDelet;
	private JPanel triggerDragInit(){
		JPanel tmp=new JPanel(new BorderLayout());
		JPanel north=new JPanel(new GridLayout(2, 1));
		triggerDragGlobal=new JCheckBox("全局触发");
		triggerDragGlobal.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				((TriggerDrag) trigger).setbGlobal(triggerDragGlobal.isSelected());
			}
		});
		north.add(triggerDragGlobal);
		JPanel eventpanel=new JPanel(new FlowLayout());
		eventpanel.add(new JLabel("退出事件选择:"));
		triggerDragToEvent=new JButton();
		triggerDragToEvent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = (String) JOptionPane.
						showInputDialog(null,"事件:\n",
						"选择", JOptionPane.PLAIN_MESSAGE,
						null, EventData.GetData().getStringList().toArray(), null);
				if(result==null)
					return;
				TriggerDrag triggerDrag=(TriggerDrag) trigger;
				triggerDrag.setToEvent(result);
				triggerDragToEvent.setText(result);
			}
		});
		eventpanel.add(triggerDragToEvent);
		north.add(eventpanel);
		tmp.add(north,BorderLayout.NORTH);
		
		String[] colName={"加速度","速度","动作"};
		triggerDragTable=new DefaultTableModel(colName, 0);
		triggerDragTablePanel=new JTable(triggerDragTable);
		triggerDragTablePanel.getColumnModel().getColumn(0).setPreferredWidth(40);
		triggerDragTablePanel.getColumnModel().getColumn(1).setPreferredWidth(40);
		triggerDragTablePanel.getColumnModel().getColumn(2).setPreferredWidth(20);
		
		JScrollPane scrollPane=new JScrollPane(triggerDragTablePanel);
		tmp.add(scrollPane,BorderLayout.CENTER);
		
		triggerDragAdd=new JButton("添加");
		triggerDragAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TriggerDragData triggerDragData=ViewTils.showTriggerDragDataDialog(centerPanel, ActData.GetData());
				if(triggerDragData==null)
					return;
				triggerDragTable.addRow(triggerDragData.toStringArrays());
				TriggerDrag triggerDrag=(TriggerDrag) trigger;
				triggerDrag.getDragActList().add(triggerDragData);
			}
		});
		triggerDragDelet=new JButton("移除");
		triggerDragDelet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex=triggerDragTablePanel.getSelectedRow();
				if(rowIndex<0)
					return;
				TriggerDrag triggerDrag=(TriggerDrag) trigger;
				triggerDrag.getDragActList().remove(rowIndex);
				triggerDragTable.removeRow(rowIndex);
			}
		});
		JPanel southPanel=new JPanel(new GridLayout(1, 2));
		southPanel.add(triggerDragAdd);
		southPanel.add(triggerDragDelet);
		tmp.add(southPanel, BorderLayout.SOUTH);
		return tmp;
	}
	
	
/******************************************************************
*	加载参数
******************************************************************/	
	public void setTrigger(TriggerData trigger) {
		this.trigger=trigger;
		titleLabel.setText(trigger.getTrigger_type()+"参数");
		card.show(centerPanel, trigger.getTrigger_type());
		setTriggerDefault(trigger);
		setTriggerDst(trigger);
		setTriggerCollision(trigger);
		setTriggerTime(trigger);
		setTriggerTouch(trigger);
		setTriggerAct(trigger);
		setTriggerDrag(trigger);
	}
	
	private void setTriggerDefault(TriggerData trigger){
		if(!trigger.getTrigger_type().equals(TriggerData.TRIGGER_TYPE[0]))
			return;
		TriggerDefault tmpTrigger=(TriggerDefault)trigger;
		triggerDefaultRandom.setSelected(tmpTrigger.isbRandom());
		if(tmpTrigger.getPosition()!=null){
			triggerDefaultPosX.setText(tmpTrigger.getPosition().x+"");
			triggerDefualtPosY.setText(tmpTrigger.getPosition().y+"");
		}
		triggerDefaultIcon.setText(tmpTrigger.getIcon());
	}
	
	private void setTriggerDst(TriggerData trigger){
		if(!trigger.getTrigger_type().equals(TriggerData.TRIGGER_TYPE[1]))
			return;
		TriggerDst act=(TriggerDst) trigger;
		triggerDstGlobal.setSelected(act.isbGlobal());
		int rowCount=triggerDstTable.getRowCount();
		while(rowCount>0){
			triggerDstTable.removeRow(0);
			rowCount--;
		}
		for(TriggerDstData rowData:act.getDstList()){
			triggerDstTable.addRow(rowData.toStringArrays());
		}
	}
	
	private void setTriggerTime(TriggerData trigger){
		if(!trigger.getTrigger_type().equals(TriggerData.TRIGGER_TYPE[2]))
			return;
		TriggerTime time=(TriggerTime)trigger;
		triggerTimeGlobal.setSelected(time.isbGlobal());
		int rowCount=triggerTimeTable.getRowCount();
		
		while(rowCount>0){
			triggerTimeTable.removeRow(0);
			rowCount--;
		}
		for(TriggerTimeData timeData:time.getTimeNextEventList()){
			triggerTimeTable.addRow(timeData.toStringArrays());
		}
	}
	
	private void setTriggerTouch(TriggerData trigger){
		if(!trigger.getTrigger_type().equals(TriggerData.TRIGGER_TYPE[3]))
			return;
		TriggerTouch touch=(TriggerTouch) trigger;
		triggerTouchGlobal.setSelected(touch.isbGlobal());
		int rowCount=triggerTouchTable.getRowCount();
		
		while(rowCount>0){
			triggerTouchTable.removeRow(0);
			rowCount--;
		}
		for(TriggerTouchData touchData:touch.getTouchList()){
			triggerTouchTable.addRow(touchData.toStringArrays());
		}
	}
	
	private void setTriggerCollision(TriggerData trigger){
		if(!trigger.getTrigger_type().equals(TriggerData.TRIGGER_TYPE[4]))
			return;
		TriggerCollision collision=(TriggerCollision) trigger;
		triggerCollisionGlobal.setSelected(collision.isbGlobal());
		int rowCount=triggerCollisionTable.getRowCount();
		while(rowCount>0){
			triggerCollisionTable.removeRow(0);
			rowCount--;
		}
		for(TriggerCollisionData rowData:collision.getCollisionEventList()){
			triggerCollisionTable.addRow(rowData.toStringArrays());
		}
	}
	
	private void setTriggerAct(TriggerData trigger){
		if(!trigger.getTrigger_type().equals(TriggerData.TRIGGER_TYPE[5]))
			return;
		TriggerAct act=(TriggerAct)trigger;
		int rowCount=triggerActTable.getRowCount();
		
		while(rowCount>0){
			triggerActTable.removeRow(0);
			rowCount--;
		}
		for(TriggerActData actData:act.getActCompleteList()){
			triggerActTable.addRow(actData.toStringArrays());
		}
	}
	
	private void setTriggerDrag(TriggerData trigger){
		if(!trigger.getTrigger_type().equals(TriggerData.TRIGGER_TYPE[6]))
			return;
		TriggerDrag drag=(TriggerDrag) trigger;
		triggerDragGlobal.setSelected(drag.isbGlobal());
		triggerDragToEvent.setText(drag.getToEvent());
		int rowCount=triggerDragTable.getRowCount();
		while(rowCount>0){
			triggerDragTable.removeRow(0);
			rowCount--;
		}
		for(TriggerDragData dragData:drag.getDragActList()){
			triggerDragTable.addRow(dragData.toStringArrays());
		}
	}
	
	public void setEvent(EventItemData event) {
		this.event=event;
		card.show(centerPanel, EVENT_PANEL);
		eventAct.setText(""+event.getAct());
		if(event.getInitSpeed()==null){
			event.setInitSpeed(new Vec2(0f, 0f));
		}
		eventInitSpeedX.setText(event.getInitSpeed().x+"");
		eventInitSpeedY.setText(event.getInitSpeed().y+"");
	}
}
