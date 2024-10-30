package rs.raf.student.aegisframework.utils.extension;

import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;

public class StringANSIEscapeExtension {

    public static String applyAttribute(String string, Attribute attribute) {
        return new StringBuilder(string).insert(0, attribute.apply())
                                        .append(attribute.clear())
                                        .toString();
    }

    public static String applyColorAttribute(String string, Attribute attribute, Color color) {
        return new StringBuilder(string).insert(0, attribute.set(color))
                                        .append(attribute.clear())
                                        .toString();
    }

}
