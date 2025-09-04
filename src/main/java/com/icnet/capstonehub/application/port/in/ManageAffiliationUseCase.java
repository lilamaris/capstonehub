package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.AssignAffiliationCommand;
import com.icnet.capstonehub.application.port.out.response.AffiliationResponse;

public interface ManageAffiliationUseCase {
    AffiliationResponse assignMajorToCollege(AssignAffiliationCommand command);
}
