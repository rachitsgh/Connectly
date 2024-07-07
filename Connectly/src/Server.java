import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ArrayList<ConectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server(){
        connections= new ArrayList<>();
        done=false;
    }
    public void broadcast(String message){
        for(ConectionHandler ch:connections){
            if(ch!=null){
                ch.sendMessage(message);
            }
        }
    }
    public void shutdown(){
        done=true;
        try{
            pool.shutdown();
            if(!server.isClosed()) {
                server.close();
            }
            for(ConectionHandler ch:connections){
                ch.shutdown();
            }
        }catch(IOException e){
            //ignore
        }
    }
    @Override
    public void run() {
        try {
            server = new ServerSocket(9999);
            pool= Executors.newCachedThreadPool();
            while(!done) {
                Socket client = server.accept();
                ConectionHandler handler = new ConectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            shutdown();

        }
    }
    class ConectionHandler implements Runnable{

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickName;

        public ConectionHandler(Socket client){
            this.client=client;
        }
        @Override
        public void run(){
            try{
                out=new PrintWriter(client.getOutputStream(),true);
                in=new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter a NickName");
                while (true) {
                    out.println("Please enter a NickName (less than 15 characters)");
                    nickName = in.readLine();

                    // Validate the nickname
                    if (nickName != null && !nickName.trim().isEmpty() && nickName.length() < 15) {
                        out.println("Welcome, " + nickName + "!");
                        break; // Exit the loop if a valid nickname is entered
                    } else {
                        out.println("Invalid nickname. Please try again.");
                    }
                }
                broadcast(nickName+"joined the chat");
                String message;
                while((message=in.readLine())!=null){
                    if(message.startsWith("/nick")) {
                        String[] messageSplit=message.split(" ",2);
                        if(messageSplit.length==2){
                            broadcast(nickName + "renamed themselves to"+messageSplit[1]);
                            System.out.println(nickName + "renamed themselves to"+messageSplit[1]);
                            nickName=messageSplit[1];
                            out.println("Succesfully changed nickname to :"+nickName);
                        }else{
                            out.println("no nickname provided");
                        }
                    } else if (message.startsWith("/quit")) {
                        broadcast(nickName+" left in the chat!");
                        shutdown();
                    }else{
                        broadcast(nickName+":"+message);
                    }
                }
            }catch(IOException e){
                shutdown();
            }
        }
        public void sendMessage(String message){
            out.println(message);
        }
        public void shutdown(){
            try{
                in.close();
                out.close();
                if(!client.isClosed()){
                    client.close();
                }
            }catch (IOException e){
                //ignore
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        Server server=new Server();
        server.run();
    }
}


