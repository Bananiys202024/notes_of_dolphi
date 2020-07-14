package com.example.notes_of_dolphi.server.synchronize.asyncTask;

import android.os.AsyncTask;

import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Draft;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class SocketCheckConnection extends AsyncTask<Void, Void, Boolean>
{


    @Override
    protected Boolean doInBackground(Void... voids) {


        try
        {


            InetSocketAddress sockAdr = new InetSocketAddress(Constants.getIp_host(), Constants.getPORT());
            Socket socket = new Socket();
            int timeout = 1000;
            socket.connect(sockAdr, timeout);


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
