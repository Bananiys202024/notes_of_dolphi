package com.example.notes_of_dolphi.server.synchronize.asyncTask.TotalSynchronization;

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

public class SocketForTableNotesTotalSynch extends AsyncTask<List<Note>, Void, List<Note>>
{


    @Override
    protected List<Note> doInBackground(List<Note>... lists) {

        try
        {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());

            //send request
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);

            String logged_user = Cashe.getLogged_user();

            User user = new User();
            user.setEmail(logged_user);

            Message message = new Message();
            message.setMessage("totally_synchronize_from_android_table_notes");
            message.setList_all_notes(lists[0]);
            message.setUser(user);

            List<Message> request = new ArrayList();
            request.add(message);
            object_output_stream.writeObject(request);
            //..

            //get response
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);

            //add alert, how much notes was changed;
            List<Note> result = (List<Note>) object_input_stream.readObject();

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
