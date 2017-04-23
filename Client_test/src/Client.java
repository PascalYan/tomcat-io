import com.me.count.ResultCount;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by young on 2017/4/2.
 */
public class Client implements Runnable {
    static int port = 8888;
    static int requestTime = 20000;
    static int testTimes = 1;
    static int capTimes = 100000;



    Socket socket;
    PrintWriter writer;
    BufferedReader reader;

    @Override
    public void run() {
        try {
            long start=System.currentTimeMillis();
            socket = new Socket("127.0.0.1", port);
            request(socket);
            response(socket);
            ResultCount.oneRequestCostTime(System.currentTimeMillis()-start);
        } catch (ConnectException e) {
            ResultCount.addfailView(1,e.getMessage());
        } catch (IOException e) {
            ResultCount.addfailView(1,e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        for(int i=0;i<testTimes;i++) {
            ResultCount.reStart();
            for (int j = 0; j < requestTime; j++) {
                new Thread(new Client(), "client" +i+"-"+ j).start();
            }
//            ResultCount.print();
//            Thread.sleep(capTimes);
        }

        Runtime.getRuntime().addShutdownHook(new ResultCount());
    }

    public void request(Socket socket) {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("hello");
            writer.println("end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void response(Socket socket) {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            String response = "";
            while (!(line = reader.readLine()).equals("end")) {
                response += line;
            }
            System.out.println("recv response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
                if (reader != null) reader.close();
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

