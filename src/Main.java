import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static EmployeeDatabase employeeDatabase;

    public static void main(String[] args) {
       try {
           Connection connection = DriverManager.getConnection
                   ("jdbc:mysql://localhost:3306/employeedatabase", "root", "admin");
           employeeDatabase=new EmployeeDatabase(connection);
       }
       catch (SQLException e)
       {
           e.printStackTrace();
           System.out.println("not connected");
       }
        Scanner scanner=new Scanner(System.in);
       while (true){
           System.out.println("Hello....Welcome>>>");
           System.out.println("1. Add Employee");
           System.out.println("2. Remove Employee");
           System.out.println("3. View Employee Details");
           System.out.println("4. View all Employees");
           System.out.println("5. Update Employee Record");
           System.out.println("6. Display Total Salary");
           System.out.println("7. Exit");
           System.out.print("Please Enter your Option to Continue: ");
           int choice=scanner.nextInt();
           switch (choice){
               case 1:
               {
                   System.out.print("Enter ID: ");
                   int id=scanner.nextInt();
                   scanner.nextLine();
                   System.out.print("Enter Employee Name: ");
                   String name=scanner.nextLine();
                   System.out.print("Enter Employee Position: ");
                   String position=scanner.nextLine();
                   System.out.print("Enter Employee Salary: ");
                   double salary=scanner.nextDouble();
                   Employee employee=new Employee(id,name,position,salary);
                   employeeDatabase.addEmployee(employee);
                   break;
               }
               case 2:
               {
                   System.out.print("Enter Employee ID to remove: ");
                   int removeId=scanner.nextInt();
                   employeeDatabase.removeEmployee(removeId);
                   System.out.println("Employee Data Removed Successfully");
                   break;
               }
               case 3:
               {
                   System.out.print("Enter Id to view Details: ");
                   int viewId=scanner.nextInt();
                   employeeDatabase.displayEmployeeDetails(viewId);
                   break;
               }
               case 4:
               {
                   System.out.println("All Employees..");
                   System.out.println("ID   Name    Position    Salary");
                   for (Employee employee:employeeDatabase.getAllEmployee())
                   {
                       System.out.println(+employee.getId()+     "   "+employee.getName()+"   "    +employee.getPosition()+    " "   +employee.getSalary());
                   }
                   break;
               }
               case 5:
               {
                   System.out.print("Enter ID of employee to Update Data:");
                   int updateID=scanner.nextInt();
                   scanner.nextLine();
                   Employee updateData=employeeDatabase.displayEmployeeDetails(updateID);
                   if (updateData!=null){
                       System.out.println("Enter new ID (press Enter to skip):");
                       String newID=scanner.nextLine();
                       System.out.print("Enter new name (press Enter to skip): ");
                       String newName = scanner.nextLine();
                       System.out.print("Enter new position (press Enter to skip): ");
                       String newPosition = scanner.nextLine();
                       System.out.print("Enter new salary (press Enter to skip): ");
                       String newSalarystr = scanner.nextLine();
                       if (!newID.isEmpty()){
                           int newID2= Integer.parseInt(newID);
                           updateData.setId(newID2);
                       }
                       if (!newName.isEmpty()) {
                           updateData.setName(newName);
                       }
                       if (!newPosition.isEmpty()) {
                           updateData.setPosition(newPosition);
                       }
                       if (!newSalarystr.isEmpty()) {
                           double newSalary = Double.parseDouble(newSalarystr);
                           updateData.setSalary(newSalary);
                       }
                       employeeDatabase.updateData(updateData);
                       System.out.println("Data Updated Successfully");
                   }
                   else{
                       System.out.println("Employee not found with ID:"  +updateID);
                   }

                   break;
               }
               case 6:
               {
                   employeeDatabase.totalSalary();
                   break;
               }
               case 7:
               {
                   System.out.println("Exiting the Employee Management System..! Good Bye...");
                   System.exit(0);
               }
               default:
                   System.out.println("Invalid Choice: Please Enter a Valid Option");

           }

       }
       }
}