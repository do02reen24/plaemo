package com.po771.plaemo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.po771.plaemo.DB.DbSchema.*;
import com.po771.plaemo.item.Item_AlarmList;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_folder;
import com.po771.plaemo.item.Item_memo;

import static com.po771.plaemo.DB.DbSchema.AlarmTable.Cols.ALARMTONE;
import static com.po771.plaemo.DB.DbSchema.AlarmTable.Cols.SNOOZE;

public class BaseHelper extends SQLiteOpenHelper {

    public static BaseHelper baseHelper = null;
    private static SQLiteDatabase db;

    public static BaseHelper getInstance(Context context){ // 싱글턴 패턴으로 구현하였다.
        if(baseHelper == null){
            baseHelper = new BaseHelper(context.getApplicationContext());
            db = baseHelper.getWritableDatabase();
        }

        return baseHelper;
    }

    private static final int version = 1;
    private static final String DATABASE_NAME = "po771.db";
    public BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase create_db) {

        //알람 테이블
        create_db.execSQL("create table " + AlarmTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AlarmTable.Cols.ALARMNAME + ", " +
                AlarmTable.Cols.HOUR + ", " +
                AlarmTable.Cols.MINUTE + ", " +
                AlarmTable.Cols.REPEAT + ", " +
                AlarmTable.Cols.DAYSOFTHEWEEK + ", " +
                AlarmTable.Cols.ON + ", " +
                AlarmTable.Cols.ALARMTONE + ", " +
                AlarmTable.Cols.SNOOZE + ", " +
                AlarmTable.Cols.VIBRATE + ", " +
                AlarmTable.Cols.AMPM +
                ")"
        );

        //책 테이블
        create_db.execSQL("create table " + BookList.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BookList.Cols.BOOKNAME + ", " +
                BookList.Cols.BOOKURI + ", " +
                BookList.Cols.CURRNETPAGE + ", " +
                BookList.Cols.TOTALPAGE + ", " +
                BookList.Cols.BOOKINFO + ", " +
                BookList.Cols.STAR + "," +
                BookList.Cols.FOLDER + "," +
                BookList.Cols.BOOKIMAGE +
                ")"
        );

        //메모 테이블
        create_db.execSQL("create table " + BookMemo.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BookMemo.Cols.BOOKID + ", " +
                BookMemo.Cols.PAGESTART + ", " +
                BookMemo.Cols.PAGEEND + ", " +
                BookMemo.Cols.CONTENT + ", " +
                BookMemo.Cols.DATA +
                ")"
        );

        //폴더 테이블
        create_db.execSQL("create table " + Folder.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Folder.Cols.FOLDERNAME+ ", " +
                BookMemo.Cols.BOOKID +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

   ///////////////////////////////api들 ////////////////////////////////
//   public void initDB2(){ // 추가로 테스트해보고 싶으면.

    public boolean initDB(){ // 최초 설치시만 작동하도록

        String query = "SELECT count(*) FROM "+Folder.NAME;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                return false;
            } else {
                //////////////////////////메모리스트////////////////////////////////
                Item_memo memolist = new Item_memo();

                memolist.setBoook_id(1);
                memolist.setPage_start(789);
                memolist.setContent("안녕하세요. 메모입니다.");
                memolist.setDate("2019-11-12 10:00:35");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(1);
                memolist.setPage_start(123);
                memolist.setContent("어려워요 ㅜㅜㅜ. 메모입니다.");
                memolist.setDate("2019-11-20 09:24:00");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(2);
                memolist.setPage_start(456);
                memolist.setContent("마지막. 메모입니다.");
                memolist.setDate("2019-11-30 12:41:00");
                baseHelper.insertMemoList(memolist);
                /////////////////////////////////////////////////////////////////////////

                Item_AlarmList alarmList = new Item_AlarmList();

                alarmList.setAlarm_name("복습");
                alarmList.setHour(18);
                alarmList.setMinute(20);
                alarmList.setIson(1);
                alarmList.setVibrate(1);
                baseHelper.insertAlarmList(alarmList);

                alarmList.setAlarm_name("논문 읽기");
                alarmList.setHour(20);
                alarmList.setMinute(03);
                alarmList.setIson(0);
                alarmList.setVibrate(0);
                baseHelper.insertAlarmList(alarmList);

                alarmList.setAlarm_name("알람아 제발 들어가줘");
                alarmList.setHour(19);
                alarmList.setMinute(50);
                alarmList.setIson(1);
                alarmList.setVibrate(1);
                baseHelper.insertAlarmList(alarmList);

//                alarmList.setAlarm_name("알람아 제발 들어가줘");
//                alarmList.setHour(19);
//                alarmList.setMinute(50);
//                alarmList.setIson(1);
//                baseHelper.insertAlarmList(alarmList);
//
//                alarmList.setAlarm_name("알람아 제발 들어가줘");
//                alarmList.setHour(19);
//                alarmList.setMinute(50);
//                alarmList.setIson(1);
//                baseHelper.insertAlarmList(alarmList);
//
//                alarmList.setAlarm_name("알람아 제발 들어가줘");
//                alarmList.setHour(19);
//                alarmList.setMinute(50);
//                alarmList.setIson(1);
//                baseHelper.insertAlarmList(alarmList);
//
//                alarmList.setAlarm_name("알람아 제발 들어가줘");
//                alarmList.setHour(19);
//                alarmList.setMinute(50);
//                alarmList.setIson(1);
//                baseHelper.insertAlarmList(alarmList);
//
//                alarmList.setAlarm_name("알람아 제발 들어가줘");
//                alarmList.setHour(19);
//                alarmList.setMinute(50);
//                alarmList.setIson(1);
//                baseHelper.insertAlarmList(alarmList);
//
//                alarmList.setAlarm_name("알람아 제발 들어가줘");
//                alarmList.setHour(19);
//                alarmList.setMinute(50);
//                alarmList.setIson(1);
//                baseHelper.insertAlarmList(alarmList);
//
//                alarmList.setAlarm_name("알람아 제발 들어가줘");
//                alarmList.setHour(19);
//                alarmList.setMinute(50);
//                alarmList.setIson(1);
//                baseHelper.insertAlarmList(alarmList);
//
//                alarmList.setAlarm_name("알람아 제발 들어가줘");
//                alarmList.setHour(19);
//                alarmList.setMinute(50);
//                alarmList.setIson(1);
//                baseHelper.insertAlarmList(alarmList);

                memolist.setBoook_id(1);
                memolist.setPage_start(789);
                memolist.setPage_end(987);
                memolist.setContent("안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래에 있는 애만 이상하게 안되는 거 같더라구요. 왜그러는 걸까요 ㅜㅜ 저는 개발을 잘하고 싶어요~ 돈도 많이 벌고 싶어요. 그래서 이렇게 데이터를 집어넣는 중이랍니다.");
                memolist.setDate("2019-11-12 10:00:35");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(2);
                memolist.setPage_start(123);
                memolist.setPage_end(321);
                memolist.setContent("어려워요 ㅜㅜㅜ. 메모입니다. 여기에는 내용을 진짜 많이 입력할 수 있어요. 어디까지 입력을 받을 수 있는 지 볼까요??!! 아마 여기까지는 전부다 보일겁니다~ 잘보이시죠? 문장을 길게 쓴다는 일은 참 어려운 거 같아요. 오늘 점심으로 저는 치즈돈까스를 먹었는데 너무 맛있었어요.");
                memolist.setDate("2019-11-20 09:24:00");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(2);
                memolist.setPage_start(456);
                memolist.setPage_end(654);
                memolist.setContent("마지막. 메모입니다. 내용이 초과되면 어디까지 출력되는지 보기위해서 내용을 많이 입력해보도록 하겠습니다. 내용이 초과될때는 한번 터치하면 전체를 출력하고 다시 터치하면 잘린 내용을 출력하도록 제가 한번 구현해보도록 하겠습니다.");
                memolist.setDate("2019-11-30 12:41:00");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(1);
                memolist.setPage_end(2);
                memolist.setContent("안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래에 있는 애만 이상하게 안되는 거 같더라구요. 왜그러는 걸까요 ㅜㅜ 저는 개발을 잘하고 싶어요~ 돈도 많이 벌고 싶어요. 그래서 이렇게 데이터를 집어넣는 중이랍니다.");
                memolist.setDate("2019-11-12 10:00:35");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(2);
                memolist.setPage_start(3);
                memolist.setPage_end(4);
                memolist.setContent("어려워요 ㅜㅜㅜ. 메모입니다. 여기에는 내용을 진짜 많이 입력할 수 있어요. 어디까지 입력을 받을 수 있는 지 볼까요??!! 아마 여기까지는 전부다 보일겁니다~ 잘보이시죠? 문장을 길게 쓴다는 일은 참 어려운 거 같아요. 오늘 점심으로 저는 치즈돈까스를 먹었는데 너무 맛있었어요.");
                memolist.setDate("2019-11-20 09:24:00");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(2);
                memolist.setPage_start(5);
                memolist.setPage_end(6);
                memolist.setContent("마지막. 메모입니다. 내용이 초과되면 어디까지 출력되는지 보기위해서 내용을 많이 입력해보도록 하겠습니다. 내용이 초과될때는 한번 터치하면 전체를 출력하고 다시 터치하면 잘린 내용을 출력하도록 제가 한번 구현해보도록 하겠습니다.");
                memolist.setDate("2019-11-30 12:41:00");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(4);
                memolist.setPage_start(7);
                memolist.setPage_end(8);
                memolist.setContent("안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래에 있는 애만 이상하게 안되는 거 같더라구요. 왜그러는 걸까요 ㅜㅜ 저는 개발을 잘하고 싶어요~ 돈도 많이 벌고 싶어요. 그래서 이렇게 데이터를 집어넣는 중이랍니다.");
                memolist.setDate("2019-11-12 10:00:35");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(3);
                memolist.setPage_start(11);
                memolist.setPage_end(12);
                memolist.setContent("어려워요 ㅜㅜㅜ. 메모입니다. 여기에는 내용을 진짜 많이 입력할 수 있어요. 어디까지 입력을 받을 수 있는 지 볼까요??!! 아마 여기까지는 전부다 보일겁니다~ 잘보이시죠? 문장을 길게 쓴다는 일은 참 어려운 거 같아요. 오늘 점심으로 저는 치즈돈까스를 먹었는데 너무 맛있었어요.");
                memolist.setDate("2019-11-20 09:24:00");
                baseHelper.insertMemoList(memolist);

                memolist.setBoook_id(4);
                memolist.setPage_start(43);
                memolist.setPage_end(355);
                memolist.setContent("마지막. 메모입니다. 내용이 초과되면 어디까지 출력되는지 보기위해서 내용을 많이 입력해보도록 하겠습니다. 내용이 초과될때는 한번 터치하면 전체를 출력하고 다시 터치하면 잘린 내용을 출력하도록 제가 한번 구현해보도록 하겠습니다.");
                memolist.setDate("2019-11-30 12:41:00");
                baseHelper.insertMemoList(memolist);
                return true;
            }
        }
        return true;
    }


    //////////////////folder
    public void insertFolder(Item_folder item_folder){
        ContentValues values = new ContentValues();
        values.put(Folder.Cols.BOOKID,item_folder.getBook_id());
        values.put(Folder.Cols.FOLDERNAME,item_folder.getFolder_name());
        db.insert(Folder.NAME,null,values);
    }
    public void insertMemoList(Item_memo memolist){
        ContentValues values = new ContentValues();
        values.put(BookMemo.Cols.BOOKID, memolist.getBoook_id());
        values.put(BookMemo.Cols.PAGESTART,memolist.getPage_start());
        values.put(BookMemo.Cols.CONTENT,memolist.getContent());
        values.put(BookMemo.Cols.DATA, memolist.getDate());
        db.insert(BookMemo.NAME,null,values);
    }

    public void insertAlarmList(Item_AlarmList alarmList){
        ContentValues values = new ContentValues();
//        values.put(AlarmTable.Cols.BOOKID,alarmList.get_id());
        values.put(AlarmTable.Cols.ALARMNAME,alarmList.getAlarm_name());
        values.put(AlarmTable.Cols.HOUR,alarmList.getHour());
        values.put(AlarmTable.Cols.MINUTE,alarmList.getMinute());
        values.put(AlarmTable.Cols.ON,alarmList.getIson());
        values.put(AlarmTable.Cols.VIBRATE,alarmList.getVibrate());
        db.insert(AlarmTable.NAME,null,values);
    }

    public void insertAlarmSet(Item_AlarmList alarmList){
        ContentValues values = new ContentValues();
//        values.put(AlarmTable.Cols.BOOKID,alarmList.get_id());
        values.put(AlarmTable.Cols.AMPM,alarmList.getAmpm());
        values.put(AlarmTable.Cols.HOUR,alarmList.getHour());
        values.put(AlarmTable.Cols.MINUTE,alarmList.getMinute());
        values.put(AlarmTable.Cols.ALARMNAME,alarmList.getAlarm_name());
        values.put(AlarmTable.Cols.VIBRATE,alarmList.getVibrate());
//        values.put(AlarmTable.Cols.SNOOZE,alarmList.getSnooze());
//        values.put(AlarmTable.Cols.REPEAT,alarmList.getRepeat());
        values.put(AlarmTable.Cols.ON,alarmList.getIson());
        db.insert(AlarmTable.NAME,null,values);
    }

    public void editAlarmOnOff(Item_AlarmList alarmList){
        db.execSQL("UPDATE "+
                AlarmTable.Cols.ALARMNAME+"SET "+AlarmTable.Cols.ON+" = "+ alarmList.getIson()+
                " WHERE _id = "+alarmList.get_id());
    }

    public Item_AlarmList getEditAlarmOnOff(int alarm_id){
        Item_AlarmList alarm = new Item_AlarmList();
        String query = "SELECT "+
                AlarmTable.Cols.ON+
                " FROM "+AlarmTable.Cols.ALARMNAME+
                " WHERE _id = "+alarm_id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            alarm.setIson(cursor.getInt(0));
            alarm.set_id(alarm_id);
        }
        return alarm;
    }



    public List<Item_AlarmList> getAllalarm(){
        List<Item_AlarmList> alarmLists = new ArrayList<Item_AlarmList>();
        String query = "SELECT "+
                AlarmTable.Cols.ALARMNAME+", "+AlarmTable.Cols.HOUR+", "+AlarmTable.Cols.MINUTE+", "+AlarmTable.Cols.ON+", "+AlarmTable.Cols.VIBRATE+
                " FROM "+AlarmTable.NAME;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Log.d("qweqwe",cursor.getString(0) + cursor.getString(1));

                Item_AlarmList item = new Item_AlarmList();
                item.setAlarm_name(cursor.getString(0));
                item.setHour(cursor.getInt(1));
                item.setMinute(cursor.getInt(2));
                item.setIson(cursor.getInt(3));
                item.setVibrate(cursor.getInt(4));
                alarmLists.add(item);
            }while (cursor.moveToNext());
        }

        return alarmLists;
    }

    public List<Item_AlarmList> getAlarmSetting(int alarm_id) {
        List<Item_AlarmList> alarmLists = new ArrayList<Item_AlarmList>();
        String query = "SELECT"+
            AlarmTable.Cols.AMPM+AlarmTable.Cols.HOUR+", "+AlarmTable.Cols.MINUTE+", "+
//                AlarmTable.Cols.DAYSOFTHEWEEK+", "+
            AlarmTable.Cols.ALARMNAME+", "+AlarmTable.Cols.VIBRATE+", "+
//                AlarmTable.Cols.SNOOZE+", "+AlarmTable.Cols.REPEAT+", "+
            AlarmTable.Cols.ON+ " FROM "+AlarmTable.NAME+" WHERE alarm_id = "+alarm_id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do{
                Log.d("qweqwe",cursor.getString(4));

                Item_AlarmList item = new Item_AlarmList();
                item.setAmpm(cursor.getInt(0));
                item.setHour(cursor.getInt(1));
                item.setMinute(cursor.getInt(2));
//                item.setDaysoftheweek(cursor.getString(3));
                item.setAlarm_name(cursor.getString(3));
                item.setVibrate(cursor.getInt(4));
//                item.setSnooze(cursor.getInt(4));
//                item.setRepeat(cursor.getInt(5));
                item.setIson(cursor.getInt(5));
                alarmLists.add(item);
            } while (cursor.moveToNext());
        }
        return alarmLists;
    }






