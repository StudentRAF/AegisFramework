package rs.raf.student.aegisframework.web.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.IPipelineStage;

public record WebPipelineStage(String name, int order) implements IPipelineStage {

    public static final WebPipelineStage WEB_ENDPOINT_INITIALISE_CONTAINER = new WebPipelineStage("Aegis Web - Initialise Endpoint Container",  64);
    public static final WebPipelineStage WEB_RUN_SERVER                    = new WebPipelineStage("Aegis Web - Run Server",                    256);

}
