package user;

import java.util.HashMap;
import java.util.Stack;


import java.util.TreeSet;

import syntaxtree.*;


public class symboltable implements visitor.Visitor {

	//Stack<HashMap<Identifier, Type>> hashstack;
	node root;
	node environment;
	
	public symboltable(){
		root = new node(new Identifier("Program"));
		environment = root;
	}

	  // MainClass m;
	  // ClassDeclList cl;
	  public void visit(Program n) {
		  
		n.m.accept(this);
	    for ( int i = 0; i < n.cl.size(); i++ ) {
	        n.cl.elementAt(i).accept(this);
	    }
	  }
	  
	  // Identifier i1,i2;
	  // Statement s;
	  public void visit(MainClass n) {
		addSymbol(n);
		
		node newNode = new node(n.i1);
		environment.add(newNode);
		environment = newNode;

		addMainArg(n);
		
		n.i1.accept(this);
	    n.i2.accept(this);
	    n.s.accept(this);
	    
	    environment = environment.parent;
	    
	  }

	  // Identifier i;
	  // VarDeclList vl;
	  // MethodDeclList ml;
	  public void visit(ClassDeclSimple n) {
		addSymbol(n);

		node newNode = new node(n.i);
		environment.add(newNode);
		environment = newNode;

	    n.i.accept(this);
	    for ( int i = 0; i < n.vl.size(); i++ ) {
	        n.vl.elementAt(i).accept(this);
	        if ( i+1 < n.vl.size() ) {}
	    }
	    for ( int i = 0; i < n.ml.size(); i++ ) {
	        n.ml.elementAt(i).accept(this);
	    }
	    
	    environment = environment.parent;
	  }
	 
	  // Identifier i;
	  // Identifier j;
	  // VarDeclList vl;
	  // MethodDeclList ml;
	  public void visit(ClassDeclExtends n) {
		addSymbol(n);
		
		node newNode = new node(n.i);
		environment.add(newNode);
		environment = newNode;
		
	    n.i.accept(this);
	    n.j.accept(this);
	    for ( int i = 0; i < n.vl.size(); i++ ) {
	        n.vl.elementAt(i).accept(this);
	        if ( i+1 < n.vl.size() ) { }
	    }
	    for ( int i = 0; i < n.ml.size(); i++ ) {
	        n.ml.elementAt(i).accept(this);
	    }
	    
	    environment = environment.parent;
	  }

	  // Type t;
	  // Identifier i;
	  public void visit(VarDecl n) {
		addSymbol(n);
		n.t.accept(this);
	    n.i.accept(this);
	  }

	  // Type t;
	  // Identifier i;
	  // FormalList fl;
	  // VarDeclList vl;
	  // StatementList sl;
	  // Exp e;
	  public void visit(MethodDecl n) {
		addSymbol(n);
		
		node newNode = new node(n.i);
		environment.add(newNode);
		environment = newNode;
		
	    n.t.accept(this);
	    n.i.accept(this);
	    for ( int i = 0; i < n.fl.size(); i++ ) {
	        n.fl.elementAt(i).accept(this);
	        if (i+1 < n.fl.size()) { }
	    }
	    for ( int i = 0; i < n.vl.size(); i++ ) {
	        n.vl.elementAt(i).accept(this);
	    }
	    for ( int i = 0; i < n.sl.size(); i++ ) {
	        n.sl.elementAt(i).accept(this);
	        if ( i < n.sl.size() ) { }
	    }
	    n.e.accept(this);
	    
	    environment = environment.parent;
	    
	  }

	  // Type t;
	  // Identifier i;
	  public void visit(Formal n) {
		addSymbol(n);
	    n.t.accept(this);
	    n.i.accept(this);
	  }

	  public void visit(IntArrayType n) {
	  }

	  public void visit(BooleanType n) {
	  }

	  public void visit(IntegerType n) {
	  }

	  // String s;
	  public void visit(IdentifierType n) {
	  }

	  // StatementList sl;
	  public void visit(Block n) {
		addSymbol(n);
		
		node newNode = new node(new Identifier(n.toString()));
		environment.add(newNode);
		environment = newNode;
		
	    for ( int i = 0; i < n.sl.size(); i++ ) {
	        n.sl.elementAt(i).accept(this);
	    }
	    
	    environment = environment.parent;
	    
	  }

