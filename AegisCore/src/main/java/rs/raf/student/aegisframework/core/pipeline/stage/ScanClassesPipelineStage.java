package rs.raf.student.aegisframework.core.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.AbstractPipelineStage;
import rs.raf.student.aegisframework.core.scanner.ClassScanner;

public class ScanClassesPipelineStage extends AbstractPipelineStage {

    public ScanClassesPipelineStage() {
        super(CorePipelineStage.SCAN_CLASSES);
    }

    @Override
    public void perform() {
        ClassScanner.scan();
    }

}
