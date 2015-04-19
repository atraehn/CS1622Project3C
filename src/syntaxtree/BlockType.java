package syntaxtree;

import user.irgenerator;
import visitor.IRVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class BlockType extends Type {

	public void accept(Visitor v) {
	}

	public Type accept(TypeVisitor v) {
		return null;
	}
	
	public String accept(IRVisitor v) {
		return "";
  }
}
