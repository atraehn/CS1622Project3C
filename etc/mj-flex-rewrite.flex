import java_cup.runtime.*;

%%

%public
%class lexer
%implements sym

%unicode

%line
%column

%cup
%cupdebug

%{
  StringBuilder string = new StringBuilder();
  
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

  /** 
   * assumes correct representation of a long value for 
   * specified radix in scanner buffer from <code>start</code> 
   * to <code>end</code> 
   */
  private long parseLong(int start, int end, int radix) {
    long result = 0;
    long digit;

    for (int i = start; i < end; i++) {
      digit  = Character.digit(yycharat(i),radix);
      result*= radix;
      result+= digit;
    }

    return result;
  }
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | 
          {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"

/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*

%%

<YYINITIAL> {

  /* keywords */
  "int"        { return symbol(sym.INT); }
  "boolean"    { return symbol(sym.BOOL); }
  "if"         { return symbol(sym.IF); }
  "else"       { return symbol(sym.ELSE); }
  "while"      { return symbol(sym.WHILE); }
  "System.out.println" { return symbol(sym.PRINT); }
  "return"     { return symbol(sym.RETURN); }
  "static"     { return symbol(sym.STATIC); }
  "void"       { return symbol(sym.VOID); }
  "main"       { return symbol(sym.MAIN); }
  "String"     { return symbol(sym.KSTRING); }
  "length"     { return symbol(sym.LEN); }
  "new"        { return symbol(sym.NEW); }
  "this"       { return symbol(sym.THIS); }
  "public"     { return symbol(sym.PUBLIC); }
  "class"      { return symbol(sym.CLASS); }
  "extends"    { return symbol(sym.EXTENDS); }
  
  /* boolean literals */
  "true"                         { return symbol(sym.TRUE); }
  "false"                        { return symbol(sym.FALSE); }
    
  /* separators */
  "{" 	       {return symbol(sym.LBRACE); }
  "}" 	       {return symbol(sym.RBRACE); }
  "[" 	       { return symbol(sym.LBRACK); }
  "]" 	       { return symbol(sym.RBRACK); }
  "(" 	       { return symbol(sym.LPAR); }
  ")" 	       { return symbol(sym.RPAR); }
  
  /* operators */
  "&&"         { return symbol(sym.AND); }
  "<" 	       { return symbol(sym.LT); }
  "+" 	       { return symbol(sym.PLUS); }
  "-" 	       { return symbol(sym.MINUS); }
  "*" 	       { return symbol(sym.TIMES); }
  "=" 	       { return symbol(sym.EQUALS); }
  "." 	       { return symbol(sym.DOT); }
  "," 	       { return symbol(sym.COMMA); }
  ";" 	       { return symbol(sym.SEMICOL); }
  "!" 	       { return symbol(sym.EXCL); }
  
  /* numeric literals */

  /* This is matched together with the minus, because the number is too big to 
     be represented by a positive integer. */
  "-2147483648"                  { return symbol(sym.NUMBER, new Integer(Integer.MIN_VALUE)); }
  
  {DecIntegerLiteral}            { return symbol(sym.NUMBER, new Integer(yytext())); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return symbol(sym.IDENTIFIER, yytext()); }  
}