package syntaxtree;
import user.irgenerator;
import visitor.IRVisitor;
import visitor.Visitor;
import visitor.TypeVisitor;

public class This extends Exp {
	  public int linenum;
	  public int colnum;

	  public This(int l, int c){
		  linenum = l;
		  colnum = c;
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
