package ind.chy.pet.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class PetWin extends JWindow{
	//宠物窗体,用于渲染单只宠物
	
	private PetPanel petPanel;
	private AffineTransform transform=null;
	private ImageIcon img;
	private class PetPanel extends JPanel{
		
		@Override
		protected void paintComponent(Graphics arg0) {
			super.paintComponent(arg0);
			Graphics2D g2d = (Graphics2D)arg0;
			g2d.drawImage(img.getImage(), transform, null);
		}
		
	}
	
	public PetWin() {
		init();
	}
	
	protected  void init(){
		petPanel=new PetPanel();
		setLayout(new BorderLayout());
		add(petPanel,BorderLayout.CENTER);
		setContentPane(petPanel);
		setBackground(new Color(0, 0, 0,0));
		setVisible(true);
	}
	
	public void drawImage(ImageIcon img,AffineTransform transform) {
		this.img=img;
		this.transform=transform;
		this.setSize(img.getIconWidth(), img.getIconHeight());
		petPanel.repaint();
	}
}
