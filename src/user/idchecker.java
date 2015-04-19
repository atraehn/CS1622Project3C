package user;

import java.util.HashMap;
import java.util.Stack;


import java.util.TreeSet;

import java_cup.action_part;
import syntaxtree.*;


public class idchecker implements visitor.Visitor {

	//Stack<HashMap<Identifier, Type>> hashstack;
	node root;
	node environment;

	public idchecker(node symboltable){
		root = symboltable;
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

		if(!environment.symboltable.containsKey(n.i1)){
			System.out.println("\nUse of undefined identifier " + n.i1.s + " at line " + n.i1.linenum + ", character " + n.i1.colnum);
		}

		boolean flag = false;
		for(node i:environment.children){
			if(i.identifier.equals(n.i1)){
				flag = true;
				environment = i;
				break;
			}
		}

		if(!flag) System.out.println("\nDIDN'T FIND CHILD\n");

		n.i1.accept(this);
		n.i2.accept(this);
		n.s.accept(this);

		environment = environment.parent;

	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclSimple n) {
		if(!environment.symboltable.containsKey(n.i)){
			System.out.println("\nUse of undefined identifier " + n.i.s + " at line " + n.i.linenum + ", character " + n.i.colnum);
		}

		boolean flag = false;
		for(node i:environment.children){
			if(i.identifier.equals(n.i)){
				flag = true;
				environment = i;
				break;
			}
		}

		if(!flag) System.out.println("\nDIDN'T FIND CHILD\n");

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

		if(!environment.symboltable.containsKey(n.i)){
			System.out.println("\nUse of undefined identifier " + n.i.s + " at line " + n.i.linenum + ", character " + n.i.colnum);
		}

		boolean flag = false;
		for(node i:environment.children){
			if(i.identifier.equals(n.i)){
				flag = true;
				environment = i;
				break;
			}
		}

		if(!flag) System.out.println("\nDIDN'T FIND CHILD\n");

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

		if(!environment.symboltable.containsKey(n.i)){
			System.out.println("\nUse of undefined identifier " + n.i.s + " at line " + n.i.linenum + ", character " + n.i.colnum);
		}

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

		if(!environment.symboltable.containsKey(n.i)){
			System.out.println("\nUse of undefined identifier " + n.i.s + " at line " + n.i.linenum + ", character " + n.i.colnum);
		}

		boolean flag = false;
		for(node i:environment.children){
			if(i.identifier.equals(n.i)){
				flag = true;
				environment = i;
				break;
			}
		}

		if(!flag) System.out.println("\nDIDN'T FIND CHILD\n");

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

		boolean flag = false;
		for(Identifier i:environment.symboltable.keySet()){
			if(n.toString().equals(i.s)){
				flag = true;
			}
		}

		if(!flag) System.out.println("\nBlock not contained in Program");

		flag = false;
		for(node i:environment.children){

			if(i.identifier.s.equals(n.toString())){	
				flag = true;
				environment = i;
				break;
			}
		}

		if(!flag) System.out.println("\nDIDN'T FIND CHILD\n");

		for ( int i = 0; i < n.sl.size(); i++ ) {
			n.sl.elementAt(i).accept(this);
		}

		environment = environment.parent;

	}

	// Exp e;
	// Statement s1,s2;
	public void visit(If n) {
		typecheck10(n.e);
		n.e.accept(this);
		n.s1.accept(this);
		n.s2.accept(this);
	}

