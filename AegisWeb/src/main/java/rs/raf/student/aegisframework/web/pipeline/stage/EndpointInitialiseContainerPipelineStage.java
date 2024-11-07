package rs.raf.student.aegisframework.web.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.AbstractPipelineStage;
import rs.raf.student.aegisframework.web.endpoint.EndpointFactory;

public class EndpointInitialiseContainerPipelineStage extends AbstractPipelineStage {

    public EndpointInitialiseContainerPipelineStage() {
        super(WebPipelineStage.WEB_ENDPOINT_INITIALISE_CONTAINER);
    }

    @Override
    public void perform() {
        EndpointFactory.initialiseContainer();
    }

}
