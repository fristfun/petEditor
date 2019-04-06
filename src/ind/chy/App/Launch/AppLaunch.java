package ind.chy.App.Launch;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;

import ind.chy.pet.view.ViewTils;


public class AppLaunch {
	
	public static String APP_PATH=null;
	public static Rectangle SCREEN_DOUNDS=null;
	public static Dimension SCREEN=null;
	
	public static String SCRIPT_FOLDER="PetScript\\";
	public static String STORE_ACT="_action.json";
	public static String STORE_EVENT="_event.json";

	//初始化
	static{
		File file=new File("");
		APP_PATH=file.getAbsolutePath()+"\\";
		SCRIPT_FOLDER=APP_PATH+SCRIPT_FOLDER;
		SCREEN=Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_DOUNDS=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		ViewTils.init(APP_PATH);
	}
	
	/*
	 * 应用启动入口
	 * */
	public static void main(String[] arg0) {
		System.out.println("hello");
		System.out.println("screen"+SCREEN.toString());
		new AppLaunchWin();		//启动器界面
	}
}
