package rs.raf.student.aegisframework.web.endpoint.request;

import rs.raf.student.aegisframework.web.annotation.Put;
import rs.raf.student.aegisframework.web.endpoint.AbstractRequestAnnotation;
import rs.raf.student.aegisframework.web.http.HttpMethod;

public class PutRequestAnnotation extends AbstractRequestAnnotation<Put> {

    public PutRequestAnnotation() {
        super(Put.class);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.PUT;
    }

}
