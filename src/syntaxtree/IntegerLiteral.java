package syntaxtree;
import user.irgenerator;
import visitor.IRVisitor;
import visitor.Visitor;
import visitor.TypeVisitor;

public class IntegerLiteral extends Exp {
  public int i;
  public int linenum;
  public int colnum;

  public IntegerLiteral(int ai, int l, int c){
	  i = ai;
	  linenum = l;
	  colnum = c;
  }
  public IntegerLiteral(int ai) {
    i=ai;
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
