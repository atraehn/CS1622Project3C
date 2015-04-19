package syntaxtree;
import visitor.IRVisitor;
import visitor.Visitor;
import visitor.TypeVisitor;

public abstract class Type {
  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
  public abstract Object accept(IRVisitor v);
}
