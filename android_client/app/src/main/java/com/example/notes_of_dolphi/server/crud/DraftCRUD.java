package com.example.notes_of_dolphi.server.crud;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;
import com.example.notes_of_dolphi.server.crud.asyncTask.draft.Read;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DraftCRUD {


    public List<Draft> read() throws IOException, ClassNotFoundException, ExecutionException, InterruptedException
    {
        Read asynch_task_read = new Read();
        return asynch_task_read.execute().get();
    }


    public int count_records() throws ClassNotFoundException, ExecutionException, InterruptedException, IOException
    {
        List<Draft> list = read();

        if(list != null)
        return list.size();

        return 0;
    }
}
