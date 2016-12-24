import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.mock.DetachedMockFactory

/**
 * Created by passyt on 2016/12/19.
 * include spock-spring.jar
 */
@ContextConfiguration(locations = [
        "classpath:spring.test.xml"
])
class SpringWithXml extends Specification {

    @Autowired
    MyService service;

    def "test with xml"() {
        expect:
        service.value == "123456"
    }

}

@ContextConfiguration
class SpringWithJavaConfig extends Specification {

    @Autowired
    MyService service;

    def "test with java config"() {
        expect:
        service.value == "123456"
    }


    @Configuration
    static class SpringConfiguration {

        @Bean
        def MyService myService() {
            return new MyService();
        }

        @Bean
        def static PropertyPlaceholderConfigurer testConfigurer() {
            return new PropertyPlaceholderConfigurer(locations: new DefaultResourceLoader().getResource("classpath:/test.properties"));
        }

    }

}

@ContextConfiguration
class SpringWithMock extends Specification {

    @Autowired
    MyService service;

    def "test with mock"() {
        when:
        service.say("something");

        then:
        1 * service.say("something") >> { throw new Exception("123") }
        Exception e = thrown();
        e.getMessage() == "123"
    }

    @Configuration
    static class SpringMockConfiguration {

        def mockFactory = new DetachedMockFactory()

        @Bean
        def MyService myService() {
            return mockFactory.Mock(MyService);
        }

        @Bean
        def static PropertyPlaceholderConfigurer testConfigurer() {
            return new PropertyPlaceholderConfigurer(locations: new DefaultResourceLoader().getResource("classpath:/test.properties"));
        }

    }

}

class MyService {

    @Value('${test.value}')
    private String value;

    public String say(String message) {
        return "hello, ${message}";
    }

}
