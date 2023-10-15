package dev.lsrdev.authbasejwt.commons.generic.repositories;

import dev.lsrdev.authbasejwt.commons.generic.domain.GenericDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends GenericDomain<T>, I> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
}
