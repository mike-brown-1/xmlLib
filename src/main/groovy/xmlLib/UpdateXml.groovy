/*
Copyright 2017 Mike Brown

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package xmlLib

import groovy.xml.XmlUtil

/**
 * Main class that uses library to update an old Spring XML config based on a new one.
 *
 * @author Mike Brown
 */
class UpdateXml {

    public static void main(String[] args) {
        println "arguments: ${args}"
        if (args.length != 4) {
            println """
Usage: groovy xmlLib.UpdateXML oldConfig newConfig fileWithCommands mergedConfig
oldConfig: old Spring XML config file.
newConfig: new Spring XML config file containing new or modified elements.
fileWithCommands: text file with commands.
mergedConfig: file name of the merged Spring XML config file.

fileWithCommands contents:
lines 1 through n:
<command> <element-name> <id-value> // space delimited

command: either rep (replace) or app (append)
element-name: bean, util, etc.
id-value: value of the id attribute of the element
"""
            System.exit(1)
        }
        try {
// create and parse Spring XML files
            def oldFile = new SpringXmlFile(args[0])
            def oldBeans = oldFile.parse()
            def newFile = new SpringXmlFile(args[1])
            def newBeans = newFile.parse()
            processCommands(oldFile, newFile, oldBeans, newBeans, args[2])
            File mergedFile = new File(args[3])
            mergedFile.text = XmlUtil.serialize( oldBeans )
            println "produced merged output: ${args[3]}"
        } catch (Exception e) {
            println "ERROR: ${e.message}"
        }
    }

    static void processCommands(SpringXmlFile oldFile, SpringXmlFile newFile, oldBeans, newBeans,
                                String commandFileName) {
        def commandFile = new File(commandFileName)
        if (!commandFile.exists()) {
            println "${commandFile} does not exist, exiting."
            System.exit(1)
        }
        def commands = commandFile.readLines()
        println "read ${commands.size()} lines from command file"
        commands.each { command ->
            def tokens = command.split(' ')
            if (tokens.size() == 3) {
                if (tokens[0].toLowerCase().equals('app')) {
                    oldFile.appendNode(oldBeans, newBeans, tokens[1].trim(), tokens[2].trim())
                } else {
                    println "ignoring invalid line: ${command}"
                }
            }
        }
    }
}
