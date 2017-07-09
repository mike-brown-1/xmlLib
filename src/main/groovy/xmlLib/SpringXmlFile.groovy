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
 * Class representing a Spring Framework XML config file.
 * Methods are provided to modify the configuration of one file based on contents of a second file.
 * <p>
 * Assumes we are dealing with elements directly under the "beans" element that look like the following.
 * <p>
 * &lt;bean id="config" class="some.class.name"&gt;
 *
 * @author Mike Brown
 */
class SpringXmlFile extends XmlFile {

    /**
     * Create new SpringXmlFile and verify it exists.
     * @param fileName path to the file
     * @throws FileNotFoundException File does not exist
     */
    SpringXmlFile(String fileName) throws FileNotFoundException{
        super(fileName)
    }

    /**
     * Append node from newBeans to oldBeans
     * Assumes we are appending elements to the root document.
     * @param oldBeans old spring xml config file beans
     * @param newBeans new spring xml config file beans with element we want to append.
     * @param elementName elements name (e.g., bean, list, map)
     * @param id value if id attribute to find in the newBeans
     */
    void appendNode(oldBeans, newBeans, elementName, id) {
        def newBean = newBeans."${elementName}".find { it.@id == id }
        oldBeans.appendNode(newBean)
    }

    /**
     * Replace oldBeans node with node with same id in newBeans.
     * @param oldBeans oldBeans cust spring xml config file beans
     * @param newBeans dev spring xml config file beans
     * @param elementName elements name (e.g., bean, list, map)
     * @param id value if id attribute to find in the newBeans
     */
    void replaceNode(oldBeans, newBeans, elementName, id) {
        def custBean = oldBeans."${elementName}".find{it.@id == id}
        def devBean = newBeans."${elementName}".find{it.@id == id}
        custBean.replaceNode {
            mkp.yield(devBean)
        }
    }

    /**
     * Remove node from beans based on element and id attribute.
     * @param beans parsed xml
     * @param element name
     * @param id value of id attribute
     */
    void removeNode(beans, element, id) {
        def theBean = beans."${element}".find{it.@id == id}
        theBean.replaceNode{}
    }
}
