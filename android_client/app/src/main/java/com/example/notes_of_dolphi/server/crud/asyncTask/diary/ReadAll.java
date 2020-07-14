package com.example.notes_of_dolphi.server.crud.asyncTask.diary;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.User;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReadAll extends AsyncTask<User, Void, List<User>> {
    @Override
    protected List<User> doInBackground(User... users) {

        User model_user = new User();

        for(User note : users)
        {
            model_user = note;
        }

        try {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());

            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            Message message = new Message();
            message.setMessage("read_all_users");
            message.setUser(model_user);

            List<Message> example_class = new ArrayList();
            example_class.add(message);
            object_output_stream.writeObject(example_class);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            List<User> result = (List<User>) object_input_stream.readObject();
            //...

            return result;
        }
        catch(Exception e)
        {
            System.out.println("Error---"+e);
        }

        return null;

    }
}