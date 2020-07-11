
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