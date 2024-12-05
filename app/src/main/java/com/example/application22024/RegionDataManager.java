package com.example.application22024;

import java.util.ArrayList;
import java.util.HashMap;

public class RegionDataManager {

    public static HashMap<String, ArrayList<String>> getRegionsData() {
        HashMap<String, ArrayList<String>> regions = new HashMap<>();

        ArrayList<String> all = new ArrayList<>();
        all.add("");
        regions.put("All", all);


        ArrayList<String> seoulAreas = new ArrayList<>();
        seoulAreas.add("All");
        seoulAreas.add("Jongno-gu");
        seoulAreas.add("Gangnam-gu");
        seoulAreas.add("Mapo-gu");
        seoulAreas.add("Dongdaemun-gu");
        regions.put("Seoul", seoulAreas);

        ArrayList<String> gyeonggiAreas = new ArrayList<>();
        gyeonggiAreas.add("Bundang-gu");
        gyeonggiAreas.add("Uijeongbu-si");
        gyeonggiAreas.add("Anyang-si");
        gyeonggiAreas.add("Suwon-si");
        regions.put("Gyeonggi", gyeonggiAreas);

        // Thêm các tỉnh/thành phố khác...
        return regions;
    }
}
