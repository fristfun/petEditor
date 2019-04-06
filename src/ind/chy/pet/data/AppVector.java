package ind.chy.pet.data;

import java.util.Vector;

public class AppVector<E> extends Vector<E>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void insertData(E o,int index){
		if(index<size())
			insertElementAt(o, index);
		else
			add(o);
	}
	
	public void moveDown(int index){
		E tmp=get(index);
		remove(index);
		insertData(tmp, index+1);
	}
	
	public void moveUp(int index){
		E tmp= get(index);
		remove(index);
		insertData(tmp, index-1);
		
	}
	
	public void replaceIndex(E obj,int index){
		remove(index);
		insertData(obj, index);
	}
}
