package ind.chy.App.Launch;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ind.chy.pet.data.ActData;
import ind.chy.pet.data.EventData;
import ind.chy.pet.model.DesktopPet;
import ind.chy.pet.model.DesktopWorld;
import ind.chy.pet.view.PetTray;
import ind.chy.pet.view.PetWin;

public class PetLaunch {

	private DesktopWorld world=null;
	private int initTime=5;
	private static PetLaunch pets=null;
	private HashMap<String, PetTray> petTray=new HashMap<>();
	
	//物理仿真线程
	private Runnable simulation=new Runnable() {
		
		@Override
		public void run() {
			long startTime;
			long endTime;
			int lossTime;
			int tick=initTime;
			while(!world.isEmpty()){
				startTime=System.currentTimeMillis();
				world.update(tick);
				endTime=System.currentTimeMillis();
				lossTime=(int)(endTime-startTime);
				if(lossTime<initTime)
					tick=initTime;
				else
					tick=lossTime;
				if(tick>initTime){
					continue;
				}
				try {
					Thread.sleep(tick);	//时间需根据实际作图时间缩短
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			world=null;
		}
	};
	
	//加入桌宠及桌宠启动
	public static PetLaunch addPet(AppLaunchWin launchWin,String petName){
		boolean worldRun=false;
		String scriptPath=AppLaunch.SCRIPT_FOLDER+petName;
		String actPath=scriptPath+AppLaunch.STORE_ACT;
		String eventPath=scriptPath+AppLaunch.STORE_EVENT;
		ActData actData=ActData.GetPetAct(actPath);
		EventData eventData=EventData.GetPetEvent(eventPath);
		if(actData==null||eventData==null) {
			JOptionPane.showConfirmDialog(null, "脚本文件丢失!","错误",JOptionPane.CLOSED_OPTION);
			return null;
		}
		DesktopPet pet=DesktopPet.createPet(new PetWin(),actData,eventData);
		if(pet==null)
			return null;
		if(launchWin!=null)
			launchWin.dispose();
		
		if(pets==null){
			pets=new PetLaunch();
		}else{
			worldRun=true;
		}
		if(pets.world==null)
			pets.world=DesktopWorld.createWorld(AppLaunch.SCREEN, AppLaunch.SCREEN_DOUNDS);
		PetTray tmPetTray=pets.petTray.get(pet.getName());
		if(tmPetTray==null){
			tmPetTray=new PetTray(pets.world,pet.getName(),pet.getTrayIcon());
			pets.petTray.put(pet.getName(),tmPetTray);
		}
		tmPetTray.AddPet(pet);
		pets.world.addObject(pet);
		if(worldRun==false){
			Thread playThread=new Thread(pets.simulation);
			playThread.start();
		}
		return pets;
	}
	
	private PetLaunch() {
		System.out.println("new PetLaunch");
	}
	
}
