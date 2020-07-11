package com.example.notes_of_dolphi.server.synchronize.asyncTask;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class GetDatabaseFromServer extends AsyncTask<Message, Void, Message>
{
    @Override
    protected Message doInBackground(Message ... mesages) {

        try
        {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());

            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            Message message = new Message();
            message.setMessage("totally_synchronization");

            List<Message> request = new ArrayList();
            request.add(message);
            object_output_stream.writeObject(request);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            //add alert, how much notes was changed;
            Message result = (Message) object_input_stream.readObject();


            System.out.println("Result ----"+ result);
            System.out.println("Result ----list_all_drafts-----"+ result.getList_all_drafts());
            System.out.println("Result ----list_all_notes-----"+ result.getList_all_notes());
            System.out.println("Result ----list_all_users-----"+ result.getList_all_users());

            return result;

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("---Error_---"+e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("---Error_---"+e);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("---Error_---"+e);

        }

        return null;
    }
}
