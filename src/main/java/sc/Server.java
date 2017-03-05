package sc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.lang.*;
/**
 * Created by dyh on 17-3-4.
 */

public class Server extends Application{
    @Override
    public void start(Stage ps){
        TextArea ta = new TextArea();
        Scene scene = new Scene(new ScrollPane(ta),480,200);
        ps.setTitle("Server");
        ps.setScene(scene);
        ps.show();

        new Thread(()->{
            try{
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(()->ta.appendText("Server start at " + new Date() + "\n"));
                Socket socket = serverSocket.accept();
                DataInputStream ipfc = new DataInputStream(socket.getInputStream());
                DataOutputStream optc = new DataOutputStream(socket.getOutputStream());

                while(true){
                    double r = ipfc.readDouble();
                    double area = r * r * Math.PI;
                    optc.writeDouble(area);
                    Platform.runLater(()->{ta.appendText("radius from client: " + r +"\n");
                        ta.appendText("Area is: " + area +"\n");
                    });
                }
            }
            catch(Exception e){
              e.printStackTrace();
            }
        }).start();
    }
}
