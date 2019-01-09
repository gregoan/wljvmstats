WLJvmStats
------------

 * Copyright (c) 2019 GREGOIRE Alain
 * Version:  0.3
 * Licence:  "3-clause" BSD based Licence - refer to the files 'LICENSE'
 * Last updated:  01-Jan-2019
 * Home Page:  https://github.com/gregoan/wljvmstats

Introduction
------------

WLJvmStats is a small agent (JMX MBean) that runs in every WebLogic Server in a WebLogic domain. It is used to collect statistics about JVM instance.
WLJvmStats is also useful when employed in conjunction with DomainHealth (https://github.com/ccristian/domainhealth) enabling historical graphs of host machine operating system statistics to be displayed alongside regular WebLogic Server statistics.
WLJvmStats is a deployable JEE web-application (WAR archive). It is only supported for WebLogic versions 10.3 or greater, and on host machines running the following operating systems only: 

 * Linux x86 64-bit
 * Linux x86 32-bit
 * Solaris SPARC 64-bit
 * Solaris SPARC 32-bit
 * Solaris x86 64-bit
 * Solaris x86 32-bit

Quick Installation How To
-------------------------

   1. Navigate to this project's 'Download' page and download the zip file: wljvmstats-nn.zip
   2. Unpack the zip file to a temporary directory.
   3. Start (or re-start) your WebLogic domain's servers, so that the previous steps, above, take affect. 
   4. From the unzipped directory, deploy wljvmstats-nn.war Web Application to your WebLogic domain, targeted to admin server only

Building From Source
--------------------

This project includes a Maven buildfile in the root directory to enable the project to be completely re-built from source and modified and enhanced where necessary.
The project also includes an Eclipse (OEPE) '.project' Project file, enabling developers to optionally use Eclipse to modify the source (just import WLJvmStats as an existing project into Eclipse. 
To re-build the project, first ensure the Java SDK and Maven is installed and their 'bin' directories are present in PATH environment variable, then check the values in the pom.xml file in the project's root directory to ensure this reflects your local WebLogic environment settings. 
Run the following commands to clean the project, compile the source code and build the WAR web-application:

 > mvn clean package

Project Contact
---------------

GREGOIRE Alain (send email to the "gmail.com" email address for gmail user "gregoire.alain")