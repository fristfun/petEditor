package ind.chy.editor.view;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ind.chy.pet.data.ActItemData;
import ind.chy.pet.data.ActItemData.PicData;
import ind.chy.pet.data.DataTils;
import ind.chy.pet.view.ViewTils;

public class EditorActPara {
	private final static String BLANK_PANEL="blank";
	private final static String IMG_PANEL="img";
	private final static String ACT_PANEL="act";
	private final static String[] DIRECT= {"前","后","上","下"};
	private final static String SHOW_SPEED_STRING="速度(pixel)";
	public JPanel panel;
	public JPanel centerPanel;
	private CardLayout card=new CardLayout();
	private JLabel titleLabel=new JLabel("动作参数");
	
	//动作参数
//	public JComboBox<String> actEnv;
	public JTextField actFrame;
	public JComboBox<String> actDirect;
	public JTextField actSpeed;
	public JCheckBox actSimulationCheck;
	public JTextField actSimulationXNiu;	//阻力
	public JTextField actSimulationYNiu;	//重力
	public JButton actPlay;
	
	//图片参数
	public JLabel picInf;
	public JTextField picDelay;
	public JComboBox<String> picDirect;
	public JTextField picSpeed;
	public JCheckBox picUsePara;
	public JButton picOK;
	
	public EditorActPara() {
		centerPanel=new JPanel(card);
		panel=ViewTils.createTitlePanel(centerPanel,titleLabel);
		centerPanel.add(BLANK_PANEL, new JPanel());
		centerPanel.add(ACT_PANEL,actPanelInit());
		centerPanel.add(IMG_PANEL,imgPanelInit());
	}
	
	private JPanel actPanelInit() {
		JPanel para=new JPanel();
		para.setLayout(new GridLayout(6, 1));
//		actEnv=new JComboBox<>(ActItemData.ENV_TYPE);
		actFrame=new JTextField(10);
		actFrame.setBorder(BorderFactory.createTitledBorder("图片延时(ms)"));
		actDirect=new JComboBox<>(DIRECT);
		actSpeed=new JTextField(10);
		actSpeed.setBorder(BorderFactory.createTitledBorder(SHOW_SPEED_STRING));
		JPanel simulationPanle=new JPanel(new FlowLayout());
		actSimulationCheck=new JCheckBox("物理模拟");
		actSimulationXNiu=new JTextField(3);
		actSimulationYNiu=new JTextField(3);
		simulationPanle.add(new JLabel("阻力:"));
		simulationPanle.add(actSimulationXNiu);
		simulationPanle.add(new JLabel("重力:"));
		simulationPanle.add(actSimulationYNiu);
		actPlay=new JButton("播放");
//		para.add(actEnv);
		para.add(actFrame);
		para.add(actDirect);
		para.add(actSpeed);
		para.add(actSimulationCheck);
		para.add(simulationPanle);
		para.add(actPlay);
		return para;
	}
	
	private JPanel imgPanelInit() {
		JPanel para=new JPanel();
		para.setLayout(new GridLayout(6, 1));
		picInf=new JLabel("pic info");
		picDelay=new JTextField(10);
		picDelay.setBorder(BorderFactory.createTitledBorder("延迟时间(ms)"));
		picDirect=new JComboBox<>(DIRECT);
		picSpeed=new JTextField(10);
		picSpeed.setBorder(BorderFactory.createTitledBorder(SHOW_SPEED_STRING));
		picUsePara=new JCheckBox("是否使用图片参数");
		picOK=new JButton("确定");
		para.add(picInf);
		para.add(picDelay);
		para.add(picDirect);
		para.add(picSpeed);
		para.add(picUsePara);
		para.add(picOK);
		return para;
	}
	
	public void setBlank() {
		titleLabel.setText("动作参数");
		card.show(centerPanel, BLANK_PANEL);
	}
	
	public void setImagePara(ImageIcon img,PicData pic) {
		titleLabel.setText("图片参数");
		card.show(centerPanel, IMG_PANEL);
		picInf.setText("img width="+img.getIconWidth()+",height="+img.getIconHeight());
		picDelay.setText(""+pic.getPicDelay());
		picDirect.setSelectedIndex(DataTils.getShowDirectInt(pic.getSpeed()));
		picSpeed.setText(""+DataTils.getShowSpeed(pic.getSpeed()));
		picUsePara.setSelected(pic.isbUsePicPara());
	}
	
	public void setActPara(ActItemData actItem) {
		titleLabel.setText("动作参数");
		card.show(centerPanel, ACT_PANEL);
//		actEnv.setSelectedIndex(Arrays.binarySearch(ActItemData.ENV_TYPE, actItem.getEnv_type()));
		actDirect.setSelectedIndex(DataTils.getShowDirectInt(actItem.getSpeed()));
		actFrame.setText(""+actItem.getPicDelay());
		actSpeed.setText(""+DataTils.getShowSpeed(actItem.getSpeed()));
		boolean bSimulation=actItem.isbSimulation();
		actSimulationCheck.setSelected(bSimulation);
		if(!bSimulation)
			return;
		actSimulationXNiu.setText(""+actItem.getWorldForce().x);
		actSimulationYNiu.setText(""+actItem.getWorldForce().y);
	}

}
