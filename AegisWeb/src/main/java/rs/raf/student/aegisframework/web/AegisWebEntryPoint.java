package rs.raf.student.aegisframework.web;

import rs.raf.student.aegisframework.core.annotation.AegisLibrary;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionManager;
import rs.raf.student.aegisframework.core.pipeline.PipelineManager;
import rs.raf.student.aegisframework.core.pipeline.StageAction;
import rs.raf.student.aegisframework.core.pipeline.stage.CorePipelineStage;
import rs.raf.student.aegisframework.web.dependency_injection.ControllerContainerAnnotation;
import rs.raf.student.aegisframework.web.endpoint.EndpointManager;
import rs.raf.student.aegisframework.web.endpoint.request.DeleteRequestAnnotation;
import rs.raf.student.aegisframework.web.endpoint.request.GetRequestAnnotation;
import rs.raf.student.aegisframework.web.endpoint.request.PatchRequestAnnotation;
import rs.raf.student.aegisframework.web.endpoint.request.PostRequestAnnotation;
import rs.raf.student.aegisframework.web.endpoint.request.PutRequestAnnotation;
import rs.raf.student.aegisframework.web.pipeline.stage.EndpointInitialiseContainerPipelineStage;
import rs.raf.student.aegisframework.web.pipeline.stage.RunServerPipelineStage;
import rs.raf.student.aegisframework.web.pipeline.stage.WebPipelineStage;
import rs.raf.student.aegisframework.web.scanner.WebScanner;

@AegisLibrary
public class AegisWebEntryPoint {

    static {
        PipelineManager.register(new RunServerPipelineStage());
        PipelineManager.register(new EndpointInitialiseContainerPipelineStage());

        PipelineManager.register(CorePipelineStage.DEPENDENCY_INJECTION_INITIALISE_CONTAINER, StageAction.PERFORM_BEFORE, AegisWebEntryPoint::executeScanners); //TODO: perform after ScanAnnotationsPipelineStage
        PipelineManager.register(CorePipelineStage.DEPENDENCY_INJECTION_INITIALISE_CONTAINER, StageAction.PERFORM_BEFORE, AegisWebEntryPoint::registerToDependencyInjectionManager);

        PipelineManager.register(WebPipelineStage.WEB_ENDPOINT_INITIALISE_CONTAINER, StageAction.PERFORM_BEFORE, AegisWebEntryPoint::registerToEndpointManager);
    }

    private static void registerToEndpointManager() {
        EndpointManager.registerRequestAnnotation(new DeleteRequestAnnotation());
        EndpointManager.registerRequestAnnotation(new GetRequestAnnotation());
        EndpointManager.registerRequestAnnotation(new PatchRequestAnnotation());
        EndpointManager.registerRequestAnnotation(new PostRequestAnnotation());
        EndpointManager.registerRequestAnnotation(new PutRequestAnnotation());
    }

    private static void registerToDependencyInjectionManager() {
        DependencyInjectionManager.registerContainerAnnotation(new ControllerContainerAnnotation());
    }

    private static void executeScanners() {
        WebScanner.scan();
    }

}
