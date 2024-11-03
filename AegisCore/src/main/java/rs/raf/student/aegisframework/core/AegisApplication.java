package rs.raf.student.aegisframework.core;

import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.core.annotation.AegisApplicationRoot;
import rs.raf.student.aegisframework.core.pipeline.PipelineManager;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class AegisApplication {

    @Getter
    private static String basePackage;

    public static void run(Class<?> appClass, String[] args) {
        AegisApplicationRoot application = appClass.getAnnotation(AegisApplicationRoot.class);

        if (application == null)
            throw new RuntimeException("Application entry point class must be annotated with @Application");

        System.out.print(new StringBuilder().appendSeparatorWide()
                                            .appendFormattedLine("     {0}",
                                                                 application.name()
                                                                            .applyColorAttribute(Attribute.SET_FOREGROUND, Color.NAVY)
                                                                            .applyAttribute(Attribute.BOLD))
                                            .appendSeparatorWide());

        basePackage = appClass.getPackageName();

        PipelineManager.start();
    }

}
