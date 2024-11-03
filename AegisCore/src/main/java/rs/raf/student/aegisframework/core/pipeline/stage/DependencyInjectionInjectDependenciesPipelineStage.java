package rs.raf.student.aegisframework.core.pipeline.stage;

import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionFactory;
import rs.raf.student.aegisframework.core.pipeline.AbstractPipelineStage;

public class DependencyInjectionInjectDependenciesPipelineStage extends AbstractPipelineStage {

    public DependencyInjectionInjectDependenciesPipelineStage() {
        super(CorePipelineStage.DEPENDENCY_INJECTION_INJECT_DEPENDENCIES);
    }

    @Override
    public void perform() {
        DependencyInjectionFactory.injectDependencies();
    }

}
