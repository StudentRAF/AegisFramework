package rs.raf.student.aegisframework.core.pipeline.stage;

import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionFactory;
import rs.raf.student.aegisframework.core.pipeline.AbstractPipelineStage;

public class DependencyInjectionInitialiseContainerPipelineStage extends AbstractPipelineStage {

    public DependencyInjectionInitialiseContainerPipelineStage() {
        super(CorePipelineStage.DEPENDENCY_INJECTION_INITIALISE_CONTAINER);
    }

    @Override
    public void perform() {
        DependencyInjectionFactory.initialiseContainer();
    }

}
