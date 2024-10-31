package rs.raf.student.aegisframework.utils.extension;

import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;

import java.text.MessageFormat;

@ExtensionMethod({StringANSIEscapeExtension.class})
public class StringBuilderExtension {


    public static StringBuilder appendLine(StringBuilder stringBuilder, String value) {
        return stringBuilder.append(value)
                            .append(System.lineSeparator());
    }

    public static StringBuilder appendFormatted(StringBuilder stringBuilder, String format, Object... values) {
        return stringBuilder.append(MessageFormat.format(format, values));
    }

    public static StringBuilder appendFormattedLine(StringBuilder stringBuilder, String format, Object... values) {
        return appendFormatted(stringBuilder, format, values).append(System.lineSeparator());
    }

    private static final int   SEPARATOR_LENGTH     = 160;
    private static final Color SEPARATOR_WIDE_COLOR = Color.SILVER;
    private static final Color SEPARATOR_COLOR      = Color.SILVER;
    private static final Color SEPARATOR_THIN_COLOR = Color.SILVER;

    public static StringBuilder appendSeparatorWide(StringBuilder stringBuilder) {
        return appendLine(stringBuilder, "=".repeat(SEPARATOR_LENGTH)
                                            .applyColorAttribute(Attribute.SET_FOREGROUND, SEPARATOR_WIDE_COLOR));
    }

    public static StringBuilder appendSeparator(StringBuilder stringBuilder) {
        return appendLine(stringBuilder, "-".repeat(SEPARATOR_LENGTH)
                                            .applyColorAttribute(Attribute.SET_FOREGROUND, SEPARATOR_COLOR));
    }

    public static StringBuilder appendSeparatorThin(StringBuilder stringBuilder) {
        return appendLine(stringBuilder, "·".repeat(SEPARATOR_LENGTH)
                                            .applyColorAttribute(Attribute.SET_FOREGROUND, SEPARATOR_THIN_COLOR));
    }

}
