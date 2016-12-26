package masco.mis.software.mascoapproval.DB;

import android.content.ContentValues;

import masco.mis.software.mascoapproval.Tapplication;

/**
 * Created by TahmidH_MIS on 12/24/2016.
 */

public class TDbHelper {
    public static ContentValues setLocatioContent(String lat,String lon,double time,String deviceid,double emp)
    {
        ContentValues values = new ContentValues();
        values.put(LocationContract.LocationEntry.COLUMN_NAME_LAT, lat);
        values.put(LocationContract.LocationEntry.COLUMN_NAME_LON, lon);
        values.put(LocationContract.LocationEntry.COLUMN_NAME_TIME, time);
        values.put(LocationContract.LocationEntry.COLUMN_NAME_EMPID, emp);
        values.put(LocationContract.LocationEntry.COLUMN_NAME_DEVICEID, deviceid);
        return values;
    }
    public static long insertLocation(ContentValues values)
    {
        return Tapplication.WriteDB().insert(LocationContract.LocationEntry.TABLE_NAME,null,values);
    }
}
