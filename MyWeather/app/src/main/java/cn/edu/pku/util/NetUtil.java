package cn.edu.pku.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

/**
 * Created by Yue on 2016/9/26.
 */
public class NetUtil {
    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_MOBILE = 2;

    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORK_WIFI;
        }

        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if(state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORK_MOBILE;
        }
        return NETWORK_NONE;
    }

}
