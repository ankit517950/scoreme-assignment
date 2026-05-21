package scoremeassignment.algorithm;
import scoremeassignment.model.Instance;
import scoremeassignment.model.Result;

import java.util.*;

public class GreedyScheduler {

    public Result schedule(Instance instance) {

        Result result = new Result();

        Map<String, Integer> assignment = new HashMap<>();

        int n = instance.tasks.size();

        double[][] used = new double[instance.K][4];

        List<Integer> order = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            order.add(i);
        }

        order.sort((a, b) -> Double.compare(
                instance.weights.get(b),
                instance.weights.get(a)
        ));

        for (int idx : order) {

            boolean placed = false;

            int start = instance.windows.get(idx).get(0);
            int end = instance.windows.get(idx).get(1);

            for (int slot = start; slot <= end; slot++) {

                if (conflictFree(idx, slot, assignment, instance)
                        && capacityValid(idx, slot, used, instance)) {

                    assignment.put(instance.tasks.get(idx), slot);

                    for (int d = 0; d < 4; d++) {
                        used[slot][d] += instance.resources.get(idx).get(d);
                    }

                    placed = true;
                    break;
                }
            }

            if (!placed) {
                result.feasible = false;
                result.violationReason =
                        "Could not place task: "
                                + instance.tasks.get(idx);

                return result;
            }
        }

        result.assignment = assignment;
        result.feasible = true;
        result.penalty = computePenalty(instance, assignment);

        return result;
    }

    private boolean conflictFree(
            int task,
            int slot,
            Map<String, Integer> assignment,
            Instance instance) {

        for (List<Integer> edge : instance.conflicts) {

            int u = edge.get(0);
            int v = edge.get(1);

            if (u == task) {

                String other = instance.tasks.get(v);

                if (assignment.containsKey(other)
                        && assignment.get(other) == slot) {
                    return false;
                }
            }

            if (v == task) {

                String other = instance.tasks.get(u);

                if (assignment.containsKey(other)
                        && assignment.get(other) == slot) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean capacityValid(
            int task,
            int slot,
            double[][] used,
            Instance instance) {

        for (int d = 0; d < 4; d++) {

            double need =
                    instance.resources.get(task).get(d);

            double cap =
                    instance.capacities.get(slot).get(d);

            if (used[slot][d] + need > cap) {
                return false;
            }
        }

        return true;
    }

    private double computePenalty(
            Instance instance,
            Map<String, Integer> assignment) {

        double total = 0;

        for (int i = 0; i < instance.tasks.size(); i++) {

            String task = instance.tasks.get(i);

            int slot = assignment.get(task);

            total += instance.weights.get(i) * (slot + 1);
        }

        return total;
    }
}