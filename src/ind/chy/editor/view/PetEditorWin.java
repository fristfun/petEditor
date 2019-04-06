package ind.chy.editor.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ind.chy.App.Launch.AppLaunch;
import ind.chy.pet.data.ActData;
import ind.chy.pet.data.ActItemData;
import ind.chy.pet.data.ActItemData.PicData;
import ind.chy.pet.data.DataTils;
import ind.chy.pet.data.EventData;
import ind.chy.pet.view.ViewTils;

public class PetEditorWin extends JFrame{
	//编辑框主窗体
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditorActList act;
	private EditorEventList event;
	public PetEditorWin() {
		setTitle("桌宠编辑器");
		setSize(800, 600);
		menuInit();
		panelInit();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void menuInit(){
		JMenuBar menuBar=new JMenuBar();
		
		JMenu menu=new JMenu("文件");
		
		JMenuItem menuItem=new JMenuItem("读取/新建");
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String petName=JOptionPane.showInputDialog("输入宠物名");
				if(petName==null)
					return;
				ActData.GetData(AppLaunch.SCRIPT_FOLDER+petName+AppLaunch.STORE_ACT);
				EventData tmp=EventData.GetData(AppLaunch.SCRIPT_FOLDER+petName+AppLaunch.STORE_EVENT);
				if(!petName.equals(tmp.getPetName())){
					tmp.setPetName(petName);
				}
				act.updateData();
				event.updateData();
			}
		});
		menu.add(menuItem);
		
		menuItem=new JMenuItem("保存");
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EventData event=EventData.GetData();
				if(event==null)
					return;
				DataTils.SaveObject(ActData.GetData(), AppLaunch.SCRIPT_FOLDER+event.getPetName()+AppLaunch.STORE_ACT);
				DataTils.SaveObject(EventData.GetData(), AppLaunch.SCRIPT_FOLDER+event.getPetName()+AppLaunch.STORE_EVENT);
			}
		});
		menu.add(menuItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}
	
	private void panelInit() {
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JPanel actPanel=new JPanel();
		actPanel.setLayout(new GridLayout(1, 4));
		actPanel.setBorder(BorderFactory.createTitledBorder(
				null, "动作编辑器", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION,
				new Font(null, Font.BOLD, 18), null));
		DrawPanel drawPanel=new DrawPanel();
		JPanel titileDraw=ViewTils.createTitlePanel(drawPanel,"预览");
		titileDraw.add(drawPanel,BorderLayout.CENTER);
		EditorActPara actPara=new EditorActPara();
		EditorPicList picList=new EditorPicList("图片列表",actPara,drawPanel);
		act=new EditorActList("动作列表",picList,actPara,drawPanel);
		//动作编辑
		actPanel.add(act.panel);
		actPanel.add(titileDraw);
		actPanel.add(picList.panel);
		actPanel.add(actPara.panel);
		//事件编辑
		JPanel eventPanel=new JPanel();
		eventPanel.setLayout(new GridLayout(1, 3));
		eventPanel.setBorder(BorderFactory.createTitledBorder(
				null, "事件编辑器", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION,
				new Font(null, Font.BOLD, 18), null));
		
		EditorEventPara eventPara=new EditorEventPara();
		EditorTriggerList triggerList=new EditorTriggerList("触发器列表", eventPara);
		event=new EditorEventList("事件列表",triggerList,eventPara);
		eventPanel.add(event.panel);
		eventPanel.add(triggerList.panel);
		eventPanel.add(eventPara.panel);
		
		//动作编辑+事件编辑
		panel.add(actPanel);
		panel.add(eventPanel);
		add(panel);
		setContentPane(panel);
	}
	
	protected class DrawPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ImageIcon img=null;
		private Image iBuffer=null;
		private Graphics2D gBuffer;
		private boolean bPlay=false;
		private ActItemData actItemData=null;
		private Runnable play=new Runnable() {
			
			@Override
			public void run() {
				if(actItemData==null)
					return;
				int picIndex=0;
				int size=actItemData.getListPic().size();
				System.out.println("play");
				while(bPlay) {
					int delay=0;
					PicData pic=actItemData.getListPic().get(picIndex);
					picIndex++;
					if(pic.isbUsePicPara()) {
						delay=pic.getPicDelay();
					}else {
						delay=actItemData.getPicDelay();
					}
					if(picIndex>=size) {
						delay=1000;
						picIndex=0;
					}
					
					drawAction(pic.getPicName());
					
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		};
		
		public DrawPanel() {
			setOpaque(false);
		}
		
		@Override
		protected void paintComponent(Graphics arg0) {
			super.paintComponent(arg0);
			if(iBuffer==null){
				iBuffer=createImage(this.getSize().width,this.getSize().height);
				gBuffer=(Graphics2D)iBuffer.getGraphics();
			}
			if(img==null)
				return;
			int drawX,drawY;
			//清除画布
			gBuffer.setColor(getBackground());
			gBuffer.fillRect(0,0,this.getSize().width,this.getSize().height);
			
			//二级缓冲画图
			gBuffer.drawImage(img.getImage(),null,null);
		
			drawX=this.getSize().width-img.getIconWidth();
			drawX=drawX>0?drawX/2:0;
			drawY=this.getSize().height-img.getIconHeight();
			drawY=drawY>0?drawY/2:0;
			
			//画图
			arg0.drawImage(iBuffer,drawX,drawY,this);
			img=null;
		}
		
		
		protected void drawImage(ImageIcon img) {
			stopPlay();
			this.img=img;
			this.repaint();
		}
		
		protected void drawImage(String imgName) {
			stopPlay();
			drawAction(imgName);
		}
		
		private void drawAction(String imgName) {
			this.img=new ImageIcon(AppLaunch.APP_PATH+imgName);
			this.repaint();
		}
		
		public void stopPlay() {
			bPlay=false;
		}
		
		protected void playAction(ActItemData act) {
			bPlay=true;
			this.actItemData=act;
			Thread showPlay=new Thread(play);
			showPlay.start();
		}
		
		public boolean isPlay() {
			return bPlay;
		}
	}
	
}
