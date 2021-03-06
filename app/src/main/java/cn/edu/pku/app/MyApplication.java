package cn.edu.pku.app;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.edu.pku.db.CityDB;
import cn.edu.pku.model.City;

/**
 * Created by Yue on 2016/9/27.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    /**
     * 访问城市数据库对象
     */
    private CityDB mCityDB;

    /**
     * 城市列表
     */
    private List<City> mCityList;

    /**
     * 城市Map
     */
    private Map<String, Object> cityMap;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mCityDB = openCityDB(); // 获取访问城市数据库的对象（/data/database1/city.db）
        initCityListAndMap();
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    /**
     * 获取访问城市数据库的对象
     * /data/database1/city.db
     * @return
     */
    private CityDB openCityDB() {
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "databases1"
                + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        if(!db.exists()) {
            String pathFolder = "/data"
                    + Environment.getDataDirectory().getAbsolutePath()
                    + File.separator + getPackageName()
                    + File.separator + "databases1"
                    + File.separator;
            File dirFirstFolder = new File(pathFolder);
            if(!dirFirstFolder.exists()) {
                dirFirstFolder.mkdir();
            }
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch(Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this, path);
    }

    /**
     * 多线程初始化城市列表
     */
    private void initCityListAndMap() {
        mCityList = new ArrayList<>();
        cityMap = new LinkedHashMap<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityListAndMap();
            }
        }).start();
    }

    /**
     * 由城市数据库对象获取城市列表
     * @return
     */
    private boolean prepareCityListAndMap() {
        mCityList = mCityDB.getAllCity();
        for(City city : mCityList) {
            cityMap.put(city.getCity(), city.getNumber());
        }
        return true;
    }

    public List<City> getmCityList() {
        return mCityList;
    }

    public Map<String, Object> getCityMap() {
        return cityMap;
    }

    public void setCityMap(Map<String, Object> cityMap) {
        this.cityMap = cityMap;
    }
}
