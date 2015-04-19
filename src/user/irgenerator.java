package user;

import user.irinstruction;
import java.util.ArrayList;

import syntaxtree.*;

public class irgenerator implements visitor.IRVisitor{
	int labelnumber = 0;
	int temporarynumber = 0;
	public ArrayList<irinstruction> IR = new ArrayList<irinstruction>();
	
	public void print(){
		for(irinstruction i: IR){
			System.out.println(i);
		}
	}

	public irgenerator(){

	}

	// MainClass m;
	// ClassDeclList cl;
	public Object visit(Program n) {
		n.m.accept(this);
		for ( int i = 0; i < n.cl.size(); i++ ) {
			n.cl.elementAt(i).accept(this);
		}
		return "";
	}

	// Identifier i1,i2;
	// Statement s;
	public Object visit(MainClass n) {
		IR.add(new irinstruction(irinstruction.IRLABEL, n.i1));
		labelnumber++;
		
		n.i1.accept(this);
		n.i2.accept(this);
		n.s.accept(this);
		IR.add(new irinstruction(irinstruction.IREXIT));
		return "";
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Object visit(ClassDeclSimple n) {
		IR.add(new irinstruction(irinstruction.IRLABEL, n.i));
		labelnumber++;
		
		n.i.accept(this);
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.elementAt(i).accept(this);
			if ( i+1 < n.vl.size() ) {  }
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			n.ml.elementAt(i).accept(this);
		}
		return "";
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Object visit(ClassDeclExtends n) {
		IR.add(new irinstruction(irinstruction.IRLABEL, n));
		labelnumber++;
		
		n.i.accept(this);
		n.j.accept(this);
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.elementAt(i).accept(this);
			if ( i+1 < n.vl.size() ) { }
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			n.ml.elementAt(i).accept(this);
		}
		return "";
	}

	// Type t;
	// Identifier i;
	public Object visit(VarDecl n) {
		n.t.accept(this);
		n.i.accept(this);
		return "";
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Object visit(MethodDecl n) {
		irinstruction.labelmap.put(n.i.s, "LABEL"+labelnumber);
		IR.add(new irinstruction(irinstruction.IRLABEL, n));
		
		n.t.accept(this);
		n.i.accept(this);
		for ( int i = 0; i < n.fl.size(); i++ ) {
			n.fl.elementAt(i).accept(this);
			if (i+1 < n.fl.size()) {}
		}
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.elementAt(i).accept(this);
		}
		for ( int i = 0; i < n.sl.size(); i++ ) {
			n.sl.elementAt(i).accept(this);
			if ( i < n.sl.size() ) { }
		}
		String sres;
		Object res = n.e.accept(this);
		if(res instanceof IdentifierExp){
			sres = ((IdentifierExp)res).s;
			IR.add(new irinstruction(irinstruction.IRRETURN, new Identifier(sres)));
		}else{
			IR.add(new irinstruction(irinstruction.IRRETURN, res));
		}
		return res;
	}

	// Type t;
	// Identifier i;
	public Object visit(Formal n) {
		n.t.accept(this);
		n.i.accept(this);
		return n.i;
	}

	public Object visit(IntArrayType n) {
		return "";
	}

	public Object visit(BooleanType n) {
		return "";
	}

	public Object visit(IntegerType n) {
		return "";
	}

	// String s;
	public Object visit(IdentifierType n) {
		return n.s;
	}

	// StatementList sl;
	public Object visit(Block n) {
		for ( int i = 0; i < n.sl.size(); i++ ) {
			n.sl.elementAt(i).accept(this);
		}
		return "BLOCK";
	}

	// Exp e;
	// Statement s1,s2;
	public Object visit(If n) {
		int elselabel = labelnumber;
		labelnumber++;
		int iflabel = labelnumber;
		labelnumber++;
		Identifier ifi = new Identifier("LABEL"+iflabel);
		Identifier elsei = new Identifier("LABEL"+elselabel);
		
		Object arg1 = n.e.accept(this);
		IR.add(new irinstruction(irinstruction.IRCONDITIONALJUMP, arg1, elsei));
		n.s1.accept(this);
		IR.add(new irinstruction(irinstruction.IRUNCONDITIONALJUMP, ifi));
		IR.add(new irinstruction(irinstruction.IRLABEL, elsei));
		labelnumber--;
		n.s2.accept(this);
		IR.add(new irinstruction(irinstruction.IRLABEL, ifi));
		labelnumber--;
		return "IFCASE";
	}

