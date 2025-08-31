package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.College;

public interface CreateCollegePort {
    College create(College college);
}
