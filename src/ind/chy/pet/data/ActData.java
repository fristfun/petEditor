package ind.chy.pet.data;

import java.util.ArrayList;

public class ActData {
	
	private AppVector<ActItemData> actList=null;
	private static ActData obj=null;
	private  ActData(){

	}
	
	public static ActData GetData(String filepath){
		if(obj==null){
			obj=(ActData)DataTils.ReadObject(ActData.class, filepath);
		}
		if(obj==null){
			obj=new ActData();
		}
		return obj;
	}
	
	public static ActData GetPetAct(String filepath) {
		return (ActData)DataTils.ReadObject(ActData.class, filepath);
	}
	
	public static ActData GetData(){
		return obj;
	}

	public AppVector<ActItemData> getActList() {
		return actList;
	}

	public void setActList(AppVector<ActItemData> actList) {
		this.actList = actList;
	}
	
	public ArrayList<String> getStringList(){
		if(actList==null)
			return null;
		ArrayList<String> tmp=new ArrayList<>();
		for(ActItemData item:actList){
			tmp.add(item.getName());
		}
		return tmp;
	}
	
}
