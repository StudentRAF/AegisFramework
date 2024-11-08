package rs.raf.student.aegisframework.web.server;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import rs.raf.student.aegisframework.web.http.HttpRequest;
import rs.raf.student.aegisframework.web.http.HttpResponse;

import java.net.ServerSocket;
import java.net.Socket;

@RequiredArgsConstructor
public class ServerThread implements Runnable {

    private final Socket socket;

    @Override
    @SneakyThrows
    public void run() {
        HttpRequest  request  = new HttpRequest(socket);
        HttpResponse response = new HttpResponse(socket, request);

        response.process();
        socket.close();
    }

    @SneakyThrows
    public static void create(ServerSocket serverSocket) {
        new Thread(new ServerThread(serverSocket.accept())).start();
    }

}
