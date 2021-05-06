import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;

//create employee objects for records in csv file
public class Employee {
    
    String Name;
    float Rate;
    float Hour;
    String Role;
    float Salary;

    NumberFormat converter = NumberFormat.getCurrencyInstance(Locale.US); //formatter for US currency 
    
    //getters and setters for employee object
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public float getRate() {
        return Rate;
    }

    public void setRate(float Rate) {
        this.Rate = Rate;
    }

    public float getHour() {
        return Hour;
    }

    public void setHour(float Hour) {
        this.Hour = Hour;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public float getSalary() {
        return Salary;
    }

    public void setSalary(float Salary) {
        this.Salary = Salary;
    }


    @Override
    public String toString() {
        return "\n" + "Employee { " +
                "firstName = '" + Name + '\'' +
                ", Rate = " + Rate +
                ", Hour = " + Hour +
                ", Role = '" + Role + '\'' +
                ", Salary = " + converter.format(Salary) + //convert salary attribute into dollars
                '}' + "\n";
    }

    //convert records from csv file into a list
    public static List<Employee> open(String fileName) {
        List<Employee> employees;
        BufferedReader bufferReader = null;
        try
        {            
            bufferReader = new BufferedReader(new FileReader(fileName)); //initializing bufferreader object    
            String line;
            employees = new ArrayList<Employee>(); //creates a list of employee objects
            
            while ((line = bufferReader.readLine()) != null) { //read csv file line be line 

                String[] tokens = line.split(",");
                Employee employee = new Employee(); //new employee object for every line

                //set object attributes from columns in csv file
                employee.setName(tokens[0]);
                employee.setRate(Float.valueOf(tokens[1]));
                employee.setHour(Float.valueOf(tokens[2]));
                employee.setRole(tokens[3]);
                employees.add(employee);
                
            }
            
            bufferReader.close(); //close buffer to prevent memory leak
            return employees;
            
            
        } 
        catch(Exception e) 
        {
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }
    
        return new ArrayList<Employee>();
    }

    //main method to calculate employee salaries 
    public static void main(String[] args){
        try
        {
            String calc_type = args[0]; //variable used to determine which calculation type we want
            float rate;
            float hoursWorked;
            String role;
            float weeks = 52; //52 weeks in a year
    
            List<Employee> employees = open(args[1]); //enter file path for second command line argument ex. C:\\Users\\user\\Desktop\\filename.csv
    
            for(Employee employee : employees) {;
    
                hoursWorked = employee.getHour();
                rate = employee.getRate();
                role = employee.getRole();
    
                if (role.equals("Fulltime"))
                {
                    employee.setSalary(hoursWorked * rate * weeks); 
                    if (employee.getSalary() > 50000)
                    {
                        employee.setSalary(50000); //salary capped at 50000
                    }
                }
    
                else if (role.equals("Contract"))
                {
                    employee.setSalary((hoursWorked * rate) * weeks + 10000); //base salary for contractors is 10000
                }
    
                else 
                {
                    employee.setSalary(hoursWorked * rate * weeks); //part timers
                }
               
            }
            
            if (calc_type.equals("calc_a"))  //calculate total salary in dollars
            {
                System.out.println("\n" + employees + "\n");
            }
    
            else if (calc_type.equals("calc_b")) //calculate totaly salary in dollars and sort by role 
            {
                employees.sort(Comparator.comparing(Employee::getRole)); //sort
                System.out.println("\n" + employees + "\n");
            }
            else    
            {
                System.out.println("please enter calc_a or calc_b as first command line argument");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error running program: try entering calc_a or calc_b as first argument and the file path as second argument: " + e.getMessage());
        }
   
    }

}

