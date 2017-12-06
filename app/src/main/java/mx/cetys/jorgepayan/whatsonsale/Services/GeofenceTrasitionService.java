package mx.cetys.jorgepayan.whatsonsale.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.CustomerHomeActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.MainActivity;
import mx.cetys.jorgepayan.whatsonsale.Models.BusinessLocation;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.LocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleViewHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;


public class GeofenceTrasitionService extends IntentService {

    private static final String TAG = GeofenceTrasitionService.class.getSimpleName();

    public static final int GEOFENCE_NOTIFICATION_ID = 0;

    public GeofenceTrasitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if ( geofencingEvent.hasError() ) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e( TAG, errorMsg );
            return;
        }

        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            ArrayList<Sale> validSales =
                Utils.getLocationValidSales(triggeringGeofences.get(0).getRequestId(),
                    CustomerHomeActivity.customerCategories);
            if (!validSales.isEmpty()) {
                SaleViewHelper saleViewHelper = new SaleViewHelper(CustomerHomeActivity.context);
                for(final Sale sale : validSales) {
                    saleViewHelper.addSaleView(sale.getSaleId(),
                        CustomerHomeActivity.currentCustomer.getCustomerId());

                    CustomerHomeActivity.salesViewed.add(sale.getSaleId());

                    Utils.post("sale_view", CustomerHomeActivity.context, new HashMap<String, String>(){{
                        put("sale_id", sale.getSaleId());
                        put("customer_id", CustomerHomeActivity.currentCustomer.getCustomerId());
                    }});
                }
                String notificationMessage = getNotificationMessage(triggeringGeofences);
                sendNotification(notificationMessage);
            }
        }
    }


    private String getNotificationMessage(List<Geofence> triggeringGeofences) {
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for ( Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add( geofence.getRequestId() );
        }

        String message = "New sales nearby! Just go to the closest ";
        BusinessLocation location = Utils.locationHelper.getLocation(
            triggeringGeofences.get(0).getRequestId());
        message += location.getName() + "!";
        return message;
    }

    private void sendNotification( String msg ) {
        Log.i(TAG, "sendNotification: " + msg );

        Intent notificationIntent = CustomerHomeActivity.makeNotificationIntent(
                getApplicationContext(), msg
        );

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager notificatioMng =
                (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        notificatioMng.notify(
                GEOFENCE_NOTIFICATION_ID,
                createNotification(msg, notificationPendingIntent));

    }

    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_place_accent_24dp)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("Check it out!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }

    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }
}
