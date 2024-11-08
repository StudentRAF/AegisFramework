package rs.raf.student.aegisframework.web.http;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class HttpRequest {

    private HttpMethod   method;
    private HttpProtocol protocol;
    private String       resource;
    private String       body;
    private boolean      isValid = true;

    private final Map<HttpHeader, HttpHeader> headers = new HashMap<>();

    private final BufferedReader input;

    @SneakyThrows
    public HttpRequest(Socket socket) {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        readRequestLine();
        readHeaders();
        readBody();
    }

    @SneakyThrows
    private void readRequestLine() {
        String requestLine = input.readLine();

        if (!(isValid = requestLine != null))
            return;

        String[] requestTokens = requestLine.split(" ");

        method   = HttpMethod.valueOf(requestTokens[0]);
        resource = requestTokens[1];
        protocol = HttpProtocol.find(requestTokens[2]);
    }

    @SneakyThrows
    public void readHeaders() {
        if (!isValid)
            return;

        String     line;
        HttpHeader header;

        while ((line = input.readLine()) != null && !line.isBlank()) {
            header = HttpHeader.parse(line);

            if (headers.containsKey(header))
                headers.get(header).addValues(header.values());
            else
                headers.put(HttpHeader.of(header.name()), header);
        }
    }

    @SneakyThrows
    public void readBody() {
        if (!isValid)
            return;

        HttpHeader headerContentLength = headers.get(HttpHeader.CONTENT_LENGTH);

        if (headerContentLength == null)
            return;

        int length = Integer.parseInt(headerContentLength.firstValue());
        char[] bodyArray = new char[length];

        if (input.read(bodyArray) == -1)
            return;

        body = new String(bodyArray);
    }

}
