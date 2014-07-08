/*
 * The ANTLR grammar for the trio inputs
 */

grammar Trio;

system
    : component+
    ;

component:
    'component' ID 
    ;

DIGIT
: [0-9]
;

LETTER
: [a-zA-Z\u0080-\u00FF_]
;

ID
: LETTER(LETTER|DIGIT)+;


WS
: [ \t\n\r]+ -> skip
;

LINE_COMMENT
: '#' ~[\r\n]* -> channel(HIDDEN)
;