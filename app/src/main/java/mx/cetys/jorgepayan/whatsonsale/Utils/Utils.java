package mx.cetys.jorgepayan.whatsonsale.Utils;

import java.util.UUID;

/**
 * Created by fidel on 12/2/2017.
 */

public class Utils {
    public static String generateId(){
        return UUID.randomUUID().toString();
    }
}
