import java.io.BufferedReader;
import java.io.FileReader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class exchange {
    LinkedList <String> call = new LinkedList <>();
    static HashMap <String, LinkedList <String>> hashMap = new HashMap <>();
    Object lock = new Object();


    public static void main(String[] args) throws Exception {

        LinkedList <String> call = new LinkedList <>();

        BufferedReader inputFile = new BufferedReader(new FileReader("calls.txt"));
        String line = inputFile.readLine();
        while (line != null) {
            String[] people = line.split(",");

//            String[] people = newString.split(" ");
//            System.out.println(people.length);
            for (int i = 0; i < people.length; i++) {
                String newString = people[i].replaceAll("[^a-zA-Z]", " ");
                String nospace = newString.trim();
                people[i] = nospace;
//                System.out.print(people[i] + " ");
            }
//            System.out.println();
            String key = people[0];
            call.add(key);

//            LinkedList <Customer> value = new LinkedList <>();

            LinkedList <String> value = new LinkedList <>();

            for (int i = 1; i < people.length; i++) {
//                Customer cur = new Customer(people[i], false);
                value.add(people[i]);

            }

            hashMap.put(key, value);
            line = inputFile.readLine();
        }


        System.out.println("** Calls to be made **");
        for (int i = 0; i < call.size(); i++) {
            System.out.print(call.get(i) + ": [");
            LinkedList <String> value = hashMap.get(call.get(i));
            for (int j = 0; j < value.size() - 1; j++) {
                System.out.print(value.get(j) + ",");
            }
            System.out.println(value.get(value.size() - 1) + "]");

        }

        System.out.println();

//        CheckFinish[] checkFinishes=new CheckFinish[call.size()];
        BlockingQueue <Customer> blockingQueue = new LinkedBlockingQueue <>();
        HashMap <String, BlockingQueue <Customer>> customer_infor = new HashMap <>();

        for (String caller : hashMap.keySet()) {
            BlockingQueue <Customer> callee = new LinkedBlockingQueue <>();
            customer_infor.put(caller, callee);
            ChildThread childThread = new ChildThread(caller, blockingQueue, hashMap, customer_infor);
            childThread.start();
        }

//        CheckFinish[] checkFinishes=new CheckFinish[call.size()];
//        for(int i=0;i<call.size();i++){
//            String name=call.get(i);
//            String[] value ={call.get(i),"1"};
////            String[] value=call.get(i).split(" ");
//            checkFinishes[i]=new CheckFinish(name,value);
//        }




        boolean flag = false;

        while (!flag) {
            while (!blockingQueue.isEmpty()) {
                Customer customer = blockingQueue.poll();
                if (!(customer.sendOrReciever.equals("end"))) {
                    int len=customer.timeStamp.length();
                    System.out.println(customer.callee + " received " + customer.sendOrReciever +
                            " message from " + customer.caller + " [" + customer.timeStamp.substring(len-6) + "]");
                } else {
                    System.out.println("\nProcess " + customer.caller + " has received no calls for 1 second, ending...");
                }
            }
            long time = System.currentTimeMillis();
            while (blockingQueue.isEmpty()) {
                long how_long = System.currentTimeMillis() - time;
                if (how_long > 1500) {
                    flag = true;
                    break;
                }
            }
        }

        System.out.println("\nMaster has received no replies for 1.5 seconds, ending...");
    }


}

class Customer {

    String caller;
    String callee;
//    boolean receive;
    String sendOrReciever;
    String timeStamp;

//    Customer(){}
//    Customer(String caller,String callee, boolean receive,String timeStamp){
//        this.caller=caller;
//        this.callee=callee;
//        this.receive=receive;
//        this.timeStamp=timeStamp;
//
//    }

    Customer(String caller, String callee, String sendOrReciever, String timeStamp) {
        this.caller = caller;
        this.callee = callee;
        this.sendOrReciever = sendOrReciever;
        this.timeStamp = timeStamp;
    }

}

class ChildThread extends Thread {

    Thread t;
    String caller;
//    String[] people;
    BlockingQueue<Customer> blockingQueue;
    HashMap<String, LinkedList<String>> caller_map;
    HashMap<String, BlockingQueue<Customer>> customer_infor;

    public ChildThread(String caller,BlockingQueue<Customer> blockingQueue,
                HashMap<String, LinkedList<String>> calls_map,
                HashMap<String, BlockingQueue<Customer>> customer_infor) {

        this.caller = caller;
        this.blockingQueue = blockingQueue;
        this.caller_map = calls_map;
        this.customer_infor = customer_infor;
    }


    public void run() {
        t=new Thread(this);
        t.setName(this.caller);
        send();
        receive();
    }

    public void send() {
        LinkedList<String> callees = caller_map.get(caller);
        for (String callee : callees) {
//            try {
//                    Customer cur = value.get(i);
//                    if (!cur.receive) {
//                        long time = System.currentTimeMillis();
//                        LinkedList <Customer> customers = hashMap.get(cur.name);
//                        Customer curName = new Customer(key, true, time);
//                        customers.add(curName);
//
//                    }
            try {
                Thread.sleep((long)(Math.random() * 100));
                String timeStamp = String.valueOf(System.currentTimeMillis());
                Customer Customer = new Customer(caller, callee, "intro", timeStamp);
                customer_infor.get(callee).add(Customer);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    public void receive() {
        boolean flag= false;
        while(!flag) {
            while(!customer_infor.get(caller).isEmpty()) {
                try {
                    Thread.sleep((long)(Math.random() * 100));
                    Customer Customer = customer_infor.get(caller).poll();

                    blockingQueue.add(Customer);

                    if ((Customer.sendOrReciever.equals("intro"))) {
                        Customer reply =  new Customer(caller, Customer.caller, "reply", Customer.timeStamp);
                        customer_infor.get(Customer.caller).add(reply);
                    }
//                } else {
//                    Customer reply =  new Customer(caller, Customer.caller, "reply", Customer.timeStamp);
//
//                }
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }

            long time = System.currentTimeMillis();
            while(customer_infor.get(caller).isEmpty()) {
                long how_long = System.currentTimeMillis() - time;
                if(how_long > 1000) {
                    flag = true;
                    break;
                }
            }
        }
        Customer finish = new Customer(caller, "", "end", "");
        blockingQueue.add(finish);
    }
}
//class CheckFinish implements Runnable{
//
//    Thread t;
//    String[] people;
//    public CheckFinish(String name,String[] people){
//        t=new Thread(this);
//        t.setName(name);
//        t.start();
//        this.people=people;
//
//    }
//
//    public void run(){
//        int len=people.length-1;
//
//        while(len>=0){
//            try {
//                t.sleep(1000);
//
//                if(people[len].equals("1")){
////                    System.out.println();
//                }else {
//
//                    System.out.println("Process "+ Thread.currentThread().getName()+" has received no calls for 1 second, ending...");
////                    System.out.println();
//
//                }
////                System.out.println();
//                len--;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//
//        }
//
//
//    }
//}