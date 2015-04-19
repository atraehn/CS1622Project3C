package syntaxtree;
import user.irgenerator;
import visitor.IRVisitor;
import visitor.Visitor;
import visitor.TypeVisitor;

public class Identifier {
  public String s;

  public int linenum;
  public int colnum;

  public Identifier(String as, int l, int c){
	    s=as;
	  linenum = l;
	  colnum = c;
  }
  public Identifier(String as) { 
    s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }

  public String toString(){
    return s;
  }
  
  public Object accept(IRVisitor v) {
	  return v.visit(this);
}
}
