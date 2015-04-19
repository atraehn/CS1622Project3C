package syntaxtree;
import user.irgenerator;
import visitor.IRVisitor;
import visitor.Visitor;
import visitor.TypeVisitor;

public class VarDecl {
  public Type t;
  public Identifier i;

  public int linenum;
  public int colnum;

  public VarDecl(Type at, Identifier ai, int l, int c){
	    t=at; i=ai;
	  linenum = l;
	  colnum = c;
  }
  public VarDecl(Type at, Identifier ai) {
    t=at; i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
  
  public Object accept(IRVisitor v) {
	  return v.visit(this);
}
}
