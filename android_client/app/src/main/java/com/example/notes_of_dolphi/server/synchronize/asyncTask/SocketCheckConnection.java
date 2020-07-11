package com.example.notes_of_dolphi.server.synchronize.asyncTask;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Draft;

import java.net.Socket;
import java.util.List;

public class SocketCheckConnection extends AsyncTask<Void, Void, Boolean>
{


    @Override
    protected Boolean doInBackground(Void... voids) {


        try
        {
            Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());
            boolean result = socket.isConnected();
            socket.close();
            return result;
        }
        catch(Exception e)
        {
            System.out.println("Error----"+e);
            return false;
        }


    }


}
