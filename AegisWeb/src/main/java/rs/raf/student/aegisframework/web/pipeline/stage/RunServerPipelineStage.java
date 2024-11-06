package rs.raf.student.aegisframework.web.pipeline.stage;

import rs.raf.student.aegisframework.core.pipeline.AbstractPipelineStage;
import rs.raf.student.aegisframework.web.server.Server;

public class RunServerPipelineStage extends AbstractPipelineStage {

    public RunServerPipelineStage() {
        super(WebPipelineStage.WEB_RUN_SERVER);
    }

    @Override
    public void perform() {
        Server.start();
    }

}