	  // Exp e;
	  // Statement s1,s2;
	  public void visit(If n) {
	    n.e.accept(this);
	    n.s1.accept(this);
	    n.s2.accept(this);
	  }

	  // Exp e;
	  // Statement s;
	  public void visit(While n) {
	    n.e.accept(this);
	    n.s.accept(this);
	  }

	  // Exp e;
	  public void visit(Print n) {
	    n.e.accept(this);
	  }
	  
	  // Identifier i;
	  // Exp e;
	  public void visit(Assign n) {
	    n.i.accept(this);
	    n.e.accept(this);
	  }

	  // Identifier i;
	  // Exp e1,e2;
	  public void visit(ArrayAssign n) {
	    n.i.accept(this);
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(And n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(LessThan n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(Plus n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(Minus n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(Times n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(ArrayLookup n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e;
	  public void visit(ArrayLength n) {
	    n.e.accept(this);
	  }

	  // Exp e;
	  // Identifier i;
	  // ExpList el;
	  public void visit(Call n) {
	    n.e.accept(this);
	    n.i.accept(this);
	    for ( int i = 0; i < n.el.size(); i++ ) {
	        n.el.elementAt(i).accept(this);
	        if ( i+1 < n.el.size() ) { }
	    }
	  }

	  // int i;
	  public void visit(IntegerLiteral n) {
	  }

	  public void visit(True n) {
	  }

	  public void visit(False n) {
	  }

	  // String s;
	  public void visit(IdentifierExp n) {
	  }

	  public void visit(This n) {
	  }

	  // Exp e;
	  public void visit(NewArray n) {
	    n.e.accept(this);
	  }

	  // Identifier i;
	  public void visit(NewObject n) {
		  
	  }

	  // Exp e;
	  public void visit(Not n) {
	    n.e.accept(this);
	  }

	  // String s;
	  public void visit(Identifier n) {
		  		  
	  }
	  
	  public void addSymbol(VarDecl n){
		  if(environment.contains(n.i)){
			  System.err.println("ERROR: Multiply defined identifier "+n.i.toString()+"at line, column.");
		  }else{
			  environment.addSymbol(n.i, n.t);
		  }
	  }
	  
	  public void addSymbol(MethodDecl n){
		  if(environment.contains(n.i)){
			  System.err.println("ERROR: Multiply defined identifier "+n.i.toString()+"at line, column.");
		  }else{
			  environment.addSymbol(n.i, new MethodType(n.t, n.fl));
		  }
	  }
	  
	  public void addSymbol(ClassDeclSimple n){
		  if(environment.contains(n.i)){
			  System.err.println("ERROR: Multiply defined identifier "+n.i.toString()+"at line, column.");
		  }else{
			  environment.addSymbol(n.i, new ClassType());
		  }
	  }
	  
	  public void addSymbol(ClassDeclExtends n){
		  if(environment.contains(n.i)){
			  System.err.println("ERROR: Multiply defined identifier "+n.i.toString()+"at line, column.");
		  }else{
			  environment.addSymbol(n.i, new ClassType());
		  }
	  }
	  
	  public void addSymbol(MainClass n){
		  if(environment.contains(n.i1)){
			  System.err.println("ERROR: Multiply defined identifier "+n.i1.toString()+"at line, column.");
		  }else{
			  environment.addSymbol(n.i1, new ClassType());
		  }
	  }
	  
	  public void addMainArg(MainClass n){
		  if(environment.contains(n.i2)){
			  System.err.println("ERROR: Multiply defined identifier "+n.i2.toString()+"at line, column.");
		  }else{
			  environment.addSymbol(n.i2, new ClassType());
		  }
	  }
	  
	  public void addSymbol(Block n){
		  if(environment.contains(new Identifier(n.toString()))){
			  System.err.println("ERROR: Multiply defined identifier "+n.toString()+" at line, column.");
		  }else{
			  environment.addSymbol(new Identifier(n.toString()), new BlockType());
		  }
	  }
	  
	  public void addSymbol(Formal n){
		  if(environment.contains(n.i)){
			  System.err.println("ERROR: Multiply defined identifier "+n.toString()+" at line, column.");
		  }else{
			  environment.addSymbol(n.i, n.t);
		  }
	  }
	  
}