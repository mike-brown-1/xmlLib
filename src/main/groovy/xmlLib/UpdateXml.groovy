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

/**
 * Main class that uses library to update an old Spring XML config based on a new one.
 *
 * @author Mike Brown
 */
class UpdateXml {

    public static void main(String[] args) {
        if (args.length != 3) {
            println """
Usage: groovy xmlLib.UpdateXML oldConfig newConfig fileWithCommands
oldConfig: old Spring XML config file
newConfig: new Spring XML config file containing new or modified elements.
fileWithCommands: text file with commands.

fileWithCommands contents:
line 1: <oldConfig file path> <newConfig file path> // space delimited
lines 2 through n:
<command> <element-name> <id-value> // space delimited

command: either rep (replace) or app (append)
element-name: bean, util, etc.
id-value: value of the id attribute of the element
"""
            System.exit(1)
        }
        def oldFile = new XmlFile(args[0])
        def oldBeans = oldFile.parse()
        def newFile = new XmlFile(args[1])
        def newBeans = newFile.parse()
        processCommands(oldBeans, newBeans, args[2])
    }

    static void processCommands(oldBeans, newBeans, commandFileName) {

    }
}
