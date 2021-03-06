# xmlLib

A utility to support modification of XML files.

The initial purpose of this project was to support making changes to an existing
Spring Framework project, which uses XML configuration files. As new development adds, 
modifies or removes elements in the Spring XML configuration file, an easy way to 
merge those changes into prior deployments was needed.

Requires Java 1.7

https://github.com/mike-brown-1/xmlLib.git

## Revisions
Version 0.1.5   
* Updated UpdateXml to support del (delete) command.
* Specified Java 1.7
* Some code cleanup

Version 0.1.4    
* Added tests for UpdateXml
* Made merged output file name optional command line
argument.  If omitted, results will be printed to 
standard output
* Added some javadoc
* Removed System.exit() calls, couldn't write tests
with them

Version 0.1.3    
* Added capability to remove node along with test

Version 0.1.2   
* Working UpdateXml for replace node
* Added TestSpringXmlFile
* Minor changes and javadoc

Version 0.1.1     
* Working UpdateXml for append element.

