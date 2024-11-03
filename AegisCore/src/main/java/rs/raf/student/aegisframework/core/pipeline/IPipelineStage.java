package rs.raf.student.aegisframework.core.pipeline;

public interface IPipelineStage extends Comparable<IPipelineStage> {

    String name();

    int order();

    @Override
    default int compareTo(IPipelineStage pipelineStage) {
        return Integer.compare(order(), pipelineStage.order());
    }

}
