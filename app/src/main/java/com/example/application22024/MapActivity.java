package com.example.application22024;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.KakaoMapSdk;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapType;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.MapViewInfo;
import com.kakao.vectormap.shape.MapPoints;


public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private LatLng selectedPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        Button selectLocationButton = findViewById(R.id.selectLocationButton);
        KakaoMapSdk.init(this, "5f301f0b001c8c2a2c63473d48b78772");
        // Lắng nghe sự kiện chạm vào bản đồ
        mapView.start(new MapLifeCycleCallback() {
            @Override
            public void onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
            }

            @Override
            public void onMapError(Exception error) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                Log.e("MapError", "Lỗi khi tải bản đồ: " + error.getMessage());
                Toast.makeText(MapActivity.this, "MapError" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(KakaoMap kakaoMap) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
                // Map is ready, set up the map properties and listeners
                kakaoMap.setOnMapClickListener((kakaoMap1, latLng, pointF, poi) -> {
                    // Update the selected point when the map is clicked
                    selectedPoint = latLng;
                });
            }

//            @Override
//            public LatLng getPosition() {
//                // 지도 시작 시 위치 좌표를 설정
//             return LatLng.from(37.406960, 127.115587);
//            }

            @Override
            public int getZoomLevel() {
                // 지도 시작 시 확대/축소 줌 레벨 설정
                return 15;
            }

            @Override
            public MapViewInfo getMapViewInfo() {
                // 지도 시작 시 App 및 MapType 설정
                return MapViewInfo.from(String.valueOf(MapType.NORMAL));
            }

            @Override
            public String getViewName() {
                // KakaoMap 의 고유한 이름을 설정
                return "MyFirstMap";
            }

            @Override
            public boolean isVisible() {
                // 지도 시작 시 visible 여부를 설정
                return true;
            }

            @Override
            public String getTag() {
                // KakaoMap 의 tag 을 설정
                return "FirstMapTag";
            }

        });


        selectLocationButton.setOnClickListener(v -> {
            if (selectedPoint != null) {
                // Lấy tọa độ
                double latitude = selectedPoint.latitude;
                double longitude = selectedPoint.longitude;

                // Trả về dữ liệu
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_location", latitude + ", " + longitude);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.pause();
        }
    }


}
