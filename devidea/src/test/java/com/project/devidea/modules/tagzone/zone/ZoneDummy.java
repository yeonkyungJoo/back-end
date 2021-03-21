package com.project.devidea.modules.tagzone.zone;

import java.util.List;

public class ZoneDummy {

    public static List<Zone> getZoneDummy() {
        Zone zone1 = Zone.builder().id(1L).city("경기도").province("수원시").build();
        Zone zone2 = Zone.builder().id(2L).city("경기도").province("강화군").build();
        Zone zone3 = Zone.builder().id(3L).city("경기도").province("옹진군").build();
        Zone zone4 = Zone.builder().id(4L).city("서울특별시").province("종로구").build();
        Zone zone5 = Zone.builder().id(5L).city("서울특별시").province("중구").build();
        return List.of(zone1, zone2, zone3, zone4, zone5);
    }
}
