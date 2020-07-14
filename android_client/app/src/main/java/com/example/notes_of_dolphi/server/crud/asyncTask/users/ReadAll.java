package com.example.notes_of_dolphi.server.crud.asyncTask.users;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReadAll extends AsyncTask<Object, Void, List<Note>>
{


    List<Note> notes;

    @Override
    protected List<Note> doInBackground(Object[] objects) {

        try {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());
            String logged_user = Cashe.getLogged_user();

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
            List<Message> example_class = new ArrayList();

            User user = new User();
            user.setEmail(logged_user);

            Message message = new Message();
            message.setMessage("read_absolutely_all_notes");
            message.setUser(user);

            example_class.add(message);
            object_output_stream.writeObject(example_class);


            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);
            List<Note> notes = (List<Note>) object_input_stream.readObject();

            return notes;
        }
        catch(Exception e)
        {
            System.out.println("Error---"+e);
        }

        return null;
    }
}

