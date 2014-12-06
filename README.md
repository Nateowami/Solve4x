Solve4x
=======
An algebra solver that shows its work

The goal of this program is to generate a lesson explaining how to solve a given problem. It should solve the simplest way possible (i.e., the way a person would), and should not include unnecessary steps. Equations should be rendered nicely, rather than as single line of text. This is not intended as a cheat-sheet, but rather as a helpful explainer for a problem the user doesn't understand.

General Progress
================
* GUI - 95% finished.  Colors need some changing, and adding a texture may be necessary. 
* Core solver/parser - Finished, but will need modifications. relies on classes that contain solving algorithms.
* Solving algorithms - Barely started, up next in line for getting worked on.
* Equation validator - Finished, but will need modifications.
* Renderer - Not started, except for a command line version intended for debugging.

Documentation
=============
For getting started you may want to checkout [the wiki](https://github.com/Nateowami/Solve4x/wiki), and for a quick overview you can read about [program flow](https://github.com/Nateowami/Solve4x/wiki/Program-Flow). There's also an [algebra syntax definition](https://github.com/Nateowami/Solve4x/wiki/Algebra-Syntax-Definition), though it's just a starting point for now. The generated JavaDocs can be found at <http://nateowami.github.io/Solve4x/doc/>. The `com.github.nateowami.solve4x.solver` package has the best documentation, and is central to the solver, so that's the best place to start.
