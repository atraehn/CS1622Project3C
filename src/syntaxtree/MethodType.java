package syntaxtree;

import user.irgenerator;
import visitor.IRVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class MethodType extends Type {

	public Type t;
	public FormalList fl;
	
	public MethodType(Type t, FormalList f1){
		this.t = t;
		this.fl = f1;
	}
	
	public void accept(Visitor v) {
	}

	public Type accept(TypeVisitor v) {
		return null;
	}

	public String accept(IRVisitor v) {
		return "";
  }
}
