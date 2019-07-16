package TDD;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.* ;

public class AppTest {
   
    @Test 
    public void testEquality(){
	assertTrue(Money.dollar(5).equals(Money.dollar(5)));
	assertFalse(Money.dollar(5).equals(Money.dollar(6)));
	assertFalse(Money.franc(5).equals(Money.dollar(5)));
	
    }

    @Test
    public void testMultiplication(){
	    Money five = Money.dollar(5);
	    assertEquals(Money.dollar(10), five.times(2));
	    assertEquals(Money.dollar(15), five.times(3));
    }

    @Test
    public void testFrancMultiplication(){
	Money five = Money.franc(5);
	assertEquals(Money.franc(10), five.times(2));
	assertEquals(Money.franc(15), five.times(3));	
    }

    @Test
    public void testCurrency(){
    	assertEquals("USD", Money.dollar(1).getCurrency());
	assertEquals("CHF", Money.franc(1).getCurrency());
    }

    @Test
    public void testHashCode(){

	Map<Money, Integer> testMap = new HashMap<Money, Integer>();
	testMap.put(Money.dollar(10),1);
	testMap.put(Money.dollar(10),1);
	assertTrue(testMap.size()==1);
	testMap.put(Money.franc(10),1);
	assertTrue(testMap.size()==2);
    }

    @Test
    public void testSumArguments(){
	Money five = Money.dollar(5);
	Expression result = five.plus(five) ;
	Sum sum = (Sum) result;
	assertEquals(five, sum.augend);
	assertEquals(five, sum.addend);
    }


    private Bank bank;

    @Before
    public void setRate(){
    	bank = new Bank();
	bank.addRate("CHF","USD",2);
	bank.addRate("USD","CHF",3);
    }

    @Test
    public void testReduceSum(){
	
	Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
	//Bank bank = new Bank();
	Money result = bank.reduce(sum, "USD");
	assertEquals(Money.dollar(7), result);
    }

    @Test
    public void testReduceMoney(){
	
	//Bank bank = new Bank();
	Money result = bank.reduce(Money.dollar(1), "USD");
	assertEquals(Money.dollar(1), result);
    }

    @Test
    public void testReduceMoneyDifferentCurrency(){

	//Bank bank = new Bank();
	bank.addRate("CHF", "USD", 2);
	Money result = bank.reduce(Money.franc(2), "USD");
	assertEquals(Money.dollar(1),result);	
    }

    @Test
    public void testIdentityRate(){

	assertEquals(1, /*new Bank()*/bank.rate("USD","USD"));	
    }

    @Test
    public void testMixedAddition(){

	Expression fiveBucks= Money.dollar(5);
	Expression tenFrancs= Money.franc(10);
	/*Bank bank = new Bank();
	bank.addRate("CHF", "USD", 2);*/
	Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
	assertEquals(Money.dollar(10), result);
    }

    @Test
    public void testSumPlusMoney(){

	Expression fiveBucks = Money.dollar(5);
	Expression tenFrancs = Money.franc(10);
	/*Bank bank = new Bank();
	bank.addRate("CHF", "USD", 2);
	bank.addRate("USD", "CHF", 3);*/
	Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
	Money result = bank.reduce(sum, "USD");
	assertEquals(Money.dollar(15),result);

	Expression newSum = new Sum(result, tenFrancs);
	Money newResult = bank.reduce(newSum, "CHF");
	assertEquals(Money.franc(15),newResult);
    }

    @Test
    public void testSumTimes(){

	Expression fiveBucks = Money.dollar(5);
	Expression tenFrancs = Money.franc(10);
	/*Bank bank = new Bank();
	bank.addRate("CHF", "USD", 2);*/
	Expression sum = new Sum(fiveBucks, tenFrancs).times(5);
	Money result = bank.reduce(sum,"USD");
	assertEquals(Money.dollar(50), result);
    }
}
