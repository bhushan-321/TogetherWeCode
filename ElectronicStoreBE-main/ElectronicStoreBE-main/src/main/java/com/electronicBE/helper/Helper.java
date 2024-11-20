package com.electronicBE.helper;

import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.dtos.UserDto;
import com.electronicBE.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type){


        List<U> entity = page.getContent();
        List<V> dto = entity.stream().map(Object -> new ModelMapper().map(Object,type)).collect(Collectors.toList());

        PageableResponse<V> response= new PageableResponse<>();
        response.setContent(dto);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        return  response;

    }

    }




