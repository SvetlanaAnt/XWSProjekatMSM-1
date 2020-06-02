package agent.app.repository;

import agent.app.model.Ad;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    Page<Ad> findAllByDeleted(Boolean deleted, Pageable pageable);

    @Query("SELECT ad FROM Ad ad, CarCalendarTerm calendar WHERE calendar.startDate<=(?3) AND calendar.endDate>=(?4) " +
            "AND ad.id = calendar.ad.id AND ad.deleted=(?1) AND ad.location=(?2)")
    Page<Ad> findAllOrdinarySearch(Boolean deleted, String location, DateTime startDate, DateTime endDate, Pageable pageable);

}
