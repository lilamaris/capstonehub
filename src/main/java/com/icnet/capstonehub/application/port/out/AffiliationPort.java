package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.Affiliation;

public interface AffiliationPort {
    Affiliation assignMajorToCollege(Affiliation inDomain);
    void rejectMajorToCollege(Affiliation inDomain);
}
