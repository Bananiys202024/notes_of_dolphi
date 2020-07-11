package com.example.notes_of_dolphi.server.crud.asyncTask.draft;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.User;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Read extends AsyncTask<User, Void, List<Draft>> {

    @Override
    protected List<Draft> doInBackground(User... users) {

        String logged_user = Cashe.getLogged_user();
        User model_user = new User();
        model_user.setEmail(logged_user);

        try {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());

            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            Message message = new Message();
            message.setMessage("read_all_records_draft_table");
            message.setUser(model_user);

            List<Message> example_class = new ArrayList();
            example_class.add(message);
            object_output_stream.writeObject(example_class);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            List<Draft> result = (List<Draft>) object_input_stream.readObject();
            //...

            return result;
        }
        catch(Exception e)
        {
            System.out.println("Error--"+e);
        }

        return null;
    }


}
