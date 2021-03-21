package com.project.devidea.modules.tagzone.tag;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class TagsResponseDto {

    private List<String> parent = new ArrayList<>();
    private Map<String, List<String>> children = new HashMap<>();
}