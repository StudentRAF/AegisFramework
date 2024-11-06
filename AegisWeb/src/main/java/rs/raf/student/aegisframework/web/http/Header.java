package rs.raf.student.aegisframework.web.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Header {

    public static final Header ACCEPT                    = Header.of("Accept");
    public static final Header ACCEPT_ENCODING           = Header.of("Accept-Encoding");
    public static final Header ACCEPT_LANGUAGE           = Header.of("Accept-Language");
    public static final Header ACCEPT_RANGES             = Header.of("Accept-Ranges");
    public static final Header ALLOW                     = Header.of("Allow");
    public static final Header AUTHENTICATION_INFO       = Header.of("Authentication-Info");
    public static final Header AUTHORIZATION             = Header.of("Authorization");
    public static final Header CONNECTION                = Header.of("Connection");
    public static final Header CONTENT_ENCODING          = Header.of("Content-Encoding");
    public static final Header CONTENT_LANGUAGE          = Header.of("Content-Language");
    public static final Header CONTENT_LENGTH            = Header.of("Content-Length");
    public static final Header CONTENT_LOCATION          = Header.of("Content-Location");
    public static final Header CONTENT_RANGE             = Header.of("Content-Range");
    public static final Header CONTENT_TYPE              = Header.of("Content-Type");
    public static final Header DATE                      = Header.of("Date");
    public static final Header ETAG                      = Header.of("ETag");
    public static final Header EXPECT                    = Header.of("Expect");
    public static final Header FROM                      = Header.of("From");
    public static final Header HOST                      = Header.of("Host");
    public static final Header IF_MATCH                  = Header.of("If-Match");
    public static final Header IF_MODIFIED_SINCE         = Header.of("If-Modified-Since");
    public static final Header IF_NONE_MATCH             = Header.of("If-None-Match");
    public static final Header IF_RANGE                  = Header.of("If-Range");
    public static final Header IF_UNMODIFIED_SINCE       = Header.of("If-Unmodified-Since");
    public static final Header LAST_MODIFIED             = Header.of("Last-Modified");
    public static final Header LOCATION                  = Header.of("Location");
    public static final Header MAX_FORWARDS              = Header.of("Max-Forwards");
    public static final Header PROXY_AUTHENTICATE        = Header.of("Proxy-Authenticate");
    public static final Header PROXY_AUTHENTICATION_INFO = Header.of("Proxy-Authentication-Info");
    public static final Header PROXY_AUTHORIZATION       = Header.of("Proxy-Authorization");
    public static final Header RANGE                     = Header.of("Range");
    public static final Header REFERER                   = Header.of("Referer");
    public static final Header RETRY_AFTER               = Header.of("Retry-After");
    public static final Header SERVER                    = Header.of("Server");
    public static final Header TE                        = Header.of("TE");
    public static final Header TRAILER                   = Header.of("Trailer");
    public static final Header UPGRADE                   = Header.of("Upgrade");
    public static final Header USER_AGENT                = Header.of("User-Agent");
    public static final Header VARY                      = Header.of("Vary");
    public static final Header VIA                       = Header.of("Via");
    public static final Header WWW_AUTHENTICATE          = Header.of("WWW-Authenticate");

    private final String       name;
    private final List<String> values = new ArrayList<>();

    public Header(String name) {
        this.name = name;
    }

    public Header(String name, String value) {
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

    public void merge(Header header) {
        this.addValues(header.values());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Header header)
            return Objects.equals(name, header.name());

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static Header of(String name) {
        return new Header(name);
    }

    public static Header of(String name, String value) {
        return new Header(name, value);
    }

    public static Header parse(String line) {
        int colonIndex = line.indexOf(':');

        Header header = new Header(line.substring(0, colonIndex));
        header.addValues(Arrays.stream(line.substring(colonIndex + 1).split(",")).map(String::trim).toList());

        return header;
    }

}
