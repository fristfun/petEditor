package ind.chy.pet.data;

import java.util.ArrayList;

public class EventData {

	private AppVector<EventItemData> listEvent=null;
	private String petName; //³èÎïÃû
	private static EventData obj=null;
	
	private EventData(){
	}
	
	public AppVector<EventItemData> getListEvent() {
		return listEvent;
	}

	public void setListEvent(AppVector<EventItemData> listEvent) {
		this.listEvent = listEvent;
	}

	public static EventData GetData(String filepath){
		if(obj==null){
			obj=(EventData)DataTils.ReadTrigger(EventData.class, filepath);
		}
		if(obj==null){
			obj=new EventData();
		}
		return obj;
	}
	
	public static EventData GetPetEvent(String filepath) {
		return (EventData)DataTils.ReadTrigger(EventData.class, filepath);
	}
	
	public static EventData GetData(){
		return obj;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}
	
	public ArrayList<String> getStringList(){
		if(listEvent==null||listEvent.size()==0)
			return null;
		ArrayList<String> tmp=new ArrayList<>();
		for(EventItemData item:listEvent){
			tmp.add(item.getName());
		}
		return tmp;
	}

}
