package rs.raf.student.aegisframework.web.http;

import lombok.SneakyThrows;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionManager;
import rs.raf.student.aegisframework.web.endpoint.EndpointManager;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;

public class HttpResponse {

    private final PrintWriter output;
    private final HttpRequest request;

    @SneakyThrows
    public HttpResponse(Socket socket, HttpRequest request) {
        this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        this.request = request;
    }

    @SneakyThrows
    public void process() {
        if (!request.isValid()) {
            output.println(responseLine(HttpProtocol.HTTP1, HttpStatusCode.BAD_REQUEST));
            return;
        }

        Method method = EndpointManager.getMethod(request.resource(), request.method());

        if (method == null) {
            output.println(responseLine(HttpProtocol.HTTP1, HttpStatusCode.NOT_FOUND));
            return;
        }

        Object instance = DependencyInjectionManager.getInstance(method.getDeclaringClass());
        method.invoke(instance);

        output.println(responseLine(HttpProtocol.HTTP1, HttpStatusCode.OK));
    }

    private String responseLine(HttpProtocol protocol, HttpStatusCode statusCode) {
        return protocol + " " + statusCode.code() + " " + statusCode.message() + "\r\n";
    }

}
