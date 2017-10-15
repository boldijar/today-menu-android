package com.todaymenu.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.todaymenu.android.R;
import com.todaymenu.android.data.Prefs;
import com.todaymenu.android.data.models.Restaurant;

import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

/**
 * @author Paul
 * @since 2017.10.14
 */

public class RestaurantsMapFragment extends BaseRestaurantsFragment implements OnMapReadyCallback {

    @BindView(R.id.restaurants_map)
    MapView mMapView;
    private List<Restaurant> mRestaurants;
    private GoogleMap mMap;

    @Override
    int getLayoutId() {
        return R.layout.fragment_restaurants_map;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.e("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Timber.e("onViewCreated");

        MapsInitializer.initialize(getActivity());
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
        setMarkers(mMap, restaurants);

    }

    private void setMarkers(GoogleMap map, List<Restaurant> restaurants) {
        if (map == null) {
            return;
        }
        map.clear();
        MarkerOptions options = new MarkerOptions().position(
                new LatLng(Prefs.LastLatitude.getDouble(), Prefs.LastLongitude.getDouble()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location));
        map.addMarker(options);
        for (Restaurant restaurant : restaurants) {
            MarkerOptions markerOptions = new MarkerOptions().position(
                    new LatLng(restaurant.mLatitude, restaurant.mLongitude))
                    .title(restaurant.mName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_pin));
            map.addMarker(markerOptions);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng latLng = new LatLng(Prefs.LastLatitude.getDouble(), Prefs.LastLongitude.getDouble());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        googleMap.moveCamera(cameraUpdate);
        setMarkers(mMap, mRestaurants);
    }

    public void zoomToCurrentPosition() {
        if (mMap == null) {
            return;
        }
        LatLng latLng = new LatLng(Prefs.LastLatitude.getDouble(), Prefs.LastLongitude.getDouble());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        mMap = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }
}