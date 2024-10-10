import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class RequestHandler {

    private String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();
        var contentLength = FCGIInterface.request.inStream.available();
        var buffer = ByteBuffer.allocate(contentLength);
        var readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);
        var requestBodyRaw = new byte[readBytes];
        buffer.get(requestBodyRaw);
        buffer.clear();
        return new String(requestBodyRaw, StandardCharsets.UTF_8);
    }


    public boolean getPoint() throws IllegalArgumentException, IOException {
        String[] inputValues;
        double[] values = new double[3];
        String request = readRequestBody();

        try {
            inputValues = getRequest(request);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid input: Not enough arguments");
        }

        try {
            values[0] = Double.parseDouble(inputValues[0]);
            values[1] = Double.parseDouble(inputValues[1]);
            values[2] = Double.parseDouble(inputValues[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: Non-numeric values");
        }

        if (!(values[0] >= -3.0 && values[0] <= 3.0 &&
                values[1] >= -4.0 && values[1] <= 4.0 &&
                values[2] >= 1.0 && values[2] <= 5.0)) {
            throw new IllegalArgumentException("Invalid input: Non correct values");
        }
        return Checker.check(values[0], values[1], values[2]);
    }

    private String[] getRequest(String request) {
        String[] values = request.replace("{", "")
                .replace("}", "")
                .split(":");

        String x = values[1].split(",")[0].replace(",", ".");
        String y = values[2].split(",")[0].replace(",", ".");
        String r = values[3].split(",")[0].replace(",", ".");

        return new String[]{x, y, r};
    }
}
