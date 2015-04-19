package user;

import java.util.ArrayList;
import java.util.HashMap;

import syntaxtree.*;

public class node{

	Identifier identifier;
	HashMap<Identifier, Type> symboltable;
	ArrayList<node> children;
	node parent;
	
	public node(Identifier name){
		identifier = name;
		symboltable = new HashMap<Identifier, Type>();
		children = new ArrayList<node>();
		parent = null;
	}
	
	public node(node parent){
		symboltable = new HashMap<Identifier, Type>();
		children = new ArrayList<node>();
		this.parent = parent;
	}
	
	@SuppressWarnings("unchecked")
	public void add(node child){
		children.add(child);
		child.parent = (node) this;
	}
	
	/*public void addNew(){
		node<Identifier, Type> newNode = new node<Identifier, Type>(this);
		children.add(newNode);
	}*/
	
	public void addSymbol(Identifier i, Type t){
		symboltable.put(i, t);
	}
	
	public boolean contains(Identifier i){
		return symboltable.containsKey(i);
	}
	
	public String toString(){
		
		String print = "";
		print += "\n" + identifier.toString();
		print += "\n" + symboltable.toString();
		for(node n: children){
			print += n.toString();
		}
		
		return print;
	}
	
}
