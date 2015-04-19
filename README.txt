Nicholas Peluso
nap54@pitt.edu

Anthony Raehn
atr13@pitt.edu

Included is our CS1622 Project 3b - we used Eclipse IDE to set up and maintain our code. The flex and cup files are included under the folder etc.
We have modified some of syntaxtree to appropriately handle building our syntax tree, type checking, line and column numbers.

There are notable bugs, listed below:
-We do not have a functioning 'this' IR generation - the IR generates the word 'this' as a placeholder until we are able to sort it out.
-We did not have time to include all line/column numbers in typechecking and parser errors. The errors are reported with blank line and column information for the cases we did not reach.
-Certain instances have our typechecking/errorchecking trigger a false alarm. Because of this, we've generated IR code and printed it out WITH or WITHOUT errors. The errors are listed first, followed by our attempted/incomplete IR.

Our packages are set up as follows:
-Package user contains all of our clean code - including the driver, symbol table, node, parser, lexer, IR generation visitor, and name/typechecking visitor.
-Package syntaxtree contains the expected package classes, and also extra MethodType, ClassType, and BlockType dummy classes used for typechecking. We've also augmented some of the existing classes to include line and column numbers. We've also augmented EVERY class to include our IRVisitor method.
-Package visitor contains the expected classes, plus our IRVisitor abstract type.

Comments are sparse and the code is generally unclean at this point. We will look to tidy and correct unconventional/uncommented areas in the future.
We were unable to provide a makefile. We apologize for the inconvenience.
Kindest regards,
Nick and Anthony.