package ind.chy.App.Launch;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ind.chy.editor.view.PetEditorWin;


public class AppLaunchWin extends JFrame implements ActionListener{
	private JComboBox<String> petChocieBox=new JComboBox<String>();
	private JButton btEditorLaunch=new JButton("�����༭��");
	private JButton btPetLaunch=new JButton("��������");
	//����������
	public AppLaunchWin() {
		setTitle("����������");
		setSize(200, 150);
		panelInit();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	//����ؼ���ʼ��
	private void panelInit() {
		File file =new File(AppLaunch.SCRIPT_FOLDER);
		if(file.exists()) {
			for(String scriptName:file.list()) {
				int end=scriptName.lastIndexOf(AppLaunch.STORE_EVENT);
				if(end>0) {
					petChocieBox.addItem(scriptName.substring(0, end));
				}
			}
		}
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(petChocieBox);
		panel.add(btPetLaunch);
		panel.add(btEditorLaunch);
		btPetLaunch.addActionListener(this);
		btEditorLaunch.addActionListener(this);
		add(panel);
		setContentPane(panel);
	}

	//�����¼�
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(btPetLaunch==obj) {
			PetLaunch.addPet(this,petChocieBox.getItemAt(petChocieBox.getSelectedIndex()));
		}else if(btEditorLaunch==obj){
			new PetEditorWin();
			this.dispose();
		}
	}
	
	
	

}
