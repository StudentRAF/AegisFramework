package rs.raf.student.aegisframework.core.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.IPipelineStage;

public record CorePipelineStage(String name, int order) implements IPipelineStage {

    public static final CorePipelineStage SCAN_CLASSES                              = new CorePipelineStage("Scan Classes",                                -256);
    public static final CorePipelineStage SCAN_ANNOTATIONS                          = new CorePipelineStage("Scan Annotations",                            -192);
    public static final CorePipelineStage SCAN_LIBRARIES                            = new CorePipelineStage("Scan Libraries",                              -128);
    public static final CorePipelineStage DEPENDENCY_INJECTION_INITIALISE_CONTAINER = new CorePipelineStage("Dependency Injection - Initialise Container",  -64);
    public static final CorePipelineStage DEPENDENCY_INJECTION_INJECT_DEPENDENCIES  = new CorePipelineStage("Dependency Injection - Inject Dependencies",     0);

}
