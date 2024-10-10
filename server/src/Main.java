import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ResponseSender program = new ResponseSender();
        while (true) {
            program.send();
        }
    }
}