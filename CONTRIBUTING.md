# Contributing

## Table of contents

This guide covers three topics:

1. Getting started and building the project
2. Understanding the project and making changes
3. Contributing your changes

## Getting started and building the project

### Fork and clone

Start by forking the repository and cloning it (e.g., `git clone https://github.com/Nateowami/Solve4x.git`). Or, if you use an IDE, you may be able to skip this step and clone it directly from within your IDE.

### Importing to Eclipse

This is optional. If you want to use Eclipse with Solve4x, follow these instructions. 

To start working with a project in Eclipse, you first need to import it. Choose File > Import, and in the dialog that opens choose Git > Projects from git. If you don't see git as an option, choose General > Existing projects into Workspace. Then click "Next."

If you are importing with git, you'll be given two choices: "Existing local repository" and "Clone URI." If you've cloned the project already, choose "Existing local repository" and click "Next." You'll be presented with a list of git repositories. If you don't see the Solve4x repo, you'll need to click "Add" and then browse for it. Otherwise select it from the list and click "Next," "Next," and "Finish."

If you haven't cloned the repository you can choose "Clone URI" and give it `https://github.com/Nateowami/Solve4x.git` as the clone URI. Tip: If you copy the URL to the clipboard Eclipse will automatically put it in as the clone URI. Optionally enter your user name and password for your GitHub account, and Eclipse will store them for you so you can push commits more easily.

Finally, if you chose General > Existing local repository, then you can browse for the project and click "Finish."

### Building the project on the command line

Solve4x is very easy to build because it has no dependencies other than JUnit and the JDK.

From the command line, navigate to the project's root directory and run `ant`. If Ant isn't installed,  run `sudo apt-get install ant` (if you're using Linux), `brew install ant` or `sudo port install apache-ant` (Mac), or visit http://ant.apache.org/manual/install.html and do a manual install (Windows). Then you can run it with `java -cp bin com.github.nateowami.solve4x.Main` (from the project root directory).

### Building the project in Eclipse

To run the project in Eclipse you need to define a run configuration. Click the right side of Eclipse's run button to open the drop down menu, and choose "Run Configurations." In the left panel of the dialog that appears, double-click on "Java Application" to create a new run configuration. Assuming you had the right project open, Eclipse will prepopulate "Solve4x" as the project, and you'll only need to specify the main class, with is `com.github.nateowami.solve4x.Main`. Name your configuration and click "Apply" or "Run."

For tests, navigate to the test you want to run (`AllTests` runs all of them) and click the run button. You may need Eclipse's JUnit plugin to run these.

## Understanding the project and making changes

### Project layout

What follows is a brief summery of the main directories in Solve4x.

* `src`:
  Thanks to Java's packaging conventions, you'll find the source code in `src/com/github/nateowami/solve4x`. This directory has two classes, Main and Solve4x. The main method is in Main, and Solve4x starts the GUI and handles debugging. Here's a quick rundown of the sub-directories:
  * `algorithm`: Contains solving algorithms used by the solver. This includes operations such as combining like terms, multiplying, and adding or subtracting a term from both sides.
  * `config`: Handles a minute about of configuration.
  * `solver`: Parses input and holds an algebraic representation of it, so it can be worked on by the algorithms.
  * `ui`: Contains classes that create the GUI.
  * `visual`: Basic rendering of solutions to the console.
  
* `tests`:
  The tests have the same basic layout as the src directory, but not every class is tested, so there are fewer files here.
  
* `rsc`:
  This directory has two sub-directories, `icons` and `xml`. The former used to be used by the GUI, while the latter defines the Synth look-and-feel used the GUI.

### Making changes

It's a good idea to make a new branch before starting work, but if your changes are small this might not be necessary.

Try to use a style reasonably consistent with the rest of the project (If you really want to drive me nuts put opening brackets on a new line, rather than [how they're supposed to be](https://google-styleguide.googlecode.com/svn/trunk/javaguide.html#s4.1.2-blocks-k-r-style)). This usually amounts to a loose following of the [Google Java Style](https://google-styleguide.googlecode.com/svn/trunk/javaguide.html), with the exception that you should use tabs for indenting.

* Write algorithms
  The algorithms in `com.github.nateowami.solve4x.algorithm` need to be expanded. For detailed instructions, refer to [Creating an Algorithm (revised)](https://github.com/Nateowami/Solve4x/wiki/Creating-an-Algorithm-(revised)).
* Write unit tests
  There is a fairly large number of unit tests, but more is better, and code coverage is not complete. Unit tests don't need to be perfect. They just need to show whether the code really works as it's supposed to. Solve4x uses [JUnit](http://junit.org/);
* Expand Javadocs
  Most but not all methods have been Javadoced. Expanding or improving the docs makes the project easier to understand and frees up time for others to do more coding. Edits to the wiki are also welcome.
* Create a logo
  At this time there is a logo (SVG and PNG) in the repo, but it's basically `x=?`, and we later talked about it that it's basically saying `unknown=unknown`, which is kind of silly (Later I discovered several iOS apps use `x=?` as their logo, so maybe 're not the only ones that don't think of everything the first time. :smile:).

## Contributing your changes to the master repository.

When your code is <strike>perfect</strike> ready push it to your fork and open a pull request. Please clearly state what changes were made and indicate if it breaks any functionality.

When everything is good to go your changes will be merged. :smile:
