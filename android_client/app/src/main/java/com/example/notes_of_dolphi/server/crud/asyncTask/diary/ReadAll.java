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

        System.out.println("Did start 3");

        User model_user = new User();

        for(User note : users)
        {
            model_user = note;
        }

        System.out.println("Did start 4");

        try {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());
            System.out.println("Did open--1");

            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            Message message = new Message();
            message.setMessage("read_all_users");
            message.setUser(model_user);
            System.out.println("Did open--2");

            List<Message> example_class = new ArrayList();
            example_class.add(message);
            object_output_stream.writeObject(example_class);
            //..
            System.out.println("Did open--3");

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            List<User> result = (List<User>) object_input_stream.readObject();
            //...
            System.out.println("Did open--4");

            return result;
        }
        catch(Exception e)
        {
            System.out.println("Error--"+e);
        }

        return null;

    }
}