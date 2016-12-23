import spock.lang.Specification

import javax.servlet.ServletException
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by passyt on 2016/12/19.
 */
class Servlet extends Specification {

    def test() {
        setup:
        def request = Mock(HttpServletRequest);
        def response = Mock(HttpServletResponse);
        def testServlet = new TestServlet();
        def outputStream = Mock(ServletOutputStream);

        when:
        testServlet.service(request, response);

        then:
        1 * request.getParameter("id") >> id1;
        1 * response.getOutputStream() >> outputStream;
        1 * outputStream.write(id1.getBytes())

        when:
        testServlet.service(request, response);

        then:
        1 * request.getParameter("id") >> id2;
        IllegalArgumentException e = thrown();
        e.getMessage() == "id is required"

        where:
        id1   | id2
        "123" | null
    }

}

class TestServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }

        response.getOutputStream().write(id.getBytes());
    }
}
