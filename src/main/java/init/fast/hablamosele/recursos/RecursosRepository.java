package init.fast.hablamosele.recursos;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursosRepository extends JpaRepository<Recursos, Long> {
    @Modifying
    @Transactional
    @Query("update Recursos r set r.nbVisualisaciones = coalesce(r.nbVisualisaciones, 0) + 1 where r.id = :id")
    int incrementViews(@Param("id") Long id);
}
