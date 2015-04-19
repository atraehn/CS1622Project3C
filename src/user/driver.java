package user;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import java_cup.runtime.Symbol;
import syntaxtree.*;
import visitor.*;

public class driver {
	
	public static void main(String[]args) throws Exception{
	
		Program output;
		
		if(args.length != 1){System.exit(0);}
		File inputFile = new File(args[0]);
		
		parser Parser = new parser(new lexer(new FileReader(inputFile)));		
		
		Symbol parse_action = Parser.parse();
		output = (Program) parse_action.value;
		
		//PrettyPrintVisitor printer = new PrettyPrintVisitor();
		//printer.visit(output);
		
		symboltable symbols = new symboltable();
		symbols.visit(output);
		
		//System.out.println("\nSymbol Table Tree stuff.");
		//System.out.print(symbols.root);
		
		idchecker checker = new idchecker(symbols.root);
		checker.visit(output);
		
		TypeVisitor typer = new TypeDepthFirstVisitor();
		/* Pass typer our symbol table we've constructed?? */
		typer.visit(output);
		
		irgenerator ir = new irgenerator();
		ir.visit(output);
		ir.print();
		
		System.out.println("\n\n");
		
		mipsgenerator mips = new mipsgenerator(ir.IR);
		
		File footer = new File("runtime.asm");
		PrintWriter writer = new PrintWriter("mipsout.asm", "UTF-8");
		writer.print(mips.print());
		writer.print(new Scanner(footer).useDelimiter("\\Z").next());
		writer.close();
		
		System.out.println(mips.print());
	}
}
