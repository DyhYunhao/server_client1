package sc;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by dyh on 17-3-4.
 */
public class Client extends Application{
    DataOutputStream tos = null;
    DataInputStream froms = null;
    @Override
    public void start(Stage s){
        BorderPane p = new BorderPane();
        p.setPadding(new Insets(5,5,5,5));
        p.setStyle("-fx-border-color: green");
        p.setLeft(new Label("Enter radius: "));
        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        p.setCenter(tf);
        BorderPane mp = new BorderPane();
        TextArea ta = new TextArea();
        mp.setCenter(new ScrollPane(ta));
        mp.setTop(p);
        Scene scene = new Scene(mp,480,200);
        s.setTitle("Client");
        s.setScene(scene);
        s.show();
        tf.setOnAction(e->{
            try{
               double r = Double.parseDouble(tf.getText().trim());
               tos.writeDouble(r);
               tos.flush();
               double area = froms.readDouble();
               ta.appendText("Radius is: "+r+"\n");
               ta.appendText("Area is: "+area+"\n");
            }
            catch(Exception ex){
                System.err.println(ex);
            }
        });
        try{
            Socket socket = new Socket("localhost",8000);
            froms = new DataInputStream(socket.getInputStream());
            tos = new DataOutputStream(socket.getOutputStream());
        }catch(IOException es){
            ta.appendText(es.toString() + "\n");
        }
    }
}
