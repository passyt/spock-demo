import org.codehaus.groovy.runtime.metaclass.MissingMethodExceptionNoStack
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by passyt on 2016/12/19.
 */
@Unroll
class Argument extends Specification {

    def "map.put(#key, #value) == #result"() {
        setup:
        def map = Mock(Map);

        when:
        map.put(key, value)

        then:
        1 * map.put(key, {
            println it;
            assert it == result
            it;
        } as String) >> result
        0 * map.put(_, _ as BigDecimal)

        where:
        key | value || result
        "1" | "123" || "123"
        "2" | "456" || "456"
    }

    def "test exception"() {
        setup:
        def map = Mock(Map);

        when: "clear map"
        map.clear();

        then:
        map.clear() >> { throw new RuntimeException("123") }
        thrown(RuntimeException)

        when: "call size()"
        map.size();

        then:
        map.size() >> { throw new IllegalStateException("456")}
        IllegalStateException e = thrown()
        e.getMessage() == "456"
    }

}
