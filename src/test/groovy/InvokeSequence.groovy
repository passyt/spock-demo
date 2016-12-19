import spock.lang.Specification

import java.util.concurrent.Callable

/**
 * Created by passyt on 2016/12/19.
 */
class InvokeSequence extends Specification {

    def "invoke in sequence"() {
        def callable1 = Mock(Callable);
        Callable callable2 = Mock();

        when:
        callable1.call()
        callable2.call()

        then:
        1 * callable1.call() >> obj1

        then:
        1 * callable2.call() >> obj2

        expect:
        obj1 == result1
        obj2 == result2

        where:
        obj1  | result1 | obj2  | result2
        "123" | "123"   | "456" | "456"
    }

}
