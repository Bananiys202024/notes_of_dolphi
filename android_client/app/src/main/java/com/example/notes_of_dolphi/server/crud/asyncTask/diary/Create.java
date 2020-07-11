package com.example.notes_of_dolphi.server.crud.asyncTask.diary;

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

public class Create extends AsyncTask<Note, Void, String> {
    @Override
    protected String doInBackground(Note ... model_notes) {

            Note model_note = new Note();

            for(Note note : model_notes)
            {
                 model_note = note;
            }

            try {

            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());

            String logged_user = Cashe.getLogged_user();
            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            User user = new User();
            user.setEmail(logged_user);
            user.setId(0);

            Message message = new Message();
            message.setMessage("create");
            message.setUser(user);
            message.setNote(model_note);


            List<Message> request = new ArrayList();
            request.add(message);
            object_output_stream.writeObject(request);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);
            String result = (String) object_input_stream.readObject();
            //...

            return "Note successfully created";
        }
        catch(Exception e)
        {
            System.out.println("Error--"+e);
        }

        return "something wrong, note is not created";
    }
}
