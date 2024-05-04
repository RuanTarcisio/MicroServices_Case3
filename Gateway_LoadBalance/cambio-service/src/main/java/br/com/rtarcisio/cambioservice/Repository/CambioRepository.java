package br.com.rtarcisio.cambioservice.Repository;

import br.com.rtarcisio.cambioservice.domain.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CambioRepository extends JpaRepository<Cambio, Long> {

    Cambio findByFromAndTo(String from, String to);

}