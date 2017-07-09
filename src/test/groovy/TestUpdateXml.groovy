import spock.lang.Specification
import xmlLib.UpdateXml

/**
 * Created by Mike on 7/8/2017.
 */
class TestUpdateXml extends Specification {

    def "test no args"() {
        when:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream ps = new PrintStream(baos)
        PrintStream old = System.out
        System.setOut(ps)
        def app = new UpdateXml()
        app.main()
        System.out.flush()
        System.setOut(old)
        def results = baos.toString()

        then:
        assert results.contains('Usage:')
    }

    def "test invalid xml"() {
        when:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream ps = new PrintStream(baos)
        PrintStream old = System.out
        System.setOut(ps)
        def app = new UpdateXml()
        app.main('data/bad.xml', 'data/devBeans.xml', 'cmd1.txt')
        System.out.flush()
        System.setOut(old)
        def results = baos.toString()

        then:
        assert results.contains('ERROR invalid XML:')
    }

    def "test missing cmd file"() {
        when:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream ps = new PrintStream(baos)
        PrintStream old = System.out
        System.setOut(ps)
        def app = new UpdateXml()
        app.main('data/oldBeans.xml', 'data/devBeans.xml', 'data/nocmd.txt')
        System.out.flush()
        System.setOut(old)
        def results = baos.toString()

        then:
        assert results.contains('does not exist, exiting')
    }

    def "test merged output to console"() {
        when:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream ps = new PrintStream(baos)
        PrintStream old = System.out
        System.setOut(ps)
        def app = new UpdateXml()
        app.main('data/old.xml', 'data/dev.xml', 'data/cmd1.txt')
        System.out.flush()
        System.setOut(old)
        def results = baos.toString()

        then:
        assert results.contains('<?xml version')
        assert results.contains('newMember')
        assert results.contains('bean2')
    }

    def "test merged output to file"() {
        when:
        def app = new UpdateXml()
        app.main('data/old.xml', 'data/dev.xml', 'data/cmd1.txt', 'data/cmd1.results.xml')
        def results = new File('data/cmd1.results.xml').text

        then:
        assert results.contains('<?xml version')
        assert results.contains('newMember')
        assert results.contains('bean2')
    }
}
