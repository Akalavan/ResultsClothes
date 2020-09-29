package app.unload;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeDAO {
    public static List<Employee> listEmployees() {
        List<Employee> list = new ArrayList<Employee>();

        Employee e1 = new Employee("EO1", "Tom", 200.0, 1, null);
        Employee e2 = new Employee("EO2", "Jerry", 100.2, 2, null);
        Employee e3 = new Employee("EO3", "Donald", 150.0, 2, null);
        list.addAll(Arrays.asList(e1, e2, e3));
        return list;
    }
}
