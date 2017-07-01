package vincentg.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static vincentg.dto.EnumObservatory.EnumDistance.KM;
import static vincentg.dto.EnumObservatory.EnumDistance.M;
import static vincentg.dto.EnumObservatory.EnumDistance.MILES;
import static vincentg.dto.EnumObservatory.EnumTemperature.CELSIUS;
import static vincentg.dto.EnumObservatory.EnumTemperature.FAHRENHEIT;
import static vincentg.dto.EnumObservatory.EnumTemperature.KELVIN;

/**
 * Created by buubuoy on 1/07/2017.
 */
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public enum EnumObservatory {
    AU(KM, CELSIUS),
    US(MILES, FAHRENHEIT),
    FR(M, KELVIN),
    OTHER(KM, KELVIN);

    private EnumDistance distance;
    private EnumTemperature temperature;

    public enum EnumDistance {
        KM,
        MILES,
        M,
    }

    public enum EnumTemperature{
        KELVIN,
        CELSIUS,
        FAHRENHEIT
    }
}
