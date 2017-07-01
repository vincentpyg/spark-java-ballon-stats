package vincentg.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author vincentg
 */
@Data
public class ObservatoryKey implements Serializable, Comparable<ObservatoryKey> {
    private String observatory;
    private LocalDateTime timestamp;

    @Override
    public int compareTo(ObservatoryKey other) {
        int result = other.getObservatory().compareTo(observatory);

        if (result == 0) {
            result = other.getTimestamp().compareTo(timestamp);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ObservatoryKey that = (ObservatoryKey) o;

        System.out.println(observatory.equals(that.getObservatory()));
        return observatory.equals(that.getObservatory());
    }

    @Override
    public int hashCode() {
        return 31 * observatory.hashCode();
    }
}
