package com.example.notes_of_dolphi.server.synchronize;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.notes_of_dolphi.crud.DiaryCRUD;
import com.example.notes_of_dolphi.crud.DraftCRUD;
import com.example.notes_of_dolphi.crud.UsersCRUD;
import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.GetDatabaseFromServer;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.SocketCheckConnection;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.SocketForTableNotes;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.SocketForTableUser;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.SynchDeletedRecordsSocket;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.SynchEditedRecordsSocket;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.TotalSynchronization.SocketForTableDraftTotalSynch;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.TotalSynchronization.SocketForTableNotesTotalSynch;
import com.example.notes_of_dolphi.server.synchronize.asyncTask.TotalSynchronization.SocketForTableUserTotalSynch;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Synchronise  {

    Context context;

    public void synchronize_deleted_records(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException {

        UsersCRUD users_crud = new UsersCRUD();
        DiaryCRUD diary_crud = new DiaryCRUD();
        DraftCRUD draft_crud = new DraftCRUD();

        List<User> user_list_deleted_records = users_crud.read_deleted_records(mDatabase);
        List<Draft> draft_list_deleted_records = draft_crud.read_deleted_records(mDatabase);
        List<Note> note_list_deleted_records = diary_crud.read_deleted_records(mDatabase);

        String logged_user = Cashe.getLogged_user();

        User user = new User();
        user.setEmail(logged_user);

        Message message = new Message();
        message.setList_all_users(user_list_deleted_records);
        message.setList_all_drafts(draft_list_deleted_records);
        message.setList_all_notes(note_list_deleted_records);
        message.setMessage("synchronize_deleted_records");
        message.setUser(user);

        SynchDeletedRecordsSocket socket_deleted_records = new SynchDeletedRecordsSocket();
        Message msg = socket_deleted_records.execute(message).get();

        //save deleted records from server
        //set to column deleted true
        users_crud.create_deleted_record(mDatabase, msg.getList_all_users());
        diary_crud.create_deleted_record(mDatabase, msg.getList_all_notes());
        draft_crud.create_deleted_record(mDatabase, msg.getList_all_drafts());

        //get deleted notes from server;
    }


    public void synchronise_table_user(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException, SQLException {

        //finish client part
        UsersCRUD users_crud = new UsersCRUD();
        List<User> local_list_all_not_synchronized_users = users_crud.read_not_synchronized_users(mDatabase);

        if(local_list_all_not_synchronized_users != null)
        users_crud.put_mark_on_synchronized_users(local_list_all_not_synchronized_users, mDatabase);
        //..

        //finish server part
        SocketForTableUser socket_user = new SocketForTableUser();
        List<User> list_from_server = (List<User>) socket_user.execute(local_list_all_not_synchronized_users).get();

        for(User user : list_from_server)
        {
            users_crud.create(user, mDatabase);
        }
        //...

        }

    public void synchronise_table_notes(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException {


        //finish client part
        DiaryCRUD notes_crud = new DiaryCRUD();
        List<Note> local_list_all_not_synchronized_notes = notes_crud.read_not_synchronized_notes(mDatabase);
        if(local_list_all_not_synchronized_notes != null)
        notes_crud.put_mark_on_synchronized_notes(local_list_all_not_synchronized_notes, mDatabase);
        //..

        //finish server part
        SocketForTableNotes socket_notes = new SocketForTableNotes();
        List<Note> list_from_server =  socket_notes.execute(local_list_all_not_synchronized_notes).get();

        if(list_from_server != null)
        for(Note note : list_from_server)
        {
            notes_crud.create(note, mDatabase);
        }
        //...
    }

    public void totally_synchronisation_table_notes(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException {


        //finish client part
        DiaryCRUD notes_crud = new DiaryCRUD();
        List<Note> local_list_all_not_synchronized_notes = notes_crud.read_not_synchronized_notes(mDatabase);
        if(local_list_all_not_synchronized_notes != null)
            notes_crud.put_mark_on_synchronized_notes(local_list_all_not_synchronized_notes, mDatabase);
        //..

        //finish server part
        SocketForTableNotesTotalSynch socket_notes = new SocketForTableNotesTotalSynch();
        List<Note> list_from_server =  socket_notes.execute(local_list_all_not_synchronized_notes).get();

        if(list_from_server != null)
        for(Note note : list_from_server)
        {
            notes_crud.create_synch(note, mDatabase);
        }
        //...
    }
    // create 3 separated sockets
    // change message to server
    // on server create return for comadn: totally_synchronization; just send all notes;

    public void totally_synchronisation_table_user(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException, SQLException {

        //finish client part
        UsersCRUD users_crud = new UsersCRUD();
        List<User> local_list_all_not_synchronized_users = users_crud.read_not_synchronized_users(mDatabase);

        if(local_list_all_not_synchronized_users != null)
            users_crud.put_mark_on_synchronized_users(local_list_all_not_synchronized_users, mDatabase);
        //..

        System.out.println("Start socket");
        //finish server part
        SocketForTableUserTotalSynch socket_user = new SocketForTableUserTotalSynch();
        List<User> list_from_server = (List<User>) socket_user.execute(local_list_all_not_synchronized_users).get();
        System.out.println("End socket");

        if(list_from_server != null)
        for(User user : list_from_server)
        {
            users_crud.create_synch(user, mDatabase);
        }
        //...

    }

    public void totally_synchronisation_table_draft(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException {

        //finish client part
        DraftCRUD draft_crud = new DraftCRUD();
        List<Draft> local_list_all_not_synchronized_drafts = draft_crud.read_not_synchronized_drafts(mDatabase);
        if(local_list_all_not_synchronized_drafts != null)
        {
            for(Draft draft : local_list_all_not_synchronized_drafts) {
                draft_crud.put_mark_on_synchronized_drafts(draft, mDatabase);
            }
        }
        //..

        //finish server part
        SocketForTableDraftTotalSynch socket_notes = new SocketForTableDraftTotalSynch();
        List<Draft> list_from_server =  socket_notes.execute(local_list_all_not_synchronized_drafts).get();

        if(list_from_server != null)
        for(Draft draft : list_from_server)
        {
            draft_crud.create_synch(draft, mDatabase);
        }
        //...
    }


    public void synchronise(SQLiteDatabase mDatabase, Boolean synchronize_table_draft, Boolean synchronize_table_notes, Boolean synchronize_table_user) throws ExecutionException, InterruptedException, SQLException {

        Boolean is_user_table_empty = check_if_table_users_empty(mDatabase);
        Boolean is_draft_table_empty = check_if_table_draft_empty(mDatabase);
        Boolean is_note_table_empty = check_if_table_notes_empty(mDatabase);

        System.out.println("Checking boolean---"+is_user_table_empty+"-------"+synchronize_table_user);

//            synchronize_all_edited_records_in_database(mDatabase);

        System.out.println("mark1");
            if(is_user_table_empty)
            totally_synchronisation_table_user(mDatabase);

        System.out.println("mark2");
            if(is_draft_table_empty)
            totally_synchronisation_table_draft(mDatabase);

            System.out.println("mark3");
            if(is_note_table_empty)
            totally_synchronisation_table_notes(mDatabase);

            System.out.println("mark4");
            if(synchronize_table_draft && !is_draft_table_empty)
            synchronise_table_draft(mDatabase);


        System.out.println("mark6");
            if(synchronize_table_notes && !is_note_table_empty)
            synchronise_table_notes(mDatabase);


        System.out.println("mark6");
            if(synchronize_table_user && !is_user_table_empty)
            synchronise_table_user(mDatabase);

    }

    private void synchronize_all_edited_records_in_database(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException {

        UsersCRUD users_crud = new UsersCRUD();
        DiaryCRUD diary_crud = new DiaryCRUD();
        DraftCRUD draft_crud = new DraftCRUD();

        List<Draft> draft_list_edited_records = draft_crud.read_edited_records(mDatabase);
        List<Note> note_list_edited_records = diary_crud.read_edited_records(mDatabase);

        System.out.println("Mark778132");
        if(draft_list_edited_records != null)
        for(Draft draft : draft_list_edited_records)
        {
            System.out.println(draft.getDate_of_note());
        }

        String logged_user = Cashe.getLogged_user();

        User user = new User();
        user.setEmail(logged_user);

        Message message = new Message();
        message.setList_all_drafts(draft_list_edited_records);
        message.setList_all_notes(note_list_edited_records);
        message.setMessage("synchronize_all_edited_records");
        message.setUser(user);

        SynchEditedRecordsSocket socket_deleted_records = new SynchEditedRecordsSocket();
        Message msg = socket_deleted_records.execute(message).get();

        //save deleted records from server
        //set to column deleted true
        List<Note> list_all_notes = msg.getList_all_notes();
        List<Draft> list_all_drafts = msg.getList_all_drafts();

        if(list_all_notes != null)
        for(Note note : list_all_notes)
        diary_crud.create_edited_record(mDatabase, note);

        if(list_all_drafts != null)
        for(Draft draft : list_all_drafts)
        draft_crud.create_edited_record(mDatabase, draft);

    }

    private Boolean check_if_table_notes_empty(SQLiteDatabase mDatabase)
    {
        DiaryCRUD diary_crud = new DiaryCRUD();
        int size_table_note = diary_crud.count_records(mDatabase);
        return size_table_note == 0;
    }

    private Boolean check_if_table_draft_empty(SQLiteDatabase mDatabase)
    {
        DraftCRUD draft_crud = new DraftCRUD();
        int size_table_draft = draft_crud.count_records(mDatabase);
        return size_table_draft == 0;
    }

    private Boolean check_if_table_users_empty(SQLiteDatabase mDatabase)
    {
        UsersCRUD users_crud = new UsersCRUD();
        int size_table_user = users_crud.counte_records(mDatabase);
        return size_table_user == 0;
    }

    private void totally_synchronization(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException, SQLException {
        //get from server all lists;
        GetDatabaseFromServer server_database = new GetDatabaseFromServer();
        Message list_from_server_database = server_database.execute().get();

        //save to local database
        List<Draft> list_draft = list_from_server_database.getList_all_drafts();
        List<Note> list_note = list_from_server_database.getList_all_notes();
        List<User> list_user = list_from_server_database.getList_all_users();

        DraftCRUD draft_crud = new DraftCRUD();
        UsersCRUD users_crud = new UsersCRUD();
        DiaryCRUD diary_crud = new DiaryCRUD();

        if(list_draft != null)
        for(Draft draft : list_draft)
        {
            draft_crud.create(draft, mDatabase);
        }

        if(list_note != null)
        for(Note note : list_note)
        {
            diary_crud.create(note, mDatabase);
        }

        if(list_user != null)
        for(User user : list_user)
        {
            users_crud.create(user, mDatabase);
        }

    }

    public void synchronise_table_draft(SQLiteDatabase mDatabase) throws ExecutionException, InterruptedException {

        //finish client part
        DraftCRUD draft_crud = new DraftCRUD();
        List<Draft> local_list_all_not_synchronized_drafts = draft_crud.read_not_synchronized_drafts(mDatabase);
        if(local_list_all_not_synchronized_drafts != null)
        {
            for(Draft draft : local_list_all_not_synchronized_drafts)
            {
                draft_crud.put_mark_on_synchronized_drafts(draft, mDatabase);
            }
        }
        //..

        System.out.println("This list from synchronize_table_draft");
        for(Draft draft : local_list_all_not_synchronized_drafts) {
            System.out.println("Start");
            System.out.println(draft.getEdited_record());
            System.out.println(draft.getDate_of_note());
            System.out.println(draft.getDeleted());
            System.out.println(draft.getSynchronized_server());
            System.out.println("End");
        }
        //finish server part
        SocketForTableDraft socket_notes = new SocketForTableDraft();
        List<Draft> list_from_server =  socket_notes.execute(local_list_all_not_synchronized_drafts).get();

        if(list_from_server != null)
        for(Draft draft : list_from_server)
        {
            draft_crud.create(draft, mDatabase);
        }
        //...
    }

    public static boolean isSocketAliveUitlitybyCrunchify(String hostName, int port) {
        boolean isAlive = false;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;

        try {
            socket.connect(socketAddress, timeout);
            socket.close();
            isAlive = true;

        } catch (SocketTimeoutException exception) {
            System.out.println("Error--SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
        } catch (IOException exception) {
            System.out.println(
                    "Error---IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
        }
        return isAlive;
    }

    public boolean check_connection_with_server() throws IOException {

        try {
            SocketCheckConnection check_connection = new SocketCheckConnection();
            boolean result = check_connection.execute().get();
            return result;
        }
        catch(Exception e)
        {
            System.out.println("Error---"+e);
            return false;
        }

    }

}
