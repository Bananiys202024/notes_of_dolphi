package com.example.notes_of_dolphi.server.synchronize.asyncTask;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class SynchDeletedRecordsSocket extends AsyncTask<Message, Void, Message>
{



    @Override
    protected Message doInBackground(Message... messages)
    {
        try
        {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());

            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            String logged_user = Cashe.getLogged_user();

            List<Message> request = new ArrayList();
            request.add(messages[0]);
            object_output_stream.writeObject(request);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            //add alert, how much notes was changed;
            Message result = (Message) object_input_stream.readObject();

            return result;

        } catch (UnknownHostException e) {
            System.out.println("Error---"+e);
        } catch (IOException e) {
            System.out.println("Error---"+e);
        } catch (ClassNotFoundException e) {
            System.out.println("Error---"+e);
        }

        return null;


    }



}
