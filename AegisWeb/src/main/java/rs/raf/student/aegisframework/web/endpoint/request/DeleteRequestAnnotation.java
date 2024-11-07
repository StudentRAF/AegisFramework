package rs.raf.student.aegisframework.web.endpoint.request;

import rs.raf.student.aegisframework.web.annotation.Delete;
import rs.raf.student.aegisframework.web.endpoint.AbstractRequestAnnotation;
import rs.raf.student.aegisframework.web.http.HttpMethod;

public class DeleteRequestAnnotation extends AbstractRequestAnnotation<Delete> {

    public DeleteRequestAnnotation() {
        super(Delete.class);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }

}
