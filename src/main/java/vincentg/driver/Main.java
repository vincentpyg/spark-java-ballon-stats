package vincentg.driver;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import vincentg.app.BallonStatsReportApp;
import vincentg.dto.EnumObservatory.EnumDistance;
import vincentg.dto.EnumObservatory.EnumTemperature;
import vincentg.util.DistanceConverter;
import vincentg.util.TemperatureConverter;

/**
 * @author vincentg
 */
public class Main {

    @Parameter(names = {"-i"}, required = true)
    private String inputFile;

    @Parameter(names = {"-o"}, required = true)
    private String outputFile;

    @Parameter(names = {"-d"}, converter = DistanceConverter.class, required = true)
    private EnumDistance distanceUnit;

    @Parameter(names = {"-t"}, converter = TemperatureConverter.class, required = true)
    private EnumTemperature tempUnit;

    @Parameter(names = {"--local"})
    private boolean local;

    public static void main(String... args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).args(args).build();
        main.run();
    }

    private void run() {
        try {
            BallonStatsReportApp ballonStatsReportApp = new BallonStatsReportApp(
                    inputFile,
                    outputFile,
                    local);

            ballonStatsReportApp.run(distanceUnit, tempUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
