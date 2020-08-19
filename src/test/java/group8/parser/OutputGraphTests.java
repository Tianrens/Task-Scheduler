package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Node;
import group8.models.Processor;
import group8.models.ProcessorException;
import group8.models.Schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

public class OutputGraphTests {
    private IDOTFileWriter _dataParser;

    private Schedule _schedule;
    private Schedule _noEdgesSchedule;
    private Schedule _emptySchedule;

    private List<String> _expectedSchedule;
    private List<String> _expectedNoEdgesSchedule;
    private List<String> _expectedEmptySchedule;

    private final String _actualOutputSchedule = "actualOutputSchedule.dot";

    @Before
    public void setUpParser() throws ProcessorException {
        _dataParser = new DOTFileWriter();
//        _emptySchedule = new Schedule(1, null);
    }

    @Before
    public void generateSchedule() throws ProcessorException {

        Node a = new Node(2, "a");
        Node b = new Node(3, "b");
        Node c = new Node(2, "c");
        Node d = new Node(1, "d");
        Node e = new Node(10, "e");

        a.addDestination(b, 4);
        a.addDestination(c, 5);
        c.addDestination(e, 1);
        b.addDestination(d, 6);
        d.addDestination(e, 1);

        List<Node> topology = new ArrayList<>();
        topology.add(a);
        topology.add(c);
        topology.add(b);
        topology.add(d);
        topology.add(e);
//        _schedule = new Schedule(2, topology);
//
//        List<Processor> processors = _schedule.getProcessors();
//
//        _schedule.scheduleTask(processors.get(0), a, 0);
//        _schedule.scheduleTask(processors.get(0), c, 2);
//        _schedule.scheduleTask(processors.get(0), b, 4);
//        _schedule.scheduleTask(processors.get(0), d, 7);
//        _schedule.scheduleTask(processors.get(1), e, 9);
    }

    @Before
    public void generateNoEdgesSchedule() throws ProcessorException {
        Node a = new Node(2, "a");

        List<Node> topology = new ArrayList<>();
        topology.add(a);
//        _noEdgesSchedule = new Schedule(1, topology);
//
//        List<Processor> processors = _noEdgesSchedule.getProcessors();
//        _noEdgesSchedule.scheduleTask(processors.get(0), a, 0);
    }

    @Before
    public void setUpExpectedSchedules() {
        _expectedSchedule = new ArrayList<String>() {{
            add("digraph output_graph {");
            add("\ta [Weight=2, Start=0, Processor=1];");
            add("\tc [Weight=2, Start=2, Processor=1];");
            add("\tb [Weight=3, Start=4, Processor=1];");
            add("\td [Weight=1, Start=7, Processor=1];");
            add("\te [Weight=10, Start=9, Processor=2];");
            add("\ta->b [Weight=4];");
            add("\ta->c [Weight=5];");
            add("\tc->e [Weight=1];");
            add("\tb->d [Weight=6];");
            add("\td->e [Weight=1];");
            add("}");
        }};

        _expectedNoEdgesSchedule = new ArrayList<String>() {{
            add("digraph output_graph {");
            add("\ta [Weight=2, Start=0, Processor=1];");
            add("}");
        }};

        _expectedEmptySchedule = new ArrayList<String>() {{
            add("digraph output_graph {");
            add("}");
        }};
    }

    /**
     * Test a normal schedule with nodes and edges, scheduled on TWO processors.
     */
    @Test
    public void NormalScheduleTest() throws AppConfigException {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        AppConfig.getInstance().setOutputFile(new File(pathOfOutputTestSchedule));
        _dataParser.writeOutput(_schedule);

        checkExpectedVsActual(_expectedSchedule);

    }

    /**
     * Test a schedule with no edges, just a singular node.
     */
    @Test
    public void NoEdgesTest() throws AppConfigException {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        AppConfig.getInstance().setOutputFile(new File(pathOfOutputTestSchedule));
        _dataParser.writeOutput(_noEdgesSchedule);

        checkExpectedVsActual(_expectedNoEdgesSchedule);
    }

    /**
     * Test a schedule that has no edges nor nodes.
     */
    @Test
    public void EmptyScheduleTest() throws AppConfigException {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        AppConfig.getInstance().setOutputFile(new File(pathOfOutputTestSchedule));
        _dataParser.writeOutput(_emptySchedule);

        checkExpectedVsActual(_expectedEmptySchedule);
    }

    private void checkExpectedVsActual(List<String> expectedList) {
        List<String> actual = readActualOutputSchedule();

        for (String expected : expectedList) {
            if (actual.contains(expected)) {
                actual.remove(expected); // Remove from the list so that the string that got matched up does not get compared again.
                continue;
            } else {
                fail();
            }
        }

        if (! actual.isEmpty()) { // Fail if there are extra components in the actual output that are NOT expected.
            fail();
        }
    }

    private List<String> readActualOutputSchedule() {
        List<String> output = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.getClass().getResource(_actualOutputSchedule).getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
