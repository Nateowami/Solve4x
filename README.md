Solve4x
=======

[![Build Status](https://travis-ci.org/Nateowami/Solve4x.svg?branch=master)](https://travis-ci.org/Nateowami/Solve4x)

An algebra solver that shows its work, step-by-step, with instructions (and pretty-prints/formats math nicely). The goal? Show Epsilon how he could have solved the math problem without ever leaving the doghouse.

Usage
=====

Building the project is as easy as running `ant` from within the project root directory. To run it, execute `java -cp bin com.github.nateowami.solve4x.Main`. To run the JUnit tests, run `ant AllTests`, generate a report with `ant junitreport`, and view the results in `bin/index.html`. You can also import the project in Eclipse and run the program or its tests with a single click. See [CONTRIBUTING.md](https://github.com/Nateowami/Solve4x/blob/master/CONTRIBUTING.md) for more information.

The UI is mostly non-functional, but you can type a super-simple equation (as in simple operations, not few operations), click "Build Lesson," and watch the text box say "Equation Evaluation Status: true" if it happens to like your input, or throw an ugly exception in the terminal if it feels inclined to do so. If a solution can be found, the solving steps are printed to the terminal, otherwise you'll be told that no solution was found. If you feel like playing around with it, here's a basic equation that shows every step Solve4x is currently capable of: `-x-2=2(8.6)-2x`.

The state of things
===================

Solve4x can currently combine like terms, add or subtract a term from both sides of an equation, and multiply terms. These operations are performed by classes in `com.github.nateowami.solve4x.algorithm`, which act on objects from `com.github.nateowami.solve4x.solver`, which is the parser and holds the internal representation of algebra. The algorithms need to be expanded (first priority right now), and eventually the GUI and CLI need to be independent of each other. Rendering of equations needs to come next, eventually supporting plain text, graphics (SVG, PNG, or both), JSON, and possibly XML. Ultimately support for second-degree equations should be added, but right now the focus is solely on first-degree equations.

Documentation & contributing
============================

Get started by heading over to [CONTRIBUTING.md](https://github.com/Nateowami/Solve4x/blob/master/CONTRIBUTING.md) or [the wiki](https://github.com/Nateowami/Solve4x/wiki). You might want to pay particular attention to the pages on  [program flow](https://github.com/Nateowami/Solve4x/wiki/Program-Flow) and the [algebra syntax definition](https://github.com/Nateowami/Solve4x/wiki/Algebra-Syntax-Definition). If you enjoy reading ugly Javadocs, head over to http://nateowami.github.io/Solve4x/doc/ and check out `com.github.nateowami.solve4x.solver`, as it has the best documentation and is central to the solver (but be warned, the docs there get a bit dated sometimes).

