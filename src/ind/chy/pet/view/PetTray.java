package ind.chy.pet.view;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ind.chy.App.Launch.AppLaunch;
import ind.chy.App.Launch.AppLaunchWin;
import ind.chy.App.Launch.PetLaunch;
import ind.chy.pet.model.DesktopPet;
import ind.chy.pet.model.DesktopWorld;

public class PetTray implements ActionListener,MouseListener{
	//ʹ��ϵͳ����ʱ,�Ҽ������˵���,�����޷���������
	//ʹ���Զ����Ҽ��˵���,�޷�������������Ļ������¼�,�����ٴε���ؼ����ܹرյ����˵�
	private SystemTray tray=null;
	private String petName=null;
	private DesktopWorld world;
	private ArrayList<DesktopPet> pets=new ArrayList<>();
	private TrayIcon trayIcon=null;
	private JPopupMenu trayPop;
	private JMenuItem petTop;
	private JMenuItem petAdd;
	private JMenuItem petAddOther;
	private JMenuItem petExit;
	private boolean isTop=true;
	public PetTray(DesktopWorld world,String petName,String iconPath) {
		this.world=world;
		this.petName=petName;
		if(!SystemTray.isSupported())
			return ;
		Image img=new ImageIcon(AppLaunch.APP_PATH+iconPath).getImage();
		trayIcon=new TrayIcon(img);
		trayPop=new JPopupMenu();
		petTop=new JMenuItem("������");
		petTop.addActionListener(this);
		petAdd=new JMenuItem("����");
		petAdd.addActionListener(this);
		petAddOther=new JMenuItem("��������");
		petAddOther.addActionListener(this);
		petExit=new JMenuItem("�˳�");
		petExit.addActionListener(this);
		tray=SystemTray.getSystemTray();
		trayPop.add(petTop);
		trayPop.add(petAdd);
		trayPop.add(petAddOther);
		trayPop.add(petExit);
		trayIcon.addMouseListener(this);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		};
	}
	
	public void AddPet(DesktopPet pet){
		pets.add(pet);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(petExit==obj){
			for(DesktopPet p:pets){
				world.destroyObject(p);
			}
			pets.clear();
			tray.remove(trayIcon);
		}else if(petAdd==obj){
				PetLaunch.addPet(null, petName);
		}else if(petTop==obj){
			isTop=!isTop;
			if(isTop){
				petTop.setText("������");
			}else{
				petTop.setText("����");
			}
			for(DesktopPet p:pets){
				p.setWinTop(isTop);
			}
		}else if(petAddOther==obj){
			new AppLaunchWin();
		}
	
	}

	@Override
	public void mouseClicked(MouseEvent e){
		if(e.getButton()==MouseEvent.BUTTON3) {
			PetWin win=pets.get(0).getWin();
			trayPop.show(win, e.getX()-win.getX()+10, e.getY()-win.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
