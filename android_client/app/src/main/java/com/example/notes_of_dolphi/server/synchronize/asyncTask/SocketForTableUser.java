package com.example.notes_of_dolphi.server.synchronize.asyncTask;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.crud.DiaryCRUD;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Message;
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

public class SocketForTableUser extends AsyncTask<List<User>, Void, List<User>> {

    @Override
    protected List<User> doInBackground(List<User> ... list_all_users) {

        try
        {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());

            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            Message message = new Message();
            message.setMessage("synchronize_from_android_table_users");
            message.setList_all_users(list_all_users[0]);

            List<Message> request = new ArrayList();
            request.add(message);
            object_output_stream.writeObject(request);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            //add alert, how much notes was changed;
            List<User> result = (List<User>) object_input_stream.readObject();

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
