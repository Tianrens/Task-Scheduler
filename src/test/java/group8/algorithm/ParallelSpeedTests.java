package group8.algorithm;
import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import group8.parser.*;
import group8.scheduler.AStarScheduler;
import group8.scheduler.IScheduler;
import group8.scheduler.NotParallelAStar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;

public class ParallelSpeedTests {


    @Test
    public void randomSequentialTest() throws Exception{

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("Nodes_10_Random.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(1);

        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));

        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();
        Schedule schedule = scheduler.generateValidSchedule(graph);
        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutputToConsole(schedule, graph);


    }

    @Test
    public void randomParallelTest() throws Exception{

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("Nodes_10_Random.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(8);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();
        Schedule schedule = scheduler.generateValidSchedule(graph);
        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutputToConsole(schedule, graph);
    }

    @Test
    public void tenNodeOptimalSequentialTest() throws Exception{

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("tenNodeOptimal.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(1);

        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));

        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();
        Schedule schedule = scheduler.generateValidSchedule(graph);
        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutputToConsole(schedule, graph);


    }

    @Test
    public void tenNodeOptimalParallelTest() throws Exception{

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("tenNodeOptimal.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(8);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();
        Schedule schedule = scheduler.generateValidSchedule(graph);
        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutputToConsole(schedule, graph);
    }

}
