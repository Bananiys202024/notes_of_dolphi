package com.example.notes_of_dolphi.server.crud;


import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.User;
import com.example.notes_of_dolphi.server.crud.asyncTask.diary.ReadAll;
import com.example.notes_of_dolphi.server.crud.asyncTask.users.Create;
import com.example.notes_of_dolphi.server.crud.asyncTask.users.Read;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsersCRUD {

    public String create(User user) throws ExecutionException, InterruptedException {
        Create asynch_task_create = new Create();
        return asynch_task_create.execute(user).get();
    }

    //Better to say: check user
    public Boolean read(User user) throws ExecutionException, InterruptedException {
        Read asynch_task_read = new Read();
        return asynch_task_read.execute(user).get();
    }

    public String update()
    {
        return null;
    }

    public String delete()
    {
        return null;
    }

    //Better to say: check user
    public List<User> read_all_users(User user) throws ExecutionException, InterruptedException {
        System.out.println("Did start 2");

        ReadAll asynch_task_read = new ReadAll();
        System.out.println("Did start 2..5");

        return asynch_task_read.execute(user).get();
    }

    public int count_records() throws ExecutionException, InterruptedException {
        String logged_user = Cashe.getLogged_user();

        User user = new User();
        user.setEmail(logged_user);
        List<User> list = read_all_users(user);
        if(list != null)
        return list.size();

        return 0;
    }
}
