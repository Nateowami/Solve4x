Solve4x
=======

A Java-based program to solve and explain algebra problems

The goal of this program is to generate an audio-visual lesson explaining how to solve the given problem. It aims to be simple to use, but very intuitive.

Why Carets (<>)
===============
Carets (<>) aren't used in normal algebra, but they are in this program. That's because algebra problems are 2d, while strings are 1d. Look at the following example:

    4x+5
    ----
    3x-5

Now convert it to a one-dimensional string:

    4x+5/3x-5

Now this string could just as easily be interpreted (wrongly) as the following:

        5 
    4x+--- -5
       3x

To end the confusion we will use carets as parentheses:

    <4x+5>/<3x-5>

Why didn't we use parentheses? Well, it turns out that there is a certain inconvenient rule in algebra that says when you nest parentheses you should nest in the following order: {[()]}. They all mean the same thing; it's just a matter of style. Since we don't want the parentheses we added to be shown to the user, we use carets to distinguish which ones should be shown. I hope this makes sense.

TODO
====

The validator for the equation is mostly done and working, although it may be completely changed for another one that works differently.
A temporary GUI is in place for testing, but later there will need to be a lot of work on the GUI.
Solver.java is not started; neither are the solving strategies (a group of classes). Solver.java is now top priority; hopefully finished by the end of the week.
The A/V generator is still a long way down the road.
