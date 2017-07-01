package vincentg;

import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import vincentg.app.BallonStatsReportApp;
import vincentg.dto.EnumObservatory.EnumDistance;
import vincentg.dto.EnumObservatory.EnumTemperature;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vincentg
 */
public class E2eTestCase {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();
    private static List<String> expectedResults = new ArrayList<>();

    @BeforeClass
    public static void makeInputFile() throws Exception {
        IOUtils.copy(
                E2eTestCase.class.getResourceAsStream("/input.txt"),
                new FileOutputStream(temporaryFolder.newFile("/input.txt").getPath()));
        expectedResults.add("FR|2.0 CELSIUS|6.0 CELSIUS|4.0 CELSIUS|0.004 KM");
        expectedResults.add("AU|1.0 CELSIUS|5.0 CELSIUS|3.0 CELSIUS|0.004 KM");
    }

    @Test
    public void e2eTestCase() throws Exception {

        BallonStatsReportApp app = new BallonStatsReportApp(
                temporaryFolder.getRoot().getPath()+"/input.txt",
                temporaryFolder.getRoot().getPath()+"/output.txt",
                true);

        app.run(EnumDistance.KM, EnumTemperature.CELSIUS);

        try (BufferedReader br = new BufferedReader(
                new FileReader(temporaryFolder.getRoot().getPath()+"/output.txt"))) {
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                Assert.assertTrue(expectedResults.contains(line));
                counter++;
            }
            Assert.assertEquals(2,counter);

        } catch (IOException ioe) {
            throw ioe;
        }
    }

    @AfterClass
    public static void cleanup() {
        temporaryFolder.delete();
    }
}
