package scoremeassignment;
import scoremeassignment.algorithm.GreedyScheduler;
import scoremeassignment.model.Instance;
import scoremeassignment.model.Result;
import scoremeassignment.utils.JsonUtils;

public class AssignmentApplication {

	public static void main(String[] args) throws Exception {

		String input = "instance/input.json";
		String output = "outputs/result.json";

		Instance instance = JsonUtils.readInstance(input);

		GreedyScheduler scheduler = new GreedyScheduler();

		long start = System.currentTimeMillis();

		Result result = scheduler.schedule(instance);

		long end = System.currentTimeMillis();

		result.runtimeMs = end - start;

		JsonUtils.writeResult(output, result);

		System.out.println("Done.");
	}
}