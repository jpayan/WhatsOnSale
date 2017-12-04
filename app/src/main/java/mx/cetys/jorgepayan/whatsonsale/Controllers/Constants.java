package mx.cetys.jorgepayan.whatsonsale.Controllers;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by fidel on 12/3/2017.
 */

public class Constants {
    private Constants() {
    }

    private static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";

    static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    static final HashMap<String, LatLng> BUSINESSES_LOCATION = new HashMap<>();

    static {

        BUSINESSES_LOCATION.put("SFO", new LatLng(37.621313, -122.378955));
        BUSINESSES_LOCATION.put("GOOGLE", new LatLng(37.422611,-122.0840577));
    }
}
