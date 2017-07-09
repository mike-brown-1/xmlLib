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

import groovy.util.slurpersupport.GPathResult
import groovy.xml.XmlUtil

/**
 * Base class for manipulating XML files.
 *
 * @author Mike Brown
 */
class XmlFile {
    protected String fileName
    protected File theFile

    /**
     * Create new XmlFile and verify it exists.
     * @param fileName path to the file
     * @throws FileNotFoundException File does not exist
     */
    XmlFile(String fileName) throws FileNotFoundException{
        this.fileName = fileName
        theFile = new File(fileName)
        if (!theFile.exists()) {
            throw new FileNotFoundException("${fileName} does not exist")
        }
    }

    /**
     * Parse the XML file and return a GPathResult for use in other classes.
     * @return Parse XML as GPathResult
     * @throws IllegalStateException
     */
    GPathResult parse() throws IllegalStateException {
        if (theFile == null) {
            throw new IllegalStateException('theFile is null')
        }
        def res = new XmlSlurper(false,false).parseText( theFile.text )
        res
    }

    /**
     * Save the beans back to the original file
     * @param beans parsed beans to save
     */
    void saveFile(GPathResult beans) {
        saveTheBeans(theFile, beans)
    }

    /**
     * Save the beans to the specified file name
     * @param fileName file name to save the beans to
     * @param beans the beans to save
     */
    void saveFile(String fileName, GPathResult beans) {
        File f = new File(fileName)
        saveTheBeans(f, beans)
    }

    /**
     * Helper method to save beans to a file
     * @param f File to save to
     * @param beans the beans to save
     */
    private void saveTheBeans(File f, GPathResult beans) {
        f.text = XmlUtil.serialize(beans)
    }
}
