mvn -offline install <br>
java -cp target/server.part-0.0.1-SNAPSHOT-jar-with-dependencies.jar notes.of.dolphi.server.part.Main
  
mvn -offline install; java -cp target/server.part-0.0.1-SNAPSHOT-jar-with-dependencies.jar notes.of.dolphi.server.part.Main

+3 finish synchronization of deleted records;


when make simple synchronization need to check by column delete;
if record deleted then miss that

make outpu traces and totallz clear and reload appö

after create second record appear problems

Recreate entire app(clean) and fix problem with delete second record;



Make ability to open and close database;

1. Check process of delete for local database
2. Fix problem with socek.isConnected for synch and statistic
3. add column synchronized_deleted_notes to server/client;
4. In synchronization_delete method check synchronization by that method;
  4.1  get local list not synchronized_deleted 
  4.2  put mark on that list
  4.3  send to server
  4.4. on server find that notes on server with help of read_by_date_of_note
  4.5  put mark on those notes on server(UPDATE)
  4.6  Send not synchronized list of deleted recordse from server
  4.7  Find that notes on client read_by_date_of_note
  4.8 put marks as synchronized to true and change column deleted to true;
  finish
  

and check synchronization by that column



Later: Add synchronization by time
and add one more filter, if records created before pointed time then ignore those recordse;

GET DELETD NOTE FROM SERVER;

synchronize_deleted_records

Change names of tables ;


 in synchronization process 
 add filters by column deleted on server/client side;
 
 
 While registration make email is unique
 
 find library for synchronization;
 
 
 //1
 just delete record
 
 //2
 try send on server records with 
 deleted: true;
 
 
 Refactor code;
 
 Add different test, especially for synchronization;
 
 
 
 
 add function delete
 and add delet eability to synchronization;
 
 make offline;
 
 
 To do library with synchronization;
 
 
 .. refactor code

Fix process of delete;
Add ability  atomicity to process of synchronization;

Refactor synchronization;
synchronization must include:
1.	send local database to server
    and on server save local database 
    to server and don't forget to change
    column synchronized with android to
    true; Don't forget to change column
    on server side and client side 
2.  get list from server and save 
 	to local database and don't 
 	forget to change column on server 
 	side and client side
3. 4 parts of synchronizations:
		2 synch from client;
		2 synch from server;

change process of deleted;
because now -> create problem with synchronization of deleted records;

fix problem with synchronization

//List<Object> list_objects, int size_of_list;

Just add new function:
If on client side 
all tables and columns equals to zero ->
request function of restore database
it will send all notes from server database to client side

if(records in database equals 0)
restore database






Later:
if number of records in database does not match;
Then make synchronysation;


registration
.. 	add ability to load smth;

..  add display of date of notes;

delete notes not synchronized;


.. add ability to search

.. add load icon

.. crud registration/login

...  add display of local database to application;
... make separate method read for synchronization of process 
 Next task:
 Synchronize notes;
 make ability to make notes offline and add button for synchronization;
 
 Check if columns synchronized equals to true;
 Reverse list;
 Add ability of registration new user;
 Make add notes offline; just save to local database
 Add button of synchronization;
 Make design of side menu;
 Add tab "draft"
 Rfactor code;
 
 Later:
 Add warning like registration only with internet connection;
 