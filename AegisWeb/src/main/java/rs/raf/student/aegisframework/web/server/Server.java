package rs.raf.student.aegisframework.web.server;

import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;

import java.net.ServerSocket;
import java.util.stream.IntStream;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class Server {

    private static final int PORT = 3031;

    @SneakyThrows
    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.print(new StringBuilder().appendFormattedLine("{0}: {1}{2}",
                                                                     "Aegis server running at".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                              .applyAttribute(Attribute.UNDERLINE),
                                                                     "http://localhost:".applyColorAttribute(Attribute.SET_FOREGROUND, Color.YELLOW)
                                                                                        .applyAttribute(Attribute.UNDERLINE),
                                                                     String.valueOf(PORT)
                                                                           .applyColorAttribute(Attribute.SET_FOREGROUND, Color.YELLOW)
                                                                           .applyAttribute(Attribute.UNDERLINE)));

            IntStream.iterate(0, connection -> connection + 1)
                     .forEach(connection -> ServerThread.create(serverSocket));
        }
    }

}
