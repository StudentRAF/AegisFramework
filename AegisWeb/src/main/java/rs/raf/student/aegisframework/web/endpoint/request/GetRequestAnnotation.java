package rs.raf.student.aegisframework.web.endpoint.request;

import rs.raf.student.aegisframework.web.annotation.Get;
import rs.raf.student.aegisframework.web.endpoint.AbstractRequestAnnotation;
import rs.raf.student.aegisframework.web.http.HttpMethod;

public class GetRequestAnnotation extends AbstractRequestAnnotation<Get> {

    public GetRequestAnnotation() {
        super(Get.class);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

}
