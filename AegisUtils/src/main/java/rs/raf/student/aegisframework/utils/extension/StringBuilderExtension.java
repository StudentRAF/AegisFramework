package rs.raf.student.aegisframework.utils.extension;

import java.text.MessageFormat;

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

}
