# Contributing Guidelines

Do you want to contribute your work to Onixcoin Java Library? Well, then first and most important: **THANK YOU!** 

Now, in order to accept your contribution, there are some terms you must expressly agree with, so please
read them carefully. They might look a bit cumbersome, but they are here just in order to protect 
you, your contribution, and especially the project's future.

Who can contribute?
-------------------

Anyone, with the unique condition that he/she must be a **private individual**, acting in 
his/her own name, and not being endorsed in their contributed work by any company or government.

Note that this condition will not only refer to the ownership of the effort invested in contributing 
to the project, but also to the fact that *no private or public company will be mentioned as a a part 
of your contribution on the project's website or code*, including but not limited to web/email addresses 
or package names.

What is the first step to be taken?
-----------------------------------

First of all, **Discuss with the [onixcoinj google group](https://groups.google.com/forum/#!forum/onixcoinj)**
(a new thread should do) about  your ideas: new features, fixes, documentation... whatever you would like to contribute 
to the project. Let we discuss the possibilities with you so that we make sure your contribution goes in the right direction 
and aligns with the project's standards, intentions and roadmap. 


How will your relation with the Onixcoinj project be?
-----------------------------------------------------

Your contributions will have the form of GitHub *pull requests*. Note that contributors do not 
have read+write (or *pull+push*) access to the project repositories, only project *members* do. 

Also, please understand that *not all pull requests will be accepted and merged into the project's 
repositories*. Talking about your planned contributions with the project members before creating pull requests
will maximize the possibilities of your contributions being accepted.


About the code you contribute
-----------------------------

### General guidelines:
  
  - Obviously, **your code must both compile and work correctly**. Also, the addition of any new patches to the 
    codebase should not render it unstable in any way.
  - All your code should be easy to read and understand by a human.
  - There should be no compilation warnings at all.
  - Checkstyle must pass without errors or warnings. Currently this is embedded into the maven build.

### Detailed Java code quality standards:
  
  - All your code should compile and run in **Java 8.0**.
  - All comments, names of classes and variables, log messages, etc. must be **in English**.
  - All `.java` files must include the standard Apache License Version 2.0 header.
  - All your code should follow the Java Code Conventions regarding variable/method/class naming.
  - Maximum line size is 160 characters.
  - Indentation should be made with 4 spaces, not tabs.
  - Line feeds should be UNIX-like (`\n`).
  - All .java source files should be pure ASCII. All .properties files should be ISO-8859-1. 
  - Number autoboxing and/or autounboxing is forbidden.
  - Every class should define a constructor, even if it is the default one, and include a call to `super()`.
  - Include `/* ... */` comments for every algorithm you develop with a minimum of complexity. *"Minimum 
    of complexity"* usually means you had to take some design decisions in order to write it the way you did. Do 
    not write obvious comments.
  - All public methods and classes directly available to users (i.e. public) should have comprehensive javadoc.
  - We also have some defined checkstyle rules in checkstyle.xml
  

Pay special attention to this
-----------------------------

All Onixcoin Java software is distributed under the **Apache License 2.0** open source license, and your contributions
will be licensed in the same way.

If you work for a company which, by the way or place in which your code was written, by your contract terms 
or by the laws in your country, could claim any rights (including but not limited to intellectual or industrial 
property) over your contributed code, you will have to send the project members (either by email from your 
authorised superiors or by signed fax), a statement indicating that your company agrees with the terms 
explained in this page, and that it both authorises your contribution to Onixcoinj and states that will 
never claim any kind of rights over it.


  
  
  
  
  
  

