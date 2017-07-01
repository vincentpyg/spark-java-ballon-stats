package vincentg.util;

import com.beust.jcommander.IStringConverter;
import vincentg.dto.EnumObservatory.EnumTemperature;

/**
 * @author vincentg
 */
public class TemperatureConverter implements IStringConverter<EnumTemperature> {
    @Override
    public EnumTemperature convert(String value) {
        EnumTemperature tempUnit;
        try {
            tempUnit = EnumTemperature.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException iae) {
            tempUnit = EnumTemperature.CELSIUS;
        }
        return tempUnit;
    }
}