//    public List<Item_AlarmList> deleteAlarm() {
//        List<Item_AlarmList> alarmLists = new ArrayList<Item_AlarmList>();
//        String query = "DELETE "+
//
//    }


    public void editBookMemo(Item_memo memolist){
        Log.w("메모 수정",String.valueOf(memolist.get_id())+memolist.getContent());
        db.execSQL("UPDATE "+BookMemo.NAME+" SET "+BookMemo.Cols.PAGESTART+" = "+ memolist.getPage_start()+", "+
                BookMemo.Cols.PAGEEND+" = "+memolist.getPage_end()+", "+
                BookMemo.Cols.CONTENT+" = \""+memolist.getContent()+"\", "+
                BookMemo.Cols.DATA+" = \""+memolist.getDate()+"\""+
                " WHERE _id = "+memolist.get_id());
    }

    public List<Item_memo> getMemos(){
        List<Item_memo> memoList = new ArrayList<Item_memo>();
        String query = "SELECT "+
                BookMemo.Cols.BOOKID+", "+BookMemo.Cols.PAGESTART+", "+BookMemo.Cols.PAGEEND+", "+BookMemo.Cols.CONTENT+", "+BookMemo.Cols.DATA+", _id"+
                " FROM "+BookMemo.NAME;
        //String query = "SELECT * FROM "+BookMemo.NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("qweqwe",cursor.getString(0) + cursor.getString(1));
                Item_memo item = new Item_memo();
                item.setBoook_id(cursor.getInt(0));
                item.setPage_start(cursor.getInt(1));
                item.setPage_end(cursor.getInt(2));
                item.setContent(cursor.getString(3));
                item.setDate(cursor.getString(4));
                item.set_id(cursor.getInt(5));
                memoList.add(item);

            } while (cursor.moveToNext());
        }

        return memoList;
    }

    public Item_memo getEditMemo(int memo_id){
        Item_memo memo = new Item_memo();
        String query = "SELECT "+
                BookMemo.Cols.BOOKID+", "+BookMemo.Cols.PAGESTART+", "+BookMemo.Cols.PAGEEND+", "+BookMemo.Cols.CONTENT+", "+BookMemo.Cols.DATA+
                " FROM "+BookMemo.NAME+" WHERE _id = "+memo_id;
        //String query = "SELECT * FROM "+BookMemo.NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Log.d("qweqwe",cursor.getString(0) + cursor.getString(1));
            memo.setBoook_id(cursor.getInt(0));
            memo.setPage_start(cursor.getInt(1));
            memo.setPage_end(cursor.getInt(2));
            memo.setContent(cursor.getString(3));
            memo.setDate(cursor.getString(4));
            memo.set_id(memo_id);
        }
        return memo;
    }


    public List<Item_memo> getBookMemo(int book_id){
        List<Item_memo> memoList = new ArrayList<Item_memo>();
        String query = "SELECT "+
                BookMemo.Cols.BOOKID+", "+BookMemo.Cols.PAGESTART+", "+BookMemo.Cols.PAGEEND+", "+BookMemo.Cols.CONTENT+", "+BookMemo.Cols.DATA+", _id"+
                " FROM "+BookMemo.NAME+" WHERE book_id = "+book_id;
        //String query = "SELECT * FROM "+BookMemo.NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("qweqwe",cursor.getString(0) + cursor.getString(1));
                // String pagestart = new String(cursor.getString(0));
                //  String content = new String(cursor.getString(1));
                // String date = new String(cursor.getString(2));
                Item_memo item = new Item_memo();
                item.setBoook_id(cursor.getInt(0));
                item.setPage_start(cursor.getInt(1));
                item.setPage_end(cursor.getInt(2));
                //  item.setPage_start(Integer.getInteger(cursor.getString(0)));
                item.setContent(cursor.getString(3));
                item.setDate(cursor.getString(4));
                item.set_id(cursor.getInt(5));
                memoList.add(item);
                //memoList.add(folder_name);
            } while (cursor.moveToNext());
        }

        return memoList;
    }

    public List<String> getAllmemo(){
        List<String> folderList = new ArrayList<String>();
        String query = "SELECT DISTINCT "+Folder.Cols.FOLDERNAME+" FROM "+Folder.NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String folder_name = new String();
                folder_name = cursor.getString(0);
                folderList.add(folder_name);
            } while (cursor.moveToNext());
        }

        return folderList;
    }

    ////////////////Alarm
