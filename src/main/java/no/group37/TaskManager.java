package no.group37;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class TaskManager {
    public static void main(String[] args) throws IOException, SQLException {
        Scanner input = new Scanner(System.in);

        System.out.println("Main Menu \n" + "1. Add a new member \n" + "2. Add a new project \n" +
                "3. Assign to a project");
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
        System.out.println(projectDao.listAll());
        System.out.println("Enter number of the project: ");
        int userChoiceProject = Integer.parseInt(input.nextLine());
        System.out.println("Project : " + projectDao.listSelectedProjects(userChoiceProject));
        System.out.println("List of members: " + memberDao.listAll());
        System.out.println("Choose a member you want to assign: ");
        int userChoiceMember = Integer.parseInt(input.nextLine());



        memberToProject.setProjectId(userChoiceProject);
        memberToProject.setMemberId(userChoiceMember);
        try {
            memberToProjectDao.insert(memberToProject);
            System.out.println("Members assigned to this project: " + memberDao.listAssignedMembers(userChoiceProject));
        } catch (PSQLException e) {
           if (memberToProjectDao.selectUnique(userChoiceProject, userChoiceMember).size() > 0) {
               System.out.println("Member " + userChoiceMember + " is already assigned to project " + );
           } else {
               System.out.println("Unhandled exception in function assignMemberToProject \n" + e);
           }
        }
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
            // im gonna turn this line below into a method :P so its not that long. the code below does that we dont print brackets of array anymore so it looks nicer
            System.out.println(Arrays.toString((memberDao.listAll()).toArray())
                    .replace("[", " ")
                    .replace("]", "")
                    .replace(",", ""));


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

