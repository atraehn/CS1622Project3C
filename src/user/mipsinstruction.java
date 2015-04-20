package user;
import syntaxtree.*;

import java.util.HashMap;
import java.util.Stack;

import syntaxtree.IntegerLiteral;

public class mipsinstruction {

	String mips;
	Object res;
	Object arg1;
	Object arg2;
	Object op;
	
	static HashMap<String, String> tregisters = new HashMap<String, String>();
	static HashMap<String, String> aregisters = new HashMap<String, String>();
	static HashMap<String, HashMap<String, String>> formals = new HashMap<String, HashMap<String, String>>();
	static Stack<String> current_class = new Stack<String>();
	static Stack<HashMap<String, String>> regstack = new Stack<HashMap<String, String>>();
	
	@SuppressWarnings("static-access")
	public mipsinstruction(irinstruction ir){
		
		mips = "";
		res = ir.result;
		arg1 = ir.argument1;
		arg2 = ir.argument2;
		op = ir.operator;
		
		String sres;
		String sarg1;
		String sarg2;
		String sop;

		if(arg1 instanceof Identifier){
			sarg1 = ((Identifier)arg1).s;
		}else if(arg1 instanceof IntegerLiteral){
			sarg1 = ((IntegerLiteral)arg1).toString();
		}else if(arg1 instanceof IdentifierExp){
			sarg1 = ((IdentifierExp)arg1).toString();
		}else if(arg1 instanceof Integer){
			sarg1 = ((Integer)arg1).toString();
		}else if(arg1 instanceof String){
			sarg1 = (String)arg1;
		}else if(arg1 instanceof Call && ir.instructiontype == ir.IRCALL){
			sarg1 = ((Call)arg1).i.s;
		}else if(arg1 instanceof Call){
			sarg1 = "$v0";
		}else{
			sarg1 = "ARG1 TYPE UNDEF";
		}

		if(arg2 instanceof Identifier){
			sarg2 = ((Identifier)arg2).s;
		}else if(arg2 instanceof IntegerLiteral){
			sarg2 = ((IntegerLiteral)arg2).toString();
		}else if(arg2 instanceof IdentifierExp){
			sarg2 = ((IdentifierExp)arg2).toString();
		}else if(arg2 instanceof Integer){
			sarg2 = ((Integer)arg2).toString();
		}else if(arg2 instanceof String){
			sarg2 = (String)arg2;
		}else if(arg2 instanceof Call && ir.instructiontype == ir.IRCALL){
			sarg2 = ((Call)arg2).i.s;
		}else if(arg2 instanceof Call){
			sarg2 = "$v0";
		}else{
			sarg2 = "ARG2 TYPE UNDEF";
		}
		
		if(res instanceof Identifier){
			sres = ((Identifier)res).s;
		}else if(res instanceof IntegerLiteral){
			sres = ((IntegerLiteral)res).toString();
		}else if(res instanceof IdentifierExp){
			sres = ((IdentifierExp)res).toString();
		}else if(res instanceof Integer){
			sres = ((Integer)res).toString();
		}else if(res instanceof String){
			sres = (String)res;
		}else if(res instanceof Call && ir.instructiontype == ir.IRCALL){
			sres = ((Call)res).i.s;
		}else if(res instanceof Call){
			sres = "$v0";
		}else{
			sres = "RES TYPE UNDEF";
		}

		if(arg1 instanceof Identifier){
			boolean flag = false;
			if(!current_class.isEmpty() && formals.containsKey(current_class.peek())){
				HashMap<String, String> forms = formals.get(current_class.peek());
				System.out.println(forms.keySet());
				System.out.println(((Identifier)arg1).s);
				if(forms.containsKey(((Identifier)arg1).s)){
					System.out.println("FLAG");
					sarg1 = forms.get(((Identifier)arg1).s);
					flag = true;
				}
			}
			if(flag){
			}else if(aregisters.containsKey(((Identifier)arg1).s)){
				sarg1 = aregisters.get(((Identifier)arg1).s);
			}else if(tregisters.containsKey(((Identifier)arg1).s)){
				sarg1 = tregisters.get(((Identifier)arg1).s);
			}else{
				tregisters.put(((Identifier)arg1).s, "$t"+(tregisters.size()));
				sarg1 = tregisters.get(((Identifier)arg1).s);
			}
		}else if(arg1 instanceof IdentifierExp){
			boolean flag = false;
			if(!current_class.isEmpty() && formals.containsKey(current_class.peek())){
				HashMap<String, String> forms = formals.get(current_class.peek());
				System.out.println(forms.keySet());
				System.out.println(((IdentifierExp)arg1).s);
				if(forms.containsKey(((IdentifierExp)arg1).s)){
					System.out.println("FLAG");
					sarg1 = forms.get(((IdentifierExp)arg1).s);
					flag = true;
				}
			}
			if(flag){
			}else if(aregisters.containsKey(((IdentifierExp)arg1).s)){
				sarg1 = aregisters.get(((IdentifierExp)arg1).s);
			}else if(tregisters.containsKey(((IdentifierExp)arg1).s)){
				sarg1 = tregisters.get(((IdentifierExp)arg1).s);
			}else{
				tregisters.put(((IdentifierExp)arg1).s, "$t"+(tregisters.size()));
				sarg1 = tregisters.get(((IdentifierExp)arg1).s);
			}
		}
		
		if(arg2 instanceof Identifier){
			boolean flag = false;
			if(!current_class.isEmpty() && formals.containsKey(current_class.peek())){
				HashMap<String, String> forms = formals.get(current_class.peek());
				if(forms.containsKey(((Identifier)arg2).s)){
					sarg2 = forms.get(((Identifier)arg2).s);
					flag = true;
				}
			}
			
			if(flag){
			}else if(aregisters.containsKey(((Identifier)arg2).s)){
				sarg2 = aregisters.get(((Identifier)arg2).s);
			}else if(tregisters.containsKey(((Identifier)arg2).s)){
				sarg2 = tregisters.get(((Identifier)arg2).s);
			}else{
				tregisters.put(((Identifier)arg2).s, "$t"+(tregisters.size()));
				sarg2 = tregisters.get(((Identifier)arg2).s);
			}
		}else if(arg2 instanceof IdentifierExp){
			boolean flag = false;
			if(!current_class.isEmpty() && formals.containsKey(current_class.peek())){
				HashMap<String, String> forms = formals.get(current_class.peek());
				if(forms.containsKey(((IdentifierExp)arg2).s)){
					sarg2 = forms.get(((IdentifierExp)arg2).s);
					flag = true;
				}
			}
			
			if(flag){
			}else if(aregisters.containsKey(((IdentifierExp)arg2).s)){
				sarg2 = aregisters.get(((IdentifierExp)arg2).s);
			}else if(tregisters.containsKey(((IdentifierExp)arg2).s)){
				sarg2 = tregisters.get(((IdentifierExp)arg2).s);
			}else{
				tregisters.put(((IdentifierExp)arg2).s, "$t"+(tregisters.size()));
				sarg2 = tregisters.get(((IdentifierExp)arg2).s);
			}
		}
		
		if(res instanceof Identifier){
			boolean flag = false;
			if(!current_class.isEmpty() && formals.containsKey(current_class.peek())){
				HashMap<String, String> forms = formals.get(current_class.peek());
				if(forms.containsKey(((Identifier)res).s)){
					sres = forms.get(((Identifier)res).s);
					flag = true;
				}
			}
			
			if(flag){
			}else if(aregisters.containsKey(((Identifier)res).s)){
				sres = aregisters.get(((Identifier)res).s);
			}else if(tregisters.containsKey(((Identifier)res).s)){
				sres = tregisters.get(((Identifier)res).s);
			}else{
				tregisters.put(((Identifier)res).s, "$t"+(tregisters.size()));
				sres = tregisters.get(((Identifier)res).s);
			}
		}else if(res instanceof IdentifierExp){
			boolean flag = false;
			if(!current_class.isEmpty() && formals.containsKey(current_class.peek())){
				HashMap<String, String> forms = formals.get(current_class.peek());
				if(forms.containsKey(((IdentifierExp)res).s)){
					sres = forms.get(((IdentifierExp)res).s);
					flag = true;
				}
			}
			if(flag){
			}else if(aregisters.containsKey(((IdentifierExp)res).s)){
				sres = aregisters.get(((IdentifierExp)res).s);
			}else if(tregisters.containsKey(((IdentifierExp)res).s)){
				sres = tregisters.get(((IdentifierExp)res).s);
			}else{
				tregisters.put(((IdentifierExp)res).s, "$t"+(tregisters.size()));
				sres = tregisters.get(((IdentifierExp)res).s);
			}
		}
		
		if(ir.instructiontype == ir.IRASSIGNMENT){
			if(op instanceof Plus){
				mips += "add " + sres + ", " + sarg1 + ", " + sarg2;
			}else if(op instanceof Minus){
				mips += "sub " + sres + ", " + sarg1 + ", " + sarg2;
			}else if(op instanceof Times){
				mips += "mult " + sres + ", " + sarg1 + ", " + sarg2;
			}else if(op instanceof LessThan){
				mips = "slt " + sres + ", " + sarg1 + ", " + sarg2;
			}else if(op instanceof And){
				mips = "slt " + sres + ", " + sarg1 + ", " + sarg2;
			}else if(ir.argument1 instanceof Assign){
				mips = "ASSIGN ASSIGNMENT";
			}else if(op == ""){
				mips = "NO OP IN ASSIGNMENT";
			}else if(op == null && arg2 != null){
				mips = "li " + sres + ", " + sarg2;
			}else if(op == null){
				mips = "NULL OP IN ASSIGNMENT";
			}else{
				mips = "ASSIGNMENT IR ERROR";
			}
		}else if(ir.instructiontype == ir.IRUNARYASSIGNMENT){
			if(op == ""){
				if(arg1 instanceof IntegerLiteral){
					mips = "li " + sres + ", " + sarg1;
				}else if(arg1 instanceof Integer){
						mips = "li " + sres + ", " + sarg1;
				}else{
					mips = "move " + sres + ", " + sarg1;
				}
			}else{
				mips = "NON-EMPTY UNARY OP";
			}
		}else if(ir.instructiontype == ir.IRCOPY){
			if(arg1 instanceof Integer)
				mips += "li " + ir.result + ", " + sarg1;
			else if(arg1 instanceof IntegerLiteral)
				mips += "li " + ir.result + ", " + sarg1;
			else if(arg1 instanceof Identifier){
				System.out.println("ERROR");
				mips += "move " + ir.result + ", " + sarg1;
			}else
				mips += "ERROR";
				
		}else if(ir.instructiontype == ir.IRUNCONDITIONALJUMP){
			mips += "j "+ir.result;
		}else if(ir.instructiontype == ir.IRCONDITIONALJUMP){
			mips += "beq "+sres+", $zero, "+ir.argument1;
		}else if(ir.instructiontype == ir.IRCALL){
			
			for(int i = 0; i < 10; i++){
				mips += "sw $t" + i + ", " + i*4 + "($sp)\n";
			}
			
			mips += "jal " + sarg1;
		
			for(int i = 0; i < 10; i++){
				mips += "\nlw $t" + i + ", " + i*4 + "($sp)";
			}
			
		}else if(ir.instructiontype == ir.IRRETURN){
			
			if(!regstack.isEmpty()){
				tregisters = regstack.pop();
			}else{
				mips+="STACK ERROR";
			}
			
			if(res.equals("")){
				mips += "jr $ra";
			}else{
				mips += "move $v0, " + sres;
				mips += "\njr $ra";
			}
			//if(!current_class.isEmpty()) current_class.pop();
		}else if(ir.instructiontype == ir.IRINDEXEDASSIGNTO){
			mips += "indexedassignto";
		}else if(ir.instructiontype == ir.IRINDEXEDASSIGNFROM){
			mips += "indexedassignfrom";
		}else if(ir.instructiontype == ir.IRNEW){
			mips += "move $a0, " + /*Size*/"$zero";
			mips += "\njal _new_object";
			mips += "\nmove " + sres + ", $v0";
		}else if(ir.instructiontype == ir.IRNEWARRAY){
			mips += "newarray";
		}else if(ir.instructiontype == ir.IRLENGTH){
			mips += "length";
		}else if(ir.instructiontype == ir.IRPARAM){
			
			aregisters.put(sarg1, "$a"+(aregisters.size()));
			mips = "move " +  aregisters.get(sarg1) +", " + sarg1;
			
			if(aregisters.size() > 4){
				mips += "\nARG OVERFLOW!!!";
			}
			
			
		}else if(ir.instructiontype == ir.IRLABEL){
			if(ir.argument1 instanceof MethodDecl){
				MethodDecl decl = (MethodDecl) arg1;
				current_class.push(decl.i.s);
				regstack.push(tregisters);
				tregisters = new HashMap<String, String>();
				mips += decl.i + ":";
			}else{
				mips += ir.argument1 + ":";
			}
		}else if(ir.instructiontype == ir.IRPRINT){
			mips += "move $a0, " + sarg1;
			mips += "\njal _system_out_println";
		}else if(ir.instructiontype == ir.IREXIT){
			mips += "j _system_exit";
		}else{
			/*	ERROR	*/
		}
	}
	
	public String toString(){
		return mips;
	}
	
}