	// Exp e;
	// Statement s;
	public void visit(While n) {
		typecheck10(n.e);
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
		
		typecheck1(n);
		typecheck2(n.e);
		typecheck11(n);

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
		if(n.e1 instanceof IdentifierExp){
			typecheck8((IdentifierExp) n.e1);
		}else if(n.e1 instanceof True || n.e1 instanceof False){
		}else if(n.e1 instanceof LessThan || n.e1 instanceof And){
		}else{
			System.out.println("Attempt to use boolean operator && on non-boolean operands at line d, character d");
		}
		
		if(n.e2 instanceof IdentifierExp){
			typecheck8((IdentifierExp) n.e2);
		}else if(n.e2 instanceof True || n.e2 instanceof False){
		}else if(n.e1 instanceof LessThan || n.e1 instanceof And){
		}else{
			System.out.println("Attempt to use boolean operator && on non-boolean operands at line d, character d");
		}
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(LessThan n) {
		if(n.e1 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e1);
		}
		
		if(n.e2 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e2);
		}
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Plus n) {
		if(n.e1 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e1);
		}
		
		if(n.e2 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e2);
		}
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Minus n) {
		if(n.e1 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e1);
		}
		
		if(n.e2 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e2);
		}
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Times n) {
		if(n.e1 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e1);
		}
		
		if(n.e2 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e2);
		}
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(ArrayLookup n) {
		if(n.e1 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e1);
		}
		
		if(n.e2 instanceof IdentifierExp){
			typecheck3((IdentifierExp) n.e2);
		}
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e;
	public void visit(ArrayLength n) {
		if(n.e instanceof IdentifierExp){
			typecheck9((IdentifierExp) n.e);
		}else{
			System.out.println("Length property only applies to arrays, line d, character d");
		}
		n.e.accept(this);
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public void visit(Call n) {
		typecheck4(n);
		typecheck5(n);
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
		boolean flag = false;
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(n.s.equals(i.s) || n.equals(i)){
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(n.s.equals(z.s) || n.equals(z)){
						flag = true;
						break;
					}
				}
			}
		}

		if(!flag)	System.out.println("Use of an undefined identifier " + n.s + " at line " + n.linenum + ", character " + n.colnum);
	}

	/*
	 * If there is an attempt to assign to a class or method name, or the keyword this, output the message...
	 */
	public void typecheck1(Assign n){
		boolean flag = false;
		Type t = null;
		
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(n.i.s.equals(i.s) || n.equals(i)){
					t = runner.symboltable.get(i);
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(n.i.s.equals(z.s) || n.equals(z)){
						flag = true;
						t = i.symboltable.get(z);
						break;
					}
				}
			}
		}

		if(!flag){
			System.out.println("Assignment identifier " + n.i + " not found.");
		}else{
			if(t instanceof ClassType){
				System.out.println("Invalid l-value " + n.i + " is a class, at line " + n.i.linenum + ", character " + n.i.colnum);
			}else if(t instanceof MethodType){
				System.out.println("Invalid l-value " + n.i + " is a method, at line " + n.i.linenum + ", character " + n.i.colnum);
			}//else if THIS, wouldn't it just be a parsing error?
		}
	}
	
	/*
	 * If there is an attempt to assign from a class or method name, or the keyword this, output the message...
	 */
	public void typecheck2(Exp n){
		
		if(n instanceof IdentifierExp){
			IdentifierExp yo = (IdentifierExp) n;
			boolean flag = false;
			Type t = null;
			
			node runner = environment;
			while(true){	
				for(Identifier i: runner.symboltable.keySet()){
					if(yo.s.equals(i.s) || yo.equals(i.s)){
						t = runner.symboltable.get(i);
						flag = true;
						break;
					}
				}

				if(runner.parent == null) break;
				runner = runner.parent;	
			}

			if(!flag){
				for(node i:runner.children){
					for(Identifier z: i.symboltable.keySet()){
						if(yo.s.equals(z.s) || yo.equals(z.s)){
							flag = true;
							t = i.symboltable.get(z);
							break;
						}
					}
				}
			}

			if(!flag){
				System.out.println("Assignment identifier " + yo.s + " not found.");
			}else{
				if(t instanceof ClassType){
					System.out.println("Invalid r-value " + yo.s + " is a class, at line " + yo.linenum + ", character " + yo.colnum);
				}else if(t instanceof MethodType){
					System.out.println("Invalid r-value " + yo.s + " is a method, at line " + yo.linenum + ", character " + yo.colnum);
				}//else if THIS, wouldn't it just be a parsing error?
			}
		}
	}
	
	/*
	 * If an operator is applied to a class or method name, output the error.
	 */
	public void typecheck3(IdentifierExp yo){
		boolean flag = false;
		Type t = null;
		
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(yo.s.equals(i.s) || yo.equals(i.s)){
					t = runner.symboltable.get(i);
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(yo.s.equals(z.s) || yo.equals(z.s)){
						flag = true;
						t = i.symboltable.get(z);
						break;
					}
				}
			}
		}

		if(!flag){
			System.out.println("Assignment identifier " + yo.s + " not found.");
		}else{
			if(t instanceof ClassType){
				System.out.println("Invalid operands for " + yo.s + ", at line " + yo.linenum + ", character " + yo.colnum);
			}else if(t instanceof MethodType){
				System.out.println("Invalid operands for " + yo.s + ", at line " + yo.linenum + ", character " + yo.colnum);
			}else if(!(t instanceof IntegerType)){
				System.out.println("Non-integer operand for operator at line " + yo.linenum + ", character " + yo.colnum);
			}
		}
	}
	
	/*
	 * If an attempt to call something that isn't a method (class, variable, etc.) is made, output:
	 */
	public void typecheck4(Call n){
		boolean flag = false;
		Type t = null;
		
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(n.i.s.equals(i.s) || n.i.equals(i)){
					t = runner.symboltable.get(i);
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(n.i.s.equals(z.s) || n.i.equals(z)){
						flag = true;
						t = i.symboltable.get(z);
						break;
					}
				}
			}
		}

		if(!flag){
			System.out.println("Assignment identifier " + n.i + " not found.");
		}else{
			if(!(t instanceof MethodType)){
				System.out.println("Attempt to call a non-method at line " + n.i.linenum + ", character " + n.i.colnum);
			}
		}
	}
	
	/*
	 * If a method is called with the wrong number of arguments
	 */
	/*
	 * If a method is called with the wrong type of argument
	 */
	public void typecheck5(Call n){
		boolean flag = false;
		Type t = null;
		
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(n.i.s.equals(i.s) || n.i.equals(i)){
					t = runner.symboltable.get(i);
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(n.i.s.equals(z.s) || n.i.equals(z)){
						flag = true;
						t = i.symboltable.get(z);
						break;
					}
				}
			}
		}

		if(!flag){
			System.out.println("Assignment identifier " + n.i + " not found.");
		}else{
			if(t instanceof MethodType){
				MethodType tm = (MethodType) t;
				if(tm.fl.size() != n.el.size()){
				System.out.println("Call of method " + n.i + " does not match its declared signature at line " + n.i.linenum + ", character " + n.i.colnum);
				}else{
					for(int i = 0; i < tm.fl.size(); i++){
						if(n.el.elementAt(i) instanceof IdentifierExp){
							Type explisttype = typecheck((IdentifierExp)n.el.elementAt(i));
							if(tm.fl.elementAt(i).t.equals(explisttype)){
								
							}else{
								System.out.println("Call of method " + n.i + " does not match its declared signature at line " + n.i.linenum + ", character " + n.i.colnum);
							}
						}else if(n.el.elementAt(i) instanceof IntegerLiteral){
							if(tm.fl.elementAt(i).t instanceof IntegerType){
								
							}else{
								System.out.println("Call of method " + n.i + " does not match its declared signature at line " + n.i.linenum + ", character " + n.i.colnum);
							}
						}else if(n.el.elementAt(i) instanceof True || n.el.elementAt(i) instanceof False){
							if(tm.fl.elementAt(i).t instanceof BooleanType){
								
							}else{
								System.out.println("Call of method " + n.i + " does not match its declared signature at line " + n.i.linenum + ", character " + n.i.colnum);
							}
						}else{
							//System.out.println("Parameter not an expression identifier in " + n.el.elementAt(i).toString());
						}
					}
				}
			}
		}
	}
	
	/*
	 * If you attempt to use a Boolean operator with non-boolean types (&&), output:
	 */
	public void typecheck8(IdentifierExp yo){
		boolean flag = false;
		Type t = null;
		
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(yo.s.equals(i.s) || yo.equals(i.s)){
					t = runner.symboltable.get(i);
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(yo.s.equals(z.s) || yo.equals(z.s)){
						flag = true;
						t = i.symboltable.get(z);
						break;
					}
				}
			}
		}

		if(!flag){
			System.out.println("Assignment identifier " + yo.s + " not found.");
		}else{
			if(t instanceof ClassType){
				System.out.println("Invalid operands for " + yo.s + ", at line d, character d");
			}else if(t instanceof MethodType){
				System.out.println("Invalid operands for " + yo.s + ", at line d, character d");
			}else if(!(t instanceof BooleanType)){
				System.out.println("Attempt to use boolean operator && on non-boolean operands at line " + yo.linenum + ", character " + yo.colnum);
			}else{
			}
		}
	}
	
	/*
	 * If you attempt to use a Boolean operator with non-boolean types (&&), output:
	 */
	public void typecheck9(IdentifierExp yo){
		boolean flag = false;
		Type t = null;
		
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(yo.s.equals(i.s) || yo.equals(i.s)){
					t = runner.symboltable.get(i);
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(yo.s.equals(z.s) || yo.equals(z.s)){
						flag = true;
						t = i.symboltable.get(z);
						break;
					}
				}
			}
		}

		if(!flag){
			System.out.println("Length identifier " + yo.s + " not found.");
		}else{
			if(t instanceof ClassType){
				System.out.println("Invalid operands for " + yo.s + ", at line " + yo.linenum + ", character " + yo.colnum);
			}else if(t instanceof MethodType){
				System.out.println("Invalid operands for " + yo.s + ", at line " + yo.linenum + ", character " + yo.colnum);
			}else if(!(t instanceof IntArrayType)){
				System.out.println("Length property only applies to arrays, line " + yo.linenum + ", character " + yo.colnum);
			}else{
			}
		}
	}
	
	/*
	 * If the condition of an if or while statement does not evaluate to a boolean, output...
	 */
	public void typecheck10(Exp e){
		if(e instanceof And){
		}else if(e instanceof LessThan){
		}else if(e instanceof True){
		}else if(e instanceof False){
		}else if(e instanceof IdentifierExp){
			Type t = typecheck((IdentifierExp) e);
			if(t instanceof BooleanType){
			}else{
				System.out.println("Non-boolean expression used as the condition of if/while statement at line d, character d.");
			}
		}else{
			System.out.println("Non-boolean expression used as the condition of if/while statement at line d, character d.");
		}
	}
	
	public void typecheck11(Assign a){
		Type id_t = typecheck(a.i);
		if(a.e instanceof IdentifierExp){
			Type e_t = typecheck((IdentifierExp)a.e);
			if(id_t instanceof IntegerType && e_t instanceof IntegerType){
			}else if(id_t instanceof BooleanType && e_t instanceof BooleanType){
			}else if(id_t instanceof IdentifierType && e_t instanceof IdentifierType){
				if(((IdentifierType)id_t).s == ((IdentifierType)e_t).s){	
				}else{
					IdentifierExp ae = (IdentifierExp)a.e;
					System.out.println("Type mismatch during assignment at line " + ae.linenum + ", character " + ae.colnum);
				}
			}else{
				IdentifierExp ae = (IdentifierExp)a.e;
				System.out.println("Type mismatch during assignment at line " + ae.linenum + ", character " + ae.colnum);
			}
		}else if(a.e instanceof Plus){
			if(id_t instanceof IntegerType){
			}else{
				System.out.println("Type mismatch during assignment at line d, character d");
			}
		}else if(a.e instanceof Minus){
			if(id_t instanceof IntegerType){
			}else{
				System.out.println("Type mismatch during assignment at line d, character d");
			}
		}else if(a.e instanceof Times){
			if(id_t instanceof IntegerType){
			}else{
				System.out.println("Type mismatch during assignment at line d, character d");
			}
		}else if(a.e instanceof And){
			if(id_t instanceof BooleanType){
			}else{
				System.out.println("Type mismatch during assignment at line d, character d");
			}
		}else if(a.e instanceof LessThan){
			if(id_t instanceof BooleanType){
			}else{
				System.out.println("Type mismatch during assignment at line d, character d");
			}
		}else if(a.e instanceof False){
			if(id_t instanceof BooleanType){
			}else{
				False e = (False) a.e;
				System.out.println("Type mismatch during assignment at line " + e.linenum + ", character " + e.colnum);
			}
		}else if(a.e instanceof True){
			if(id_t instanceof BooleanType){
			}else{
				True e = (True) a.e;
				System.out.println("Type mismatch during assignment at line " + e.linenum + ", character " + e.colnum);
			}
		}else{
			
		}
	}
	
	public Type typecheck(Identifier yo){
		boolean flag = false;
		Type t = null;
		
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(yo.s.equals(i.s) || yo.equals(i)){
					t = runner.symboltable.get(i);
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(yo.s.equals(z.s) || yo.equals(z)){
						flag = true;
						t = i.symboltable.get(z);
						break;
					}
				}
			}
		}

		if(!flag){
			return null;
		}else{
			return t;
		}
	}
	public Type typecheck(IdentifierExp e){
		boolean flag = false;
		Type t = null;
		
		node runner = environment;
		while(true){	
			for(Identifier i: runner.symboltable.keySet()){
				if(e.s.equals(i.s) || e.equals(i)){
					t = runner.symboltable.get(i);
					flag = true;
					break;
				}
			}

			if(runner.parent == null) break;
			runner = runner.parent;	
		}

		if(!flag){
			for(node i:runner.children){
				for(Identifier z: i.symboltable.keySet()){
					if(e.s.equals(z.s) || e.equals(z)){
						flag = true;
						t = i.symboltable.get(z);
						break;
					}
				}
			}
		}
		
		if(!flag){
			return null;
		}else{
			return t;
		}
	}
	
}