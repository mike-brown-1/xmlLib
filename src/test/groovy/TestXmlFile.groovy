import spock.lang.Specification
import xmlLib.XmlFile

/**
 * Created by Mike on 7/2/2017.
 */
class TestXmlFile extends Specification {

    def "test file does not exist"() {
        when: new XmlFile('notthere')
        then: thrown(FileNotFoundException)
    }

    def "test file exists"() {
        when:
            def f = new XmlFile('data/caa.status.xml')
        then:
            assert f != null
            notThrown FileNotFoundException
    }
}
