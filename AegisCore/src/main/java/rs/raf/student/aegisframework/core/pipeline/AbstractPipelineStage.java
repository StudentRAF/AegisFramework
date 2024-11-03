package rs.raf.student.aegisframework.core.pipeline;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractPipelineStage {

    private final IPipelineStage stage;

    public void perform() { }

    public void performAfter() { }

    public void performBefore() { }

}
