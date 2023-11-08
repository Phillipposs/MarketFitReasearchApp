package com.example.filip.zdravahrana;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.snatik.polygon.Point;
import com.snatik.polygon.Polygon;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hu.supercluster.overpasser.library.output.OutputModificator;
import hu.supercluster.overpasser.library.output.OutputOrder;
import hu.supercluster.overpasser.library.output.OutputVerbosity;
import hu.supercluster.overpasser.library.query.OverpassQuery;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static hu.supercluster.overpasser.library.output.OutputFormat.JSON;

public class MainActivity extends AppCompatActivity {

    private static MainActivity mInstance;
    private static MapView mapView = null;
    private MyLocationNewOverlay mLocationOverlay;
    private ItemizedIconOverlay<OverlayItem> mMyLocationOverlay;
    private static Polygon medijana;
    private static Polygon palilula;
    private static Polygon pantelej;
    private static Polygon donjiKomren;
    private static Polygon gornjiKomren;
    private static Polygon medosevac;
    private static Polygon brankoBjegovic;
    private static Polygon ledenaStena;
    ArrayList<OverlayItem> items = new ArrayList<>();
    static String response;
    List<info.metadude.java.library.overpass.models.Element> elements = new ArrayList<>();
    private static ArrayList<ShopData> publicBuildings;
    private static ArrayList<ShopData> shops;
    private EditText searchShop;
    private boolean keyboardNeccessary = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medijana = Utils.makeMedijana();
        palilula = Utils.makePalilula();
        pantelej = Utils.makePantelej();
        donjiKomren = Utils.makeDonjiKomren();
        gornjiKomren = Utils.makeGorjiKomren();
        medosevac = Utils.makeMedosevac();
        brankoBjegovic = Utils.makeBrankoBjegovic();
        ledenaStena = Utils.makeLedenaStena();
        mInstance = this;
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        searchShop = findViewById(R.id.searchShop);
        searchShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!keyboardNeccessary) {
                    searchForShops(searchShop.getText().toString());
                    keyboardNeccessary = true;
                }

                else {
                    showSoftKeyboard();
                    searchShop.requestFocus();
                    keyboardNeccessary = false;
                }
            }
        });
        IMapController mapController = mapView.getController();
        mapController.setZoom(16);
        GeoPoint startPoint = new GeoPoint(0.0, 0.0);
        mapController.setCenter(startPoint);
        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        this.mLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(this.mLocationOverlay);
        OverlayItem newItem = new OverlayItem("Here", "You are here", startPoint);
        items.add(newItem);
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Fragment fragment;
                fragment = new ListFragment();
                MainActivity.getInstance().loadFragment(fragment);
                return false;
            }
        });
        // mapView.getOverlays().add(startMarker);
        final FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(this.getApplicationContext());
        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
        // feedReaderDbHelper.onCreate(db);
        // fillTables(feedReaderDbHelper, db);

        shops = feedReaderDbHelper.getShopData();
        // postavljanje markera shopova na mapi
        for (final ShopData shop : shops) {
            MarkerWithLabel marker = new MarkerWithLabel(mapView, shop.getName());
            marker.setPosition(new GeoPoint(shop.getLatitude(), shop.getLongitude()));
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            final ShopData shopPricess = feedReaderDbHelper.getShopPrices(shop.getId());

            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {

                    Fragment fragment;
                    fragment = new ListFragment(shop.getName(), shopPricess);
                    MainActivity.getInstance().loadFragment(fragment);
                    return false;
                }
            });
            // mapView.getOverlays().add(marker);
        }

        publicBuildings = feedReaderDbHelper.getPublicBuildings();

        db.close();

        goThroughMap();

    }

    private void searchForShops(String s) {

        if (shops == null || shops.isEmpty() || s.equals(""))
            return;
        for (ShopData shop : shops) {
            if (shop.getName().toLowerCase().contains(s.toLowerCase())) {
                MarkerWithLabel marker = new MarkerWithLabel(mapView, shop.getName());
                marker.setPosition(new GeoPoint(shop.getLatitude(), shop.getLongitude()));
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                mapView.getOverlays().add(marker);
            }

        }
    }

    private void fillTables(FeedReaderDbHelper feedReaderDbHelper, SQLiteDatabase db) {
        Utils.fillShopsTable(feedReaderDbHelper, db);
        Utils.fillPublicBuildingsTable(feedReaderDbHelper, db);
    }

    private void testPoint(MapView mapView, String label, GeoPoint position) {
        MarkerWithLabel marker = new MarkerWithLabel(mapView, label);
        marker.setPosition(position);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
    }

    private static void AddPolygon(GeoPoint point, double coefficient) {
        float pub = 0;
        float con = 0;
        float coeff = 0; // (float) (coefficient*0.375);
        GeoPoint pt1 = new GeoPoint(Utils.addDistanceToLatitude(point, -Constants.dy).getLatitude(),
                Utils.addDistanceToLongitude(point, -Constants.dx).getLongitude());
        GeoPoint pt2 = new GeoPoint(Utils.addDistanceToLatitude(pt1, Constants.dy * 2).getLatitude(),
                pt1.getLongitude());
        GeoPoint pt3 = new GeoPoint(Utils.addDistanceToLatitude(pt1, Constants.dy * 2).getLatitude(),
                Utils.addDistanceToLongitude(pt1, Constants.dx * 2).getLongitude());
        GeoPoint pt4 = new GeoPoint(pt1.getLatitude(),
                Utils.addDistanceToLongitude(pt1, Constants.dx * 2).getLongitude());
        GeoPoint pt5 = pt1;
        com.snatik.polygon.Point p1 = new Point(pt1.getLatitude(), pt1.getLongitude());
        com.snatik.polygon.Point p2 = new Point(pt2.getLatitude(), pt2.getLongitude());
        com.snatik.polygon.Point p3 = new Point(pt3.getLatitude(), pt3.getLongitude());
        com.snatik.polygon.Point p4 = new Point(pt4.getLatitude(), pt4.getLongitude());
        com.snatik.polygon.Point p5 = p1;

        Polygon polygonTest = new com.snatik.polygon.Polygon.Builder()
                .addVertex(p1)
                .addVertex(p2)
                .addVertex(p3)
                .addVertex(p4)
                .addVertex(p5)
                .close()
                .build();
        for (ShopData building : publicBuildings) {
            // if(polygonTest.contains(new
            // Point(building.getLatitude(),building.getLongitude())))

            if (building.getName().toLowerCase().contains("park")
                    && Utils.distanceKm(point, new GeoPoint(building.getLatitude(), building.getLongitude())) <= 0.3)
                pub += 1;
            else {
                if (Utils.distanceKm(point, new GeoPoint(building.getLatitude(), building.getLongitude())) <= 0.15
                        || polygonTest.contains(new Point(building.getLatitude(), building.getLongitude())))
                    pub += 1;
            }

        }
        for (ShopData data : shops) {
            // if(polygonTest.contains(new Point(data.getLatitude(),data.getLongitude())))
            if (Utils.distanceKm(point, new GeoPoint(data.getLatitude(), data.getLongitude())) <= 0.1
                    || polygonTest.contains(new Point(data.getLatitude(), data.getLongitude())))
                con += 1;
        }
        ArrayList<GeoPoint> bgRectPoints = new ArrayList<>();
        bgRectPoints.add(pt1);
        bgRectPoints.add(pt2);
        bgRectPoints.add(pt3);
        bgRectPoints.add(pt4);
        bgRectPoints.add(pt1);

        org.osmdroid.views.overlay.Polygon polygon = new org.osmdroid.views.overlay.Polygon();
        polygon.setStrokeColor(Color.TRANSPARENT);
        polygon.setPoints(bgRectPoints);
        coeff = (float) (coefficient * 0.3) + 0.7f * (pub / 3) - 0.25f * con;
        if (coeff > 0.2) {
            if (coeff > 0.5)
                polygon.setFillColor(Color.argb(0.4f, 0.0f, Math.abs(1 - coeff), 0.0f));
            else
                polygon.setFillColor(Color.argb(0.4f, Math.abs(1 - coeff / 2), Math.abs(1 - coeff / 2), 0.0f));

        } else {
            if (coeff >= 0.1)
                polygon.setFillColor(Color.argb(0.4f, Math.abs(1 - coeff), 0.0f, 0.0f));
            else
                polygon.setFillColor(Color.argb(0.5f, 1f, 0.0f, 0.0f));
        }
        mapView.getOverlays().add(polygon);
    }

    public static synchronized MainActivity getInstance() {
        return mInstance;
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView != null) {
            mapView.onPause();
        }
    }

    public String request(double southernLat, double westernLon, double northernLat, double easternLon) {
        String query = new OverpassQuery()
                .format(JSON)
                .timeout(30)
                .filterQuery()
                .node()
                .amenity("park")
                .tagNot("access", "private")
                /*
                 * .boundingBox(
                 * 47.48047027491862, 19.039797484874725,
                 * 47.51331674014172, 19.07404761761427
                 * )
                 */
                .boundingBox(southernLat, westernLon, northernLat, easternLon)
                .end()
                .output(OutputVerbosity.BODY, OutputModificator.CENTER, OutputOrder.QT, 100)
                .build();
        return query;
    }

    private String getOsmQuery(GeoPoint location) {
        return "[out:json];" +
                "way(around:15,"
                + location.getLatitude() + ","
                + location.getLongitude() +
                ")" +
                "[\"parks\"];out body geom;";
    }

    public static final MediaType JSONN = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSONN, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static GeoPoint goThroughMap() {
        int x = (int) (12 * (1 / (Constants.dx * 2)));
        int y = (int) (10 * (1 / (Constants.dx * 2)));
        GeoPoint newPoint = null;
        GeoPoint start = new GeoPoint(0.0, 0.0);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                newPoint = Utils.addDistanceToBoth(start, i * Constants.dx * 2, j * Constants.dy * 2);
                Point p = new Point(newPoint.getLatitude(), newPoint.getLongitude());
                AddPolygon(newPoint, Utils.getDensityCoefficient(municipality(p)));

            }
        }
        return newPoint;
    }

    public static String municipality(Point point) {
        if (medijana.contains(point))
            return "medijana";
        if (pantelej.contains(point))
            return "pantelej";
        if (palilula.contains(point)) {
            if (ledenaStena.contains(point))
                return "ledenaStena";
            return "palilula";
        }
        if (medosevac.contains(point))
            return "medosevac";
        if (brankoBjegovic.contains(point))
            return "brankoBjegovic";
        if (gornjiKomren.contains(point))
            return "gornjiKomren";
        if (donjiKomren.contains(point))
            return "donjiKomren";
        return "nis";
    }

    private void showSoftKeyboard() {
        if (this != null && findViewById(R.id.container) != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(findViewById(android.R.id.content), 0);
        }
    }
}
