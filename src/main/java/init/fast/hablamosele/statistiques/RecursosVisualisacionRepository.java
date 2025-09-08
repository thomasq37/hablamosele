package init.fast.hablamosele.statistiques;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursosVisualisacionRepository extends JpaRepository<RecursosVisualisacion, Long> {
}