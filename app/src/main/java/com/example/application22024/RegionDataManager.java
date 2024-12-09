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
        seoulAreas.add("종로구");
        seoulAreas.add("강남구");
        seoulAreas.add("마포구");
        seoulAreas.add("동대문구");
        seoulAreas.add("강동구");
        seoulAreas.add("강북구");
        seoulAreas.add("강서구");
        seoulAreas.add("관악구");
        seoulAreas.add("광진구");
        seoulAreas.add("구로구");
        seoulAreas.add("금천구");
        seoulAreas.add("노원구");
        seoulAreas.add("도봉구");
        seoulAreas.add("동작구");
        seoulAreas.add("서대문구");
        seoulAreas.add("서초구");
        seoulAreas.add("성동구");
        seoulAreas.add("성북구");
        seoulAreas.add("송파구");
        seoulAreas.add("양천구");
        seoulAreas.add("영등포구");
        seoulAreas.add("용산구");
        seoulAreas.add("은평구");
        seoulAreas.add("중구");
        seoulAreas.add("중랑구");
        regions.put("서울", seoulAreas);

        ArrayList<String> incheonAreas = new ArrayList<>();
        incheonAreas.add("All");
        incheonAreas.add("중구");
        incheonAreas.add("연수구");
        incheonAreas.add("남동구");
        incheonAreas.add("부평구");
        incheonAreas.add("계양구");
        incheonAreas.add("서구");
        incheonAreas.add("강화군");
        incheonAreas.add("옹진군");
        regions.put("인천", incheonAreas);

        ArrayList<String> daeguAreas = new ArrayList<>();
        daeguAreas.add("All");
        daeguAreas.add("달서구");
        daeguAreas.add("대구구");
        daeguAreas.add("남구");
        daeguAreas.add("북구");
        daeguAreas.add("동구");
        daeguAreas.add("서구");
        daeguAreas.add("수성구");
        regions.put("대구", daeguAreas);

        ArrayList<String> daejeonAreas = new ArrayList<>();
        daejeonAreas.add("All");
        daejeonAreas.add("동구");
        daejeonAreas.add("유성구");
        daejeonAreas.add("대덕구");
        daejeonAreas.add("서구");
        regions.put("대전", daejeonAreas);

        ArrayList<String> gwangjuAreas = new ArrayList<>();
        gwangjuAreas.add("All");
        gwangjuAreas.add("서구");
        gwangjuAreas.add("광산구");
        gwangjuAreas.add("북구");
        gwangjuAreas.add("동구");
        regions.put("광주", gwangjuAreas);

        ArrayList<String> ulsanAreas = new ArrayList<>();
        ulsanAreas.add("All");
        ulsanAreas.add("남구");
        ulsanAreas.add("북구");
        ulsanAreas.add("동구");
        ulsanAreas.add("중구");
        ulsanAreas.add("울주군");
        regions.put("울산", ulsanAreas);


        ArrayList<String> gyeonggiAreas = new ArrayList<>();
        gyeonggiAreas.add("All");
        gyeonggiAreas.add("수원시");
        gyeonggiAreas.add("성남시");
        gyeonggiAreas.add("용인시");
        gyeonggiAreas.add("고양시");
        gyeonggiAreas.add("평택시");
        gyeonggiAreas.add("안산시");
        gyeonggiAreas.add("화성시");
        gyeonggiAreas.add("부천시");
        gyeonggiAreas.add("남양주시");
        gyeonggiAreas.add("시흥시");
        gyeonggiAreas.add("광명시");
        gyeonggiAreas.add("안양시");
        gyeonggiAreas.add("광주시");
        gyeonggiAreas.add("파주시");
        gyeonggiAreas.add("하남시");
        gyeonggiAreas.add("김포시");
        gyeonggiAreas.add("오산시");
        gyeonggiAreas.add("군포시");
        gyeonggiAreas.add("의정부시");
        gyeonggiAreas.add("이천시");
        gyeonggiAreas.add("양주시");
        gyeonggiAreas.add("포천시");
        gyeonggiAreas.add("구리시");
        gyeonggiAreas.add("여주시");
        gyeonggiAreas.add("동두천시");
        gyeonggiAreas.add("연천군");
        gyeonggiAreas.add("가평군");
        gyeonggiAreas.add("양평군");
        regions.put("경기도", gyeonggiAreas);

        ArrayList<String> chungcheongbukdoAreas = new ArrayList<>();
        chungcheongbukdoAreas.add("All");
        chungcheongbukdoAreas.add("청주시");
        chungcheongbukdoAreas.add("제천시");
        chungcheongbukdoAreas.add("충주시");
        chungcheongbukdoAreas.add("진천군");
        chungcheongbukdoAreas.add("괴산군");
        chungcheongbukdoAreas.add("음성군");
        chungcheongbukdoAreas.add("단양군");
        chungcheongbukdoAreas.add("보은군");
        chungcheongbukdoAreas.add("옥천군");
        chungcheongbukdoAreas.add("영동군");
        chungcheongbukdoAreas.add("김천군");
        regions.put("충청북도", chungcheongbukdoAreas);

        ArrayList<String> chungcheongnamdoAreas = new ArrayList<>();
        chungcheongnamdoAreas.add("All");
        chungcheongnamdoAreas.add("천안시");
        chungcheongnamdoAreas.add("아산시");
        chungcheongnamdoAreas.add("홍성군");
        chungcheongnamdoAreas.add("서산시");
        chungcheongnamdoAreas.add("논산시");
        chungcheongnamdoAreas.add("보령시");
        chungcheongnamdoAreas.add("당진시");
        chungcheongnamdoAreas.add("금산군");
        chungcheongnamdoAreas.add("계룡시");
        chungcheongnamdoAreas.add("예산군");
        chungcheongnamdoAreas.add("청양군");
        chungcheongnamdoAreas.add("부여군");
        chungcheongnamdoAreas.add("서천군");
        chungcheongnamdoAreas.add("태안군");
        regions.put("충청남도", chungcheongnamdoAreas);

        ArrayList<String> jeollabukdoAreas = new ArrayList<>();
        jeollabukdoAreas.add("All");
        jeollabukdoAreas.add("전주시");
        jeollabukdoAreas.add("익산시");
        jeollabukdoAreas.add("군산시");
        jeollabukdoAreas.add("정읍시");
        jeollabukdoAreas.add("남원시");
        jeollabukdoAreas.add("김제시");
        jeollabukdoAreas.add("완주군");
        jeollabukdoAreas.add("진안군");
        jeollabukdoAreas.add("무주군");
        jeollabukdoAreas.add("장수군");
        jeollabukdoAreas.add("임실군");
        jeollabukdoAreas.add("순창군");
        jeollabukdoAreas.add("고창군");
        jeollabukdoAreas.add("부안군");
        regions.put("전라북도", jeollabukdoAreas);

        ArrayList<String> jeollanamdoAreas = new ArrayList<>();
        jeollanamdoAreas.add("All");
        jeollanamdoAreas.add("목포시");
        jeollanamdoAreas.add("여수시");
        jeollanamdoAreas.add("순천시");
        jeollanamdoAreas.add("광양시");
        jeollanamdoAreas.add("나주시");
        jeollanamdoAreas.add("무안군");
        jeollanamdoAreas.add("함평군");
        jeollanamdoAreas.add("영암군");
        jeollanamdoAreas.add("진도군");
        jeollanamdoAreas.add("완도군");
        jeollanamdoAreas.add("해남군");
        jeollanamdoAreas.add("장흥군");
        jeollanamdoAreas.add("강진군");
        jeollanamdoAreas.add("고흥군");
        jeollanamdoAreas.add("보성군");
        jeollanamdoAreas.add("화순군");
        regions.put("전라남도", jeollanamdoAreas);


        ArrayList<String> busanAreas = new ArrayList<>();
        busanAreas.add("All");
        busanAreas.add("해운대구");
        busanAreas.add("중구");
        busanAreas.add("수영구");
        busanAreas.add("부산진구");
        busanAreas.add("연제구");
        busanAreas.add("동래구");
        busanAreas.add("서구");
        busanAreas.add("남구");
        busanAreas.add("북구");
        busanAreas.add("금정구");
        busanAreas.add("사하구");
        busanAreas.add("강서구");
        busanAreas.add("연제구");
        busanAreas.add("동구");
        busanAreas.add("부산진구");
        regions.put("부산", busanAreas);

        ArrayList<String> gyeongsangbukdoAreas = new ArrayList<>();
        gyeongsangbukdoAreas.add("All");
        gyeongsangbukdoAreas.add("경주시");
        gyeongsangbukdoAreas.add("포항시");
        gyeongsangbukdoAreas.add("구미시");
        gyeongsangbukdoAreas.add("김천시");
        gyeongsangbukdoAreas.add("안동시");
        gyeongsangbukdoAreas.add("상주시");
        gyeongsangbukdoAreas.add("영천시");
        gyeongsangbukdoAreas.add("문경시");
        gyeongsangbukdoAreas.add("칠곡군");
        gyeongsangbukdoAreas.add("고령군");
        gyeongsangbukdoAreas.add("성주군");
        gyeongsangbukdoAreas.add("청도군");
        gyeongsangbukdoAreas.add("영양군");
        gyeongsangbukdoAreas.add("영덕군");
        gyeongsangbukdoAreas.add("봉화군");
        gyeongsangbukdoAreas.add("울진군");
        gyeongsangbukdoAreas.add("울릉군");
        regions.put("경상북도", gyeongsangbukdoAreas);

        ArrayList<String> gyeongsangnamdoAreas = new ArrayList<>();
        gyeongsangnamdoAreas.add("All");
        gyeongsangnamdoAreas.add("창원시");
        gyeongsangnamdoAreas.add("진주시");
        gyeongsangnamdoAreas.add("통영시");
        gyeongsangnamdoAreas.add("사천시");
        gyeongsangnamdoAreas.add("김해시");
        gyeongsangnamdoAreas.add("밀양시");
        gyeongsangnamdoAreas.add("거제시");
        gyeongsangnamdoAreas.add("양산시");
        gyeongsangnamdoAreas.add("하동군");
        gyeongsangnamdoAreas.add("남해군");
        gyeongsangnamdoAreas.add("함양군");
        gyeongsangnamdoAreas.add("산청군");
        gyeongsangnamdoAreas.add("거창군");
        gyeongsangnamdoAreas.add("합천군");
        regions.put("경상남도", gyeongsangnamdoAreas);

        ArrayList<String> gangwondoAreas = new ArrayList<>();
        gangwondoAreas.add("All");
        gangwondoAreas.add("춘천시");
        gangwondoAreas.add("원주시");
        gangwondoAreas.add("강릉시");
        gangwondoAreas.add("동해시");
        gangwondoAreas.add("태백시");
        gangwondoAreas.add("속초시");
        gangwondoAreas.add("삼척시");
        gangwondoAreas.add("홍천군");
        gangwondoAreas.add("횡성군");
        gangwondoAreas.add("영월군");
        gangwondoAreas.add("평창군");
        gangwondoAreas.add("정선군");
        gangwondoAreas.add("철원군");
        gangwondoAreas.add("화천군");
        gangwondoAreas.add("양구군");
        gangwondoAreas.add("인제군");
        gangwondoAreas.add("고성군");
        gangwondoAreas.add("양양군");
        regions.put("강원도", gangwondoAreas);

        ArrayList<String> jejudoAreas = new ArrayList<>();
        jejudoAreas.add("All");
        jejudoAreas.add("제주시");
        jejudoAreas.add("서귀포시");
        regions.put("제주", jejudoAreas);


        // Thêm các tỉnh/thành phố khác...
        return regions;
    }
}
