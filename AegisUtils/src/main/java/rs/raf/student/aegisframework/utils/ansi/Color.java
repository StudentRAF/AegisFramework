package rs.raf.student.aegisframework.utils.ansi;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Color {

    BLACK  (0),
    MAROON (1),
    GREEN  (2),
    OLIVE  (3),
    NAVY   (4),
    PURPLE (5),
    TEAL   (6),
    SILVER (7),
    GREY   (8),
    RED    (9),
    LIME   (10),
    YELLOW (11),
    BLUE   (12),
    FUCHSIA(13),
    AQUA   (14),
    WHITE  (15);

    private final int code;

    public int code() {
        return code;
    }

}
