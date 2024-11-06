package rs.raf.student.aegisframework.core.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.AbstractPipelineStage;
import rs.raf.student.aegisframework.core.scanner.LibraryScanner;

public class ScanLibrariesPipelineStage extends AbstractPipelineStage {

    public ScanLibrariesPipelineStage() {
        super(CorePipelineStage.SCAN_LIBRARIES);
    }

    @Override
    public void perform() {
        LibraryScanner.scan();
    }

}
