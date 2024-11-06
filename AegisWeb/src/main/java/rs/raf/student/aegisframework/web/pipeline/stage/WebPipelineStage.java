package rs.raf.student.aegisframework.web.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.IPipelineStage;

public record WebPipelineStage(String name, int order) implements IPipelineStage {

    public static final WebPipelineStage WEB_RUN_SERVER = new WebPipelineStage("Aegis Web - Run Server", 256);

}
