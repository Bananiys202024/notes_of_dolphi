package com.example.notes_of_dolphi.server.synchronize.asyncTask;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class SocketCheckConnection extends AsyncTask<Void, Void, Boolean>
{


    @Override
    protected Boolean doInBackground(Void... voids) {



        try {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());
            socket.setSoTimeout(10000);

System.out.println("sendin resources check connection");
            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            Message message = new Message();
            message.setMessage("Check connection");

            List<Message> request = new ArrayList();
            request.add(message);
            object_output_stream.writeObject(request);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            //add alert, how much notes was changed;
            String result = (String) object_input_stream.readObject();


            System.out.println("finish resources check connection"+ result);

            if (result.equals("we have connection with server"))
                return true;

            return false;

        } catch (UnknownHostException e) {
            System.out.println("Error---"+e);
        } catch (IOException e) {
            System.out.println("Error---"+e);
        } catch (ClassNotFoundException e) {
            System.out.println("Error---"+e);
        }

        return null;

/*


        try
        {
            InetSocketAddress sockAdr = new InetSocketAddress(Constants.getIp_host(), Constants.getPORT());
            Socket socket = new Socket();
            int timeout = 1000;
            socket.connect(sockAdr, timeout);

            boolean result = socket.isConnected();
            socket.close();
            return result;
        }
        catch(Exception e)
        {
            System.out.println("Error----"+e);
            return false;
        }
*/

    }


}
