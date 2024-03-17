import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabase {
    private Connection connection;
    private ArrayList<Employee>employees=new ArrayList<>();
    private Employee employee;

    public EmployeeDatabase(Connection connection) {
        this.connection=connection;
    }

    public void addEmployee(Employee employee){
        try(PreparedStatement statement=connection.prepareStatement(
                "INSERT INTO emp_data(ID,Name,Position,Salary) VALUES (?,?,?,?)")){
            statement.setInt(1,employee.getId());
            statement.setString(2,employee.getName());
            statement.setString(3,employee.getPosition());
            statement.setDouble(4,employee.getSalary());
            statement.executeUpdate();
            System.out.println("Employee Added Successfully");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("not Updated");
        }
    }
    public void removeEmployee(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM emp_data WHERE ID=?")) {
            statement.setInt(1, id);
            int effectedRows = statement.executeUpdate();
            if (effectedRows > 0) {
                System.out.println("Data Removed Successfully");
            } else
                System.out.println("Employee Not found with ID" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Employee displayEmployeeDetails(int id){
        try(PreparedStatement statement=connection.prepareStatement("SELECT * FROM emp_data WHERE ID=?")) {
            statement.setInt(1, id);
            ResultSet resultSet=statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Employee ID: "+resultSet.getInt("ID"));
                    System.out.println("Name: " + resultSet.getString("Name"));
                    System.out.println("Position: " + resultSet.getString("Position"));
                    System.out.println("Salary: " + resultSet.getDouble("Salary"));
                }
                else
                    System.out.println("Employee not found with ID: "+id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public List<Employee>getAllEmployee(){
        List<Employee> allEmployee=new ArrayList<>();
        try(PreparedStatement statement=connection.prepareStatement("SELECT * FROM emp_data")){
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("ID");
                String name=resultSet.getString("Name");
                String position=resultSet.getString("Position");
                double salary=resultSet.getDouble("Salary");
                allEmployee.add(new Employee(id,name,position,salary));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return allEmployee;
    }
    public void updateData(Employee employee){
        try(PreparedStatement statement=connection.prepareStatement("UPDATE emp_data SET ID=?,Name=?, Position=?, Salary=? WHERE ID=?")){
         statement.setInt(1, employee.getId());
         statement.setString(2, employee.getName());
         statement.setString(3, employee.getPosition());
         statement.setDouble(4, employee.getSalary());
         statement.setInt(5, employee.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Failed to update employee with ID: " + employee.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }


    public void totalSalary(){
        double totalSalary=0;
        try(PreparedStatement preparedStatement=connection.prepareStatement("SELECT Salary FROM emp_data")){

        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            totalSalary+=resultSet.getDouble("Salary");
        }
        System.out.println("Total Salary of all employees: $" +totalSalary);
    }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
