package vincentg.dto;

import lombok.Getter;

import java.io.Serializable;

import static vincentg.dto.EnumObservatory.*;

/**
 * @author vincentg
 */
@Getter
public class BallonStats implements Serializable {
    private double maxTemp;
    private double minTemp;
    private double tempAgg;
    private int observations;

    private String observatory;
    private EnumObservatory observatoryUnit;

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public void setObservatory(String observatory) {
        this.observatory = observatory;
        try {
            this.observatoryUnit = EnumObservatory.valueOf(observatory);
        } catch (IllegalArgumentException iae) {
            this.observatoryUnit = OTHER;
        }
    }

    public void addTemp(double temp){
        if (maxTemp < temp) {
            maxTemp = temp;
            if (minTemp == 0) {
                minTemp = maxTemp;
            }
        } else if (minTemp > temp){
            minTemp = temp;
        }
        tempAgg += temp;
    }

    public void addLocation(int x, int y) {
        if (startX == 0 && endX == 0) {
            startX = x;
            startY = y;
        }
        endX = x;
        endY = y;
    }

    public double getMinTempInUnit(EnumTemperature tempUnit) {
        return getTempInUnit(tempUnit, minTemp);
    }

    public double getMaxTempInUnit(EnumTemperature tempUnit) {
        return getTempInUnit(tempUnit, maxTemp);
    }


    private double getTempInUnit(EnumTemperature tempUnit, double temp) {
        if (tempUnit == null)
            tempUnit = observatoryUnit.getTemperature();

        double tempInUnit = temp;
        switch (tempUnit) {
            case KELVIN:
                tempInUnit = temp + 273.15;
                break;
            case FAHRENHEIT:
                tempInUnit = temp * 1.8 + 32;
                break;
        }
        return tempInUnit;
    }

    public double getTotalDistanceInUnit(EnumDistance distanceUnit) {
        double distanceInUnit = Math.sqrt((endX-startX)*(endY-startY));
        if (distanceUnit != null) {
            switch (distanceUnit) {
                case MILES:
                    distanceInUnit = distanceInUnit / 0.00062137;
                    break;
                case KM:
                    distanceInUnit = distanceInUnit / 1000;
                    break;
            }
        }
        return distanceInUnit;
    }

    public double getMeanTempInUnit(EnumTemperature tempUnit) {
        return getTempInUnit(tempUnit,tempAgg)/observations;
    }

    public void incrementObservations(){
        observations++;
    }

    public String createOutputEntry(EnumDistance distanceUnit, EnumTemperature tempUnit) {
        return observatory + "|"
                + getMinTempInUnit(tempUnit)+" "+tempUnit.name()+"|"
                + getMaxTempInUnit(tempUnit)+" "+tempUnit.name()+"|"
                + getMeanTempInUnit(tempUnit)+" "+tempUnit.name()+"|"
                + getTotalDistanceInUnit(distanceUnit)+" "+distanceUnit.name();
    }

}
