package com.project.devidea.modules.tagzone.zone;

import java.util.List;

public class ZoneDummy {

    public static List<Zone> getZoneDummy() {
        Zone zone1 = Zone.builder().city("경기도").province("수원시").build();
        Zone zone2 = Zone.builder().city("경기도").province("강화군").build();
        Zone zone3 = Zone.builder().city("경기도").province("옹진군").build();
        Zone zone4 = Zone.builder().city("서울특별시").province("종로구").build();
        Zone zone5 = Zone.builder().city("서울특별시").province("중구").build();
        return List.of(zone1, zone2, zone3, zone4, zone5);
    }
}
