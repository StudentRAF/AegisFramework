package rs.raf.student.aegisframework.core.scanner;

import rs.raf.student.aegisframework.utils.ClassUtils;

public class LibraryScanner {

    public static void scan() {
        AnnotationScanner.getLibraryClasses()
                         .forEach(ClassUtils::loadClass);
    }

}
