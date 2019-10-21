package no.group37;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class TaskManager  {
    public static void main(String[] args) throws IOException, SQLException {
        Scanner input = new Scanner(System.in);

        mainMenuWindow(input);

    }

    private static void mainMenuWindow(Scanner input) throws IOException, SQLException {
        System.out.println("Main Menu \n"
                + "1. Add a new member \n"
                + "2. Add a new project \n"
                + "3. Assign to a project");
        int userChoice = Integer.parseInt(input.nextLine());

        if (userChoice == 1) {
            addNewMember(input);
        }
        else if (userChoice == 2) {
            addNewProject(input);
        }
        else if (userChoice == 3) {
            assignMemberToProject(input);
        }
    }

    private static void assignMemberToProject(Scanner input) throws IOException, SQLException {
        PGSimpleDataSource dataSource = getDataSource();
        ProjectDao projectDao = new ProjectDao(dataSource);
        MemberToProjectDao memberToProjectDao = new MemberToProjectDao(dataSource);
        MemberToProject memberToProject = new MemberToProject();
        MemberDao memberDao = new MemberDao(dataSource);


        System.out.println("Which project you want to start?");
        System.out.println(Arrays.toString((projectDao.listAll()).toArray())
                .replace("[", " ")
                .replace("]", "")
                .replace(",", ""));


        System.out.println("Enter number of the project: ");
        int userChoiceProject = Integer.parseInt(input.nextLine());
        System.out.println("Project : " + Arrays.toString((projectDao.listSelectedProjects(userChoiceProject)).toArray())
                .replace("[", " ")
                .replace("]", "")
                .replace(",", ""));


        System.out.println("List of members: \n " + Arrays.toString((memberDao.listAll()).toArray())
                .replace("[", " ")
                .replace("]", "")
                .replace(",", ""));
        System.out.println("Choose a member you want to assign: ");
        int userChoiceMember = Integer.parseInt(input.nextLine());



        memberToProject.setProjectId(userChoiceProject);
        memberToProject.setMemberId(userChoiceMember);
        try {
            memberToProjectDao.insert(memberToProject);
            System.out.println("Members assigned to this project: " + Arrays.toString((memberDao.listAssignedMembers(userChoiceProject)).toArray())
                    .replace("[", " ")
                    .replace("]", "")
                    .replace(",", ""));
        } catch (PSQLException e) {
           if (memberToProjectDao.selectUnique(userChoiceProject, userChoiceMember).size() > 0) {
               System.out.println("Member " + userChoiceMember + " is already assigned to project " + userChoiceProject);
           } else {
               System.out.println("Unhandled exception in function assignMemberToProject \n" + e);
           }
        }

        //not finished yet 
       /* System.out.println("1. Assign another member \n" + "2. Choose other project \n" + "3. Go back to main menu" );
        int userChoice = Integer.parseInt(input.nextLine());
        if (userChoice == 1) {
            addNewProject(input);
        }
        else if (userChoice == 2) {
            mainMenuWindow(input);
        } */

        //mainMenuWindow(input);
    }

    private static void addNewProject(Scanner input) throws IOException, SQLException {
        System.out.println("Add a new project name");
        String projectName = input.nextLine();

        if (projectName.isEmpty()) {
            System.out.println("You didnt write any name. Try again");
        } else {
            PGSimpleDataSource dataSource = getDataSource();

            Project project = new Project();
            project.setProjectName(projectName);
            ProjectDao projectDao = new ProjectDao(dataSource);
            projectDao.insert(project);
            System.out.println(Arrays.toString((projectDao.listAll()).toArray())
                    .replace("[", " ")
                    .replace("]", "")
                    .replace(",", ""));
        }

        System.out.println("1. Add another project \n" + "2. Go back to main menu");
        int userChoice = Integer.parseInt(input.nextLine());
        if (userChoice == 1) {
            addNewProject(input);
        }
        else if (userChoice == 2) {
            mainMenuWindow(input);
        }

    }

    private static void addNewMember(Scanner input) throws IOException, SQLException {
        System.out.println("Add a new member name:");
        String memberName = input.nextLine();
        System.out.println("Add a new member email:");
        String email = input.nextLine();

        if (memberName.isEmpty() || email.isEmpty()) {
            System.out.println("You didnt write correct name or email. Try again.");
        } else {

            PGSimpleDataSource dataSource = getDataSource();


            Member member = new Member();
            member.setMemberName(memberName);
            member.setMail(email);
            MemberDao memberDao = new MemberDao(dataSource);
            memberDao.insert(member);
            //System.out.println(printArray(memberDao.listAll()));
        }

        System.out.println("1. Add another member \n" + "2. Go back to main menu");
        int userChoice = Integer.parseInt(input.nextLine());
        if (userChoice == 1) {
            addNewMember(input);
        }
        else if (userChoice == 2) {
            mainMenuWindow(input);
        }


    }

    private static PGSimpleDataSource getDataSource() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("projectdb.properties"));

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        dataSource.setUser(properties.getProperty("dataSource.user"));

        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    //problem here. it wants to me print concrete class object. i dont know how to fix it yet :P
    private static String printArray(List<Class> list) {
        return Arrays.toString((list).toArray())
                .replace("[", " ")
                .replace("]", "")
                .replace(",", "");
    }
}

