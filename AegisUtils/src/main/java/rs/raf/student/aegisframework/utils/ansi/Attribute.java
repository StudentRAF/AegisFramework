package rs.raf.student.aegisframework.utils.ansi;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public enum Attribute {

    RESET                    (      0),
    BOLD                     (  1, 22),
    FAINT                    (  2, 22),
    ITALIC                   (  3, 23),
    UNDERLINE                (  4, 24),
    SLOW_BLINK               (  5, 25),
    RAPID_BLINK              (  6, 25),
    REVERSE                  (  7, 27),
    HIDE                     (  8, 28),
    STRIKE                   (  9, 29),
    BLACK_FOREGROUND         ( 30, 39),
    RED_FOREGROUND           ( 31, 39),
    GREEN_FOREGROUND         ( 32, 39),
    YELLOW_FOREGROUND        ( 33, 39),
    BLUE_FOREGROUND          ( 34, 39),
    MAGENTA_FOREGROUND       ( 35, 39),
    CYAN_FOREGROUND          ( 36, 39),
    WHITE_FOREGROUND         ( 37, 39),
    SET_FOREGROUND           ( 38, 39),
    BLACK_BACKGROUND         ( 40, 49),
    RED_BACKGROUND           ( 41, 49),
    GREEN_BACKGROUND         ( 42, 49),
    YELLOW_BACKGROUND        ( 43, 49),
    BLUE_BACKGROUND          ( 44, 49),
    MAGENTA_BACKGROUND       ( 45, 49),
    CYAN_BACKGROUND          ( 46, 49),
    WHITE_BACKGROUND         ( 47, 49),
    SET_BACKGROUND           ( 48, 49),
    SET_UNDERLINE            ( 58, 59),
    BRIGHT_BLACK_FOREGROUND  ( 90, 39),
    BRIGHT_RED_FOREGROUND    ( 91, 39),
    BRIGHT_GREEN_FOREGROUND  ( 92, 39),
    BRIGHT_YELLOW_FOREGROUND ( 93, 39),
    BRIGHT_BLUE_FOREGROUND   ( 94, 39),
    BRIGHT_MAGENTA_FOREGROUND( 95, 39),
    BRIGHT_CYAN_FOREGROUND   ( 96, 39),
    BRIGHT_WHITE_FOREGROUND  ( 97, 39),
    BRIGHT_BLACK_BACKGROUND  (100, 49),
    BRIGHT_RED_BACKGROUND    (101, 49),
    BRIGHT_GREEN_BACKGROUND  (102, 49),
    BRIGHT_YELLOW_BACKGROUND (103, 49),
    BRIGHT_BLUE_BACKGROUND   (104, 49),
    BRIGHT_MAGENTA_BACKGROUND(105, 49),
    BRIGHT_CYAN_BACKGROUND   (106, 49),
    BRIGHT_WHITE_BACKGROUND  (107, 49);

    private final int applyCode;
    private final int clearCode;

    Attribute(int applyCode) {
        this(applyCode, 0);
    }

    public String apply() {
        return MessageFormat.format("\u001B[{0}m", applyCode);
    }

    public String set(Color color) {
        return MessageFormat.format("\u001B[{0};5;{1}m", applyCode, color.code());
    }

    public String clear() {
        return MessageFormat.format("\u001B[{0}m", clearCode);
    }

}
