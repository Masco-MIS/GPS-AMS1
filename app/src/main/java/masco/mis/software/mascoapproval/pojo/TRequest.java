package masco.mis.software.mascoapproval.pojo;

import java.util.List;

/**
 * Created by TahmidH_MIS on 11/30/2016.
 */

public class TRequest {
    public String sp;

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public List<TParam> getDict() {
        return dict;
    }

    public void setDict(List<TParam> dict) {
        this.dict = dict;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public List<TParam> dict;
    public String db;

}
