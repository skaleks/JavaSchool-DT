package com.magenta.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MyModelMapper extends ModelMapper {

    public <T, K> List<T> mapToList(List<K> baseList, Class<T> type){

        List<T> resultList = new ArrayList<>();
        baseList.forEach(element -> resultList.add(super.map(element, type)));
        return resultList;
    }

    public <T, K> Set<T> mapToSet(Set<K> baseList, Class<T> type){

        Set<T> resultList = new HashSet<>();
        baseList.forEach(element -> resultList.add(super.map(element, type)));
        return resultList;
    }
}
