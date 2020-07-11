package com.example.notes_of_dolphi.server.crud.asyncTask.diary;

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
import java.util.ArrayList;
import java.util.List;

public class Delete extends AsyncTask<Integer, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Integer... integers) {

        int id = 0;

        for(int id_loop : integers)
        {
            id = id_loop;
        }

        Socket socket = null;
        try {
            socket = new Socket(Constants.getIp_host(), Constants.getPORT());

        String logged_user = Cashe.getLogged_user();

        //send request
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

        User user  = new User();
        user.setEmail(logged_user);
        user.setId(id);

        Message message = new Message();
        message.setUser(user);
        message.setMessage("delete");

        List<Message> example_class = new ArrayList();
        example_class.add(message);
        object_output_stream.writeObject(example_class);
        //..

        //get response
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

        String result = (String) object_input_stream.readObject();
        return true;

        } catch (IOException e) {
         System.out.println("Error---"+e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;

    }

}
