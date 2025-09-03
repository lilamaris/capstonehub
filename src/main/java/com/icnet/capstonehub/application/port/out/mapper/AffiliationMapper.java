package com.icnet.capstonehub.application.port.out.mapper;

import com.icnet.capstonehub.application.port.out.response.AffiliationResponse;
import com.icnet.capstonehub.domain.Affiliation;
import org.springframework.stereotype.Component;

@Component
public class AffiliationMapper {
    public AffiliationResponse toResponse(Affiliation domain) {
        if (domain == null) return null;
        return AffiliationResponse.builder()
                .effectiveStartDate(domain.effective().start())
                .effectiveEndDate(domain.effective().end())
                .build();
    }
}
