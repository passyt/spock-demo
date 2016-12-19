import spock.lang.Unroll

/**
 * Created by passyt on 2016/12/19.
 */

@Unroll
class Helloworld extends spock.lang.Specification {

    def "size of '#name' is #length"() {
        expect:
        name.size() == length

        where:
        name     || length
        "Spock"  || 5
        "Kirk"   || 4
        "Scotty" || 6
    }

    def "maximum of (#a, #b) is #c"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [3, 5, 9]
        b << [7, 4, 9]
        c << [7, 5, 9]
    }

    def "minimum of (#a, #b) is #c"() {
        expect:
        Math.min(a, b) == c

        where:
        a | b | c
        3 | 7 | 3
        5 | 4 | 5
        9 | 9 | 9
    }

    def "#person.name is a #sex.toLowerCase() person"() {
        expect:
        person.getSex() == sex

        where:
        person                    || sex
        new Person(name: "Fred")  || "Male"
        new Person(name: "Wilma") || "Female"
    }

    static class Person {
        String name

        String getSex() {
            name == "Fred" ? "Male" : "Female"
        }
    }

}
