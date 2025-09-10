package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.AffiliationLineageAmendCommand;
import com.icnet.capstonehub.application.port.in.command.AffiliationLineageAppendCommand;
import com.icnet.capstonehub.application.port.in.command.AffiliationLineageInitialCommand;
import com.icnet.capstonehub.application.port.in.result.AffiliationResult;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AffiliationUseCase {
    List<AffiliationResult> getAffiliationLineage(UUID lineageSharedId);
    List<AffiliationResult> getAffiliationLineage(UUID lineageSharedId, LocalDate txAt);
    AffiliationResult initialAffiliationLineage(AffiliationLineageInitialCommand command);
    AffiliationResult appendAffiliationLineage(AffiliationLineageAppendCommand command);
    AffiliationResult amendAffiliationLineage(AffiliationLineageAmendCommand command);
}
