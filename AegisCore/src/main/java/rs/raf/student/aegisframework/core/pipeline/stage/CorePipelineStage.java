package rs.raf.student.aegisframework.core.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.IPipelineStage;

public record CorePipelineStage(String name, int order) implements IPipelineStage {

    public static final CorePipelineStage DEPENDENCY_INJECTION_INITIALISE_CONTAINER = new CorePipelineStage("Dependency Injection - Initialise Container", -64);
    public static final CorePipelineStage DEPENDENCY_INJECTION_INJECT_DEPENDENCIES  = new CorePipelineStage("Dependency Injection - Inject Dependencies",    0);

}
