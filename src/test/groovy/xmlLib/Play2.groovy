package xmlLib

import groovy.xml.XmlUtil
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult
import groovy.xml.slurpersupport.Node
import groovy.xml.slurpersupport.NodeChild

/**
 * Created by Mike on 7/8/2017.
 */

def theFile = new File('/home/mike/projects/xmlLib/data/origBeans.xml')
GPathResult beans = new XmlSlurper(false,true).parseText( theFile.text )

// want to find:
// <util:map id="responsiveImageFormats"
// ...
// <property name="beanTwo" ref="AnotherBean2"/>

NodeChild theNode = beans.'*'.find{it.@id == 'responsiveImageFormats'}
boolean foundNode = theNode.size() == 1
println "found bean: ${foundNode}"
if (foundNode) {
    println XmlUtil.serialize(theNode)
}

//println XmlUtil.serialize(beans)
