package web.rainlf.Util;

import java.util.UUID;

/**
 * Created by Administrator on 2017/7/1.
 */
public class UuidUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }
}
