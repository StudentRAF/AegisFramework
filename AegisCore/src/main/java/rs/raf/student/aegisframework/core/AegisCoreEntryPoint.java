package rs.raf.student.aegisframework.core;

import rs.raf.student.aegisframework.core.annotation.AegisLibrary;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionContainer;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionManager;
import rs.raf.student.aegisframework.core.dependency_injection.container.BeanContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.container.ComponentContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.container.QualifierContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.container.ServiceContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.injection.AutowiredInjectionAnnotation;
import rs.raf.student.aegisframework.core.pipeline.PipelineManager;
import rs.raf.student.aegisframework.core.pipeline.StageAction;
import rs.raf.student.aegisframework.core.pipeline.stage.CorePipelineStage;
import rs.raf.student.aegisframework.core.pipeline.stage.DependencyInjectionInitialiseContainerPipelineStage;
import rs.raf.student.aegisframework.core.pipeline.stage.DependencyInjectionInjectDependenciesPipelineStage;

@AegisLibrary
public class AegisCoreEntryPoint {

    static {
        PipelineManager.register(new DependencyInjectionInitialiseContainerPipelineStage());
        PipelineManager.register(new DependencyInjectionInjectDependenciesPipelineStage());

        PipelineManager.register(CorePipelineStage.DEPENDENCY_INJECTION_INITIALISE_CONTAINER, StageAction.PERFORM_BEFORE, AegisCoreEntryPoint::registerToDependencyInjectionManager);
        PipelineManager.register(CorePipelineStage.DEPENDENCY_INJECTION_INJECT_DEPENDENCIES, StageAction.PERFORM_AFTER, DependencyInjectionContainer::logState);
    }

    private static void registerToDependencyInjectionManager() {
        DependencyInjectionManager.registerContainerAnnotation(new BeanContainerAnnotation());
        DependencyInjectionManager.registerContainerAnnotation(new ComponentContainerAnnotation());
        DependencyInjectionManager.registerContainerAnnotation(new QualifierContainerAnnotation());
        DependencyInjectionManager.registerContainerAnnotation(new ServiceContainerAnnotation());

        DependencyInjectionManager.registerInjectionAnnotation(new AutowiredInjectionAnnotation());
    }

}
