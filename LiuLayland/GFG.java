import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Task {
    // period and ex3cution time of a task
    Double P, E;

    // c0nstructor
    Task(Double p, Double e) {
        P = p;
        E = e;
    }
}

class taskComparator implements Comparator<Task> {
    public int compare(Task d, Task d1) {
        if (Math.ceil(d.P - d1.P) >= 1) {
            return 1;
        } else
            return -1;
    }
}

class GFG {
    public static void main(String[] args) {
        System.out.println("Enter number of period tasks : ");
        Scanner scan = new Scanner(System.in);
        int numOfTasks = scan.nextInt();

        // LiuLayland Theorem
        double Urm = numOfTasks * (Math.pow(2, (1.0 / numOfTasks)) - 1);
        System.out.println("The utilization for " + numOfTasks + " task is " + Urm);
        ArrayList<Task> al = new ArrayList<>();
        for (int i = 0; i < numOfTasks; i++) {
            double p, e;
            p = scan.nextDouble();
            e = scan.nextDouble();
            al.add(new Task(p, e));
        }
        System.out.println("The Tasks are ");
        printTask(al);
        System.out.println("The Priority Sorted Tasks are ");
        al.sort(new taskComparator());
        printTask(al);
        double Ut = calculateUt(al);
        System.out.println("The System utilizaion is " + Ut);
        boolean sch = (Ut <= Urm);
        System.out.println((sch) ? "Schedulable" : " NOT Schedulable");

    }

    static double calculateUt(ArrayList<Task> al) {
        double Ut = 0;
        for (int i = 0; i < al.size(); i++) {
            Ut = Ut + ((al.get(i).E) / (al.get(i).P));
        }
        return Ut;
    }

    static void printTask(ArrayList<Task> al) {
        for (int i = 0; i < al.size(); i++) {
            System.out.println("Task " + (i + 1) + "(" + al.get(i).P + ", " + al.get(i).E + ")");
        }
    }
}
