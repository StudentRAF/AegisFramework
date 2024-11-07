package rs.raf.student.aegisframework.web.endpoint.request;

import rs.raf.student.aegisframework.web.annotation.Patch;
import rs.raf.student.aegisframework.web.endpoint.AbstractRequestAnnotation;
import rs.raf.student.aegisframework.web.http.HttpMethod;

public class PatchRequestAnnotation extends AbstractRequestAnnotation<Patch> {

    public PatchRequestAnnotation() {
        super(Patch.class);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.PATCH;
    }

}
