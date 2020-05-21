package services.app.adservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import services.app.adservice.model.Ad;

@Repository
public interface AdRepository  extends JpaRepository<Ad, Long> {

    Page<Ad> findAllByDeleted(Boolean deleted, Pageable pageable);
}
