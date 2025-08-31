package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.response.CollegeResponse;

import java.util.List;

public interface FindAllCollegeUseCase {
    List<CollegeResponse> find();
}
