package rs.raf.student.aegisframework.web.server;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.net.ServerSocket;
import java.net.Socket;

@RequiredArgsConstructor
public class ServerThread implements Runnable {

    private final Socket socket;

    @Override
    @SneakyThrows
    public void run() {

    }

    @SneakyThrows
    public static void create(ServerSocket serverSocket) {
        new Thread(new ServerThread(serverSocket.accept())).start();
    }

}
