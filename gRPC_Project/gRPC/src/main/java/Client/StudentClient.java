package Client;

import com.project.grpc.Student;
import com.project.grpc.studentGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class StudentClient {
    public static void main(String[] args) {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 1234).usePlaintext().build();
        studentGrpc.studentBlockingStub studentBlockingStub = new studentGrpc.studentBlockingStub(managedChannel);


        boolean isAuthenicated = false;
        Scanner scanner = new Scanner(System.in);
        while(!isAuthenicated){
            System.out.print("Enter user name: ");
            String name = scanner.next();
            System.out.print("Enter password: ");
            String pass = scanner.next();
            Student.LoginRequest loginRequest = Student.LoginRequest.newBuilder().
                    setUserName(name).setPassword(pass).build();
            Student.Response response = studentBlockingStub.login(loginRequest);
            if(response.getResponseCode() == 200) {
                isAuthenicated = true;
            }
            System.out.println(response.getResponse());
        }


        System.out.print("Enter registration ID: ");
        long ID = scanner.nextLong();
        System.out.print("Enter student name: ");
        String name = scanner.next();

        Student.RegisterRequest registerRequest = Student.RegisterRequest.newBuilder().
                setRegistrationID(ID).setStudentName(name).build();
        Student.RegResponse regResponse = studentBlockingStub.register(registerRequest);
        System.out.println(regResponse.getResponse());
    }
}
