package user;

import java.util.HashMap;
import syntaxtree.*;

public class irinstruction {
	
	Object operator;
	Object argument1;
	Object argument2;
	Object result;
	int instructiontype;
	
	static HashMap<String, String> labelmap = new HashMap<String, String>();
	
	public final static int IRASSIGNMENT = 0;
	public final static int IRUNARYASSIGNMENT = 1;
	public final static int IRCOPY = 2;
	public final static int IRUNCONDITIONALJUMP = 3;
	public final static int IRCONDITIONALJUMP = 4;
	public final static int IRCALL = 5;
	public final static int IRRETURN = 6;
	public final static int IRINDEXEDASSIGNTO = 7;
	public final static int IRINDEXEDASSIGNFROM = 8;
	public final static int IRNEW = 9;
	public final static int IRNEWARRAY = 10;
	public final static int IRLENGTH = 11;
	public final static int IRPARAM = 12;
	public final static int IRLABEL = 13;
	public final static int IRPRINT = 14;
	public final static int IREXIT = 15;
	
	public irinstruction(int type){
		instructiontype = type;
	}
	
	public irinstruction(int type, Object arg1){
		instructiontype = type;
		switch(type){
		case IRLABEL:
			if(arg1 instanceof MethodDecl){

				MethodDecl arg = (MethodDecl) arg1;
				
				HashMap<String, String> methodformals = new HashMap<String, String>();
				methodformals.put(arg.i.s, "$a0");//NO IDEA ON HOW TO GET THE PARENT.
				for(int i = 0; i < arg.fl.size(); i++){
					methodformals.put(arg.fl.elementAt(i).i.s, "$a"+(i+1));
				}
				mipsinstruction.formals.put(arg.i.s, methodformals);
			}
			argument1 = arg1;
			break;
		case IRPARAM:
			argument1 = arg1;
			break;
		case IRRETURN:
			result = arg1;
			break;
		case IRUNCONDITIONALJUMP:
			result = arg1;
			break;
		case IRPRINT:
			argument1 = arg1;
			break;
		}
	}
	public irinstruction(int type, Object arg1, Object arg2){
		instructiontype = type;
		switch(type){
		case IRCONDITIONALJUMP:
			result = arg1;
			argument1 = arg2;
			break;
		case IRUNARYASSIGNMENT:
			result = arg1;
			argument1 = arg2;
			operator = "";
			break;
		case IRNEW:
			result = arg1;
			argument1 = arg2;
			break;
		}
	}
	public irinstruction(int type, Object arg1, Object arg2, Object arg3){
		instructiontype = type;
		switch(type){
		case IRUNARYASSIGNMENT:
			operator = arg1;
			argument1 = arg2;
			result = arg3;
			break;
		case IRASSIGNMENT:
			operator = arg1;
			argument1 = arg2;
			argument2 = arg3;
			break;
		case IRCALL:
			result = arg1;
			argument1 = arg2;
			argument2 = arg3;
			break;
		}
		
	}
	public irinstruction(int type, Object op, Object arg1, Object arg2, Object r){
		operator = op;
		argument1 = arg1;
		argument2 = arg2;
		result = r;
		instructiontype = type;
	}
	
	public String irassignment(){
		if(operator instanceof Plus){
			return result + " := " + argument1 + " + " + argument2;
		}else if(operator instanceof Minus){
			return result + " := " + argument1 + " - " + argument2;
		}else if(operator instanceof Times){
			return result + " := " + argument1 + " * " + argument2;
		}else if(operator instanceof LessThan){
			return result + " := " + argument1 + " < " + argument2;
		}else if(operator instanceof And){
			return result + " := " + argument1 + " && " + argument2;
		}else{
			return result + " := " + argument1 + " ? " + argument2;
		}
	}
	public String irunaryassignment(){
		return result + " := " + operator + " " + argument1;
	}
	public String ircopy(){
		return result + " := " + argument1;
	}
	public String irunconditionaljump(){
		return "goto " + result;
	}
	public String irconditionaljump(){
		return "iffalse " + argument1 + " goto " + result;
	}
	public String ircall(){
		return result + " := call " + ((Call)argument1).i + ", " + argument2;
	}
	public String irreturn(){
		return "return " + result;
	}
	public String irindexedassignto(){
		return result + " := " + argument1 + "[" + argument2 + "]";
	}
	public String irindexedassignfrom(){
		return argument1 + "[" + argument2 + "] := " + result;
	}
	public String irnew(){
		return result + " := new " + argument1;
	}
	public String irnewarray(){
		return result + " := new " + argument1 + ", " + argument2;
	}
	public String irlength(){
		return result + " := length " + argument1;
	}
	public String irparam(){
		return "param " + argument1;
	}
	public String irlabel(){
		return argument1+":";
	}
	public String irprint(){
		return "print";
	}
	public String irjr(){
		return "jr";
	}
	
	public String toString(){
		if(instructiontype == IRASSIGNMENT){
			return irassignment();
		}else if(instructiontype == IRUNARYASSIGNMENT){
			return irunaryassignment();
		}else if(instructiontype == IRCOPY){
			return ircopy();
		}else if(instructiontype == IRUNCONDITIONALJUMP){
			return irunconditionaljump();
		}else if(instructiontype == IRCONDITIONALJUMP){
			return irconditionaljump();
		}else if(instructiontype == IRCALL){
			return ircall();
		}else if(instructiontype == IRRETURN){
			return irreturn();
		}else if(instructiontype == IRINDEXEDASSIGNTO){
			return irindexedassignto();
		}else if(instructiontype == IRINDEXEDASSIGNFROM){
			return irindexedassignfrom();
		}else if(instructiontype == IRNEW){
			return irnew();
		}else if(instructiontype == IRNEWARRAY){
			return irnewarray();
		}else if(instructiontype == IRLENGTH){
			return irlength();
		}else if(instructiontype == IRPARAM){
			return irparam();
		}else if(instructiontype == IRLABEL){
			return irlabel();
		}else if(instructiontype == IRPRINT){
			return irprint();
		}else if(instructiontype == IREXIT){
			return "";
		}else{
			return "NO IR INSTRUCTION TYPE ERROR";
		}
	}
	
	public void addLabel(Identifier id, String label){
		labelmap.put(id.s, label);
	}
	
	public void fixLabels(){
		if(labelmap.containsKey(argument1)){
			argument1 = labelmap.get(argument1);
		}
		if(labelmap.containsKey(argument2)){
			argument2 = labelmap.get(argument2);
		}
		if(labelmap.containsKey(result)){
			result = labelmap.get(result);
		}
	}
	
}