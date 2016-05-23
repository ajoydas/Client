package sample;

import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Controller {

    public TextArea textArea;
    public TextField textInput;
    public Button bsend;
    DataOutputStream outToServer;
    BufferedReader inFromServer;
    Socket clientSocket;
    Service<Void> service;

    @FXML
    public void initialize() throws IOException {
        textArea.setEditable(false);
        textArea.appendText("\n");
        try {
            clientSocket = new Socket("localhost", 6789);
            textArea.appendText("Connected\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendButtonClicked(ActionEvent actionEvent) throws IOException {
        if(clientSocket.isClosed())
        {
            clientSocket = new Socket("localhost", 6789);
            textArea.appendText("Connected\n");
        }

        try {
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String sentence;
            String modifiedSentence;
            sentence = textInput.getText();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            textArea.appendText("From Server : " + modifiedSentence + "\n");
        } catch (Exception e) {
            clientSocket.close();
            textArea.appendText("Server Connection Lost\n");
            e.printStackTrace();
        }

    }
}
