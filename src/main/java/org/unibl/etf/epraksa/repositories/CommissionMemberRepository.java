package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.epraksa.model.entities.CommissionMember;

public interface CommissionMemberRepository extends JpaRepository<CommissionMember,Long>
{
}
