package rs.raf.student.aegisframework.web.http;

import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class HttpHeader {

    public static final HttpHeader ACCEPT                    = HttpHeader.of("Accept");
    public static final HttpHeader ACCEPT_ENCODING           = HttpHeader.of("Accept-Encoding");
    public static final HttpHeader ACCEPT_LANGUAGE           = HttpHeader.of("Accept-Language");
    public static final HttpHeader ACCEPT_RANGES             = HttpHeader.of("Accept-Ranges");
    public static final HttpHeader ALLOW                     = HttpHeader.of("Allow");
    public static final HttpHeader AUTHENTICATION_INFO       = HttpHeader.of("Authentication-Info");
    public static final HttpHeader AUTHORIZATION             = HttpHeader.of("Authorization");
    public static final HttpHeader CONNECTION                = HttpHeader.of("Connection");
    public static final HttpHeader CONTENT_ENCODING          = HttpHeader.of("Content-Encoding");
    public static final HttpHeader CONTENT_LANGUAGE          = HttpHeader.of("Content-Language");
    public static final HttpHeader CONTENT_LENGTH            = HttpHeader.of("Content-Length");
    public static final HttpHeader CONTENT_LOCATION          = HttpHeader.of("Content-Location");
    public static final HttpHeader CONTENT_RANGE             = HttpHeader.of("Content-Range");
    public static final HttpHeader CONTENT_TYPE              = HttpHeader.of("Content-Type");
    public static final HttpHeader DATE                      = HttpHeader.of("Date");
    public static final HttpHeader ETAG                      = HttpHeader.of("ETag");
    public static final HttpHeader EXPECT                    = HttpHeader.of("Expect");
    public static final HttpHeader FROM                      = HttpHeader.of("From");
    public static final HttpHeader HOST                      = HttpHeader.of("Host");
    public static final HttpHeader IF_MATCH                  = HttpHeader.of("If-Match");
    public static final HttpHeader IF_MODIFIED_SINCE         = HttpHeader.of("If-Modified-Since");
    public static final HttpHeader IF_NONE_MATCH             = HttpHeader.of("If-None-Match");
    public static final HttpHeader IF_RANGE                  = HttpHeader.of("If-Range");
    public static final HttpHeader IF_UNMODIFIED_SINCE       = HttpHeader.of("If-Unmodified-Since");
    public static final HttpHeader LAST_MODIFIED             = HttpHeader.of("Last-Modified");
    public static final HttpHeader LOCATION                  = HttpHeader.of("Location");
    public static final HttpHeader MAX_FORWARDS              = HttpHeader.of("Max-Forwards");
    public static final HttpHeader PROXY_AUTHENTICATE        = HttpHeader.of("Proxy-Authenticate");
    public static final HttpHeader PROXY_AUTHENTICATION_INFO = HttpHeader.of("Proxy-Authentication-Info");
    public static final HttpHeader PROXY_AUTHORIZATION       = HttpHeader.of("Proxy-Authorization");
    public static final HttpHeader RANGE                     = HttpHeader.of("Range");
    public static final HttpHeader REFERER                   = HttpHeader.of("Referer");
    public static final HttpHeader RETRY_AFTER               = HttpHeader.of("Retry-After");
    public static final HttpHeader SERVER                    = HttpHeader.of("Server");
    public static final HttpHeader TE                        = HttpHeader.of("TE");
    public static final HttpHeader TRAILER                   = HttpHeader.of("Trailer");
    public static final HttpHeader UPGRADE                   = HttpHeader.of("Upgrade");
    public static final HttpHeader USER_AGENT                = HttpHeader.of("User-Agent");
    public static final HttpHeader VARY                      = HttpHeader.of("Vary");
    public static final HttpHeader VIA                       = HttpHeader.of("Via");
    public static final HttpHeader WWW_AUTHENTICATE          = HttpHeader.of("WWW-Authenticate");

    private final String       name;
    private final List<String> values = new ArrayList<>();

    public HttpHeader(String name) {
        this.name = name;
    }

    public HttpHeader(String name, String value) {
        this.name = name;
        this.values.add(value);
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public void addValues(List<String> values) {
        this.values.addAll(values);
    }

    public String name() {
        return name;
    }

    public List<String> values() {
        return values;
    }

    public String firstValue() {
        return values.getFirst();
    }

    public void merge(HttpHeader header) {
        this.addValues(header.values());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof HttpHeader header)
            return Objects.equals(name, header.name());

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String log() {
        StringBuilder stringBuilder = new StringBuilder().appendFormatted("{0}: ",
                                                                          name.applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                              .applyAttribute(Attribute.UNDERLINE));

        stringBuilder.append(values.stream()
                                   .map(value -> value.applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL))
                                   .collect(Collectors.joining(", ")));

        return stringBuilder.toString();
    }

    public static HttpHeader of(String name) {
        return new HttpHeader(name);
    }

    public static HttpHeader of(String name, String value) {
        return new HttpHeader(name, value);
    }

    public static HttpHeader parse(String line) {
        int colonIndex = line.indexOf(':');

        HttpHeader header = new HttpHeader(line.substring(0, colonIndex));
        header.addValues(Arrays.stream(line.substring(colonIndex + 1).split(",")).map(String::trim).toList());

        return header;
    }

}
