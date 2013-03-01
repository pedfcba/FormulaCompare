lexer grammar formula;

options 
{
  language = Java;
}


VAR:
 'a'..'z' 
| 'A'..'Z' ;

DIGIT: ('1'..'9') ('0'..'9')*;
NUMBERS: (DIGIT |'0') ('.' ( '0'..'9' )+ )?;


TIMES: '\\times' | '*' ;
DIVID: '\\';
LIMITS: '\\limits';
SIGMA: '\\sum';
PLUS: '+';
MINUS: '-';
EQUAL: '=';
UPMARK: '^';
DOWNMARK: '_';

WS: (' '|'\n'|'\r')+ {$channel=HIDDEN;} ;