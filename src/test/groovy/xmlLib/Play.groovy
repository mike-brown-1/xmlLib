import groovy.xml.XmlUtil
import groovy.xml.XmlSlurper

/**
 * Created by Mike on 7/8/2017.
 */

def theFile = new File('data/oldBeans.xml')
def beans = new XmlSlurper(false,false).parseText( theFile.text )

// want to find:
// <bean id="exampleBean" class="examples.ExampleBean">
// ...
// <property name="beanTwo" ref="AnotherBean2"/>

def theNode = beans.bean.property.find{it.@name == 'beanTwo' && it.parent().@id == 'exampleBean'}
boolean foundNode = theNode.size() == 1
println "found bean: ${foundNode}"
if (foundNode) {
    println XmlUtil.serialize(theNode)
}

//println XmlUtil.serialize(beans)
