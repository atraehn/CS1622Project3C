package syntaxtree;
import user.irgenerator;
import visitor.IRVisitor;
import visitor.Visitor;
import visitor.TypeVisitor;

public class IdentifierExp extends Exp {
  public String s;
  public int linenum;
  public int colnum;

  public IdentifierExp(String as, int l, int c){
	    s=as;
	  linenum = l;
	  colnum = c;
  }
  public IdentifierExp(String as) { 
    s=as;
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
