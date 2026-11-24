package mx.edu.iunis.eats.domain;

import java.io.Serializable;
import java.util.Objects;

public class ComboPlatilloId implements Serializable {

    private Long idCombo;
    private Long idPlatillo;

    public ComboPlatilloId() {
    }

    public ComboPlatilloId(Long idCombo, Long idPlatillo) {
        this.idCombo = idCombo;
        this.idPlatillo = idPlatillo;
    }

    public Long getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(Long idCombo) {
        this.idCombo = idCombo;
    }

    public Long getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(Long idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComboPlatilloId)) return false;

        ComboPlatilloId that = (ComboPlatilloId) o;

        return Objects.equals(idCombo, that.idCombo)
                && Objects.equals(idPlatillo, that.idPlatillo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCombo, idPlatillo);
    }
}