	// Exp e;
	// Statement s;
	public Object visit(While n) {
		int labelloop = labelnumber;
		labelnumber++;
		int labelbreak = labelnumber;
		labelnumber++;
		Identifier loopi = new Identifier("LABEL"+labelloop);
		Identifier breaki = new Identifier("LABEL"+labelbreak);
		IR.add(new irinstruction(irinstruction.IRLABEL, loopi));
		Object arg1 = n.e.accept(this);
		IR.add(new irinstruction(irinstruction.IRCONDITIONALJUMP, arg1, breaki));
		labelnumber--;
		n.s.accept(this);
		IR.add(new irinstruction(irinstruction.IRUNCONDITIONALJUMP, loopi));
		labelnumber--;
		IR.add(new irinstruction(irinstruction.IRLABEL, breaki));
		return "WHILECASE";
	}

	// Exp e;
	public Object visit(Print n) {
		Object arg = n.e.accept(this);
		IR.add(new irinstruction(irinstruction.IRPRINT, arg));
		return arg;
	}

	// Identifier i;
	// Exp e;
	public Object visit(Assign n) {	  
		int type = irinstruction.IRUNARYASSIGNMENT;
		Object res = n.i.accept(this);
		Object arg1 = n.e.accept(this);
		
		IR.add(new irinstruction(type, res, arg1));
		return res;
	}

	// Identifier i;
	// Exp e1,e2;
	public Object visit(ArrayAssign n) {
		int type = irinstruction.IRINDEXEDASSIGNFROM;
		Object arg1 = n.i.accept(this);
		Object arg2 = n.e1.accept(this);
		Object res = n.e2.accept(this);
		
		IR.add(new irinstruction(type, arg1, arg2, res));
		return arg1;
	}

	// Exp e1,e2;
	public Object visit(And n) {
		int type = irinstruction.IRASSIGNMENT;
		Object e1result = n.e1.accept(this);
		Object e2result = n.e2.accept(this);
		Object eval;
		
		if(e1result instanceof True || e2result instanceof True){
			eval = new True();
		}else{
			eval = new False();
		}
		
		IR.add(new irinstruction(type, n, e1result, e2result));
		return eval;//NEEDSFIXED
	}

	// Exp e1,e2;
	public Object visit(LessThan n) {
		int type = irinstruction.IRASSIGNMENT;
		Object e1result = n.e1.accept(this);
		Object e2result = n.e2.accept(this);
		Object eval;
		if(e1result instanceof IntegerLiteral && e2result instanceof IntegerLiteral){
			IntegerLiteral e1r = (IntegerLiteral) e1result;
			IntegerLiteral e2r = (IntegerLiteral) e2result;
			if(e1r.i < e2r.i) eval = new True();
			else eval = new False();
		}else{
			eval = new False();
		}
		IR.add(new irinstruction(type, n, e1result, e2result));
		return eval;
	}

	// Exp e1,e2;
	public Object visit(Plus n) {
		int type = irinstruction.IRASSIGNMENT;
		Identifier temp = new Identifier("t"+temporarynumber);
		temporarynumber++;
		Object e1result = n.e1.accept(this);
		Object e2result = n.e2.accept(this);
//		IntegerLiteral result;
//		if(e1result instanceof IntegerLiteral && e2result instanceof IntegerLiteral){
//			result = new IntegerLiteral(((IntegerLiteral)e1result).i + ((IntegerLiteral)e2result).i);
//		}else{
//			result = null;
//		}
		
		IR.add(new irinstruction(type, n, e1result, e2result, temp));
		return temp;
	}

