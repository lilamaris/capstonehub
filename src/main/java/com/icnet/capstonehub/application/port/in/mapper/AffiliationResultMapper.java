package com.icnet.capstonehub.application.port.in.mapper;

import com.icnet.capstonehub.application.port.in.result.AffiliationResult;
import com.icnet.capstonehub.domain.model.Affiliation;
import org.springframework.stereotype.Component;

@Component
public class AffiliationResultMapper {
    public static AffiliationResult toResult(Affiliation domain) {
        return AffiliationResult.builder()
                .id(domain.id().value())
                .college(CollegeResultMapper.toResult(domain.college()))
                .major(MajorResultMapper.toResult(domain.major()))
                .version(VersionResultMapper.toResult(domain.version()))
                .lineage(LineageResultMapper.toResult(domain.lineage()))
                .build();
    }
}
