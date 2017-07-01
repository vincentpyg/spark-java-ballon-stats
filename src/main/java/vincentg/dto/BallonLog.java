package vincentg.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author vincentg
 */
@Getter
@Builder
public class BallonLog implements Serializable {
    private String observer;
    private LocalDateTime timestamp;
    private double temp;
    private int locationX;
    private int locationY;
}
