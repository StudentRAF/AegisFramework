package rs.raf.student.aegisframework.core.pipeline;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import rs.raf.student.aegisframework.core.scanner.AnnotationScanner;
import rs.raf.student.aegisframework.core.scanner.ClassScanner;
import rs.raf.student.aegisframework.core.scanner.LibraryScanner;
import rs.raf.student.aegisframework.utils.Pair;

import java.util.SortedSet;

public class PipelineManager {

    private static final SortedSet<IPipelineStage>                             sortedPipelineSet       = Sets.newTreeSet();
    private static final Multimap<Pair<IPipelineStage, StageAction>, Runnable> pipelineStageRunnersMap = ArrayListMultimap.create();

    public static void register(AbstractPipelineStage pipelineStage) {
        sortedPipelineSet.add(pipelineStage.getStage());

        register(pipelineStage.getStage(), StageAction.PERFORM, pipelineStage::perform);
        register(pipelineStage.getStage(), StageAction.PERFORM_AFTER, pipelineStage::performAfter);
        register(pipelineStage.getStage(), StageAction.PERFORM_BEFORE, pipelineStage::performBefore);
    }

    public static void register(IPipelineStage stage, StageAction action, Runnable runnable) {
        pipelineStageRunnersMap.put(Pair.of(stage, action), runnable);
    }

    public static void start() {
        ClassScanner.scan();
        AnnotationScanner.scan();
        LibraryScanner.scan();

        sortedPipelineSet.forEach(stage -> {
            perform(stage, StageAction.PERFORM_BEFORE);
            perform(stage, StageAction.PERFORM);
            perform(stage, StageAction.PERFORM_AFTER);
        });
    }

    private static void perform(IPipelineStage stage, StageAction action) {
        pipelineStageRunnersMap.get(Pair.of(stage, action))
                               .forEach(Runnable::run);
    }

}
