/*
 * The ANTLR grammar for the trio inputs
 */

grammar Trio;

system
    : description? 'components' ':' component+ tags?
    ;

description
    :   'system' ':' STRING
    ;

component
    :    ('-')? ID strength? requirements?
    ;

strength
    :   INTEGER
    ;

requirements
    :  'requires' expression
    ;

expression
    :   ID                                              # Reference
    |   left=expression 'and' right=expression          # Conjunction
    |   left=expression 'or' right=expression           # Disjunction
    |   '(' expression ')'                              # Brackets
    ;
    
tags
    :   'tags' ':' tag+
    ;

tag
    :   '-'? STRING 'on' ID (',' ID)*
    ;

DIGIT
    :   [0-9]
    ;

INTEGER
    :   DIGIT+
    ;

LETTER
    :   [a-zA-Z\u0080-\u00FF_]
    ;

ID
    :   LETTER(LETTER|DIGIT)+
    ;


STRING
    :   ["'] (~["'])* ["']
    ;


WS
    :   [ \t\n\r]+ -> skip
    ;

LINE_COMMENT
    :   '#' ~[\r\n]* -> channel(HIDDEN)
    ;