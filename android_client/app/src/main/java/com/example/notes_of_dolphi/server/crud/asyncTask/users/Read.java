package com.example.notes_of_dolphi.server.crud.asyncTask.users;

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

public class Read extends AsyncTask<User, Void, Boolean> {
    @Override
    protected Boolean doInBackground(User... users) {

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
            message.setMessage("check_if_user_exist_for_process_login");
            message.setUser(model_user);

            List<Message> example_class = new ArrayList();
            example_class.add(message);
            object_output_stream.writeObject(example_class);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            Boolean result = (Boolean) object_input_stream.readObject();
            //...

            return result;
        }
        catch(Exception e)
        {
            System.out.println("Error--"+e);
        }

        return false;

    }
}
