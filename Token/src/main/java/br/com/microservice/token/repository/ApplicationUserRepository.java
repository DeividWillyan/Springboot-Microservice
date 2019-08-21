package br.com.microservice.token.repository;

import br.com.microservice.token.model.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

    public ApplicationUser findByUsername(String username);

}