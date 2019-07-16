package TDD;

class Money extends abstractMoney implements Expression{
	
	protected int amount;
	protected String currency;

	Money(int amount, String currency){
		this.amount = amount;
		this.currency = currency;
	}
	
	static Money dollar(int amount){
		return new Money(amount,"USD");
	}

	static Money franc(int amount){
		return new Money(amount,"CHF");
	}

	public int getAmount(){
		return amount;
	}
	public String getCurrency(){
		return currency;	
	} 

	@Override
	public boolean equals(Object object){
		Money money = (Money) object;
		return amount == money.getAmount() 
			&& currency.equals(money.getCurrency()) ;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int hashcode = 1;
		return hashcode = prime * hashcode + amount + currency.hashCode() ;
	}

	@Override
	public String toString(){
		return amount+" "+currency;
	}

	public Expression times(int multiplier){
		return new Money(amount * multiplier, currency);
	}

	public Expression plus(Expression addend){
		return new Sum(this, addend);
	}

	public Money reduce(Bank bank, String to){
		int rate = bank.rate(currency, to);
		return new Money(amount / rate, to);
	}
}	
