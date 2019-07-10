package dao.cache;

import java.util.HashMap;

/**
 * Created by Jary on 3/24/2017.
 */
public class cacheManeger {
    private HashMap<Integer,ActiveStudentsCache> cacheMap = new HashMap<Integer,ActiveStudentsCache>();

    public ActiveStudentsCache getCache(String sutedntID){
            if(cacheMap.containsKey(sutedntID))
                return cacheMap.get(sutedntID.hashCode()%40);
        return new ActiveStudentsCache();
    }
}
