package com.example.david.simplegamecontrol;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /* This is other form to handle mouse click events*/
    public void onUp (View v){
        // Send up message
        new SendCommandTask().execute("up");
    }

    public void onLeft (View v){
        // Send left message
        new SendCommandTask().execute("left");
    }

    public void onReset (View v){
        // Send reset message
        new SendCommandTask().execute("reset");
    }

    public void onRight (View v){
        // Send right message
        new SendCommandTask().execute("right");
    }

    public void onDown (View v){
        // Send down message
        new SendCommandTask().execute("down");
    }

    private class SendCommandTask extends AsyncTask<String, Integer, String> {
        private DatagramSocket socket;
        private InetAddress screenAddress;
        private  int screenPort = 5000;

        @Override
        protected String doInBackground(String... params) {
            if(socket==null){
                // Initialize the socket only if it has not been initialized
                try{

                    ///Asigne el puerto
                    socket = new DatagramSocket();
                }catch (SocketException e){
                    Log.e("Error","Error opening the socket");
                }
            }

            if(screenAddress==null){
                // Initialize the server IP address only if it has not been initialized
                try{
                    screenAddress = InetAddress.getByName("10.0.2.2");
                }catch (UnknownHostException e){
                    Log.e("Error","Screen address not found");
                }
            }

            byte[] message = params[0].getBytes();

            try {


    DatagramPacket paquete =  new DatagramPacket(message,message.length,screenAddress,screenPort);

                socket.send(paquete);

            } catch (IOException e) {
                e.printStackTrace();
            }

            String result = "A new "+params[0]+" command was send";
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            // Update the GUI with the operation results
         //  Toast popUp =Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG);
        //    popUp.show();
        }
    }

}
