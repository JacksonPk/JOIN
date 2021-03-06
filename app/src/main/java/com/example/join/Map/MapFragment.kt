package com.example.join.Map


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.join.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

//Jinseo Test.


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var rootview: View

    lateinit var onConnectedListener : OnConnectedListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootview = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = rootview.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()          //onResume()도 연결시켜야 정상적으로 맵이 보임.

        mapView.getMapAsync(this)

        return rootview
    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(37.340201, 126.734721)))  //초기설정.KPU G동
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
        //  RecordMapActivity의 onConnect() 실행하여 맵 객체를 불러와 MapFragment에서 맵을 띄울 수 있게 해줌.
        onConnectedListener.onConnect(mMap)

        mMap.setOnMapLoadedCallback{
            try{
                mMap.isMyLocationEnabled=true   //현재위치표시 및 현재위치로 돌아올 수 있는 버튼 생성.
            }catch (e: SecurityException){}
        }
    }

    interface OnConnectedListener{
        fun onConnect(mMap: GoogleMap)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        // context -> RecordMapActivity. 프래그먼트에서 context 호출시 acitivity의 context가 불러짐.
        onConnectedListener = context as OnConnectedListener
    }
}