//    public void insertAlarm()

    public List<Item_book> getAllbookinfolder(String folder_name){

        List<Item_book> bookList = new ArrayList<Item_book>();
        String query = "select * from "+BookList.NAME+" WHERE "+BookList.NAME+"._id in (SELECT "+Folder.Cols.BOOKID+" FROM "+Folder.NAME +" WHERE "+Folder.Cols.FOLDERNAME+" = "+ "\""+folder_name+"\")";

//    public void addBook(Item_book book){
////        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(BookTable.Cols.BOOKNAME,book.getBook_name());
//        values.put(BookTable.Cols.BOOKURI,book.getBook_uri());
//        values.put(BookTable.Cols.CURRNETPAGE,book.getCurrent_page());
//        values.put(BookTable.Cols.TOTALPAGE,book.getTotal_page());
//        values.put(BookTable.Cols.STATE,book.getState());
//        values.put(BookTable.Cols.BOOKINFO,book.getBook_info());
//        values.put(BookTable.Cols.IMAGEID,book.getImage_id());
//
//        db.insert(BookTable.NAME,null,values);
//    }

//    public List<Item_book> getAllbooks(){
//        List<Item_book> bookList = new ArrayList<Item_book>();
//
//        String query = "SELECT * FROM "+BookTable.NAME;
//
////        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Item_book item_book = new Item_book();
//                item_book.set_id(Integer.valueOf(cursor.getString(0)));
//                item_book.setBook_name(cursor.getString(1));
//                item_book.setBook_uri(cursor.getString(2));
//                item_book.setCurrent_page(Integer.valueOf(cursor.getString(3)));
//                item_book.setTotal_page(Integer.valueOf(cursor.getString(4)));
//                item_book.setState(cursor.getString(5));
//                item_book.setBook_info(cursor.getString(6));
//                item_book.setImage_id(Integer.valueOf(cursor.getString(7)));
//                bookList.add(item_book);
//            } while (cursor.moveToNext());
//        }
//
//        return bookList;
//    }

//    public List<Item_image> getAllimages(){
//        List<Item_image> imageList = new ArrayList<Item_image>();
//
//        String query = "SELECT * FROM "+BookImage.NAME;
//
////        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Item_image item_image = new Item_image();
//                item_image.setImage_id(cursor.getInt(0));
//                byte[] bytes = cursor.getBlob(1);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                item_image.setImage_bitmap(bitmap);
//
//                imageList.add(item_image);
//            } while (cursor.moveToNext());
//        }
//
//        return imageList;
//    }

//    public int addImage(byte[] image){
//        ContentValues values = new ContentValues();
//        values.put(BookImage.Cols.IMAGE,     image);
//        return (int) db.insert( BookImage.NAME, null, values );
//    }
//
//    public int loadFirstimageid(){
//        String query = "SELECT * FROM "+BookImage.NAME;
//
//        int id=-1;
////        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                id = Integer.valueOf(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//        return id;
//    }


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Item_book item_book = new Item_book();
                item_book.set_id(cursor.getInt(0));
                item_book.setBook_name(cursor.getString(1));
                item_book.setBook_uri(cursor.getString(2));
                item_book.setCurrent_page(cursor.getInt(3));
                item_book.setTotal_page(cursor.getInt(4));
                item_book.setBook_info(cursor.getString(5));
                item_book.setBook_star(cursor.getInt(6));
                item_book.setFolder(cursor.getString(7));
                byte[] bytes = cursor.getBlob(8);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                item_book.setImage_bitmap(bitmap);
                bookList.add(item_book);
            } while (cursor.moveToNext());
        }
        return  bookList;
    }

    ////////////////book
    public void insertBook(Item_book item_book){
        ContentValues values = new ContentValues();
        values.put(BookList.Cols.BOOKNAME,item_book.getBook_name());
        values.put(BookList.Cols.BOOKURI,item_book.getBook_uri());
        values.put(BookList.Cols.CURRNETPAGE,item_book.getCurrent_page());
        values.put(BookList.Cols.TOTALPAGE,item_book.getTotal_page());
        values.put(BookList.Cols.BOOKINFO,item_book.getBook_info());
        values.put(BookList.Cols.STAR,item_book.getBook_star());
        values.put(BookList.Cols.FOLDER,item_book.getFolder());
        Bitmap bitmap = item_book.getImage_bitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] data = stream.toByteArray();
        values.put(BookList.Cols.BOOKIMAGE,data);
        db.insert(BookList.NAME,null,values);
    }


    public Item_book getBook(int id){
        String query = "SELECT * FROM "+BookList.NAME +" WHERE _id="+id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Item_book item_book = new Item_book();
            item_book.set_id(cursor.getInt(0));
            item_book.setBook_name(cursor.getString(1));
            item_book.setBook_uri(cursor.getString(2));
            item_book.setCurrent_page(cursor.getInt(3));
            item_book.setTotal_page(cursor.getInt(4));
            item_book.setBook_info(cursor.getString(5));
            item_book.setBook_star(cursor.getInt(6));
            item_book.setFolder(cursor.getString(7));
            byte[] bytes = cursor.getBlob(8);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            item_book.setImage_bitmap(bitmap);
            return item_book;
        }
        return null;
    }
}
