import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Created by passyt on 2016/12/19.
 * include spock-spring.jar
 */
@ContextConfiguration(locations = [
        "classpath:spring.test.xml"
])
class Spring extends Specification {

    @Autowired
    MyService service;

    def "test"() {
        expect:
        service.value == "123456"
    }

}

class MyService {

    @Value('${test.value}')
    private String value;

}