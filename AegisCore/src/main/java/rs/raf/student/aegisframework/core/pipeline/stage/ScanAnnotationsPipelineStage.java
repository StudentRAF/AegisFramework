package rs.raf.student.aegisframework.core.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.AbstractPipelineStage;
import rs.raf.student.aegisframework.core.scanner.AnnotationScanner;

public class ScanAnnotationsPipelineStage extends AbstractPipelineStage {

    public ScanAnnotationsPipelineStage() {
        super(CorePipelineStage.SCAN_ANNOTATIONS);
    }

    @Override
    public void perform() {
        AnnotationScanner.scan();
    }

}
