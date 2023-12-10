import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque(){
        StudentArrayDeque<Integer> TestArray = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> StdArray = new ArrayDequeSolution<>();
        String log = "";
        for(int i = 0; i < 1000; i++){
            if(StdArray.isEmpty()){
                int addNumber = StdRandom.uniform(1000);
                int HeadOrTail = StdRandom.uniform(2);
                if(HeadOrTail == 0){
                    log = log +"addFirst(" + addNumber + ")\n";
                    StdArray.addFirst(addNumber);
                    TestArray.addFirst(addNumber);
                }
                else{
                    log = log +"addLast(" + addNumber + ")\n";
                    StdArray.addLast(addNumber);
                    TestArray.addLast(addNumber);
                }
            }
            else{
                int x = StdRandom.uniform(4);
                int addNumber = StdRandom.uniform(1000);
                Integer testremovenumber = 1;
                Integer stdremovenumber = 1;
                switch (x){
                    case 0:
                        log = log + "addFirst(" + addNumber + ")\n";
                        TestArray.addFirst(addNumber);
                        StdArray.addFirst(addNumber);
                        break;
                    case 1:
                        log = log + "addLast(" +addNumber + ")\n";
                        TestArray.addLast(addNumber);
                        StdArray.addLast(addNumber);
                        break;
                    case 2:
                        log = log + "removeFirst()\n";
                        testremovenumber = TestArray.removeFirst();
                        stdremovenumber = StdArray.removeFirst();
                        break;
                    case 3:
                        log = log + "removeLast()\n";
                        testremovenumber = TestArray.removeLast();
                        stdremovenumber = StdArray.removeLast();
                        break;
                    default:
                }
                assertEquals(log, stdremovenumber, testremovenumber);
            }
        }
    }
}
