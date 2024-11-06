package rs.raf.student.aegisframework.web;

import rs.raf.student.aegisframework.core.annotation.AegisLibrary;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionManager;
import rs.raf.student.aegisframework.core.pipeline.PipelineManager;
import rs.raf.student.aegisframework.core.pipeline.StageAction;
import rs.raf.student.aegisframework.core.pipeline.stage.CorePipelineStage;
import rs.raf.student.aegisframework.web.dependency_injection.ControllerContainerAnnotation;
import rs.raf.student.aegisframework.web.pipeline.stage.RunServerPipelineStage;

@AegisLibrary
public class AegisWebEntryPoint {

    static {
        PipelineManager.register(new RunServerPipelineStage());

        PipelineManager.register(CorePipelineStage.DEPENDENCY_INJECTION_INITIALISE_CONTAINER, StageAction.PERFORM_BEFORE, AegisWebEntryPoint::registerToDependencyInjectionManager);
    }

    private static void registerToDependencyInjectionManager() {
        DependencyInjectionManager.registerContainerAnnotation(new ControllerContainerAnnotation());
    }

}
