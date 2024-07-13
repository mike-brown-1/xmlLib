package xmlLib

import groovy.xml.XmlUtil
import spock.lang.Specification

/**
 * Created by Mike on 7/4/2017.
 */
class TestSpringXmlFile2 extends Specification {

    SpringXmlFile oldFile
    def oldBeans
    SpringXmlFile devFile
    def devBeans

    def setup() {
        oldFile = new SpringXmlFile('data/origBeans.xml')
        oldBeans = oldFile.parse()
        devFile = new SpringXmlFile('data/devTestAppend.xml')
        devBeans = devFile.parse()
    }

    def "testing find"() {
        when:
        oldFile.removeNode(oldBeans, "map", "responsiveImageFormats")
        String updatedOldBeans = XmlUtil.serialize(oldBeans)

        then:
        !updatedOldBeans.contains('id="responsiveImageFormats"')
    }

    def "test append node"() {
        when:
        oldFile.appendNode(oldBeans, devBeans, 'bean', 'bean20')
        String updatedOldBeans = XmlUtil.serialize(oldBeans)

        then:
        updatedOldBeans.contains('bean20')
    }

    def "test replace node"() {
        when:
        oldFile.replaceNode(oldBeans, devBeans, 'bean', 'bean1')
        String updatedOldBeans = XmlUtil.serialize(oldBeans)

        then:
        updatedOldBeans.contains('bean1')
        updatedOldBeans.concat('appendedProp')
    }

    def "test remove node"() {
        when:
        devFile.removeNode(devBeans, 'bean', 'bean20')
        String updatedDevBeans = XmlUtil.serialize(devBeans)

        then:
        !updatedDevBeans.contains('bean20')
        updatedDevBeans.concat('bean1')
    }
}
