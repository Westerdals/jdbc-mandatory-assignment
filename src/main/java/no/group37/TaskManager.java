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
        System.out.println("**********\n" +
                "Main Menu \n" +
                "**********\n"
                + "1. Members \n"
                + "2. Projects \n"
                + "3. Assign to a project\n"
                + "4. Exit program");
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
        else if (userChoice == 4) {
            System.exit(0);
        }
    }

    private static void assignMemberToProject(Scanner input) throws IOException, SQLException {
        PGSimpleDataSource dataSource = getDataSource();
        ProjectDao projectDao = new ProjectDao(dataSource);
        MemberToProjectDao memberToProjectDao = new MemberToProjectDao(dataSource);
        MemberToProject memberToProject = new MemberToProject();
        MemberDao memberDao = new MemberDao(dataSource);


        System.out.println("Which project you want to start?");
        System.out.println(projectDao.listToString(projectDao.listAll()));

        System.out.println("Enter number of the project: ");
        int userChoiceProject = Integer.parseInt(input.nextLine());
        System.out.println("Project : " + projectDao.listSelectedProjects(userChoiceProject));


        System.out.println("List of members: \n" + memberDao.listToString(memberDao.listAll()));

        int userChoiceMember = Integer.parseInt(input.nextLine());



        memberToProject.setProjectId(userChoiceProject);
        memberToProject.setMemberId(userChoiceMember);
        try {
            memberToProjectDao.insertMemberToProject(memberToProject);
            System.out.println("Members assigned to this project:\n"
                    + memberDao.listToString(memberDao.listAssignedMembers(userChoiceProject)));

        } catch (PSQLException e) {
           if (memberToProjectDao.selectUnique(userChoiceProject, userChoiceMember).size() > 0) {
               System.out.println("Member " + userChoiceMember + " is already assigned to project " + userChoiceProject);
           } else {
               System.out.println("Unhandled exception in function assignMemberToProject \n" + e);
           }
        }
    }

    private static void addNewProject(Scanner input) throws IOException, SQLException {
        PGSimpleDataSource dataSource = getDataSource();
        ProjectDao projectDao = new ProjectDao(dataSource);
        System.out.println(
                        "********\n" +
                        "Projects  \n" +
                        "********\n" +
                        "List of all projects:\n" +
                         projectDao.listToString(projectDao.listAll()));

        System.out.println("1. Add a new project\n" +
                           "2. Back to main menu");
        int userChoice = Integer.parseInt(input.nextLine());

        if (userChoice == 1) {
            System.out.println("Add a new project name:");
            String projectName = input.nextLine();
            if (projectName.isEmpty()) {
                System.out.println("You didnt write any name. Try again");
                addNewProject(input);}
                else{
                Project project = new Project();
                project.setProjectName(projectName);
                projectDao.insert(project);
                addNewProject(input);
                }
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
            System.out.println(memberDao.listToString(memberDao.listAll()));
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
}

