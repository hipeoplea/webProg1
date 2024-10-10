import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseSender {
    private final RequestHandler requestHandler = new RequestHandler();
    boolean result;

    public void send() throws IOException {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            double startTime = System.currentTimeMillis();

            try {
                result = requestHandler.getPoint();
            } catch (IllegalArgumentException e) {
                System.out.println(getError(e.getMessage()));
                continue;
            }

            double totalTime = (System.currentTimeMillis() - startTime) / 1000;
            String httpResponse = getHttpResponse(result, totalTime);
            System.out.println(httpResponse);
        }
    }

    private String getHttpResponse(boolean result, double totalTime) {
        String content = String.format(
                "{\"isHit\": %s, \"time\": %.5f}",
                result ? "true" : "false", totalTime
        );

        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        int contentLength = contentBytes.length;

        return String.format(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: %d\r\n" +
                        "\r\n" +
                        "%s",
                contentLength,
                content
        );
    }


    private String getError(String message){
        String content = String.format(
                "{\"error\": %s}",
                message
        );

        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        int contentLength = contentBytes.length;

        return String.format(
                "HTTP/1.1 400 Bad Request\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: %d\r\n" +
                        "\r\n" +
                        "%s",
                contentLength,
                content
        );
    }
}