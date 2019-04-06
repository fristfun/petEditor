package ind.chy.pet.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import ind.chy.pet.model.DesktopWorld.Vec2;

public class DataTils {
	
	public static void SaveObject(Object obj,String filename){
		if(obj==null)
			return;
		Gson g=new Gson();
		String folderpath=filename.substring(0, filename.lastIndexOf("\\"));
		File f=new File(folderpath);
		f.mkdirs();
		f=new File(filename);
		if(f.exists()){
			f.delete();
		}
		FileOutputStream ou=null;
		try {
			f.createNewFile();
			ou=new FileOutputStream(f);
			ou.write(g.toJson(obj).getBytes(Charset.forName("gbk")));
			ou.flush();
			ou.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Object ReadObject(Class c,String filepath){
		Gson g=new Gson();
		Object ret=null;
		File f=new File(filepath);
		if(!f.exists()){
			return ret;
		}
		FileReader read=null;
		try {
			read=new FileReader(f);
			ret=g.fromJson(read, c);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static Object ReadTrigger(Type c,String filepath){
		RuntimeTypeAdapterFactory<TriggerData> triggerAdapterFactory=RuntimeTypeAdapterFactory.of(TriggerData.class,"trigger_type");
		for(int i=0;i<TriggerData.TRIGGER_TYPE.length;i++){
			triggerAdapterFactory.registerSubtype((Class<? extends TriggerData>) TriggerData.TRIGGER_CLASS[i], TriggerData.TRIGGER_TYPE[i]);
		}
		Gson g=new GsonBuilder().registerTypeAdapterFactory(triggerAdapterFactory).create();
		
		Object ret=null;
		File f=new File(filepath);
		if(!f.exists()){
			return ret;
		}
		FileReader read=null;
		try {
			read=new FileReader(f);
			ret=g.fromJson(read, c);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	private static Random random = new Random();
	
	public static int getRandom(int min,int max){
		if(max<min)
			return min;
		if(max<2)
			return 0;
		return random.nextInt(max) % (max - min + 1) + min;
	}
	
	public static boolean randomSelet(float percent){
		percent*=100;
		if(percent>100)
			return true;
		int random=getRandom(1,99);
		return random<percent;
	}
	
	public static int findStringInArr(String[] arr,String dst) {
		int i=0;
		for(String s:arr) {
			if(s.equals(dst)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static Vec2 getShowSpeed(int show_Direct,float showSpeed) {
		Vec2 Speed=new Vec2(0,0);
		switch(show_Direct) {
		case 0:
			Speed.x=showSpeed*-1;
			break;
		case 1:
			Speed.x=showSpeed;
			break;
		case 2:
			Speed.y=showSpeed*-1;
			break;
		case 3:
			Speed.y=showSpeed;
			break;
		}
		return Speed;
	}
	
	public static float getShowSpeed(Vec2 speed) {
		if(speed==null)
			return 0;
		if(speed.x!=0)
			return Math.abs(speed.x);
		else if(speed.y!=0)
			return Math.abs(speed.y);
		return 0;
	}
	
	public static int getShowDirectInt(Vec2 speed) {
		if(speed==null)
			return 0;
		if(speed.x!=0) {
			return speed.x<0?0:1;
		}else if(speed.y!=0){
			return speed.y>0?3:2;
		}
		return 0;
	}
	
}
