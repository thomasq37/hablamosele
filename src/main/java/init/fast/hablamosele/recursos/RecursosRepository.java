package init.fast.hablamosele.recursos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursosRepository extends JpaRepository<Recursos, Long> {
    @Query(value = "SELECT ri.infografia FROM recursos_infografias ri WHERE ri.recursos_id = :id",
            nativeQuery = true)
    List<String> findInfografiasByRecursosId(@Param("id") Long id);

}
