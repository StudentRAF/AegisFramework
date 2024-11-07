package rs.raf.student.aegisframework.web.endpoint.request;

import rs.raf.student.aegisframework.web.annotation.Post;
import rs.raf.student.aegisframework.web.endpoint.AbstractRequestAnnotation;
import rs.raf.student.aegisframework.web.http.HttpMethod;

public class PostRequestAnnotation extends AbstractRequestAnnotation<Post> {

    public PostRequestAnnotation() {
        super(Post.class);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

}