	// Exp e1,e2;
	public Object visit(Minus n) {
		int type = irinstruction.IRASSIGNMENT;
		Identifier temp = new Identifier("t"+temporarynumber);
		temporarynumber++;
		Object e1result = n.e1.accept(this);
		Object e2result = n.e2.accept(this);
		IntegerLiteral result;
		if(e1result instanceof IntegerLiteral && e2result instanceof IntegerLiteral){
			result = new IntegerLiteral(((IntegerLiteral)e1result).i - ((IntegerLiteral)e2result).i);
		}else{
			result = null;
		}
		
		IR.add(new irinstruction(type, n, temp, e1result, e2result));
		return result;
	}

	// Exp e1,e2;
	public Object visit(Times n) {
		int type = irinstruction.IRASSIGNMENT;
		Identifier temp = new Identifier("t"+temporarynumber);
		temporarynumber++;
		Object e1result = n.e1.accept(this);
		Object e2result = n.e2.accept(this);
		IntegerLiteral result;
		if(e1result instanceof IntegerLiteral && e2result instanceof IntegerLiteral){
			result = new IntegerLiteral(((IntegerLiteral)e1result).i * ((IntegerLiteral)e2result).i);
		}else{
			result = null;
		}
		
		IR.add(new irinstruction(type, n, temp, e1result, e2result));
		return result;
	}

	// Exp e1,e2;
	public Object visit(ArrayLookup n) {
		int type = irinstruction.IRINDEXEDASSIGNTO;
		Object e1result = n.e1.accept(this);
		Object e2result = n.e2.accept(this);
		IR.add(new irinstruction(type, e1result, e2result));
		return n;
		
	}

	// Exp e;
	public Object visit(ArrayLength n) {
		int type = irinstruction.IRLENGTH;
		Object eresult = n.e.accept(this);
		
		IR.add(new irinstruction(type, eresult));
		return eresult;
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public Object visit(Call n) {
		Object arg0 = n.e.accept(this);
		Object arg1 = n.i.accept(this);
		ArrayList<Object> paramargs = new ArrayList<Object>();
		
		Identifier temp = new Identifier("t"+temporarynumber);
		temporarynumber++;
		
		paramargs.add(arg0);
		for ( int i = 0; i < n.el.size(); i++ ) {
			paramargs.add(n.el.elementAt(i).accept(this));
			if ( i+1 < n.el.size() ) {}
		}
		Integer arg2 = n.el.size()+1;
		for(Object i: paramargs){
				IR.add(new irinstruction(irinstruction.IRPARAM, i));
		}
		IR.add(new irinstruction(irinstruction.IRCALL, temp, n, arg2));
		return n;
	}

	// int i;
	// int linenum;
	// int colnum;
	public Object visit(IntegerLiteral n) {
		int type = irinstruction.IRUNARYASSIGNMENT;
		Identifier temp = new Identifier("t"+temporarynumber);
		temporarynumber++;
		IR.add(new irinstruction(type, temp, (Integer)n.i));
		return temp;
	}

	public Object visit(True n) {
		return "1";
	}

	public Object visit(False n) {
		return "0";
	}

	// String s;
	public Object visit(IdentifierExp n) {
		return n;
	}

	public Object visit(This n) {
		return "this";
	}

	// Exp e;
	public Object visit(NewArray n) {
		int type = irinstruction.IRNEWARRAY;
		Object arg = n.e.accept(this);
		IR.add(new irinstruction(type, "INT", arg));
		return arg;
	}

	// Identifier i;
	public Object visit(NewObject n) {
		int type = irinstruction.IRNEW;
		Object res = new Identifier("t"+temporarynumber);
		temporarynumber++;
		Object arg = n.i.accept(this);
		IR.add(new irinstruction(type, res, n.i));
		return n.i;
	}

	// Exp e;
	public Object visit(Not n) {
		int type = irinstruction.IRUNARYASSIGNMENT;
		Object res = new Identifier("t"+temporarynumber);
		temporarynumber++;
		Object arg = n.e.accept(this);
		Object bool = null;
		if(arg instanceof True){
			bool = new False();
		}else if(arg instanceof False){
			bool = new True();
		}
		IR.add(new irinstruction(type, n, arg, res));
		return bool;
	}

	// String s;
	public Object visit(Identifier n) {
		return n;
	}

}
