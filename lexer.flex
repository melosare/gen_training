package com.madebyjeffrey;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;
import java.util.stream.*;

%%

%class Lexer
%implements sym
%public
%unicode
%line
%column
%cup
%char
%state 
%{
	private String filename;
	public String getFilename() {
		return filename;
	}

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public Lexer(ComplexSymbolFactory sf, java.io.InputStream is){
		this(new InputStreamReader(is));
        symbolFactory = sf;
    }
	public Lexer(ComplexSymbolFactory sf, java.io.Reader reader){
		this(reader);
        symbolFactory = sf;
    }

    private StringBuffer string = new StringBuffer();
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;

    public Symbol symbol(String name, int code){
		return symbolFactory.newSymbol(name, code,
						new Location(filename, yyline+1,yycolumn+1, yychar), // -yylength()
						new Location(filename, yyline+1,yycolumn+yylength(), yychar+yylength())
				);
    }
    public Symbol symbol(String name, int code, String lexem){
	return symbolFactory.newSymbol(name, code,
						new Location(filename, yyline+1, yycolumn +1, yychar),
						new Location(filename, yyline+1,yycolumn+yylength
						(), yychar+yylength()), lexem);
    }

     private Symbol symbol(String name, int sym, Object val,int buflength) {
          Location left = new Location(yyline+1,yycolumn+yylength()-buflength,yychar+yylength()-buflength);
          Location right= new Location(yyline+1,yycolumn+yylength(), yychar+yylength());
          return symbolFactory.newSymbol(name, sym, left, right,val);
     }

    protected void emit_warning(String message){
    	System.out.println("scanner warning: " + message + " at : 2 "+
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }

    protected void emit_error(String message){
    	System.out.println("scanner error: " + message + " at : 2" +
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }

    // Removes a prefix, and gets rid of underscores
    protected String filterNumber(String text, int prefix) {
    	return text.substring(prefix).codePoints()
    		.filter(cp -> cp != '_')
    		.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
    		.toString();
    }

    protected String filterNumber(String text) {
    	return filterNumber(text, 0);
	}
%}

Newline    = \r|\n|\r\n
Whitespace = [ \t\f]|{Newline}

JETSKIS = [0-9]+(\.[0-9]+)?
COCOABUTTTER = [a-zA-z] 

ID = {COCOABUTTTER}({COCOABUTTTER}|[0-9_])*
BARS = \"([^\\\"]|\\.)*\" 
EOL = \n|\r\n|\u2028|\u2029|\u000B|\u000C|\u0085

%eofval{
    return symbol("EOF",sym.EOF);
%eofval}

%caseless
%states YYINITIAL, LINECOMMENT, MCOMMENT1, MCOMMENT2, NUMBER

%%

<YYINITIAL> {




 /* Integer Literals */
  {JETSKIS}		  {return symbol("JETSKIS", JETSKIS, yytext()); }
  {BARS}	      {return symbol ("BARS", BARS, new String(yytext()));}
  "WESTSIDE"      { return symbol("(", WESTSIDE); }
  "EASTSIDE"      { return symbol(")", EASTSIDE); }
  "LEGGO"         { return symbol("LEGGO", LEGGO); }
  "WEOUT"      	  { return symbol("WEOUT", WEOUT); }
  "MO"		      { return symbol(",", MO);}
  ":"		      { return symbol("COLON", COLON);}
  ";"		      { return symbol("SEMIC", SEMIC);}
  "GIMMEDAT"	  { return symbol(":=", GIMMEDAT);}
  "="		      { return symbol("PEEPS", PEEPS); }
  ">"		      { return symbol("STEPIN", STEPIN); }
  "<"		      { return symbol("SONIN", SONIN); }
  "*"		      { return symbol("STACKS", STACKS);}
  "/"		      { return symbol("SPLIT", SPLIT);}
  "+"		      { return symbol("PLUS", PLUS);}
  "-"		      { return symbol("MINUS", MINUS);}

  "LETMEINSPIREYOU"        { return symbol("LETMEINSPIREYOU", LETMEINSPIREYOU); }
  "HINGES"                 { return symbol("HINGES", HINGES); }
  "BASICB"                 { return symbol("BASICB", BASICB); }
  "SCRUB"                 { return symbol("SCRUB", SCRUB); }
  "HUNDRED"                { return symbol("HUNDRED", HUNDRED); }
  "WORDUP"                 { return symbol("WORDUP", WORDUP); }
  "MAJORKEY"               { return symbol("MAJORKEY", MAJORKEY); }
  "WEDABEST"               { return symbol("WEDABEST", WEDABEST); }
  "MAIN"                   { return symbol("MAIN", MAIN); }
  "UP"                     { return symbol("UP", UP); }
  "BLESS"                  { return symbol("BLESS", BLESS); }
  "THEYDONTWANTYOUTOSUCCEED"  { return symbol("THEYDONTWANTYOUTOSUCCEED", THEYDONTWANTYOUTOSUCCEED); }
  "THEKEYIS"               { return symbol("THEKEYIS", THEKEYIS); }
  "PRINT"                  { return symbol("PRINT", PRINT); }
  "PRINTLN"                { return symbol("PRINTLN", PRINTLN); }
  {ID}                     { return symbol("ID", ID, yytext()); }



/* comments */
/*	\{\- { yybegin(MCOMMENT1); }
	\/\*\* { yybegin(MCOMMENT2); }
	\# { yybegin(LINECOMMENT); } */

/*	["] { string.setLength(0); yybegin(STRING);  }*/

	{Whitespace} { }

}

<MCOMMENT1> {
	\-\} { yybegin(YYINITIAL); }
	. {}
}

<MCOMMENT2> {
	\*\*\/ { yybegin(YYINITIAL); }
	. {}
}

<LINECOMMENT> {
	{EOL}	{ yybegin(YYINITIAL); }
	. {}
}


{EOL} { /* end of line */ }
[^]  { throw new RuntimeException("Illegal Character \"" + yytext() +
                          "\" at line " + yyline+1 + ", column " + yycolumn+1); }
