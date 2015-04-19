package user;

import java.util.ArrayList;

public class mipsgenerator {

	ArrayList<irinstruction> irlist;
	ArrayList<mipsinstruction> mipslist;
	
	public mipsgenerator(ArrayList<irinstruction> IR){
		irlist = IR;
		mipslist = new ArrayList<mipsinstruction>();
		for(irinstruction i: irlist){
			mipslist.add(new mipsinstruction(i));
		}
	}
	
	public String print(){
		String res = "";
		for(mipsinstruction i: mipslist){
			res += i +"\n";
		}
		return res;
	}
}