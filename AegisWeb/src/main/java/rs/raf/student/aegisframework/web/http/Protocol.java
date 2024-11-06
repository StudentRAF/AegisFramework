package rs.raf.student.aegisframework.web.http;

public enum Protocol {

    HTTP1,
    HTTP2,
    HTTP3;

    public static Protocol find(String version) {
        return switch (version) {
            case "HTTP/1.1" -> HTTP1;
            case "HTTP/2"   -> HTTP2;
            case "HTTP/3"   -> HTTP3;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case HTTP1 -> "HTTP/1.1";
            case HTTP2 -> "HTTP/2";
            case HTTP3 -> "HTTP/3";
        };
    }

}
