package client;

import java.io.IOException;

public class ThreadClientTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(!Thread.interrupted()){
            HelloClient helloClient = new HelloClient();
            helloClient.sendData("");
            Thread.sleep(1000);
        }
    }
}
