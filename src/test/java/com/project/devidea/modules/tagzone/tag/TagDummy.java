package com.project.devidea.modules.tagzone.tag;

import java.util.List;

public class TagDummy {

    public static List<Tag> getInterestDummy() {
        Tag tag1 = Tag.builder().firstName("docker").secondName("도커").thirdName(null).build();
        Tag tag2 = Tag.builder().firstName("devops").secondName("데브옵스").thirdName(null).build();
        Tag tag3 = Tag.builder().firstName("os").secondName("운영체제").thirdName(null).build();
        Tag tag4 = Tag.builder().firstName("linux").secondName("리눅스").thirdName(null).build();
        Tag tag5 = Tag.builder().firstName("go").secondName("고").thirdName(null).build();
        return List.of(tag1 ,tag2 ,tag3 ,tag4, tag5);
    }
}
