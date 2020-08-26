package group8.algorithm;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;
import org.junit.Before;

public class IdenticalNodeDuplicationTests {
    private Graph _graph;
    private IStateExpander _stateExpander;
    private Schedule _schedule;

    @Before
    public void generateGraph() throws AppConfigException {
        Node a = new Node(2, "a");
        Node b = new Node(3, "b");
        Node c = new Node(4, "c");
        Node d = new Node(3, "d");
        Node e = new Node(1, "e");

        a.addDestination(b, 2);
        a.addDestination(c, 2);
        a.addDestination(d, 2);
        b.addDestination(e, 2);
        c.addDestination(e, 2);
        d.addDestination(e, 2);

        _graph = new Graph();
        _graph.addNode(a);
        _graph.addNode(b);
        _graph.addNode(c);
        _graph.addNode(d);
        _graph.addNode(e);

        _stateExpander = new ELSModelStateExpander(_graph);
    }

    @Before
    public void generateSchedule() throws AppConfigException {
        AppConfig.getInstance().setNumProcessors(3);
        _schedule = new Schedule();

        _schedule.scheduleTask("a", 0, 0);
        _schedule.scheduleTask("c", 2, 0);
        _schedule.scheduleTask("b", 4, 0);
        _schedule.scheduleTask("d", 7, 0);
        _schedule.scheduleTask("e", 9, 1);
    }
}
