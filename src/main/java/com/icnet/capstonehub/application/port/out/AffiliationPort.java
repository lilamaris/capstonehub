package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.Affiliation;
import com.icnet.capstonehub.domain.common.EffectivePeriod;

public interface AffiliationPort {
    Affiliation assignMajorToCollege(Long majorId, Long collegeId, EffectivePeriod effective);
    void rejectMajorToCollege(Long majorId);
}
